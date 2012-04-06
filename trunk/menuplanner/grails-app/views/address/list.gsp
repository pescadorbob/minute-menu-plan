
<%@ page import="com.mp.domain.party.Address" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <meta name="layout" content="menu" />
        <g:set var="entityName" value="${message(code: 'address.label', default: 'Address')}" />
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
                        
                            <g:sortableColumn property="id" title="${message(code: 'address.id.label', default: 'Id')}" />
                        
                            <g:sortableColumn property="addressLines" title="${message(code: 'address.addressLines.label', default: 'Address Lines')}" />
                        
                            <g:sortableColumn property="city" title="${message(code: 'address.city.label', default: 'City')}" />
                        
                            <g:sortableColumn property="regionOrState" title="${message(code: 'address.regionOrState.label', default: 'Region Or State')}" />
                        
                            <g:sortableColumn property="zipOrPostCode" title="${message(code: 'address.zipOrPostCode.label', default: 'Zip Or Post Code')}" />
                        
                        </tr>
                    </thead>
                    <tbody>
                    <g:each in="${addressList}" status="i" var="address">
                        <tr class="${(i % 2) == 0 ? 'odd' : 'even'}">
                        
                            <td><g:link action="show" id="${address.id}">${fieldValue(bean: address, field: "id")}</g:link></td>
                        
                            <td>${fieldValue(bean: address, field: "addressLines")}</td>
                        
                            <td>${fieldValue(bean: address, field: "city")}</td>
                        
                            <td>${fieldValue(bean: address, field: "regionOrState")}</td>
                        
                            <td>${fieldValue(bean: address, field: "zipOrPostCode")}</td>
                        
                        </tr>
                    </g:each>
                    </tbody>
                </table>
            </div>
            <div class="paginateButtons">
                <g:paginate total="${addressTotal}" />
            </div>
          </div></div></div>
        </div>
    </body>
</html>
