<%@ page import="org.codehaus.groovy.grails.commons.ConfigurationHolder" %>
<style>

h1, h2, h3, h4, h5, h6, form, p, ul, li {
    color: #000000 !important;
    list-style: none !important;
}

.winterButton ul {
    margin-bottom: 10px;
}</style>

<div id="container">
    <div id="wrapper" class="clearfix">
        <div id="content-wrapper" class="clearfix">
            <div class="headbox">
                <h3>A message from ${party}</h3>
                <h2>${subject}</h2>
            </div>
          <p>${subject}</p>
          <p>${body}</p>
            <p>To reply visit <a href="${ConfigurationHolder.config.grails.serverURL}/message/show/${messageId}">here.</a></p>
        </div>
    </div>
</div>
<g:render template="/mail/compliance" model="['tag':'message']"/>