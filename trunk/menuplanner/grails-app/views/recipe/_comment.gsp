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
                <h3>A comment from ${party}</h3>
            </div>
            <p>${ConfigurationHolder.config.grails.serverURL}/recipe/show/${recipeId}</p>
            <p>${note}</p>
        </div>
    </div>
</div>