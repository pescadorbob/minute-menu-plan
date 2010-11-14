
<%@ page import="com.mp.domain.subscriptions.FeaturedOfferingApplicability" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <meta name="layout" content="menu" />
        <g:set var="entityName" value="${message(code: 'featuredOfferingApplicability.label', default: 'FeaturedOfferingApplicability')}" />
        <title><g:message code="default.show.label" args="[entityName]" /></title>
    </head>
    <body>
        <div class="nav">
            <span class="menuButton"><a class="home" href="${createLink(uri: '/')}">Home</a></span>
            <span class="menuButton"><g:link class="list" action="list"><g:message code="default.list.label" args="[entityName]" /></g:link></span>
            <span class="menuButton"><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link></span>
        </div>
        <div class="body">
            <h1><g:message code="default.show.label" args="[entityName]" /></h1>
            <g:if test="${flash.message}">
            <div class="message">${flash.message}</div>
            </g:if>
            <div class="dialog">
                <table>
                    <tbody>
                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="featuredOfferingApplicability.id.label" default="Id" /></td>
                            
                            <td valign="top" class="value">${fieldValue(bean: featuredOfferingApplicability, field: "id")}</td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="featuredOfferingApplicability.applicableThru.label" default="Applicable Thru" /></td>
                            
                            <td valign="top" class="value">${fieldValue(bean: featuredOfferingApplicability, field: "applicableThru")}</td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="featuredOfferingApplicability.applicableFrom.label" default="Applicable From" /></td>
                            
                            <td valign="top" class="value">${fieldValue(bean: featuredOfferingApplicability, field: "applicableFrom")}</td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="featuredOfferingApplicability.applicableThruDescription.label" default="Applicable Thru Description" /></td>
                            
                            <td valign="top" class="value">${fieldValue(bean: featuredOfferingApplicability, field: "applicableThruDescription")}</td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="featuredOfferingApplicability.applicableFromDescription.label" default="Applicable From Description" /></td>
                            
                            <td valign="top" class="value">${fieldValue(bean: featuredOfferingApplicability, field: "applicableFromDescription")}</td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="featuredOfferingApplicability.describedBy.label" default="Described By" /></td>
                            
                            <td valign="top" class="value"><g:link controller="feature" action="show" id="${featuredOfferingApplicability?.describedBy?.id}">${featuredOfferingApplicability?.describedBy?.encodeAsHTML()}</g:link></td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="featuredOfferingApplicability.availableFor.label" default="Available For" /></td>
                            
                            <td valign="top" class="value"><g:link controller="productOffering" action="show" id="${featuredOfferingApplicability?.availableFor?.id}">${featuredOfferingApplicability?.availableFor?.encodeAsHTML()}</g:link></td>
                            
                        </tr>
                    
                    </tbody>
                </table>
            </div>
            <div class="buttons">
                <g:form>
                    <g:hiddenField name="id" value="${featuredOfferingApplicability?.id}" />
                    <span class="button"><g:actionSubmit class="edit" action="edit" value="${message(code: 'default.button.edit.label', default: 'Edit')}" /></span>
                    <span class="button"><g:actionSubmit class="delete" action="delete" value="${message(code: 'default.button.delete.label', default: 'Delete')}" onclick="return confirm('${message(code: 'default.button.delete.confirm.message', default: 'Are you sure?')}');" /></span>
                </g:form>
            </div>
        </div>
    </body>
</html>
