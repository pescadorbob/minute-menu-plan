<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
    <title><g:layoutTitle default="Grails"/></title>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <g:javascript library="jquery"/>
    <g:setProvider library="jquery" />
    <link rel="stylesheet" href="${resource(dir: 'css', file: 'main.css')}"/>
    <link rel="stylesheet" href="${resource(dir: 'css', file: 'global-style.css')}"/>
    <script type="text/javascript" src="${resource(dir: 'jquery.uploadify-v2.1.0', file: 'swfobject.js')}"></script>
    <script type="text/javascript" src="${resource(dir: 'jquery.uploadify-v2.1.0', file: 'jquery.uploadify.v2.1.0.min.js')}"></script>
    <script src="${resource(dir: 'js', file: 'jquery.taginput.js')}" type="text/javascript"></script>
    <link rel="stylesheet" href="${resource(dir: 'css', file: 'token-input-facebook.css')}"/>
    <script type="text/javascript" src="${resource(dir: 'js', file: 'createRecipe.js')}"></script>
    <g:layoutHead/>
</head>
<body>
<g:setProvider library="jquery" />
<g:render template="/layouts/header"/>
<div class="wrapper">
    <div class="body-container">
        <g:layoutBody/>
    </div>
    <div class="clr"></div>
</div>

<g:render template="/layouts/footer"/>
</body>
</html>