
<%@ page import="com.mp.domain.SubCategory" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <meta name="layout" content="menu" />
        <g:set var="entityName" value="${message(code: 'subCategory.label', default: 'SubCategory')}" />
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
                        
                            <g:sortableColumn property="id" title="${message(code: 'subCategory.id.label', default: 'Id')}" />
                        
                            <g:sortableColumn property="name" title="${message(code: 'subCategory.name.label', default: 'Name')}" />
                        
                            <th><g:message code="subCategory.category.label" default="Category" /></th>
                   	    
                        </tr>
                    </thead>
                    <tbody>
                    <g:each in="${subCategoryList}" status="i" var="subCategory">
                        <tr class="${(i % 2) == 0 ? 'odd' : 'even'}">
                        
                            <td><g:link action="show" id="${subCategory.id}">${fieldValue(bean: subCategory, field: "id")}</g:link></td>
                        
                            <td>${fieldValue(bean: subCategory, field: "name")}</td>
                        
                            <td>${fieldValue(bean: subCategory, field: "category")}</td>
                        
                        </tr>
                    </g:each>
                    </tbody>
                </table>
            </div>
            <div class="paginateButtons">
                <g:paginate total="${subCategoryTotal}" />
            </div>
          </div></div></div>
        </div>
    </body>
</html>
