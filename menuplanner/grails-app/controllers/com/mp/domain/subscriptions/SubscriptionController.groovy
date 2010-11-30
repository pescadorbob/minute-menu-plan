package com.mp.domain.subscriptions

import com.mp.tools.UserTools

class SubscriptionController {

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

    def index = {
        redirect(action: "list", params: params)
    }

  def subscriptionRequest = {
    def now = new Date()
    def c = Subscription.createCriteria()
    def subscriptions = c.list {
      subscriber {
        party {
          idEq(UserTools.currentUser.party.id)
        }
      }
      lt('activeFrom',now)
      gt('activeTo',now)
    }

  }
    def list = {
        params.max = Math.min(params.max ? params.int('max') : 10, 100)
        [subscriptionList: Subscription.list(params), subscriptionTotal: Subscription.count()]
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
