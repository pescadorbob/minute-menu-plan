
<%@ page import="com.mp.domain.MeasuredProduct" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <meta name="layout" content="main" />
        <g:set var="entityName" value="${message(code: 'measuredProduct.label', default: 'MeasuredProduct')}" />
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
                        
                            <g:sortableColumn property="id" title="${message(code: 'measuredProduct.id.label', default: 'Id')}" />
                        
                            <g:sortableColumn property="name" title="${message(code: 'measuredProduct.name.label', default: 'Name')}" />
                        
                            <th><g:message code="measuredProduct.preferredMetric.label" default="Preferred Metric" /></th>
                   	    
                        </tr>
                    </thead>
                    <tbody>
                    <g:each in="${measuredProductList}" status="i" var="measuredProduct">
                        <tr class="${(i % 2) == 0 ? 'odd' : 'even'}">
                        
                            <td><g:link action="show" id="${measuredProduct.id}">${fieldValue(bean: measuredProduct, field: "id")}</g:link></td>
                        
                            <td>${fieldValue(bean: measuredProduct, field: "name")}</td>
                        
                            <td>${fieldValue(bean: measuredProduct, field: "preferredMetric")}</td>
                        
                        </tr>
                    </g:each>
                    </tbody>
                </table>
            </div>
            <div class="paginateButtons">
                <g:paginate total="${measuredProductTotal}" />
            </div>
        </div>
    </body>
</html>
