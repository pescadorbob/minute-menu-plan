jQuery.each(jQuery('#navigation>ul>li'), function() {
    jQuery(this).mouseover(function() {
        jQuery(this).addClass("sfhover")
    })
    jQuery(this).mouseout(function() {
        jQuery(this).removeClass("sfhover")
    })
});

jQuery(document).ready(function() {
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

jQuery('.scaleImageSize img').load(function() {
    scaleImageSize();
});
function scaleImageSize() {
    var imgH = jQuery('.scaleImageSize img').height();
    var imgW = jQuery('.scaleImageSize img').width();
    var divH = jQuery('.scaleImageSize').height();
    var divW = jQuery('.scaleImageSize').width();
    var imgRatio = imgH / imgW
    var divRatio = divH / divW
    if (imgRatio > divRatio) {
        jQuery('.scaleImageSize img').attr('height', divH);
        imgW = jQuery('.scaleImageSize img').width();
        var marginLeft = (divW - imgW) / 2
        jQuery('.scaleImageSize img').css('margin-left', marginLeft);
    }
    else {
        jQuery('.scaleImageSize img').attr('width', divW);
        imgH = jQuery('.scaleImageSize img').height();
        var marginTop = (divH - imgH) / 2
        jQuery('.scaleImageSize img').css('margin-top', marginTop);
    }
}

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
