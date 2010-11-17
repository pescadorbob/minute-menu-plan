package com.mp.domain.themes

class ThemeController {

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

    def index = {
        redirect(action: "list", params: params)
    }

    def list = {
        params.max = Math.min(params.max ? params.int('max') : 10, 100)
        [themeList: Theme.list(params), themeTotal: Theme.count()]
    }

    def create = {
        def theme = new Theme()
        theme.properties = params
        return [theme: theme]
    }

    def save = {
        def theme = new Theme(params)
        if (theme.save(flush: true)) {
            flash.message = "${message(code: 'default.created.message', args: [message(code: 'theme.label', default: 'Theme'), theme.id])}"
            redirect(action: "show", id: theme.id)
        }
        else {
            render(view: "create", model: [theme: theme])
        }
    }

    def show = {
        def theme = Theme.get(params.id)
        if (!theme) {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'theme.label', default: 'Theme'), params.id])}"
            redirect(action: "list")
        }
        else {
            [theme: theme]
        }
    }

    def edit = {
        def theme = Theme.get(params.id)
        if (!theme) {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'theme.label', default: 'Theme'), params.id])}"
            redirect(action: "list")
        }
        else {
            return [theme: theme]
        }
    }

    def update = {
        def theme = Theme.get(params.id)
        if (theme) {
            if (params.version) {
                def version = params.version.toLong()
                if (theme.version > version) {
                    
                    theme.errors.rejectValue("version", "default.optimistic.locking.failure", [message(code: 'theme.label', default: 'Theme')] as Object[], "Another user has updated this Theme while you were editing")
                    render(view: "edit", model: [theme: theme])
                    return
                }
            }
            theme.properties = params
            if (!theme.hasErrors() && theme.save(flush: true)) {
                flash.message = "${message(code: 'default.updated.message', args: [message(code: 'theme.label', default: 'Theme'), theme.id])}"
                redirect(action: "show", id: theme.id)
            }
            else {
                render(view: "edit", model: [theme: theme])
            }
        }
        else {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'theme.label', default: 'Theme'), params.id])}"
            redirect(action: "list")
        }
    }

    def delete = {
        def theme = Theme.get(params.id)
        if (theme) {
            try {
                theme.delete(flush: true)
                flash.message = "${message(code: 'default.deleted.message', args: [message(code: 'theme.label', default: 'Theme'), params.id])}"
                redirect(action: "list")
            }
            catch (org.springframework.dao.DataIntegrityViolationException e) {
                flash.message = "${message(code: 'default.not.deleted.message', args: [message(code: 'theme.label', default: 'Theme'), params.id])}"
                redirect(action: "show", id: params.id)
            }
        }
        else {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'theme.label', default: 'Theme'), params.id])}"
            redirect(action: "list")
        }
    }
}
