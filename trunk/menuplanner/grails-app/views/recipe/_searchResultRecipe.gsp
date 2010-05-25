<div class="product clearfix">
<ul>
<g:each in="${recipeList}" var="recipe" status="index">
    <li onclick="location.href = '${createLink(controller:'recipe', action:'show', id:recipe?.id)}'">
        <ul>
            <li><strong>${recipe.name}</strong></li>
            <li>
                <ul>
                    <li><mp:recipeImage id="${recipe?.image?.id}" noImage="no-img.gif" height="100" width="100"/></li>
                    <li>
                        <ul>
                            <li>
                                <rateable:ratings bean='${recipe}' active="false"/>
                            </li>
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
<div class="paginateButtons">
    <util:remotePaginate controller="recipe" action="search" total="${recipeTotal}" params="[query: query]"
            max="15" offset="${params.offset}" update="rightContainer"/>
</div>
