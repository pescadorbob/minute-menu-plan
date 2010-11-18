<%@ page import="com.mp.domain.*" %>
<%@ page import="com.mp.MenuConstants.*" %>
<html>
<head>
    <title>${recipe?.name}</title>
    <meta name="layout" content="menu"/>
    <p:javascript src="jquery.printElement.min"/>
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
    <p:css name='custom-ratings-inner'/>
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
    <meta name="title" content="Minute Menu Plan : ${recipe?.name}"/>
    <meta name="description" content="${(recipe?.description ? recipe?.description : '')}"/>
    <link rel="image_src" href="${createLink(controller: 'image', action: 'image', params: [id: recipe?.image?.id, size: 200], absolute: true)}"/>
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
                    <g:if test="${(party?.subAffiliate)}">
                        <mp:shareThis coachId="${party?.uniqueId}" shareUrl="${createLink(controller:'recipe',action:'show',id:recipe.id,absolute:true)}"/>
                    </g:if>
                    <g:else>
                        <mp:shareThis shareUrl="${createLink(controller:'recipe',action:'show',id:recipe.id,absolute:true)}"/>
                    </g:else>
                </span>
                <span>
                    <mp:showEditRecipe recipeId="${recipe?.id}"/>
                </span>
                <span><p:image src='printer.gif'/><a href="#" id="printButton" title="${g.message(code: 'test.clueTip')}">Print</a></span>
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
<p:javascript src="recipe"/>
<script type="text/javascript">
    jQuery(function() {
        var path = jQuery('.recipeImage').attr('src')
        var imagePath = path.substring("0", path.lastIndexOf("="))
        imagePath = imagePath + '=640';
        jQuery('.recipeImage').wrap('<a  class="lightbox" href=' + imagePath + '></a>')
        jQuery('.recipeImage').css('border', 'none')
        jQuery('.lightbox').lightBox({
            imageBtnClose:"${resource(dir: 'images', file: 'lightbox-btn-close.gif')}"
        });
    });
    jQuery.each(jQuery("*", jQuery("#showAllStepsHereTst")), function() {
        jQuery(this).css('font-family', 'Arial');
    });
    jQuery.each(jQuery("*", jQuery("#recipeDescription")), function() {
        jQuery(this).css('font-family', 'Arial');
    });
</script>
</body>
</html>