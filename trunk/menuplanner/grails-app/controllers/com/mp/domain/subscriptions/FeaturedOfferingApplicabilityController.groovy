package com.mp.domain.subscriptions

class FeaturedOfferingApplicabilityController {

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

    def index = {
        redirect(action: "list", params: params)
    }

    def list = {
        params.max = Math.min(params.max ? params.int('max') : 10, 100)
        [featuredOfferingApplicabilityList: FeaturedOfferingApplicability.list(params), featuredOfferingApplicabilityTotal: FeaturedOfferingApplicability.count()]
    }

    def create = {
        def featuredOfferingApplicability = new FeaturedOfferingApplicability()
        featuredOfferingApplicability.properties = params
        return [featuredOfferingApplicability: featuredOfferingApplicability]
    }

    def save = {
        def featuredOfferingApplicability = new FeaturedOfferingApplicability(params)
        if (featuredOfferingApplicability.save(flush: true)) {
            flash.message = "${message(code: 'default.created.message', args: [message(code: 'featuredOfferingApplicability.label', default: 'FeaturedOfferingApplicability'), featuredOfferingApplicability.id])}"
            redirect(action: "show", id: featuredOfferingApplicability.id)
        }
        else {
            render(view: "create", model: [featuredOfferingApplicability: featuredOfferingApplicability])
        }
    }

    def show = {
        def featuredOfferingApplicability = FeaturedOfferingApplicability.get(params.id)
        if (!featuredOfferingApplicability) {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'featuredOfferingApplicability.label', default: 'FeaturedOfferingApplicability'), params.id])}"
            redirect(action: "list")
        }
        else {
            [featuredOfferingApplicability: featuredOfferingApplicability]
        }
    }

    def edit = {
        def featuredOfferingApplicability = FeaturedOfferingApplicability.get(params.id)
        if (!featuredOfferingApplicability) {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'featuredOfferingApplicability.label', default: 'FeaturedOfferingApplicability'), params.id])}"
            redirect(action: "list")
        }
        else {
            return [featuredOfferingApplicability: featuredOfferingApplicability]
        }
    }

    def update = {
        def featuredOfferingApplicability = FeaturedOfferingApplicability.get(params.id)
        if (featuredOfferingApplicability) {
            if (params.version) {
                def version = params.version.toLong()
                if (featuredOfferingApplicability.version > version) {
                    
                    featuredOfferingApplicability.errors.rejectValue("version", "default.optimistic.locking.failure", [message(code: 'featuredOfferingApplicability.label', default: 'FeaturedOfferingApplicability')] as Object[], "Another user has updated this FeaturedOfferingApplicability while you were editing")
                    render(view: "edit", model: [featuredOfferingApplicability: featuredOfferingApplicability])
                    return
                }
            }
            featuredOfferingApplicability.properties = params
            if (!featuredOfferingApplicability.hasErrors() && featuredOfferingApplicability.save(flush: true)) {
                flash.message = "${message(code: 'default.updated.message', args: [message(code: 'featuredOfferingApplicability.label', default: 'FeaturedOfferingApplicability'), featuredOfferingApplicability.id])}"
                redirect(action: "show", id: featuredOfferingApplicability.id)
            }
            else {
                render(view: "edit", model: [featuredOfferingApplicability: featuredOfferingApplicability])
            }
        }
        else {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'featuredOfferingApplicability.label', default: 'FeaturedOfferingApplicability'), params.id])}"
            redirect(action: "list")
        }
    }

    def delete = {
        def featuredOfferingApplicability = FeaturedOfferingApplicability.get(params.id)
        if (featuredOfferingApplicability) {
            try {
                featuredOfferingApplicability.delete(flush: true)
                flash.message = "${message(code: 'default.deleted.message', args: [message(code: 'featuredOfferingApplicability.label', default: 'FeaturedOfferingApplicability'), params.id])}"
                redirect(action: "list")
            }
            catch (org.springframework.dao.DataIntegrityViolationException e) {
                flash.message = "${message(code: 'default.not.deleted.message', args: [message(code: 'featuredOfferingApplicability.label', default: 'FeaturedOfferingApplicability'), params.id])}"
                redirect(action: "show", id: params.id)
            }
        }
        else {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'featuredOfferingApplicability.label', default: 'FeaturedOfferingApplicability'), params.id])}"
            redirect(action: "list")
        }
    }
}
