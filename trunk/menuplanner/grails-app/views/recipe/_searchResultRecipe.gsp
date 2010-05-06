<div class="product clearfix">
<ul>
<g:each in="${recipeList}" var="recipe" status="index">
    <li>
        <ul>
            <li><g:link action="show" controller="recipe" id="${recipe?.id}">${recipe.name}</g:link></li>
            <li>
                <ul>
                    <li><img height="100" width="100" src="${createLink(controller: 'recipe', action: 'showImage', id: recipe?.id)}"/></li>
                    <li>
                        <ul>
                            <li><g:render template="/rating/rating"/></li>
                            <li>${recipe?.totalTime}</li>
                            <li>${recipe?.difficulty}</li>
                            <g:each in="${recipe?.ingredients?.ingredient}" var="product" status="i">
                                <g:if test="${(i<4)}"><li>${product}</li></g:if>
                            </g:each>
                        </ul>
                    </li>
                </ul>
            </li>
        </ul>
    </li>
    <g:if test="${!((index+1)%3) && ((index+1) < recipeList?.size())}">
        </ul></div><div class="product clearfix"><ul>
    </g:if>
</g:each>
</div>
<div class="paginateButtons" style="margin:5px; border:0px;">
    <util:remotePaginate controller="recipe" action="search" total="${recipeTotal}" params="[query: query]"
            max="15" offset="${params.offset}" update="rightContainer"/>
</div>
