
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
            <span class="menuButton"><g:link class="create" action="createList">Enter Prices</g:link></span>
            <span class="menuButton"><g:link class="create" action="calculateRecipePrices">Calculate Recipe Prices</g:link></span>
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
                        
                            <th><g:message code="itemPrice.price.label" default="Price" /></th>
                   	    
                            <th><g:message code="itemPrice.priceOf.label" default="Price Of" /></th>
                   	    
                            <g:sortableColumn property="recordedOn" title="${message(code: 'itemPrice.recordedOn.label', default: 'Recorded On')}" />
                        
                            <g:sortableColumn property="type" title="${message(code: 'itemPrice.type.label', default: 'Type')}" />

                            <g:sortableColumn property="grocer" title="${message(code: 'grocer.name.label', default: 'Grocer')}" />

                          <th><g:message code="itemPrice.recordedBy" default="Recorded By" /></th>

                        </tr>
                    </thead>
                    <tbody>
                    <g:each in="${itemPriceList}" status="i" var="itemPrice">
                        <tr class="${(i % 2) == 0 ? 'odd' : 'even'}">
                        
                            <td>${fieldValue(bean: itemPrice, field: "price.price")} for
                            ${fieldValue(bean: itemPrice, field: "price.quantity.value")}
                            ${fieldValue(bean: itemPrice, field: "price.quantity.unit.symbol")} 
                            </td>
                        
                            <td><g:link action="show" id="${itemPrice.id}">${fieldValue(bean: itemPrice, field: "priceOf")}</g:link></td>
                        
                            <td><g:formatDate date="${itemPrice.recordedOn}" /></td>
                        
                            <td>${fieldValue(bean: itemPrice, field: "type")}</td>
                        
                            <td>${fieldValue(bean: itemPrice, field: "grocer.organizationName.name")}</td>
                            <td>${fieldValue(bean: itemPrice, field: "recordedBy.name")}</td>

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
