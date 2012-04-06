package com.mp.domain.themes

import com.mp.domain.Testimonial
import com.mp.tools.ThemeTools

class LandingPageController {

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

    def go = {
      def now = new Date()
      def c = LandingPage.createCriteria()
      def landingPage = c {
         lt('activeFrom',now)
         or {
           isNull('activeTo')
           gt('activeTo',now)
         }
      }
      if(landingPage){
        forward(controller:landingPage.controllerName,action:landingPage.actionName)
      } else {
         redirect(controller:'login',action:'homePage')
      }
    }
    def index = {
        redirect(action: "list", params: params)
    }

    def list = {
        params.max = Math.min(params.max ? params.int('max') : 10, 100)
        [landingPageList: LandingPage.list(params), landingPageTotal: LandingPage.count()]
    }

    def create = {
        def landingPage = new LandingPage()
        landingPage.properties = params
        return [landingPage: landingPage]
    }

    def save = {
        def landingPage = new LandingPage(params)
        if (landingPage.save(flush: true)) {
            flash.message = "${message(code: 'default.created.message', args: [message(code: 'landingPage.label', default: 'LandingPage'), landingPage.id])}"
            redirect(action: "show", id: landingPage.id)
        }
        else {
            render(view: "create", model: [landingPage: landingPage])
        }
    }

    def show = {
        def landingPage = LandingPage.get(params.id)
        if (!landingPage) {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'landingPage.label', default: 'LandingPage'), params.id])}"
            redirect(action: "list")
        }
        else {
            [landingPage: landingPage]
        }
    }

    def edit = {
        def landingPage = LandingPage.get(params.id)
        if (!landingPage) {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'landingPage.label', default: 'LandingPage'), params.id])}"
            redirect(action: "list")
        }
        else {
            return [landingPage: landingPage]
        }
    }

    def update = {
        def landingPage = LandingPage.get(params.id)
        if (landingPage) {
            if (params.version) {
                def version = params.version.toLong()
                if (landingPage.version > version) {
                    
                    landingPage.errors.rejectValue("version", "default.optimistic.locking.failure", [message(code: 'landingPage.label', default: 'LandingPage')] as Object[], "Another user has updated this LandingPage while you were editing")
                    render(view: "edit", model: [landingPage: landingPage])
                    return
                }
            }
            landingPage.properties = params
            if (!landingPage.hasErrors() && landingPage.save(flush: true)) {
                flash.message = "${message(code: 'default.updated.message', args: [message(code: 'landingPage.label', default: 'LandingPage'), landingPage.id])}"
                redirect(action: "show", id: landingPage.id)
            }
            else {
                render(view: "edit", model: [landingPage: landingPage])
            }
        }
        else {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'landingPage.label', default: 'LandingPage'), params.id])}"
            redirect(action: "list")
        }
    }

    def delete = {
        def landingPage = LandingPage.get(params.id)
        if (landingPage) {
            try {
                landingPage.delete(flush: true)
                flash.message = "${message(code: 'default.deleted.message', args: [message(code: 'landingPage.label', default: 'LandingPage'), params.id])}"
                redirect(action: "list")
            }
            catch (org.springframework.dao.DataIntegrityViolationException e) {
                flash.message = "${message(code: 'default.not.deleted.message', args: [message(code: 'landingPage.label', default: 'LandingPage'), params.id])}"
                redirect(action: "show", id: params.id)
            }
        }
        else {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'landingPage.label', default: 'LandingPage'), params.id])}"
            redirect(action: "list")
        }
    }
}
