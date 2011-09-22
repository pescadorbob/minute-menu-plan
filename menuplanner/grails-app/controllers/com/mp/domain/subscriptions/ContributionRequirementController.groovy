package com.mp.domain.subscriptions

class ContributionRequirementController {

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

    def index = {
        redirect(action: "list", params: params)
    }

    def list = {
        params.max = Math.min(params.max ? params.int('max') : 10, 100)
        [contributionRequirementList: ContributionRequirement.list(params), contributionRequirementTotal: ContributionRequirement.count()]
    }

    def create = {
        def contributionRequirement = new ContributionRequirement()
        contributionRequirement.properties = params
        return [contributionRequirement: contributionRequirement]
    }

    def save = {
        def contributionRequirement = new ContributionRequirement(params)
        if (contributionRequirement.save(flush: true)) {
            flash.message = "${message(code: 'default.created.message', args: [message(code: 'contributionRequirement.label', default: 'ContributionRequirement'), contributionRequirement.id])}"
            redirect(action: "show", id: contributionRequirement.id)
        }
        else {
            render(view: "create", model: [contributionRequirement: contributionRequirement])
        }
    }

    def show = {
        def contributionRequirement = ContributionRequirement.get(params.id)
        if (!contributionRequirement) {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'contributionRequirement.label', default: 'ContributionRequirement'), params.id])}"
            redirect(action: "list")
        }
        else {
            [contributionRequirement: contributionRequirement]
        }
    }

    def edit = {
        def contributionRequirement = ContributionRequirement.get(params.id)
        if (!contributionRequirement) {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'contributionRequirement.label', default: 'ContributionRequirement'), params.id])}"
            redirect(action: "list")
        }
        else {
            return [contributionRequirement: contributionRequirement]
        }
    }

    def update = {
        def contributionRequirement = ContributionRequirement.get(params.id)
        if (contributionRequirement) {
            if (params.version) {
                def version = params.version.toLong()
                if (contributionRequirement.version > version) {
                    
                    contributionRequirement.errors.rejectValue("version", "default.optimistic.locking.failure", [message(code: 'contributionRequirement.label', default: 'ContributionRequirement')] as Object[], "Another user has updated this ContributionRequirement while you were editing")
                    render(view: "edit", model: [contributionRequirement: contributionRequirement])
                    return
                }
            }
            contributionRequirement.properties = params
            if (!contributionRequirement.hasErrors() && contributionRequirement.save(flush: true)) {
                flash.message = "${message(code: 'default.updated.message', args: [message(code: 'contributionRequirement.label', default: 'ContributionRequirement'), contributionRequirement.id])}"
                redirect(action: "show", id: contributionRequirement.id)
            }
            else {
                render(view: "edit", model: [contributionRequirement: contributionRequirement])
            }
        }
        else {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'contributionRequirement.label', default: 'ContributionRequirement'), params.id])}"
            redirect(action: "list")
        }
    }

    def delete = {
        def contributionRequirement = ContributionRequirement.get(params.id)
        if (contributionRequirement) {
            try {
                contributionRequirement.delete(flush: true)
                flash.message = "${message(code: 'default.deleted.message', args: [message(code: 'contributionRequirement.label', default: 'ContributionRequirement'), params.id])}"
                redirect(action: "list")
            }
            catch (org.springframework.dao.DataIntegrityViolationException e) {
                flash.message = "${message(code: 'default.not.deleted.message', args: [message(code: 'contributionRequirement.label', default: 'ContributionRequirement'), params.id])}"
                redirect(action: "show", id: params.id)
            }
        }
        else {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'contributionRequirement.label', default: 'ContributionRequirement'), params.id])}"
            redirect(action: "list")
        }
    }
}
