package com.mp.domain

class TestimonialController {

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

    def index = {
        redirect(action: "list", params: params)
    }

    def list = {
        params.max = Math.min(params.max ? params.int('max') : 10, 100)
        [testimonialList: Testimonial.list(params), testimonialTotal: Testimonial.count()]
    }

    def create = {
        def testimonial = new Testimonial()
        testimonial.properties = params
        return [testimonial: testimonial]
    }

    def save = {
        def testimonial = new Testimonial(params)
        if (testimonial.s()) {
            flash.message =  message(code: 'testimonial.save.successful')
            redirect(action: "show", id: testimonial.id)
        }
        else {
            render(view: "create", model: [testimonial: testimonial])
        }
    }

    def show = {
        def testimonial = Testimonial.get(params.id)
        if (!testimonial) {
            flash.message = message(code: 'testimonial.not.found')
            redirect(action: "list")
        }
        else {
            [testimonial: testimonial]
        }
    }

    def edit = {
        def testimonial = Testimonial.get(params.id)
        if (!testimonial) {
            flash.message = message(code: 'testimonial.not.found')
            redirect(action: "list")
        }
        else {
            return [testimonial: testimonial]
        }
    }

    def showOnHomepage = {
        Testimonial testimonial = Testimonial.get(params.long('id'))
        if (testimonial) {
            if(testimonial.showOnHomepage){
                testimonial.showOnHomepage = false
            }else{
                testimonial.showOnHomepage = true
            }
            testimonial.s()
            render "true"
        }
        else {
            render "false"
        }
    }

    def update = {
        def testimonial = Testimonial.get(params.id)
        if (testimonial) {
            if (params.version) {
                def version = params.version.toLong()
                if (testimonial.version > version) {

                    testimonial.errors.rejectValue("version", "default.optimistic.locking.failure", [message(code: 'testimonial.label', default: 'Testimonial')] as Object[], "Another user has updated this Testimonial while you were editing")
                    render(view: "edit", model: [testimonial: testimonial])
                    return
                }
            }
            testimonial.properties = params
            if (!testimonial.hasErrors() && testimonial.s()) {
                flash.message = message(code: 'testimonial.updated.successfully')
                redirect(action: "show", id: testimonial.id)
            }
            else {
                render(view: "edit", model: [testimonial: testimonial])
            }
        }
        else {
            flash.message = message(code: 'testimonial.not.found')
            redirect(action: "list")
        }
    }

    def delete = {
        def testimonial = Testimonial.get(params.id)
        if (testimonial) {
            try {
                testimonial.delete(flush: true)
                flash.message = message(code: 'testimonial.deleted.successfully')
                redirect(action: "list")
            }
            catch (org.springframework.dao.DataIntegrityViolationException e) {
                flash.message =message(code: 'testimonial.cannot.deleted')
                redirect(action: "show", id: params.id)
            }
        }
        else {
            flash.message = message(code: 'testimonial.not.found')
            redirect(action: "list")
        }
    }
}
