package com.mp.domain.pricing

class PriceController {

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

  def masterDataBootStrapService
    def populateRandomPrices = {
      masterDataBootStrapService.populateRecipePrices()
      flash.message = 'Recipes have been populated with random pricing.'
        redirect(action: "list", params: params)
    }
    def index = {
        redirect(action: "list", params: params)
    }

    def list = {
        params.max = Math.min(params.max ? params.int('max') : 10, 100)
        [priceList: Price.list(params), priceTotal: Price.count()]
    }

    def create = {
        def price = new Price()
        price.properties = params
        return [price: price]
    }

    def save = {
        def price = new Price(params)
        if (price.save(flush: true)) {
            flash.message = "${message(code: 'default.created.message', args: [message(code: 'price.label', default: 'Price'), price.id])}"
            redirect(action: "show", id: price.id)
        }
        else {
            render(view: "create", model: [price: price])
        }
    }

    def show = {
        def price = Price.get(params.id)
        if (!price) {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'price.label', default: 'Price'), params.id])}"
            redirect(action: "list")
        }
        else {
            [price: price]
        }
    }

    def edit = {
        def price = Price.get(params.id)
        if (!price) {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'price.label', default: 'Price'), params.id])}"
            redirect(action: "list")
        }
        else {
            return [price: price]
        }
    }

    def update = {
        def price = Price.get(params.id)
        if (price) {
            if (params.version) {
                def version = params.version.toLong()
                if (price.version > version) {
                    
                    price.errors.rejectValue("version", "default.optimistic.locking.failure", [message(code: 'price.label', default: 'Price')] as Object[], "Another user has updated this Price while you were editing")
                    render(view: "edit", model: [price: price])
                    return
                }
            }
            price.properties = params
            if (!price.hasErrors() && price.save(flush: true)) {
                flash.message = "${message(code: 'default.updated.message', args: [message(code: 'price.label', default: 'Price'), price.id])}"
                redirect(action: "show", id: price.id)
            }
            else {
                render(view: "edit", model: [price: price])
            }
        }
        else {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'price.label', default: 'Price'), params.id])}"
            redirect(action: "list")
        }
    }

    def delete = {
        def price = Price.get(params.id)
        if (price) {
            try {
                price.delete(flush: true)
                flash.message = "${message(code: 'default.deleted.message', args: [message(code: 'price.label', default: 'Price'), params.id])}"
                redirect(action: "list")
            }
            catch (org.springframework.dao.DataIntegrityViolationException e) {
                flash.message = "${message(code: 'default.not.deleted.message', args: [message(code: 'price.label', default: 'Price'), params.id])}"
                redirect(action: "show", id: params.id)
            }
        }
        else {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'price.label', default: 'Price'), params.id])}"
            redirect(action: "list")
        }
    }
}
