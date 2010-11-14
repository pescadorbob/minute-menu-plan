
<%@ page import="com.mp.domain.subscriptions.FeaturedOfferingApplicability" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
      <meta name="layout" content="menu"/>
        <g:set var="entityName" value="${message(code: 'featuredOfferingApplicability.label', default: 'FeaturedOfferingApplicability')}" />
        <title><g:message code="default.create.label" args="[entityName]" /></title>
    </head>
    <body>
        <div class="nav">
            <span class="menuButton"><a class="home" href="${createLink(uri: '/')}">Home</a></span>
            <span class="menuButton"><g:link class="list" action="list"><g:message code="default.list.label" args="[entityName]" /></g:link></span>
        </div>
        <div class="body">
            <h1><g:message code="default.create.label" args="[entityName]" /></h1>
            <g:if test="${flash.message}">
            <div class="message">${flash.message}</div>
            </g:if>
            <g:hasErrors bean="${featuredOfferingApplicability}">
            <div class="errors">
                <g:renderErrors bean="${featuredOfferingApplicability}" as="list" />
            </div>
            </g:hasErrors>
            <g:form action="save" method="post" >
                <div class="dialog">
                    <table>
                        <tbody>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="applicableThru"><g:message code="featuredOfferingApplicability.applicableThru.label" default="Applicable Thru" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: featuredOfferingApplicability, field: 'applicableThru', 'errors')}">
                                    <g:textField name="applicableThru" value="${featuredOfferingApplicability?.applicableThru}" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="applicableFrom"><g:message code="featuredOfferingApplicability.applicableFrom.label" default="Applicable From" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: featuredOfferingApplicability, field: 'applicableFrom', 'errors')}">
                                    <g:textField name="applicableFrom" value="${featuredOfferingApplicability?.applicableFrom}" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="applicableThruDescription"><g:message code="featuredOfferingApplicability.applicableThruDescription.label" default="Applicable Thru Description" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: featuredOfferingApplicability, field: 'applicableThruDescription', 'errors')}">
                                    <g:textField name="applicableThruDescription" value="${featuredOfferingApplicability?.applicableThruDescription}" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="applicableFromDescription"><g:message code="featuredOfferingApplicability.applicableFromDescription.label" default="Applicable From Description" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: featuredOfferingApplicability, field: 'applicableFromDescription', 'errors')}">
                                    <g:textField name="applicableFromDescription" value="${featuredOfferingApplicability?.applicableFromDescription}" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="describedBy"><g:message code="featuredOfferingApplicability.describedBy.label" default="Described By" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: featuredOfferingApplicability, field: 'describedBy', 'errors')}">
                                    <g:select name="describedBy.id" from="${com.mp.domain.subscriptions.Feature.list()}" optionKey="id" value="${featuredOfferingApplicability?.describedBy?.id}"  />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="availableFor"><g:message code="featuredOfferingApplicability.availableFor.label" default="Available For" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: featuredOfferingApplicability, field: 'availableFor', 'errors')}">
                                    <g:select name="availableFor.id" from="${com.mp.domain.subscriptions.ProductOffering.list()}" optionKey="id" value="${featuredOfferingApplicability?.availableFor?.id}"  />
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
    </body>
</html>
