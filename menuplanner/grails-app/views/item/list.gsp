
<%@ page import="com.mp.domain.Item" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <meta name="layout" content="menu" />
        <g:set var="entityName" value="${message(code: 'item.label', default: 'Item')}" />
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
              <ul>
              <g:form name="searchForm">
                  <li>
                      <span><strong>Filter Name :</strong></span>
                      <input name="searchName" type="text" class="inpbox" value="${searchName}"/>
                      <g:actionSubmit id="btnSubmit" controller="item" action="list" name="submit" value="Submit" />
                  </li>
              </g:form>
              </ul>
                <table>
                    <thead>
                        <tr>
                        
                            <g:sortableColumn property="id" title="${message(code: 'item.id.label', default: 'Id')}" />
                        
                            <th><g:message code="item.suggestedAisle.label" default="Suggested Aisle" /></th>
                   	    
                            <g:sortableColumn property="isAlcoholic" title="${message(code: 'item.isAlcoholic.label', default: 'Is Alcoholic')}" />
                        
                            <g:sortableColumn property="shareWithCommunity" title="${message(code: 'item.shareWithCommunity.label', default: 'Share With Community')}" />
                        
                            <g:sortableColumn property="density" title="${message(code: 'item.density.label', default: 'Density')}" />
                        
                            <g:sortableColumn property="name" title="${message(code: 'item.name.label', default: 'Name')}" />
                        
                        </tr>
                    </thead>
                    <tbody>
                    <g:each in="${itemList}" status="i" var="item">
                        <tr class="${(i % 2) == 0 ? 'odd' : 'even'}">
                        
                            <td><g:link action="show" id="${item.id}">${fieldValue(bean: item, field: "id")}</g:link></td>
                        
                            <td>${fieldValue(bean: item, field: "suggestedAisle")}</td>
                        
                            <td><g:formatBoolean boolean="${item.isAlcoholic}" /></td>
                        
                            <td><g:formatBoolean boolean="${item.shareWithCommunity}" /></td>
                        
                            <td>${fieldValue(bean: item, field: "density")}</td>
                        
                            <td>${fieldValue(bean: item, field: "name")}</td>
                        
                        </tr>
                    </g:each>
                    </tbody>
                </table>
            </div>
            <div class="paginateButtons">
                <g:paginate total="${itemTotal}" />
            </div>
        </div>
    </body>
</html>
