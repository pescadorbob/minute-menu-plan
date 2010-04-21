<div>
    <img src="${resource(dir: 'images', file: 'add-rgt-img4.jpg')}" width="770"/>
</div>
<div class="browse-recipes-container-content1">
    <div class="min-height-container2">
        <g:each in="${(recipeList)}" var="recipe" status="i">
        %{--<g:if test="${((i+1)%3)==1}"><div style="float: left;"></g:if>--}%
            <div class="youhave-selected-container2 recipeThumb">
                <div class="browse-recipes-content-thmb">
                    <a href="">
                        <div class="selected-data">
                            <a href="${createLink(controller: 'recipe', action: 'show', id: recipe?.id)}">
                                <g:if test="${recipe?.name?.length()<10}">
                                    ${recipe?.name}
                                </g:if>
                                <g:else>
                                    ${recipe?.name?.substring(0, 8)}...
                                </g:else>
                            </a>
                            <br/>
                            <div style="height:85px; width:85px;">
                                <g:if test="${recipe.image}">
                                    <img height="80" width="80" src="${createLink(controller: 'recipe', action: 'showImage', id: recipe?.id)}"/>
                                </g:if>
                            </div>
                            %{--<img src="${resource(dir: 'images', file: 'img1.jpg')}"/>--}%
                        </div>

                        <div class="selected-data2">
                            <div class="star-container">
                                <g:render template="/recipe/rating"/>
                            </div>
                            <em>
                                <g:if test="${recipe?.totalTime?.value<60}">
                                    ${recipe?.totalTime}.
                                </g:if>
                                <g:else>
                                    ${((recipe?.totalTime?.value) / 60).toInteger()} hrs.
                                    <g:if test="${((recipe?.totalTime?.value?.toInteger()) % 60)!=0}">
                                        ${(recipe?.totalTime?.value?.toInteger()) % 60} mins.
                                    </g:if>
                                </g:else>
                                <br/>
                                ${recipe?.difficulty}
                            </em>
                            <br/>
                            
                            <g:each in="${recipe?.ingredients?.ingredient}" status="index" var="product">
                                <g:if test="${index < 3}">
                                    <g:if test="${product?.toString()?.length()<10}">
                                        ${product?.toString()}
                                    </g:if>
                                    <g:else>
                                        ${product?.toString()?.substring(0, 8)}...<br/>
                                    </g:else>
                                </g:if>
                            </g:each>
                        </div>
                        <div class="clr"></div>
                    </a>
                </div>
            </div>
        %{--<g:if test="${((i+1)%3)==0}"></div></g:if>--}%
        </g:each>
        <div class="clr"></div>
        <div class="paginateButtons" style="margin:5px; border:0px;">
            <util:remotePaginate controller="recipe" action="search" total="${recipeTotal}" params="[query: query]"
                    max="15" offset="${params.offset}" update="rightContainer"/>
        </div>
    </div>
    <div>
        <img src="${resource(dir: 'images', file: 'left-container-img1.jpg')}" width="770" align="left">
    </div>
</div>

<script type="text/javascript">

    jQuery(document).ready(function() {
        jQuery('.recipeThumb').hover(function() {
            jQuery(this).css('backgroundColor', '#eee')
        }, function() {
            jQuery(this).css('backgroundColor', '#ffffff')
        })
    })

</script>