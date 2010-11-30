
<%@ page import="com.mp.domain.subscriptions.ControllerActionFeature" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <meta name="layout" content="menu" />
        <g:set var="entityName" value="${message(code: 'controllerActionFeature.label', default: 'ControllerActionFeature')}" />
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
            <g:hasErrors bean="${controllerActionFeature}">
            <div class="errors">
                <g:renderErrors bean="${controllerActionFeature}" as="list" />
            </div>
            </g:hasErrors>
            <g:form method="post" >
                <g:hiddenField name="id" value="${controllerActionFeature?.id}" />
                <g:hiddenField name="version" value="${controllerActionFeature?.version}" />
                <div class="dialog">
                    <table>
                        <tbody>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="rule"><g:message code="controllerActionFeature.rule.label" default="Rule" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: controllerActionFeature, field: 'rule', 'errors')}">
                                    <g:textField name="rule" value="${controllerActionFeature?.rule}" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="activeTo"><g:message code="controllerActionFeature.activeTo.label" default="Active To" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: controllerActionFeature, field: 'activeTo', 'errors')}">
                                    <g:datePicker name="activeTo" precision="day" value="${controllerActionFeature?.activeTo}" noSelection="['': '']" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="controllerFilter"><g:message code="controllerActionFeature.controllerFilter.label" default="Controller Filter" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: controllerActionFeature, field: 'controllerFilter', 'errors')}">
                                    <g:textField name="controllerFilter" value="${controllerActionFeature?.controllerFilter}" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="actionFilter"><g:message code="controllerActionFeature.actionFilter.label" default="Action Filter" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: controllerActionFeature, field: 'actionFilter', 'errors')}">
                                    <g:textField name="actionFilter" value="${controllerActionFeature?.actionFilter}" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="uriFilter"><g:message code="controllerActionFeature.uriFilter.label" default="Uri Filter" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: controllerActionFeature, field: 'uriFilter', 'errors')}">
                                    <g:textField name="uriFilter" value="${controllerActionFeature?.uriFilter}" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="activeFrom"><g:message code="controllerActionFeature.activeFrom.label" default="Active From" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: controllerActionFeature, field: 'activeFrom', 'errors')}">
                                    <g:datePicker name="activeFrom" precision="day" value="${controllerActionFeature?.activeFrom}"  />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="description"><g:message code="controllerActionFeature.description.label" default="Description" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: controllerActionFeature, field: 'description', 'errors')}">
                                    <g:textField name="description" value="${controllerActionFeature?.description}" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="name"><g:message code="controllerActionFeature.name.label" default="Name" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: controllerActionFeature, field: 'name', 'errors')}">
                                    <g:textField name="name" value="${controllerActionFeature?.name}" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="usedToDefine"><g:message code="controllerActionFeature.usedToDefine.label" default="Used To Define" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: controllerActionFeature, field: 'usedToDefine', 'errors')}">
                                    
<ul>
<g:each in="${controllerActionFeature?.usedToDefine?}" var="u">
    <li><g:link controller="featuredOfferingApplicability" action="show" id="${u.id}">${u?.encodeAsHTML()}</g:link></li>
</g:each>
</ul>
<g:link controller="featuredOfferingApplicability" action="create" params="['controllerActionFeature.id': controllerActionFeature?.id]">${message(code: 'default.add.label', args: [message(code: 'featuredOfferingApplicability.label', default: 'FeaturedOfferingApplicability')])}</g:link>

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
