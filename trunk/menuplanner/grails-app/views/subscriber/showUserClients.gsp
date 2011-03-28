<%@ page import="com.mp.domain.party.Subscriber" %>
<html>
<head>
  <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
  <meta name="layout" content="menu"/>
  <g:set var="entityName" value="${message(code: 'subscriber.label', default: 'Subscriber')}"/>
  <title><g:message code="default.list.label" args="[entityName]"/></title>
</head>
<body>
<div id="container">
  <div id="wrapper" class="clearfix">
    <div id="content-wrapper" class="clearfix">
      <div class="headbox">
        <h3>Your Clients</h3>
        </div>
        <g:if test="${flash.message}">
          <div class="message">${flash.message}</div>
        </g:if>
        <div class="list">
          <table>
            <thead>
            <tr>
              <th>Name</th>
              <th>Since</th>
              <th>Last Login</th>
              <th>Subscriptions</th>
              <th>Earning Rate</th>
            </tr>
            </thead>
            <tbody>
            <!--      CoachSubscriber.createCriteria()
                    [subscriberList: clients, subscriberTotal: clients.size()] -->
            <g:each in="${subscriberList}" status="i" var="coachSubscriber">
              <tr class="${(i % 2) == 0 ? 'odd' : 'even'}">

                <td><g:link action="show" controller="user" id="${coachSubscriber.to.party.id}">${coachSubscriber.to.party.name}</g:link></td>
                <td><g:formatDate format="dd-MMM-yyyy" date="${coachSubscriber.activeFrom}"/></td>
                <td><g:formatDate format="dd-MMM-yyyy" date="${coachSubscriber.to.party.lastLogin}"/></td>
                <td><ul><g:each in="${coachSubscriber.to.subscriptions}" var="subscription">
                  <li>${subscription.originalProductOffering}
                  [<g:formatDate format="dd-MMM-yyyy" date="${subscription.activeFrom}"/> thru
                  <g:if test="${subscription.activeTo}">
                    <g:formatDate format="dd-MMM-yyyy" date="${subscription.activeTo}"/>
                    <g:if test="${subscription.activeTo<new Date()}">: EXPIRED</g:if>
                  </g:if>]
                  </li>
                </g:each></ul>
                </td>

                <td class="percentage"><g:formatNumber number="${coachSubscriber.commission}" format="% #0"/></td>

              </tr>
            </g:each>
            </tbody>
          </table>
        </div>
        <div class="paginateButtons">
          <g:paginate total="${subscriberTotal}" id="${id}"/>
        </div>
      </div></div></div>
</body>
</html>
