<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1"/>
    <title>Minute Menu Plan : <g:layoutTitle default="Minute Menu Plan"/></title>
    <link rel="stylesheet" href="${resource(dir: 'css', file: 'common.css')}"/>
    <link rel="stylesheet" href="${resource(dir: 'css', file: 'layout.css')}"/>
    <link rel="stylesheet" href="${resource(dir: 'css', file: 'menuPlan.css')}"/>

    <link rel="stylesheet" href="${resource(dir: 'css', file: 'user.css')}"/>
    <link rel="stylesheet" href="${resource(dir: 'css', file: 'securityRole.css')}"/>
    <link rel="stylesheet" href="${resource(dir: 'css', file: 'jquery.autocomplete.css')}"/>
    %{--<link rel="stylesheet" href="${resource(dir: 'css', file: 'token-input-facebook.css')}"/>--}%
    <g:javascript library="jquery"/>
    <g:setProvider library="jquery"/>
    <script type="text/javascript" src="${resource(dir: 'jquery.uploadify-v2.1.0', file: 'swfobject.js')}"></script>
    <script type="text/javascript" src="${resource(dir: 'jquery.uploadify-v2.1.0', file: 'jquery.uploadify.v2.1.0.min.js')}"></script>
    <script type="text/javascript" src="${resource(dir: 'js', file: 'createRecipe.js')}"></script>
    <script type="text/javascript" src="${resource(dir: 'js', file: 'jquery.autocomplete.min.js')}"></script>
    <script type="text/javascript" src="${resource(dir: 'js', file: 'jquery.tools.min.js')}"></script>
    <script type="text/javascript" src="${resource(dir: 'js', file: 'shoppingList.js')}"></script>
    <g:layoutHead/>
    
</head>
<body>
<g:setProvider library="jquery"/>
<div id="container">
    <!-- start header -->
    <div id="header">
        <div id="logo"><a href="${resource(dir: '/')}">
            <img src="${resource(dir: 'images', file: 'logo.gif')}" border="0"/>
        </a>
            <h1><nobr>Plan Your Month's Menu in a Minute!</nobr></h1>
        </div>
        <g:render template="/layouts/navigationBar"/>
    </div>
    <!-- end header start wrapper -->
    <div id="wrapper" class="clearfix">
        <g:layoutBody/>
    </div>
    <!--end wrapper start footer -->
    <div id="footer"></div>
    <!-- end header -->
</div>
<script type="text/javascript">
    jQuery.each(jQuery('#navigation>ul>li'), function() {
        jQuery(this).mouseover(function() {
            jQuery(this).addClass("sfhover")
        })
        jQuery(this).mouseout(function() {
            jQuery(this).removeClass("sfhover")
        })
    });
</script>
</body>
</html>
