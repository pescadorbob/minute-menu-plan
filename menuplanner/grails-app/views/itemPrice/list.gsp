
<%@ page import="com.mp.domain.pricing.ItemPrice" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <meta name="layout" content="menu" />
        <g:set var="entityName" value="${message(code: 'itemPrice.label', default: 'ItemPrice')}" />
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
                        
                            <g:sortableColumn property="id" title="${message(code: 'itemPrice.id.label', default: 'Id')}" />
                        
                            <th><g:message code="itemPrice.price.label" default="Price" /></th>
                   	    
                            <th><g:message code="itemPrice.priceOf.label" default="Price Of" /></th>
                   	    
                            <g:sortableColumn property="recordedOn" title="${message(code: 'itemPrice.recordedOn.label', default: 'Recorded On')}" />
                        
                            <g:sortableColumn property="type" title="${message(code: 'itemPrice.type.label', default: 'Type')}" />
                        
                        </tr>
                    </thead>
                    <tbody>
                    <g:each in="${itemPriceList}" status="i" var="itemPrice">
                        <tr class="${(i % 2) == 0 ? 'odd' : 'even'}">
                        
                            <td><g:link action="show" id="${itemPrice.id}">${fieldValue(bean: itemPrice, field: "id")}</g:link></td>
                        
                            <td>${fieldValue(bean: itemPrice, field: "price")}</td>
                        
                            <td>${fieldValue(bean: itemPrice, field: "priceOf")}</td>
                        
                            <td><g:formatDate date="${itemPrice.recordedOn}" /></td>
                        
                            <td>${fieldValue(bean: itemPrice, field: "type")}</td>
                        
                        </tr>
                    </g:each>
                    </tbody>
                </table>
            </div>
            <div class="paginateButtons">
                <g:paginate total="${itemPriceTotal}" />
            </div>
          </div></div></div>
        </div>
    </body>
</html>
