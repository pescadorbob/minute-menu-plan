<div id="right-panel">
    <div id="right-head-without-image">
    </div>
    <div class="top-shadow">
        <label>&nbsp;</label>
    </div>

    <div class="leftbox clearfix">
        <div class="headbox" style="margin:0px;">
            <h3>Setup your Browser for printing</h3>
        </div>
        <div id="rightElement">
            <ul>
                <li><g:message code="recipe.print.instructions"/></li>
            </ul>
            <br/>
            <div>
                <a id="printInstructionLink" href="${resource(dir: 'images', file: 'ie.png')}" title="click to view enlarge image" target="_blank">
                    <img style="border:none;" id="printInstructionImage" width="100%" src="${resource(dir: 'images', file: 'ie.png')}"/>
                </a>
            </div>

        </div>
    </div>
    <div class="bottom-shadow">
        <label>&nbsp;</label>
    </div>
</div>
<script type="text/javascript">
    var imageSrc = jQuery("#printInstructionImage").attr('src')
    var imagePath = imageSrc.substring("0", imageSrc.lastIndexOf("/"))

    if (jQuery.browser.safari) {
        imagePath = imagePath + "/" + "safari.png"
    }
    else if (jQuery.browser.opera) {
        imagePath = imagePath + "/" + "opera.png"
    }
    else if (jQuery.browser.msie) {
        imagePath = imagePath + "/" + "ie.png"
    }
    else if (jQuery.browser.mozilla) {
        imagePath = imagePath + "/" + "mozilla.png"
    }
    else {
        (jQuery('#right-panel').remove())
    }
    jQuery("#printInstructionLink").attr('href', imagePath)
    jQuery("#printInstructionImage").attr('src', imagePath)
</script>
