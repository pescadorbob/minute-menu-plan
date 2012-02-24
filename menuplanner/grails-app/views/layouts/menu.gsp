<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page import="com.mp.domain.ElementLocation" %>

<html xmlns="http://www.w3.org/1999/xhtml" xmlns:fb="http://www.facebook.com/2008/fbml">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1"/>
    <title>Minute Menu Plan : <g:layoutTitle default="Minute Menu Plan"/></title>
  <link rel="shortcut icon" href="${resource(dir:'images',file:'favicon.ico')}" type="image/x-icon" />
  <link rel="icon" href="${resource(dir:'images',file:'favicon.ico')}" type="image/x-icon" />
    <p:css name='allMenuCss'/>
    <p:javascript src='allMenuJquery'/>
    <g:layoutHead/>
    <ga:trackPageviewAsynch />
</head>
<body>
<div id="container">
    <!-- start header -->
    <div id="header">
        <div id="logo"><a href="${resource(dir: '/')}">
            <p:image src='logo.gif'/>
        </a>
            <h1><nobr>Plan Your Month&apos;s Menu in a Minute!</nobr></h1>
        </div>
        <g:render template="/layouts/navigationBar"/>
    </div>
    <!-- end header start wrapper -->
    <div id="wrapper" class="clearfix">
    <div class="right-ads"><theme:gadget location="${ElementLocation.RIGHT}"/></div>
    <div class="left-ads"><theme:gadget location="${ElementLocation.LEFT}"/></div>
        <g:layoutBody/>
        <theme:gadget location="${ElementLocation.FOOTER}"/>
    </div>
    <!--end wrapper start footer -->
    <span id="ajax_spinner" style="display: none;position:absolute; top:40%; left:50%; z-index:3000;">
        <p:image src='spinner.gif'/>
    </span>
    <div id="footer"></div>
    
    <div class="footer">

        <div id="copyright" style="margin-bottom:5px">&copy; 2010 MinuteMenuPlan.com</div>
        <div class="analytics"><analytics:time/></div>
      
        </div>

    <!-- end header -->
</div>
<p:javascript src='menuplanner'/>
</body>
</html>
