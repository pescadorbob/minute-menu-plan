package com.mp.analytics

class AppRequestController {

  static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

  def index = {
    redirect(action: "list", params: params)
  }

  def list = {
    params.max = Math.min(params.max ? params.int('max') : 10, 100)
    [appRequestList: AppRequest.list(params), appRequestTotal: AppRequest.count()]
  }

  def create = {
    def appRequest = new AppRequest()
    appRequest.properties = params
    return [appRequest: appRequest]
  }

  def save = {
    def appRequest = new AppRequest(params)
    if (appRequest.save(flush: true)) {
      flash.message = "${message(code: 'default.created.message', args: [message(code: 'appRequest.label', default: 'AppRequest'), appRequest.id])}"
      redirect(action: "show", id: appRequest.id)
    }
    else {
      render(view: "create", model: [appRequest: appRequest])
    }
  }

  def show = {
    def appRequest
    if (params.id)
      appRequest = AppRequest.get(params.id)
    else if (params.uniqueId)
      appRequest = AppRequest.findByUniqueId(params.uniqueId)

    if (!appRequest) {
      flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'appRequest.label', default: 'AppRequest'), params.id])}"
      redirect(action: "list")
    }
    else {
      [appRequest: appRequest]
    }
  }

  def edit = {
    def appRequest = AppRequest.get(params.id)
    if (!appRequest) {
      flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'appRequest.label', default: 'AppRequest'), params.id])}"
      redirect(action: "list")
    }
    else {
      return [appRequest: appRequest]
    }
  }

  def update = {
    def appRequest = AppRequest.get(params.id)
    if (appRequest) {
      if (params.version) {
        def version = params.version.toLong()
        if (appRequest.version > version) {

          appRequest.errors.rejectValue("version", "default.optimistic.locking.failure", [message(code: 'appRequest.label', default: 'AppRequest')] as Object[], "Another user has updated this AppRequest while you were editing")
          render(view: "edit", model: [appRequest: appRequest])
          return
        }
      }
      appRequest.properties = params
      if (!appRequest.hasErrors() && appRequest.save(flush: true)) {
        flash.message = "${message(code: 'default.updated.message', args: [message(code: 'appRequest.label', default: 'AppRequest'), appRequest.id])}"
        redirect(action: "show", id: appRequest.id)
      }
      else {
        render(view: "edit", model: [appRequest: appRequest])
      }
    }
    else {
      flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'appRequest.label', default: 'AppRequest'), params.id])}"
      redirect(action: "list")
    }
  }

  def delete = {
    def appRequest = AppRequest.get(params.id)
    if (appRequest) {
      try {
        appRequest.delete(flush: true)
        flash.message = "${message(code: 'default.deleted.message', args: [message(code: 'appRequest.label', default: 'AppRequest'), params.id])}"
        redirect(action: "list")
      }
      catch (org.springframework.dao.DataIntegrityViolationException e) {
        flash.message = "${message(code: 'default.not.deleted.message', args: [message(code: 'appRequest.label', default: 'AppRequest'), params.id])}"
        redirect(action: "show", id: params.id)
      }
    }
    else {
      flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'appRequest.label', default: 'AppRequest'), params.id])}"
      redirect(action: "list")
    }
  }
}
