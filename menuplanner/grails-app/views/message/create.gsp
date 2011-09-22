
<%@ page import="com.mp.domain.social.Message" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <meta name="layout" content="menu" />
        <g:set var="entityName" value="${message(code: 'message.label', default: 'Message')}" />
        <title><g:message code="default.create.label" args="[entityName]" /></title>
    </head>
    <body>
<div id="container">
  <div id="wrapper" class="clearfix">
    <div id="content-wrapper" class="clearfix">
        <div class="body">
          <div class="headbox">
            <h3><g:message code="default.create.label" args="[entityName]" /></h3>
            </div>
            <g:if test="${flash.message}">
            <div class="message">${flash.message}</div>
            </g:if>
            <g:hasErrors bean="${message}">
            <div class="errors">
                <g:renderErrors bean="${message}" as="list" />
            </div>
            </g:hasErrors>
            <g:form action="save" method="post" >
                <div class="dialog">
                    <table>
                        <tbody>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="subject"><g:message code="message.subject.label" default="Subject" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: message, field: 'subject', 'errors')}">
                                    <g:textField size="35" name="subject" value="${message?.subject}" />
                                </td>
                            </tr>
                        
                        <tr class="prop">
                            <td valign="top" class="name">
                                <label for="body"><g:message code="message.body.label" default="Body" /></label>
                            </td>
                            <td valign="top" class="value ${hasErrors(bean: message, field: 'body', 'errors')}">
                                <g:textArea name="body" cols="40" rows="5" value="${message?.body}" />
                            </td>
                        </tr>

                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="to"><g:message code="message.to.label" default="To" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: message, field: 'to', 'errors')}">
                                    <g:select name="to.id" from="${com.mp.domain.party.Party.list()}" optionKey="id" value="${message?.to?.id}"  />
                                </td>
                            </tr>
                        
                        </tbody>
                    </table>
                </div>
                <div class="buttons">
                    <span class="button"><g:submitButton name="create" class="save" value="${message(code: 'default.button.create.label', default: 'Create')}" /></span>
                </div>
            </g:form>
        </div>
        </div>
        </div>
        </div>
    </body>
</html>
