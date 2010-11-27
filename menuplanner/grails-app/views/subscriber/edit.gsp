
<%@ page import="com.mp.domain.party.Subscriber" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <meta name="layout" content="menu" />
        <g:set var="entityName" value="${message(code: 'subscriber.label', default: 'Subscriber')}" />
        <title><g:message code="default.edit.label" args="[entityName]" /></title>
    </head>
    <body>
        <div class="nav">
            <span class="menuButton"><a class="home" href="${createLink(uri: '/')}">Home</a></span>
            <span class="menuButton"><g:link class="list" action="list"><g:message code="default.list.label" args="[entityName]" /></g:link></span>
            <span class="menuButton"><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link></span>
        </div>
        <div class="body">
            <h1><g:message code="default.edit.label" args="[entityName]" /></h1>
            <g:if test="${flash.message}">
            <div class="message">${flash.message}</div>
            </g:if>
            <g:hasErrors bean="${subscriber}">
            <div class="errors">
                <g:renderErrors bean="${subscriber}" as="list" />
            </div>
            </g:hasErrors>
            <g:form method="post" >
                <g:hiddenField name="id" value="${subscriber?.id}" />
                <g:hiddenField name="version" value="${subscriber?.version}" />
                <div class="dialog">
                    <table>
                        <tbody>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="image"><g:message code="subscriber.image.label" default="Image" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: subscriber, field: 'image', 'errors')}">
                                    <g:select name="image.id" from="${com.mp.domain.Image.list()}" optionKey="id" value="${subscriber?.image?.id}" noSelection="['null': '']" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="city"><g:message code="subscriber.city.label" default="City" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: subscriber, field: 'city', 'errors')}">
                                    <g:textField name="city" value="${subscriber?.city}" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="mouthsToFeed"><g:message code="subscriber.mouthsToFeed.label" default="Mouths To Feed" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: subscriber, field: 'mouthsToFeed', 'errors')}">
                                    <g:textField name="mouthsToFeed" value="${fieldValue(bean: subscriber, field: 'mouthsToFeed')}" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="introduction"><g:message code="subscriber.introduction.label" default="Introduction" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: subscriber, field: 'introduction', 'errors')}">
                                    <g:textArea name="introduction" cols="40" rows="5" value="${subscriber?.introduction}" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="coachId"><g:message code="subscriber.coachId.label" default="Coach Id" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: subscriber, field: 'coachId', 'errors')}">
                                    <g:textField name="coachId" value="${subscriber?.coachId}" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="subscriptions"><g:message code="subscriber.subscriptions.label" default="Subscriptions" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: subscriber, field: 'subscriptions', 'errors')}">
                                    <g:select name="subscriptions" from="${com.mp.domain.subscriptions.Subscription.list()}" multiple="yes" optionKey="id" size="5" value="${subscriber?.subscriptions}" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="party"><g:message code="subscriber.party.label" default="Party" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: subscriber, field: 'party', 'errors')}">
                                    <g:select name="party.id" from="${com.mp.domain.party.Party.list()}" optionKey="id" value="${subscriber?.party?.id}"  />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="imageDir"><g:message code="subscriber.imageDir.label" default="Image Dir" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: subscriber, field: 'imageDir', 'errors')}">
                                    <g:textField name="imageDir" value="${subscriber?.imageDir}" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="type"><g:message code="subscriber.type.label" default="Type" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: subscriber, field: 'type', 'errors')}">
                                    <g:select name="type" from="${com.mp.domain.PartyRoleType?.values()}" value="${subscriber?.type}"  />
                                </td>
                            </tr>
                        
                        </tbody>
                    </table>
                </div>
                <div class="buttons">
                    <span class="button"><g:actionSubmit class="save" action="update" value="${message(code: 'default.button.update.label', default: 'Update')}" /></span>
                    <span class="button"><g:actionSubmit class="delete" action="delete" value="${message(code: 'default.button.delete.label', default: 'Delete')}" onclick="return confirm('${message(code: 'default.button.delete.confirm.message', default: 'Are you sure?')}');" /></span>
                </div>
            </g:form>
        </div>
    </body>
</html>
