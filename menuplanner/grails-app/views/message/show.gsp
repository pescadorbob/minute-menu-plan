
<%@ page import="com.mp.domain.social.Message" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <meta name="layout" content="menu" />
        <g:set var="entityName" value="${message(code: 'message.label', default: 'Message')}" />
        <title><g:message code="default.show.label" args="[entityName]" /></title>
    </head>
    <body>
<div id="container">
  <div id="wrapper" class="clearfix">
    <div id="content-wrapper" class="clearfix">
        <div class="nav">
            <span class="menuButton"><a class="home" href="${createLink(uri: '/')}">Home</a></span>
            <span class="menuButton"><g:link class="list" action="list"><g:message code="default.list.label" args="[entityName]" /></g:link></span>
            <span class="menuButton"><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link></span>
        </div>
        <div class="body">
          <div class="headbox">
            <h3><g:message code="default.show.label" args="[entityName]" /></h3>
            </div>
            <g:if test="${flash.message}">
            <div class="message">${flash.message}</div>
            </g:if>
            <div class="dialog">
                <table>
                    <tbody>
                    
                    <tr class="prop">
                        <td valign="top" class="name"><g:message code="message.frum.label" default="From" /></td>

                        <td valign="top" class="value"><g:link controller="party" action="show" id="${message?.frum?.id}">${message?.frum?.encodeAsHTML()}</g:link></td>

                    </tr>
                    <tr class="prop">
                        <td valign="top" class="name"><g:message code="message.subject.label" default="Subject" /></td>

                        <td valign="top" class="value">${fieldValue(bean: message, field: "subject")}</td>

                    </tr>

                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="message.body.label" default="Body" /></td>
                            
                            <td valign="top" class="value">${fieldValue(bean: message, field: "body")}</td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="message.replyTo.label" default="Reply To" /></td>
                            
                            <td valign="top" class="value"><g:link controller="message" action="show" id="${message?.replyTo?.id}">${message?.replyTo?.encodeAsHTML()}</g:link></td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="message.to.label" default="To" /></td>
                            
                            <td valign="top" class="value"><g:link controller="party" action="show" id="${message?.to?.id}">${message?.to?.encodeAsHTML()}</g:link></td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="message.transactions.label" default="Transactions" /></td>
                            
                            <td valign="top" style="text-align: left;" class="value">
                                <ul>
                                <g:each in="${message.transactions}" var="t">
                                    <li><g:link controller="messageStatus" action="show" id="${t.id}">${t?.encodeAsHTML()}</g:link></li>
                                </g:each>
                                </ul>
                            </td>
                            
                        </tr>
                    
                    </tbody>
                </table>
            </div>
            <div class="buttons">
                <g:form>
                    <g:hiddenField name="id" value="${message?.id}" />
                    <span class="button"><g:actionSubmit class="delete" action="delete" value="${message(code: 'default.button.delete.label', default: 'Delete')}" onclick="return confirm('${message(code: 'default.button.delete.confirm.message', default: 'Are you sure?')}');" /></span>
                </g:form>
            </div>
        </div>
    </div></div></div>

    </body>
</html>
