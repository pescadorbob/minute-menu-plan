package com.mp.domain.subscriptions

import com.mp.tools.UserTools

class SubscriptionController {

  static allowedMethods = [save: "POST", update: "POST", delete: "POST"]
  def subscriptionService

  def paymentConfirm = {
      render(view: 'confirm')
  }

  def paymentCancel = {
      render(view: 'cancel')
  }

  def paymentNotify = {
      subscriptionService.generateSubscription(params.long('custom'),params.long('item_number'))
      render "Notify"
  }

  def index = {
    redirect(action: "list", params: params)
  }
  def createSubscription = {
    String userId = params?.userId
    int productId = params?.int('productId')
    ProductOffering po = ProductOffering.get(productId)
    String item_name = po.name
    RecurringCharge rc = po.pricing.toList().first()
    String item_description = rc.description
    String item_price = rc.value
    String item_currency = "USD"
    String item_quantity = "1"
    String recurrence = rc.recurrence
    render(view: 'connectToPaypal', model: [recurrence:recurrence,item_name: item_name,
            item_description: item_description, item_price: item_price, item_currency: item_currency,
            item_quantity: item_quantity, userId: userId.toLong(), item_number: po.id])
  }
  def list = {
    def now = new Date()
    params.max = Math.min(params.max ? params.int('max') : 10, 100)
    def c = Subscription.createCriteria()
    def subscriptionList = c.list(params) {
      subscriptionFor {
        party {
          idEq(UserTools.currentUser.party.id)
        }
      }
      lt('activeFrom', now)
      gt('activeTo', now)
    }
    [subscriptionList: subscriptionList, subscriptionTotal: subscriptionList.size()]
  }


  def create = {
    def subscription = new Subscription()
    subscription.properties = params
    return [subscription: subscription]
  }

  def save = {
    def subscription = new Subscription(params)
    if (subscription.save(flush: true)) {
      flash.message = "${message(code: 'default.created.message', args: [message(code: 'subscription.label', default: 'Subscription'), subscription.id])}"
      redirect(action: "show", id: subscription.id)
    }
    else {
      render(view: "create", model: [subscription: subscription])
    }
  }

  def show = {
    def subscription = Subscription.get(params.id)
    if (!subscription) {
      flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'subscription.label', default: 'Subscription'), params.id])}"
      redirect(action: "list")
    }
    else {
      [subscription: subscription]
    }
  }

  def edit = {
    def subscription = Subscription.get(params.id)
    if (!subscription) {
      flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'subscription.label', default: 'Subscription'), params.id])}"
      redirect(action: "list")
    }
    else {
      return [subscription: subscription]
    }
  }

  def update = {
    def subscription = Subscription.get(params.id)
    if (subscription) {
      if (params.version) {
        def version = params.version.toLong()
        if (subscription.version > version) {

          subscription.errors.rejectValue("version", "default.optimistic.locking.failure", [message(code: 'subscription.label', default: 'Subscription')] as Object[], "Another user has updated this Subscription while you were editing")
          render(view: "edit", model: [subscription: subscription])
          return
        }
      }
      subscription.properties = params
      if (!subscription.hasErrors() && subscription.save(flush: true)) {
        flash.message = "${message(code: 'default.updated.message', args: [message(code: 'subscription.label', default: 'Subscription'), subscription.id])}"
        redirect(action: "show", id: subscription.id)
      }
      else {
        render(view: "edit", model: [subscription: subscription])
      }
    }
    else {
      flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'subscription.label', default: 'Subscription'), params.id])}"
      redirect(action: "list")
    }
  }

  def delete = {
    def subscription = Subscription.get(params.id)
    if (subscription) {
      try {
        subscription.delete(flush: true)
        flash.message = "${message(code: 'default.deleted.message', args: [message(code: 'subscription.label', default: 'Subscription'), params.id])}"
        redirect(action: "list")
      }
      catch (org.springframework.dao.DataIntegrityViolationException e) {
        flash.message = "${message(code: 'default.not.deleted.message', args: [message(code: 'subscription.label', default: 'Subscription'), params.id])}"
        redirect(action: "show", id: params.id)
      }
    }
    else {
      flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'subscription.label', default: 'Subscription'), params.id])}"
      redirect(action: "list")
    }
  }
}
