<%@ page import="grails.util.Environment; grails.util.GrailsUtil" %>
<g:form controller="login" name="loginForm">
  <g:hiddenField name="targetUri" value="${params.targetUri}"/>

  <li>Username : (Email Address)<div class="search-input">
    <input name="email" type="text" class="inp  ${hasErrors(bean: loginCO, field: 'email', 'loginExc')}" value="${loginCO?.email}"/></div>
    <g:hasErrors bean="${loginCO}" field="email">
      <div id="displayEmailError" class="loginError">
        <g:renderErrors bean="${loginCO}" field="email"/>
      </div>
    </g:hasErrors>
  </li>
  <li>Password :<div class="search-input">
    <input name="password" type="password" class="inp  ${hasErrors(bean: loginCO, field: 'password', 'loginExc')}" value=""/></div>
    <g:hasErrors bean="${loginCO}" field="password">
      <div id="displayPasswordError" class="loginError">
        <g:renderErrors bean="${loginCO}" field="password"/>
      </div>
    </g:hasErrors>

    <g:if test="${flash.message}">
      <div id="display_WrongPassword_DisabledUser_Error" class="loginError">
        ${flash.message}
      </div>
    </g:if>
  </li>
  <li>
    <g:actionSubmit controller="login" action="login" value="Login" class="userLoginLink"/>
  </li>
  <li><g:link controller="login" action="forgotPassword">forgot password or username?</g:link></li>
  <span style="color:#007AD8">Or login using Facebook</span>
  <g:if test="${!(GrailsUtil.environment in ['test'])}">
    <fb:login-button onlogin="loginToMenuPlanner()" autologoutlink="true">Login</fb:login-button>
  </g:if>
</g:form>
