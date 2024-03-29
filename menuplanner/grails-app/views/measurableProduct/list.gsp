
<%@ page import="com.mp.domain.MeasurableProduct" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <meta name="layout" content="main" />
        <g:set var="entityName" value="${message(code: 'measurableProduct.label', default: 'MeasurableProduct')}" />
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
                        
                            <g:sortableColumn property="id" title="${message(code: 'measurableProduct.id.label', default: 'Id')}" />
                        
                            <g:sortableColumn property="name" title="${message(code: 'measurableProduct.name.label', default: 'Name')}" />
                        
                            <th><g:message code="measurableProduct.preferredUnit.label" default="Preferred Unit" /></th>
                   	    
                        </tr>
                    </thead>
                    <tbody>
                    <g:each in="${measurableProductList}" status="i" var="measurableProduct">
                        <tr class="${(i % 2) == 0 ? 'odd' : 'even'}">
                        
                            <td><g:link action="show" id="${measurableProduct.id}">${fieldValue(bean: measurableProduct, field: "id")}</g:link></td>
                        
                            <td>${fieldValue(bean: measurableProduct, field: "name")}</td>
                        
                            <td>${fieldValue(bean: measurableProduct, field: "preferredUnit")}</td>
                        
                        </tr>
                    </g:each>
                    </tbody>
                </table>
            </div>
            <div class="paginateButtons">
                <g:paginate total="${measurableProductTotal}" />
            </div>
        </div>
    </body>
</html>
