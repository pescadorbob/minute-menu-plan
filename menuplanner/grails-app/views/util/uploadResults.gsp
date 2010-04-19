<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <title>Minute Menu Plan</title>
    <meta name="layout" content="menu"/>
</head>
<body>
<div id="content-wrapper" class="clearfix">
    <div id="left-panel">
        <div id='divResult' style="padding:30px;font-size: 14px;">
            <g:each in="${result}">
                <span>${it}<br/></span>
            </g:each>
        </div>
    </div>
</div>
</body>
</html>