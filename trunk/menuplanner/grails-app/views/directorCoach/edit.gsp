<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <meta name="layout" content="menu" />
        <g:set var="entityName" value="${message(code: 'directorCoach.label', default: 'DirectorCoach')}" />
        <title><g:message code="default.edit.label" args="[entityName]" /></title>
    </head>
    <body>
<div id="container">
  <div id="wrapper" class="clearfix">
    <div id="content-wrapper" class="clearfix">
      <div class="nav">
            <span class="menuButton"><a class="home" href="${createLink(uri: '/')}">Home</a></span>
            <span class="menuButton"><g:link class="list" action="list"><g:message code="default.list.label" args="[entityName]" /></g:link></span>
            <span class="menuButton"><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link></span>
        </div>
        <div class="body">
            <div class="headbox">
              <h3><g:message code="default.edit.label" args="[entityName]" /></h3>
            </div>
            <g:if test="${flash.message}">
            <div class="message">${flash.message}</div>
            </g:if>
            <g:hasErrors bean="${directorCoach}">
            <div class="errors">
                <g:renderErrors bean="${directorCoach}" as="list" />
            </div>
            </g:hasErrors>
            <g:form method="post" >
                <g:hiddenField name="id" value="${directorCoach?.id}" />
                <g:hiddenField name="version" value="${directorCoach?.version}" />
                <div class="dialog">
                    <table>
                        <tbody>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="activeTo"><g:message code="directorCoach.activeTo.label" default="Active To" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: directorCoach, field: 'activeTo', 'errors')}">
                                    <g:datePicker name="activeTo" precision="day" value="${directorCoach?.activeTo}" noSelection="['': '']" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="commission"><g:message code="directorCoach.commission.label" default="Commission" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: directorCoach, field: 'commission', 'errors')}">
                                    <g:textField name="commission" value="${fieldValue(bean: directorCoach, field: 'commission')}" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="frum"><g:message code="directorCoach.frum.label" default="Supplier" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: directorCoach, field: 'frum', 'errors')}">
                                    <g:select name="frum.id" from="${com.mp.domain.party.PartyRole.list()}" optionKey="id" value="${directorCoach?.frum?.id}"  />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="to"><g:message code="directorCoach.to.label" default="Client" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: directorCoach, field: 'to', 'errors')}">
                                    <g:select name="to.id" from="${com.mp.domain.party.PartyRole.list()}" optionKey="id" value="${directorCoach?.to?.id}"  />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="activeFrom"><g:message code="directorCoach.activeFrom.label" default="Active From" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: directorCoach, field: 'activeFrom', 'errors')}">
                                    <g:datePicker name="activeFrom" precision="day" value="${directorCoach?.activeFrom}"  />
                                </td>
                            </tr>
                        
                        </tbody>
                    </table>
                </div>
                <div class="buttons">
                    <span class="button"><g:actionSubmit class="save" action="update" value="${message(code: 'default.button.update.label', default: 'Update')}" /></span>
                    <span class="button"><g:actionSubmit class="delete" action="delete" value="${message(code: 'default.button.delete.label', default: 'Delete')}" onclick="return confirm('${message(code: 'default.button.delete.confirm.message', default: 'Are you sure?')}');" /></span>
                </div>
            </g:form>
        </div>
        </div>
        </div>
        </div>
    </body>
</html>
