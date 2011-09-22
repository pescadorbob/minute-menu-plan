package com.mp.domain.social

import com.mp.domain.party.Party
import com.mp.tools.UserTools

class MessageController {

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]
    def messageService
    def index = {
        redirect(action: "list", params: params)
    }

    def list = {
      params.max = Math.min(params.max ? params.int('max') : 10, 100)
        def messages = messageService.getMessagesForParty(UserTools.getCurrentUser().party.id,params.max,params.min)
        def total = messageService.getTotalMessagesForParty(UserTools.getCurrentUser().party.id)
        [messageList: messages, messageTotal: total]
    }

    def create = {
        def message = new Message()
        message.properties = params
        return [message: message]
    }

    def save = {
        params.frum = UserTools.getCurrentUser()
        def the_message = messageService.sendMessage(params,new Date())
        if (the_message) {
            flash.message = "${message(code: 'default.created.message', args: [message(code: 'message.label', default: 'Message'), the_message.id])}"
            redirect(action: "show", id: the_message.id)
        }
        else {
            render(view: "create", model: [message: the_message])
        }
    }

    def show = {
        def the_message = Message.get(params.id)
        if (!the_message) {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'message.label', default: 'Message'), params.id])}"
            redirect(action: "list")
        }
        else {
            [message: the_message]
        }
    }

    def delete = {
        def message = Message.get(params.id)
        if (message) {
            try {
                message.delete(flush: true)
                flash.message = "${message(code: 'default.deleted.message', args: [message(code: 'message.label', default: 'Message'), params.id])}"
                redirect(action: "list")
            }
            catch (org.springframework.dao.DataIntegrityViolationException e) {
                flash.message = "${message(code: 'default.not.deleted.message', args: [message(code: 'message.label', default: 'Message'), params.id])}"
                redirect(action: "show", id: params.id)
            }
        }
        else {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'message.label', default: 'Message'), params.id])}"
            redirect(action: "list")
        }
    }
}
