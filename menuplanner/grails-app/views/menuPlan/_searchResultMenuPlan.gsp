%{--<div class="ratingbox resultContainer">--}%
    %{--<ul>--}%
        %{--<g:each in="${recipeList}" var="recipe" status="index">--}%
            %{--<li class="clearfix">--}%
                %{--<ul>--}%
                    %{--<li><h3><g:link action="show" controller="recipe" id="${recipe?.id}">${recipe.name}</g:link></h3></li>--}%
                    %{--<li>--}%
                        %{--<ul>--}%
                            %{--<li><img class="imgbor" src="${createLink(controller: 'recipe', action: 'showImage', id: recipe?.id)}"/></li>--}%
                            %{--<li>--}%
                                %{--<ul>--}%
                                    %{--<li><g:render template="/rating/rating"/></li>--}%
                                    %{--<g:if test="${recipe?.totalTime}">--}%
                                        %{--<li>${recipe?.totalTime}</li>--}%
                                    %{--</g:if>--}%
                                    %{--<g:if test="${recipe?.difficulty}">--}%
                                        %{--<li>${recipe?.difficulty}</li>--}%
                                    %{--</g:if>--}%
                                    %{--<g:each in="${recipe?.ingredients?.ingredient}" var="product" status="i">--}%
                                        %{--<g:if test="${(i<3)}"><li>${product}</li></g:if>--}%
                                    %{--</g:each>--}%
                                %{--</ul>--}%
                            %{--</li>--}%
                        %{--</ul>--}%
                    %{--</li>--}%
                %{--</ul>--}%
            %{--</li>--}%
        %{--</g:each>--}%

    %{--</ul>--}%
%{--</div>--}%

<ul class="resultContainer">
    <g:each in="${recipeList}" var="recipe" status="index">
        <g:render template="/menuPlan/recipeThumb" model="[recipe:recipe, index: index]"/>
    </g:each>
</ul>

<div class="paginateButtons">
    <util:remotePaginate
            controller="menuPlan"
            action="search"
            total="${recipeTotal}"
            params="[query: query]"
            max="4"
            offset="${params.offset}"
            update="searchResult"
            maxsteps="5"/>
</div>


<script type="text/javascript">

    jQuery(document).ready(function() {
        jQuery('.recipe-detail-thumb').hover(function() {
            jQuery(this).css('backgroundColor', 'orange')
        }, function() {
            jQuery(this).css('backgroundColor', '#329ca7')
        })

        jQuery(".resultContainer").sortable({
            remove:function(event, ui) {
                var elemId = jQuery(ui.item).attr("id")
                var elemNo = elemId.split("_")[1]
                if (parseInt(elemNo) == 1) {
                    jQuery(this).prepend("<li id='" + elemId + "'>" + jQuery(ui.item).clone().html() + "</li>")
                } else {
                    var prevElemNo = parseInt(elemNo) - 1
                    jQuery("#draggableSearchItem_" + prevElemNo).after("<li id='" + elemId + "'>" + jQuery(ui.item).clone().html() + "</li>")
                }
                //            jQuery('.recipe-detail-thumb').hover(function() {
                //                jQuery(this).css('backgroundColor', 'orange')
                //            }, function() {
                //                jQuery(this).css('backgroundColor', '#329ca7')
                //            })
            },
            over:function(event, ui) {
                ui.helper = jQuery(ui.helper)
                        .removeClass("recipe-detail-thumb")
                        .css("width", "50px")
                        .css("height", "10px")
                        .html(jQuery("h3", jQuery(ui.helper)).text())
            },
            opacity:0.6,
            //        tolerance: 'pointer',
            helper: 'clone',
            cursorAt: 'top',
            revert: true,
            scrollSensitivity: 40 ,
            connectWith: '.menuContainer',
            zIndex:10001
        });

    })

</script>