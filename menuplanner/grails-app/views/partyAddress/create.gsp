
<%@ page import="com.mp.domain.party.PartyAddress" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <meta name="layout" content="menu" />
        <g:set var="entityName" value="${message(code: 'partyAddress.label', default: 'PartyAddress')}" />
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
            <g:hasErrors bean="${partyAddress}">
            <div class="errors">
                <g:renderErrors bean="${partyAddress}" as="list" />
            </div>
            </g:hasErrors>
            <g:form action="save" method="post" >
                <div class="dialog">
                    <table>
                        <tbody>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="thruDate"><g:message code="partyAddress.thruDate.label" default="Thru Date" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: partyAddress, field: 'thruDate', 'errors')}">
                                    <g:datePicker name="thruDate" precision="day" value="${partyAddress?.thruDate}" noSelection="['': '']" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="fromDate"><g:message code="partyAddress.fromDate.label" default="From Date" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: partyAddress, field: 'fromDate', 'errors')}">
                                    <g:datePicker name="fromDate" precision="day" value="${partyAddress?.fromDate}"  />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="address"><g:message code="partyAddress.address.label" default="Address" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: partyAddress, field: 'address', 'errors')}">
                                    <g:select name="address.id" from="${com.mp.domain.party.Address.list()}" optionKey="id" value="${partyAddress?.address?.id}"  />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="party"><g:message code="partyAddress.party.label" default="Party" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: partyAddress, field: 'party', 'errors')}">
                                    <g:select name="party.id" from="${com.mp.domain.party.Party.list()}" optionKey="id" value="${partyAddress?.party?.id}"  />
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
