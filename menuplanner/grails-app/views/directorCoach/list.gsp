<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <meta name="layout" content="menu" />
        <g:set var="entityName" value="${message(code: 'directorCoach.label', default: 'DirectorCoach')}" />
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
                        
                            <g:sortableColumn property="id" title="${message(code: 'directorCoach.id.label', default: 'Id')}" />
                        
                            <g:sortableColumn property="activeTo" title="${message(code: 'directorCoach.activeTo.label', default: 'Active To')}" />
                        
                            <g:sortableColumn property="commission" title="${message(code: 'directorCoach.commission.label', default: 'Commission')}" />
                        
                            <th><g:message code="directorCoach.supplier.label" default="Supplier" /></th>
                   	    
                            <th><g:message code="directorCoach.client.label" default="Client" /></th>
                   	    
                        </tr>
                    </thead>
                    <tbody>
                    <g:each in="${directorCoachList}" status="i" var="directorCoach">
                        <tr class="${(i % 2) == 0 ? 'odd' : 'even'}">
                        
                            <td><g:link action="show" id="${directorCoach.id}">${fieldValue(bean: directorCoach, field: "id")}</g:link></td>
                        
                            <td><g:formatDate date="${directorCoach.activeTo}" /></td>
                        
                            <td>${fieldValue(bean: directorCoach, field: "commission")}</td>
                        
                            <td>${fieldValue(bean: directorCoach, field: "supplier")}</td>
                        
                            <td>${fieldValue(bean: directorCoach, field: "client")}</td>
                        
                        </tr>
                    </g:each>
                    </tbody>
                </table>
            </div>
            <div class="paginateButtons">
                <g:paginate total="${directorCoachTotal}" />
            </div>
          </div></div></div>
        </div>
    </body>
</html>
