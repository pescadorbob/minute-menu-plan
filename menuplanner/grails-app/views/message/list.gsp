
<%@ page import="com.mp.domain.social.Message" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <meta name="layout" content="menu" />
        <g:set var="entityName" value="${message(code: 'message.label', default: 'Message')}" />
        <title><g:message code="default.list.label" args="[entityName]" /></title>
    </head>
    <body>
       <div id="container">
  <div id="wrapper" class="clearfix">
    <div id="content-wrapper" class="clearfix">
        <div class="nav">
            <span class="menuButton"><a class="home" href="${createLink(uri: '/')}">Home</a></span>
            <span class="menuButton"><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link></span>
        </div>
        <div class="body">
            <h3><g:message code="default.list.label" args="[entityName]" /></h3>
            <g:if test="${flash.message}">
            <div class="message">${flash.message}</div>
            </g:if>
            <div class="list">
                <table>
                    <thead>
                        <tr>
                        
                            <g:sortableColumn property="subject" title="${message(code: 'message.subject.label', default: 'Subject')}" />
                        
                            <th><g:message code="message.frum.label" default="From" /></th>
                   	    
                            <th><g:message code="message.replyTo.label" default="Reply To" /></th>
                   	    
                            <th><g:message code="message.to.label" default="To" /></th>
                   	    
                        </tr>
                    </thead>
                    <tbody>
                    <g:each in="${messageList}" status="i" var="message">
                        <tr class="${(i % 2) == 0 ? 'odd' : 'even'}">
                        
                            <td><g:link action="show" id="${message.id}">${fieldValue(bean: message, field: "subject")}</g:link></td>
                        
                            <td>${fieldValue(bean: message, field: "frum")}</td>
                        
                            <td>${fieldValue(bean: message.replyTo, field: "subject")}</td>
                        
                            <td>${fieldValue(bean: message, field: "to")}</td>
                        
                        </tr>
                    </g:each>
                    </tbody>
                </table>
            </div>
            <div class="paginateButtons">
                <g:paginate total="${messageTotal}" />
            </div>
          </div></div></div>
        </div>
    </body>
</html>
