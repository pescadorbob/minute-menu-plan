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
            <div class="filter">
              <g:form method="post" action="showCoaches">
            <g:hiddenField name="id" value="${party?.id}"/>
            <table><tbody>
            <tr class="prop">
              <td valign="top" class="name">
                <label for="from">From</label>
                <g:datePicker name="from" precision="day" value="${from}"/>
                <label for="thru">Thru</label>
                <g:datePicker name="thru" precision="day" value="${thru}"/>
              </td>
            </tr>
                <tr><td colspan="2"><g:textField name="nameFilter" value="${nameFilter}"/>
                <g:submitButton name="filter" value="Filter"/>
                </td></tr>
            </tbody></table>
          </g:form>
              </div>
            <div class="list">
                <table>
                    <thead>
                        <tr>
                            <th>Name</th>
                            <th>Since</th>
                            <th>Director - Coach %</th>
                        </tr>
                    </thead>
                    <tbody>
                    <g:each in="${directorCoachList}" status="i" var="directorCoach">
                        <tr class="${(i % 2) == 0 ? 'odd' : 'even'}">
                        
                            <td><g:link action="show" id="${directorCoach.id}">${directorCoach?.supplier?.party?.name}</g:link></td>
                        
                            <td><g:formatDate date="${directorCoach.activeFrom}" /></td>
                        
                            <td>${fieldValue(bean: directorCoach, field: "commission")}</td>
                        
                        </tr>
                    </g:each>
                    </tbody>
                </table>
            </div>
          </div></div></div>
        </div>
    </body>
</html>
