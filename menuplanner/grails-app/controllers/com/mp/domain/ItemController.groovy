package com.mp.domain

class ItemController {

  static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

  def index = {
    redirect(action: "list", params: params)
  }

  def list = {
    params.max = Math.min(params.max ? params.int('max') : 10, 100)
    params.offset = params.offset ? params.offset : 0
    String name = params.searchName
    def itemList
    Integer total
    if (name) {
      itemList = Item.createCriteria().list(max: params.max, offset: params.offset) {
          ilike('name', "%${name}%")
      }
      total = itemList.getTotalCount()
    } else {
      itemList = Item.list(params)
      total = Item.count()
    }
    [itemList: itemList, itemTotal: total]
  }

  def create = {
    def item = new Item()
    item.properties = params
    return [item: item]
  }

  def save = {
    def item = new Item(params)
    if (item.save(flush: true)) {
      flash.message = "${message(code: 'default.created.message', args: [message(code: 'item.label', default: 'Item'), item.id])}"
      redirect(action: "show", id: item.id)
    }
    else {
      render(view: "create", model: [item: item])
    }
  }

  def show = {
    def item = Item.get(params.id)
    if (!item) {
      flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'item.label', default: 'Item'), params.id])}"
      redirect(action: "list")
    }
    else {
      [item: item]
    }
  }

  def edit = {
    def item = Item.get(params.id)
    if (!item) {
      flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'item.label', default: 'Item'), params.id])}"
      redirect(action: "list")
    }
    else {
      return [item: item]
    }
  }

  def update = {
    def item = Item.get(params.id)
    if (item) {
      if (params.version) {
        def version = params.version.toLong()
        if (item.version > version) {

          item.errors.rejectValue("version", "default.optimistic.locking.failure", [message(code: 'item.label', default: 'Item')] as Object[], "Another user has updated this Item while you were editing")
          render(view: "edit", model: [item: item])
          return
        }
      }
      item.properties = params
      if (!item.hasErrors() && item.save(flush: true)) {
        flash.message = "${message(code: 'default.updated.message', args: [message(code: 'item.label', default: 'Item'), item.id])}"
        redirect(action: "show", id: item.id)
      }
      else {
        render(view: "edit", model: [item: item])
      }
    }
    else {
      flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'item.label', default: 'Item'), params.id])}"
      redirect(action: "list")
    }
  }

  def delete = {
    def item = Item.get(params.id)
    if (item) {
      try {
        item.delete(flush: true)
        flash.message = "${message(code: 'default.deleted.message', args: [message(code: 'item.label', default: 'Item'), params.id])}"
        redirect(action: "list")
      }
      catch (org.springframework.dao.DataIntegrityViolationException e) {
        flash.message = "${message(code: 'default.not.deleted.message', args: [message(code: 'item.label', default: 'Item'), params.id])}"
        redirect(action: "show", id: params.id)
      }
    }
    else {
      flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'item.label', default: 'Item'), params.id])}"
      redirect(action: "list")
    }
  }
}
