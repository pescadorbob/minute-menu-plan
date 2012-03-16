
<%@ page import="com.mp.domain.pricing.ItemPrice" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <meta name="layout" content="menu" />
        <g:set var="entityName" value="${message(code: 'itemPrice.label', default: 'ItemPrice')}" />
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
            <g:hasErrors bean="${itemPrice}">
            <div class="errors">
                <g:renderErrors bean="${itemPrice}" as="list" />
            </div>
            </g:hasErrors>
            <g:form action="save" method="post" >
                <div class="dialog">
                    <table>
                        <tbody>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="price"><g:message code="itemPrice.price.label" default="Price" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: itemPrice, field: 'price', 'errors')}">
                                    <g:select name="price.id" from="${com.mp.domain.pricing.Price.list()}" optionKey="id" value="${itemPrice?.price?.id}"  />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="priceOf"><g:message code="itemPrice.priceOf.label" default="Price Of" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: itemPrice, field: 'priceOf', 'errors')}">
                                    <g:select name="priceOf.id" from="${com.mp.domain.Item.list()}" optionKey="id" value="${itemPrice?.priceOf?.id}"  />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="recordedOn"><g:message code="itemPrice.recordedOn.label" default="Recorded On" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: itemPrice, field: 'recordedOn', 'errors')}">
                                    <g:datePicker name="recordedOn" precision="day" value="${itemPrice?.recordedOn}"  />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="type"><g:message code="itemPrice.type.label" default="Type" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: itemPrice, field: 'type', 'errors')}">
                                    <g:select name="type" from="${com.mp.domain.pricing.PriceType?.values()}" value="${itemPrice?.type}"  />
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
