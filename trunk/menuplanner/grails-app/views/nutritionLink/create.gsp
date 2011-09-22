
<%@ page import="com.mp.domain.ndb.*" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <meta name="layout" content="menu" />
        <g:set var="entityName" value="${message(code: 'NDBProductMapping.label', default: 'NDBProductMapping')}" />
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
            <g:hasErrors bean="${NutritionLink}">
            <div class="errors">
                <g:renderErrors bean="${NutritionLink}" as="list" />
            </div>
            </g:hasErrors>
            <g:form action="save" method="post" >
                <div class="dialog">
                    <table>
                        <tbody>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="thru"><g:message code="NDBProductMapping.thru.label" default="Thru" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: NutritionLink, field: 'thru', 'errors')}">
                                    <g:datePicker name="thru" precision="day" value="${NutritionLink?.thru}" noSelection="['': '']" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="food"><g:message code="NDBProductMapping.food.label" default="Food" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: NutritionLink, field: 'food', 'errors')}">
                                    <g:select name="food.id" from="${com.mp.domain.ndb.NDBFood.list()}" optionKey="id" value="${NutritionLink?.food?.id}"  />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="frum"><g:message code="NDBProductMapping.frum.label" default="Frum" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: NutritionLink, field: 'frum', 'errors')}">
                                    <g:datePicker name="frum" precision="day" value="${NutritionLink?.frum}"  />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="perQuantity"><g:message code="NDBProductMapping.perQuantity.label" default="Per Quantity" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: NutritionLink, field: 'perQuantity', 'errors')}">
                                    <g:select name="perQuantity.id" from="${com.mp.domain.Quantity.list()}" optionKey="id" value="${NutritionLink?.perQuantity?.id}"  />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="product"><g:message code="NDBProductMapping.product.label" default="Product" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: NutritionLink, field: 'product', 'errors')}">
                                    <g:select name="product.id" from="${com.mp.domain.MeasurableProduct.list()}" optionKey="id" value="${NutritionLink?.product?.id}"  />
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
