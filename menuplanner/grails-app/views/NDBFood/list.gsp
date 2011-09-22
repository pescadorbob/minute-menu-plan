
<%@ page import="com.mp.domain.ndb.NDBFood" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <meta name="layout" content="menu" />
        <g:set var="entityName" value="${message(code: 'NDBFood.label', default: 'NDBFood')}" />
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
                        
                            <g:sortableColumn property="id" title="${message(code: 'NDBFood.id.label', default: 'Id')}" />
                        
                            <g:sortableColumn property="ndbNo" title="${message(code: 'NDBFood.ndbNo.label', default: 'Ndb No')}" />
                        
                            <g:sortableColumn property="refuse_Pct" title="${message(code: 'NDBFood.refuse_Pct.label', default: 'Refuse Pct')}" />
                        
                            <g:sortableColumn property="water" title="${message(code: 'NDBFood.water.label', default: 'Water')}" />
                        
                            <g:sortableColumn property="energ_Kcal" title="${message(code: 'NDBFood.energ_Kcal.label', default: 'Energ Kcal')}" />
                        
                            <g:sortableColumn property="protein" title="${message(code: 'NDBFood.protein.label', default: 'Protein')}" />
                        
                        </tr>
                    </thead>
                    <tbody>
                    <g:each in="${NDBFoodList}" status="i" var="NDBFood">
                        <tr class="${(i % 2) == 0 ? 'odd' : 'even'}">
                        
                            <td><g:link action="show" id="${NDBFood.id}">${fieldValue(bean: NDBFood, field: "id")}</g:link></td>
                        
                            <td>${fieldValue(bean: NDBFood, field: "ndbNo")}</td>
                        
                            <td>${fieldValue(bean: NDBFood, field: "refuse_Pct")}</td>
                        
                            <td>${fieldValue(bean: NDBFood, field: "water")}</td>
                        
                            <td>${fieldValue(bean: NDBFood, field: "energ_Kcal")}</td>
                        
                            <td>${fieldValue(bean: NDBFood, field: "protein")}</td>
                        
                        </tr>
                    </g:each>
                    </tbody>
                </table>
            </div>
            <div class="paginateButtons">
                <g:paginate total="${NDBFoodTotal}" />
            </div>
          </div></div></div>
        </div>
    </body>
</html>
