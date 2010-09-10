<%@ page import="org.codehaus.groovy.grails.commons.ConfigurationHolder; com.mp.domain.*" %>
<%@ page import="com.mp.MenuConstants.*" %>
<html>
<head>
    <title>Show Recipe ${recipe?.name}</title>
    <meta name="layout" content="menu"/>
    <g:javascript src="jquery.printElement.min.js"/>
    <rateable:resources/>
    <script type="text/javascript">
        jQuery(function() {
            jQuery("#printButton").click(function() {
                window.open("${createLink(controller:'recipe',action:'printRecipes',params:[ids:recipe?.id])}", 'print_Recipe', 'width=800, height=800')
                return false;
            });
        });
        function printElem(options) {
            jQuery(".printMe:first").printElement(options);
        }
    </script>
    <link rel="stylesheet" href="${resource(dir: 'css', file: 'custom-ratings-inner.css')}"/>
    <style type="text/css">
    .tooltip {
        background: transparent url('${resource(dir: 'images', file: '/tooltip/white_arrow-small.png')}');
        height: 40px;
        width: 210px;
    }

    .tooltip.bottom {
        background: transparent url('${resource(dir: 'images', file: '/tooltip/white_arrow_inverse-small.png')}');
        height: 30px;
    }

    </style>
    <script type="text/javascript" src="http://w.sharethis.com/button/buttons.js"></script><script type="text/javascript">
    stLight.options({publisher:'${ConfigurationHolder.config.externalKeys.shareThisKey}',popup:true});</script>
</head>
<body>
<div class="clearfix printMe" id="content-wrapper">
    <div id="left-panel">
        <g:if test="${flash.message}">
            <div class="flashMessage">${flash.message}</div>
        </g:if>
        <div id="right-head">
            <div id="leftpart">
                <label id="recipeNameTst">${recipe?.name}</label>
                <span id="spanRateable"><rateable:ratings bean='${recipe}'/></span>
            </div>
            <div id="rightpart">
                <span>
                    <span class="st_email"></span>
                    <span class="st_facebook"></span>
                    <span class="st_twitter"></span>
                    <span class="st_sharethis" displayText="ShareThis" st_url="${createLink(controller:'recipe',action:'show',id:recipe.id,absolute:true)}?guestVisitor=true"></span>
                </span>
                <span>
                    <mp:showEditRecipe recipeId="${recipe?.id}"/>
                </span>
                <span><img src="${resource(dir: 'images', file: 'printer.gif')}"/><a href="#" id="printButton" title="${g.message(code: 'test.clueTip')}">Print</a></span>
            </div>
        </div>
        <div class="top-shadow"><label></label></div>
        <div class="leftbox clearfix">
            <g:render template="/recipe/showRecipeDetails" model="[recipe: recipe,imageSize:200]"/>
        </div>
        <div class="bottom-shadow"><label></label></div>
    </div>
    <div id="showPrintInstructions">
        <g:render template="/recipe/printInstructions"/>
    </div>
</div>
<script type="text/javascript">
    window.onload = preventRatingClickEvent;
    function preventRatingClickEvent() {
        $(".rating a").click(function(e) {
            e.preventDefault()
        })
    }
    jQuery(document).ready(function() {
        jQuery('#showPrintInstructions').hide();
        $("#printButton").tooltip({events: {
            input: "focus mouseenter"
        },
            effect:'slide',
            sticky:true
        }).dynamic({ bottom: { direction: 'down', bounce: false } })
    });

    function showPrintInstruction() {
        jQuery('#showPrintInstructions').show()
        return false;
    }

    jQuery(function() {
        var path = jQuery('.recipeImage').attr('src')
        var imagePath = path.substring("0", path.lastIndexOf("="))
        imagePath = imagePath + '=1080';
        jQuery('.recipeImage').wrap('<a  class="lightbox" href=' + imagePath + '></a>')
        jQuery('.recipeImage').css('border', 'none')
        jQuery('.lightbox').lightBox({
            imageBtnClose:"${resource(dir: 'images', file: 'lightbox-btn-close.gif')}"
        });
    });
</script>
</body>
</html>