<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <meta name="layout" content="menu" />
        <g:set var="entityName" value="${message(code: 'directorCoach.label', default: 'DirectorCoach')}" />
        <title><g:message code="default.create.label" args="[entityName]" /></title>
    </head>
    <body>
<div id="container">
  <div id="wrapper" class="clearfix">
    <div id="content-wrapper" class="clearfix">
      <div class="nav">
            <span class="menuButton"><a class="home" href="${createLink(uri: '/')}">Home</a></span>
            <span class="menuButton"><g:link class="list" action="list"><g:message code="default.list.label" args="[entityName]" /></g:link></span>
        </div>
        <div class="body">
          <div class="headbox">
            <h3><g:message code="default.create.label" args="[entityName]" /></h3>
            </div>
            <g:if test="${flash.message}">
            <div class="message">${flash.message}</div>
            </g:if>
            <g:hasErrors bean="${directorCoach}">
            <div class="errors">
                <g:renderErrors bean="${directorCoach}" as="list" />
            </div>
            </g:hasErrors>
            <g:form action="save" method="post" >
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
                                    <label for="supplier"><g:message code="directorCoach.supplier.label" default="Supplier" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: directorCoach, field: 'supplier', 'errors')}">
                                    <g:select optionValue="${directorCoach?.supplier?.party?.name}" name="supplier.id"
                                            from="${com.mp.domain.party.PartyRole.list()}" optionKey="id"
                                            value="${directorCoach?.supplier?.id}"  />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="client"><g:message code="directorCoach.client.label" default="Client" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: directorCoach, field: 'client', 'errors')}">
                                    <g:select optionValue="${directorCoach?.client?.party?.name}" name="client.id" from="${com.mp.domain.party.PartyRole.list()}" optionKey="id" value="${directorCoach?.client?.id}"  />
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
                    <span class="button"><g:submitButton name="create" class="save" value="${message(code: 'default.button.create.label', default: 'Create')}" /></span>
                </div>
            </g:form>
        </div>
        </div>
        </div>
        </div>
    </body>
</html>
