<style type="text/css">
.paginateButtons {
    border: 0px;
    color: #666;
    font-size: 10px;
    overflow: hidden;
    padding: 10px 3px;
    margin-top: 10px;
}

.paginateButtons a {
    font-weight: bold;
    font-size: 11px;
    color: royalblue;
    margin: 0 3px;
}

.paginateButtons span {
    padding: 2px 3px;
}
</style>

<div class="product clearfix">
    <ul>
        <g:each in="${(recipeList)}" var="recipe" status="i">
            <li class="recipeThumb">
                <ul>
                    <li class="center">
                        <ul>
                            <li>
                                <a href="${createLink(controller: 'recipe', action: 'show', id: recipe?.id)}">
                                    <g:if test="${recipe?.name?.length()<10}">
                                        ${recipe?.name}
                                    </g:if>
                                    <g:else>
                                        ${recipe?.name?.substring(0, 8)}...
                                    </g:else>
                                </a>
                            </li>
                            <li>
                                <g:if test="${recipe.image}">
                                    <img height="100" width="100" src="${createLink(controller: 'recipe', action: 'showImage', id: recipe?.id)}"/>
                                </g:if>
                                <g:else>
                                    <img src="${resource(dir: 'images', file: 'no-img.gif')}" width="100" height="100"/>
                                </g:else>
                            </li>
                        </ul>
                    </li>
                    <li>
                        <ul>
                            <li>
                                <g:render template="/recipe/rating"/>
                            </li>
                            <li>
                                ${recipe?.totalTime}
                            </li>
                            <li>
                                ${recipe?.difficulty}
                            </li>
                            <g:each in="${recipe?.ingredients?.ingredient}" status="index" var="product">
                                <li>
                                    <g:if test="${index < 3}">
                                        <g:if test="${product?.toString()?.length()<10}">
                                            ${product?.toString()}
                                        </g:if>
                                        <g:else>
                                            ${product?.toString()?.substring(0, 8)}...<br/>
                                        </g:else>
                                    </g:if>
                                </li>
                            </g:each>
                        </ul>
                    </li>
                </ul>
            </li>
        </g:each>
    </ul>
    <div class="clr">
    </div>
</div>
<div class="paginateButtons" style="margin:5px; border:0px;">
    <util:remotePaginate controller="recipe" action="search" total="${recipeTotal}" params="[query: query]"
            max="15" offset="${params.offset}" update="rightContainer"/>
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