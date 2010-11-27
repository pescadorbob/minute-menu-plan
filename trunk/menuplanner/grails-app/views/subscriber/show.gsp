
<%@ page import="com.mp.domain.party.Subscriber" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <meta name="layout" content="menu" />
        <g:set var="entityName" value="${message(code: 'subscriber.label', default: 'Subscriber')}" />
        <title><g:message code="default.show.label" args="[entityName]" /></title>
    </head>
    <body>
        <div class="nav">
            <span class="menuButton"><a class="home" href="${createLink(uri: '/')}">Home</a></span>
            <span class="menuButton"><g:link class="list" action="list"><g:message code="default.list.label" args="[entityName]" /></g:link></span>
            <span class="menuButton"><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link></span>
        </div>
        <div class="body">
            <h1><g:message code="default.show.label" args="[entityName]" /></h1>
            <g:if test="${flash.message}">
            <div class="message">${flash.message}</div>
            </g:if>
            <div class="dialog">
                <table>
                    <tbody>
                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="subscriber.id.label" default="Id" /></td>
                            
                            <td valign="top" class="value">${fieldValue(bean: subscriber, field: "id")}</td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="subscriber.image.label" default="Image" /></td>
                            
                            <td valign="top" class="value"><g:link controller="image" action="show" id="${subscriber?.image?.id}">${subscriber?.image?.encodeAsHTML()}</g:link></td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="subscriber.city.label" default="City" /></td>
                            
                            <td valign="top" class="value">${fieldValue(bean: subscriber, field: "city")}</td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="subscriber.mouthsToFeed.label" default="Mouths To Feed" /></td>
                            
                            <td valign="top" class="value">${fieldValue(bean: subscriber, field: "mouthsToFeed")}</td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="subscriber.introduction.label" default="Introduction" /></td>
                            
                            <td valign="top" class="value">${fieldValue(bean: subscriber, field: "introduction")}</td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="subscriber.coachId.label" default="Coach Id" /></td>
                            
                            <td valign="top" class="value">${fieldValue(bean: subscriber, field: "coachId")}</td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="subscriber.subscriptions.label" default="Subscriptions" /></td>
                            
                            <td valign="top" style="text-align: left;" class="value">
                                <ul>
                                <g:each in="${subscriber.subscriptions}" var="s">
                                    <li><g:link controller="subscription" action="show" id="${s.id}">${s?.encodeAsHTML()}</g:link></li>
                                </g:each>
                                </ul>
                            </td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="subscriber.party.label" default="Party" /></td>
                            
                            <td valign="top" class="value"><g:link controller="party" action="show" id="${subscriber?.party?.id}">${subscriber?.party?.encodeAsHTML()}</g:link></td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="subscriber.imageDir.label" default="Image Dir" /></td>
                            
                            <td valign="top" class="value">${fieldValue(bean: subscriber, field: "imageDir")}</td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="subscriber.type.label" default="Type" /></td>
                            
                            <td valign="top" class="value">${subscriber?.type?.encodeAsHTML()}</td>
                            
                        </tr>
                    
                    </tbody>
                </table>
            </div>
            <div class="buttons">
                <g:form>
                    <g:hiddenField name="id" value="${subscriber?.id}" />
                    <span class="button"><g:actionSubmit class="edit" action="edit" value="${message(code: 'default.button.edit.label', default: 'Edit')}" /></span>
                    <span class="button"><g:actionSubmit class="delete" action="delete" value="${message(code: 'default.button.delete.label', default: 'Delete')}" onclick="return confirm('${message(code: 'default.button.delete.confirm.message', default: 'Are you sure?')}');" /></span>
                </g:form>
            </div>
        </div>
    </body>
</html>
