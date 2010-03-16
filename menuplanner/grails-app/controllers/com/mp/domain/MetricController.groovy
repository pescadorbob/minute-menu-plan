package com.mp.domain

class MetricController {

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

    def index = {
        redirect(action: "list", params: params)
    }

    def list = {
        params.max = Math.min(params.max ? params.int('max') : 10, 100)
        [metricList: Metric.list(params), metricTotal: Metric.count()]
    }

    def create = {
        def metric = new Metric()
        metric.properties = params
        return [metric: metric]
    }

    def save = {
        def metric = new Metric(params)
        if (metric.save(flush: true)) {
            flash.message = "${message(code: 'default.created.message', args: [message(code: 'metric.label', default: 'Metric'), metric.id])}"
            redirect(action: "show", id: metric.id)
        }
        else {
            render(view: "create", model: [metric: metric])
        }
    }

    def show = {
        def metric = Metric.get(params.id)
        if (!metric) {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'metric.label', default: 'Metric'), params.id])}"
            redirect(action: "list")
        }
        else {
            [metric: metric]
        }
    }

    def edit = {
        def metric = Metric.get(params.id)
        if (!metric) {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'metric.label', default: 'Metric'), params.id])}"
            redirect(action: "list")
        }
        else {
            return [metric: metric]
        }
    }

    def update = {
        def metric = Metric.get(params.id)
        if (metric) {
            if (params.version) {
                def version = params.version.toLong()
                if (metric.version > version) {
                    
                    metric.errors.rejectValue("version", "default.optimistic.locking.failure", [message(code: 'metric.label', default: 'Metric')] as Object[], "Another user has updated this Metric while you were editing")
                    render(view: "edit", model: [metric: metric])
                    return
                }
            }
            metric.properties = params
            if (!metric.hasErrors() && metric.save(flush: true)) {
                flash.message = "${message(code: 'default.updated.message', args: [message(code: 'metric.label', default: 'Metric'), metric.id])}"
                redirect(action: "show", id: metric.id)
            }
            else {
                render(view: "edit", model: [metric: metric])
            }
        }
        else {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'metric.label', default: 'Metric'), params.id])}"
            redirect(action: "list")
        }
    }

    def delete = {
        def metric = Metric.get(params.id)
        if (metric) {
            try {
                metric.delete(flush: true)
                flash.message = "${message(code: 'default.deleted.message', args: [message(code: 'metric.label', default: 'Metric'), params.id])}"
                redirect(action: "list")
            }
            catch (org.springframework.dao.DataIntegrityViolationException e) {
                flash.message = "${message(code: 'default.not.deleted.message', args: [message(code: 'metric.label', default: 'Metric'), params.id])}"
                redirect(action: "show", id: params.id)
            }
        }
        else {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'metric.label', default: 'Metric'), params.id])}"
            redirect(action: "list")
        }
    }
}
