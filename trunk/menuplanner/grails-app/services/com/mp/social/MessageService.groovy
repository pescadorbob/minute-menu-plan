package com.mp.social

import com.mp.domain.social.Message
import com.mp.domain.party.Party
import com.mp.domain.social.MessageStatus
import com.mp.domain.message.MessageStatusType
import com.mp.email.Tag

class MessageService {

    boolean transactional = true

    def asynchronousMailService


def grailsApplication

  def Message sendMessage(params,date){
    def message = new Message(params).s()
    new MessageStatus(type:MessageStatusType.SENT,historyFor:message,date:date).s()
    def g = grailsApplication.mainContext.
    getBean('org.codehaus.groovy.grails.plugins.web.taglib.RenderTagLib')

    if(message?.to?.email){
      asynchronousMailService.sendAsynchronousMail {
        from  'noreply@minutemenuplan.com'
        to message?.to?.email
        subject "${Tag.message} ${message.subject}"
        html g.render(template: '/social/message', model: [messageId:message.id,tag:Tag.message,party: message?.frum, body: message?.body])
      }
    }
    message
  }
  def Message sendMessage(String body,Party from,Party to,String subject,Message replyTo,Date date) {
    def params = [body:body,frum:from,to:to,subject:subject,replyTo:replyTo]
    sendMessage(params)
  }
  def getMessagesForParty(id,max,min){
      Message.findAllByToAndReplyToIsNull(Party.get(id),[max: max as Integer, offset: min as Integer])
  }
  def getTotalMessagesForParty(id){
      Message.findAllByToAndReplyToIsNull(Party.get(id)).count()
  }

}
