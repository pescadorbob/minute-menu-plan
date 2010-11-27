
<%@ page import="com.mp.domain.party.Subscriber" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <meta name="layout" content="menu" />
        <g:set var="entityName" value="${message(code: 'subscriber.label', default: 'Subscriber')}" />
        <title><g:message code="default.list.label" args="[entityName]" /></title>
    </head>
    <body>
        <div class="nav">
            <span class="menuButton"><a class="home" href="${createLink(uri: '/')}">Home</a></span>
            <span class="menuButton"><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link></span>
        </div>
        <div class="body">
            <h1><g:message code="default.list.label" args="[entityName]" /></h1>
            <g:if test="${flash.message}">
            <div class="message">${flash.message}</div>
            </g:if>
            <div class="list">
                <table>
                    <thead>
                        <tr>
                        
                            <g:sortableColumn property="id" title="${message(code: 'subscriber.id.label', default: 'Id')}" />
                        
                            <th><g:message code="subscriber.image.label" default="Image" /></th>
                   	    
                            <g:sortableColumn property="city" title="${message(code: 'subscriber.city.label', default: 'City')}" />
                        
                            <g:sortableColumn property="mouthsToFeed" title="${message(code: 'subscriber.mouthsToFeed.label', default: 'Mouths To Feed')}" />
                        
                            <g:sortableColumn property="introduction" title="${message(code: 'subscriber.introduction.label', default: 'Introduction')}" />
                        
                            <g:sortableColumn property="coachId" title="${message(code: 'subscriber.coachId.label', default: 'Coach Id')}" />
                        
                        </tr>
                    </thead>
                    <tbody>
                    <g:each in="${subscriberList}" status="i" var="subscriber">
                        <tr class="${(i % 2) == 0 ? 'odd' : 'even'}">
                        
                            <td><g:link action="show" id="${subscriber.id}">${fieldValue(bean: subscriber, field: "id")}</g:link></td>
                        
                            <td>${fieldValue(bean: subscriber, field: "image")}</td>
                        
                            <td>${fieldValue(bean: subscriber, field: "city")}</td>
                        
                            <td>${fieldValue(bean: subscriber, field: "mouthsToFeed")}</td>
                        
                            <td>${fieldValue(bean: subscriber, field: "introduction")}</td>
                        
                            <td>${fieldValue(bean: subscriber, field: "coachId")}</td>
                        
                        </tr>
                    </g:each>
                    </tbody>
                </table>
            </div>
            <div class="paginateButtons">
                <g:paginate total="${subscriberTotal}" />
            </div>
        </div>
    </body>
</html>
