
<%@ page import="com.mp.domain.subscriptions.FeaturedOfferingApplicability" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <meta name="layout" content="menu" />
        <g:set var="entityName" value="${message(code: 'featuredOfferingApplicability.label', default: 'FeaturedOfferingApplicability')}" />
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
                        
                            <g:sortableColumn property="id" title="${message(code: 'featuredOfferingApplicability.id.label', default: 'Id')}" />
                        
                            <g:sortableColumn property="applicableThru" title="${message(code: 'featuredOfferingApplicability.applicableThru.label', default: 'Applicable Thru')}" />
                        
                            <g:sortableColumn property="applicableFrom" title="${message(code: 'featuredOfferingApplicability.applicableFrom.label', default: 'Applicable From')}" />
                        
                            <g:sortableColumn property="applicableThruDescription" title="${message(code: 'featuredOfferingApplicability.applicableThruDescription.label', default: 'Applicable Thru Description')}" />
                        
                            <g:sortableColumn property="applicableFromDescription" title="${message(code: 'featuredOfferingApplicability.applicableFromDescription.label', default: 'Applicable From Description')}" />
                        
                            <th><g:message code="featuredOfferingApplicability.describedBy.label" default="Described By" /></th>
                   	    
                        </tr>
                    </thead>
                    <tbody>
                    <g:each in="${featuredOfferingApplicabilityList}" status="i" var="featuredOfferingApplicability">
                        <tr class="${(i % 2) == 0 ? 'odd' : 'even'}">
                        
                            <td><g:link action="show" id="${featuredOfferingApplicability.id}">${fieldValue(bean: featuredOfferingApplicability, field: "id")}</g:link></td>
                        
                            <td>${fieldValue(bean: featuredOfferingApplicability, field: "applicableThru")}</td>
                        
                            <td>${fieldValue(bean: featuredOfferingApplicability, field: "applicableFrom")}</td>
                        
                            <td>${fieldValue(bean: featuredOfferingApplicability, field: "applicableThruDescription")}</td>
                        
                            <td>${fieldValue(bean: featuredOfferingApplicability, field: "applicableFromDescription")}</td>
                        
                            <td>${fieldValue(bean: featuredOfferingApplicability, field: "describedBy")}</td>
                        
                        </tr>
                    </g:each>
                    </tbody>
                </table>
            </div>
            <div class="paginateButtons">
                <g:paginate total="${featuredOfferingApplicabilityTotal}" />
            </div>
        </div>
    </body>
</html>
