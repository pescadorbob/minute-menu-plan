
<%@ page import="com.mp.domain.pricing.Price" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <meta name="layout" content="menu" />
        <g:set var="entityName" value="${message(code: 'price.label', default: 'Price')}" />
        <title><g:message code="default.list.label" args="[entityName]" /></title>
    </head>
    <body>
       <div id="container">
  <div id="wrapper" class="clearfix">
    <div id="content-wrapper" class="clearfix">
        <div class="nav">
            <span class="menuButton"><a class="home" href="${createLink(uri: '/')}">Home</a></span>
            <span class="menuButton"><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link></span>
            <span class="menuButton"><g:link class="create" action="populateRandomPrices">populate random recipe prices</g:link></span>
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
                        
                            <g:sortableColumn property="id" title="${message(code: 'price.id.label', default: 'Id')}" />
                        
                            <g:sortableColumn property="price" title="${message(code: 'price.price.label', default: 'Price')}" />
                        
                            <th><g:message code="price.quantity.label" default="Quantity" /></th>
                   	    
                        </tr>
                    </thead>
                    <tbody>
                    <g:each in="${priceList}" status="i" var="price">
                        <tr class="${(i % 2) == 0 ? 'odd' : 'even'}">
                        
                            <td><g:link action="show" id="${price.id}">${fieldValue(bean: price, field: "id")}</g:link></td>
                        
                            <td>${fieldValue(bean: price, field: "price")}</td>
                        
                            <td>${fieldValue(bean: price, field: "quantity")}</td>
                        
                        </tr>
                    </g:each>
                    </tbody>
                </table>
            </div>
            <div class="paginateButtons">
                <g:paginate total="${priceTotal}" />
            </div>
          </div></div></div>
        </div>
    </body>
</html>
