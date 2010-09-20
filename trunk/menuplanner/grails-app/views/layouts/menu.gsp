<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" xmlns:fb="http://www.facebook.com/2008/fbml">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1"/>
    <title>Minute Menu Plan : <g:layoutTitle default="Minute Menu Plan"/></title>
    <p:css name='allMenuCss'/>
    <g:javascript library="jquery"/>
    <g:setProvider library="jquery"/>
    <p:javascript src='allMenuJquery'/>
    <g:layoutHead/>
</head>
<body>
<g:setProvider library="jquery"/>
<div id="container">
    <!-- start header -->
    <div id="header">
        <div id="logo"><a href="${resource(dir: '/')}">
            <p:image src='logo.gif'/>
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
    <span id="ajax_spinner" style="display: none;position:absolute; top:40%; left:50%; z-index:3000;">
        <p:image src='spinner.gif'/>
    </span>
    <div id="footer"></div>
    <!-- end header -->
</div>
<p:javascript src='menuplanner'/>
</body>
</html>
