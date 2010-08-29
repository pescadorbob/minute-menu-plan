<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" xmlns:fb="http://www.facebook.com/2008/fbml">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1"/>
    <title>Minute Menu Plan : <g:layoutTitle default="Minute Menu Plan"/></title>
    <link rel="stylesheet" href="${resource(dir: 'css', file: 'common.css')}"/>
    <link rel="stylesheet" href="${resource(dir: 'css', file: 'layout.css')}"/>
    <link rel="stylesheet" href="${resource(dir: 'css', file: 'menuPlan.css')}"/>
    <link rel="stylesheet" href="${resource(dir: 'css', file: 'user.css')}"/>
    <link rel="stylesheet" href="${resource(dir: 'css', file: 'securityRole.css')}"/>
    <link rel="stylesheet" href="${resource(dir: 'css', file: 'jquery.autocomplete.css')}"/>
    <link rel="stylesheet" href="${resource(dir: 'css', file: 'jquery.lightbox-0.5.css')}"/>
    %{--<link rel="stylesheet" href="${resource(dir: 'css', file: 'token-input-facebook.css')}"/>--}%
    <g:javascript library="jquery"/>
    <g:setProvider library="jquery"/>
    <script type="text/javascript" src="${resource(dir: '/js/tiny_mce', file: 'tiny_mce.js')}"></script
    <script type="text/javascript" src="${resource(dir: 'jquery.uploadify-v2.1.0', file: 'swfobject.js')}"></script>
    <script type="text/javascript" src="${resource(dir: 'jquery.uploadify-v2.1.0', file: 'jquery.uploadify.v2.1.0.min.js')}"></script>
    <script type="text/javascript" src="${resource(dir: 'js', file: 'createRecipe.js')}"></script>
    <script type="text/javascript" src="${resource(dir: 'js', file: 'jquery.autocomplete.min.js')}"></script>
    <script type="text/javascript" src="${resource(dir: 'js', file: 'jquery.tools.min.js')}"></script>
    <script type="text/javascript" src="${resource(dir: 'js', file: 'shoppingList.js')}"></script>
    <script type="text/javascript" src="${resource(dir: 'js', file: 'menuplanner.js')}"></script>
    <script type="text/javascript" src="${resource(dir: 'js', file: 'jquery.lightbox-0.5.js')}"></script>
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
    <span id="ajax_spinner" style="display: none;position:absolute; top:40%; left:50%; z-index:3000;">
        <img src="${createLinkTo(dir: 'images', file: 'spinner.gif')}"/>
    </span>
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

    jQuery(document).ready(function() {
        scaleImageSize();
        jQuery("#ajax_spinner").ajaxStart(function() {
            jQuery(this).show();
        });
        jQuery("#ajax_spinner").ajaxComplete(function(request, xhr) {
            var rText = xhr.responseText;
            if (rText.indexOf("Session TimedOut") > 0) {
                window.location.href = rText.substring(rText.indexOf("=") + 1, rText.length);
            }
            jQuery(this).hide();
            adjustDropDownWidth();
        });
        jQuery.ajaxSetup({cache: false});
    });

    function adjustDropDownWidth() {
        if (jQuery.browser.msie) {
            jQuery('.auto-resize').each(function() {
                var originalWidth = jQuery(this).css('width');
                jQuery(this).parent('div').css('overflow', 'hidden');
                jQuery(this).parent('span').css('overflow', 'hidden');
                jQuery(this).blur(function() {
                    jQuery(this).width(originalWidth);
                });
                jQuery(this).change(function() {
                    jQuery(this).width(originalWidth);
                });
                jQuery(this).mousedown(function() {
                    jQuery(this).css("width", "auto");
                });
            });
        }
    }
</script>
</body>
</html>
