package com.mp.domain.party

class SubscriberController {

  static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

  def index = {
    redirect(action: "list", params: params)
  }

  def list = {
    params.max = Math.min(params.max ? params.int('max') : 10, 100)
    [subscriberList: Subscriber.list(params), subscriberTotal: Subscriber.count()]
  }

  def create = {
    def subscriber = new Subscriber()
    subscriber.properties = params
    return [subscriber: subscriber]
  }

  def save = {
    def subscriber = new Subscriber(params)
    if (subscriber.save(flush: true)) {
      flash.message = "${message(code: 'default.created.message', args: [message(code: 'subscriber.label', default: 'Subscriber'), subscriber.id])}"
      redirect(action: "show", id: subscriber.id)
    }
    else {
      render(view: "create", model: [subscriber: subscriber])
    }
  }

  def showUserClients = {
    def c = CoachSubscriber.createCriteria();
    def clients = c.list {
      client {
        party {
          idEq(params.long('id'))
        }
      }
      lt('activeFrom',new Date())
      or{
        isNull('activeTo')
        gt('activeTo',new Date())
      }
    }
     [subscriberList: clients, subscriberTotal: clients.size()]

  }

  def show = {
    def subscriber = Subscriber.get(params.id)
    if (!subscriber) {
      flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'subscriber.label', default: 'Subscriber'), params.id])}"
      redirect(action: "list")
    }
    else {
      [subscriber: subscriber]
    }
  }

  def edit = {
    def subscriber = Subscriber.get(params.id)
    if (!subscriber) {
      flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'subscriber.label', default: 'Subscriber'), params.id])}"
      redirect(action: "list")
    }
    else {
      return [subscriber: subscriber]
    }
  }

  def update = {
    def subscriber = Subscriber.get(params.id)
    if (subscriber) {
      if (params.version) {
        def version = params.version.toLong()
        if (subscriber.version > version) {

          subscriber.errors.rejectValue("version", "default.optimistic.locking.failure", [message(code: 'subscriber.label', default: 'Subscriber')] as Object[], "Another user has updated this Subscriber while you were editing")
          render(view: "edit", model: [subscriber: subscriber])
          return
        }
      }
      subscriber.properties = params
      if (!subscriber.hasErrors() && subscriber.save(flush: true)) {
        flash.message = "${message(code: 'default.updated.message', args: [message(code: 'subscriber.label', default: 'Subscriber'), subscriber.id])}"
        redirect(action: "show", id: subscriber.id)
      }
      else {
        render(view: "edit", model: [subscriber: subscriber])
      }
    }
    else {
      flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'subscriber.label', default: 'Subscriber'), params.id])}"
      redirect(action: "list")
    }
  }

  def delete = {
    def subscriber = Subscriber.get(params.id)
    if (subscriber) {
      try {
        subscriber.delete(flush: true)
        flash.message = "${message(code: 'default.deleted.message', args: [message(code: 'subscriber.label', default: 'Subscriber'), params.id])}"
        redirect(action: "list")
      }
      catch (org.springframework.dao.DataIntegrityViolationException e) {
        flash.message = "${message(code: 'default.not.deleted.message', args: [message(code: 'subscriber.label', default: 'Subscriber'), params.id])}"
        redirect(action: "show", id: params.id)
      }
    }
    else {
      flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'subscriber.label', default: 'Subscriber'), params.id])}"
      redirect(action: "list")
    }
  }
}
