
<%@ page import="com.mp.domain.party.PartyAddress" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <meta name="layout" content="menu" />
        <g:set var="entityName" value="${message(code: 'partyAddress.label', default: 'PartyAddress')}" />
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
                        
                            <g:sortableColumn property="id" title="${message(code: 'partyAddress.id.label', default: 'Id')}" />
                        
                            <g:sortableColumn property="thruDate" title="${message(code: 'partyAddress.thruDate.label', default: 'Thru Date')}" />
                        
                            <g:sortableColumn property="fromDate" title="${message(code: 'partyAddress.fromDate.label', default: 'From Date')}" />
                        
                            <th><g:message code="partyAddress.address.label" default="Address" /></th>
                   	    
                            <th><g:message code="partyAddress.party.label" default="Party" /></th>
                   	    
                        </tr>
                    </thead>
                    <tbody>
                    <g:each in="${partyAddressList}" status="i" var="partyAddress">
                        <tr class="${(i % 2) == 0 ? 'odd' : 'even'}">
                        
                            <td><g:link action="show" id="${partyAddress.id}">${fieldValue(bean: partyAddress, field: "id")}</g:link></td>
                        
                            <td><g:formatDate date="${partyAddress.thruDate}" /></td>
                        
                            <td><g:formatDate date="${partyAddress.fromDate}" /></td>
                        
                            <td>${fieldValue(bean: partyAddress, field: "address")}</td>
                        
                            <td>${fieldValue(bean: partyAddress, field: "party")}</td>
                        
                        </tr>
                    </g:each>
                    </tbody>
                </table>
            </div>
            <div class="paginateButtons">
                <g:paginate total="${partyAddressTotal}" />
            </div>
          </div></div></div>
        </div>
    </body>
</html>
