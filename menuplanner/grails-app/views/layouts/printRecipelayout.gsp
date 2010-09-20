<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1"/>
    <title>Minute Menu Plan : <g:layoutTitle default="Minute Menu Plan"/></title>
    <p:css name="allPrintRecipeCss"/>
    <g:javascript library="jquery"/>
    <g:setProvider library="jquery"/>
    <p:javascript src="allPrintRecipeJquery"/>
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
            <h1></h1>
        </div>
    </div>
    <!-- end header start wrapper -->
    <div id="wrapper" class="clearfix">
        <g:layoutBody/>
    </div>
    <!--end wrapper start footer -->
    <div id="footer"></div>
    <!-- end header -->
</div>
</body>
</html>
