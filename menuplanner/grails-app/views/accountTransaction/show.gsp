
<%@ page import="com.mp.domain.accounting.AccountTransaction" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <meta name="layout" content="menu" />
        <g:set var="entityName" value="${message(code: 'accountTransaction.label', default: 'AccountTransaction')}" />
        <title><g:message code="default.show.label" args="[entityName]" /></title>
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
            <h3><g:message code="default.show.label" args="[entityName]" /></h3>
            </div>
            <g:if test="${flash.message}">
            <div class="message">${flash.message}</div>
            </g:if>
            <div class="dialog">
                <table>
                    <tbody>
                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="accountTransaction.id.label" default="Id" /></td>
                            
                            <td valign="top" class="value">${fieldValue(bean: accountTransaction, field: "id")}</td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="accountTransaction.amount.label" default="Amount" /></td>
                            
                            <td valign="top" class="value">${fieldValue(bean: accountTransaction, field: "amount")}</td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="accountTransaction.transactionType.label" default="Transaction Type" /></td>
                            
                            <td valign="top" class="value">${accountTransaction?.transactionType?.encodeAsHTML()}</td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="accountTransaction.transactionDate.label" default="Transaction Date" /></td>
                            
                            <td valign="top" class="value"><g:formatDate date="${accountTransaction?.transactionDate}" /></td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="accountTransaction.isVoid.label" default="Is Void" /></td>
                            
                            <td valign="top" class="value"><g:formatBoolean boolean="${accountTransaction?.isVoid}" /></td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="accountTransaction.description.label" default="Description" /></td>
                            
                            <td valign="top" class="value">${fieldValue(bean: accountTransaction, field: "description")}</td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="accountTransaction.transactionFor.label" default="Transaction For" /></td>
                            
                            <td valign="top" class="value"><g:link controller="account" action="show" id="${accountTransaction?.transactionFor?.id}">${accountTransaction?.transactionFor?.encodeAsHTML()}</g:link></td>
                            
                        </tr>
                    
                    </tbody>
                </table>
            </div>
            <div class="buttons">
                <g:form>
                    <g:hiddenField name="id" value="${accountTransaction?.id}" />
                    <span class="button"><g:actionSubmit class="edit" action="edit" value="${message(code: 'default.button.edit.label', default: 'Edit')}" /></span>
                    <span class="button"><g:actionSubmit class="delete" action="delete" value="${message(code: 'default.button.delete.label', default: 'Delete')}" onclick="return confirm('${message(code: 'default.button.delete.confirm.message', default: 'Are you sure?')}');" /></span>
                </g:form>
            </div>
        </div>
    </div></div></div>

    </body>
</html>
