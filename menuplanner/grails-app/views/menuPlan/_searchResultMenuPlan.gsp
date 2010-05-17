<div class="ratingbox">
    <ul class="resultContainer">
        <g:each in="${recipeList}" var="recipe" status="index">
            <li class="clearfix draggableItem" id="draggableSearchItem_${index + 1}">
                <ul>
                    <li>
                        <input type="hidden" name="menuItemId" value="${recipe.id}"/>
                        <h3 class="recipeName"><g:link action="show" controller="recipe" id="${recipe?.id}">${recipe.name}</g:link></h3></li>
                    <li>
                        <ul>
                            <li><img class="imgbor" src="${createLink(controller: 'image', action: 'image', id: recipe?.image?.id)}"/></li>
                            <li>
                                <ul>
                                    <li><g:render template="/rating/rating"/></li>
                                    <g:if test="${recipe?.totalTime}">
                                        <li>${recipe?.totalTime}</li>
                                    </g:if>
                                    <g:if test="${recipe?.difficulty}">
                                        <li>${recipe?.difficulty}</li>
                                    </g:if>
                                    <g:each in="${recipe?.ingredients?.ingredient}" var="product" status="i">
                                        <g:if test="${(i<3)}"><li>${product}</li></g:if>
                                    </g:each>
                                </ul>
                            </li>
                        </ul>
                    </li>
                </ul>
            </li>
        </g:each>
    </ul>
</div>

%{--<ul class="resultContainer">--}%
    %{--<g:each in="${recipeList}" var="recipe" status="index">--}%
        %{--<g:render template="/menuPlan/recipeThumb" model="[recipe:recipe, index: index]"/>--}%
    %{--</g:each>--}%
%{--</ul>--}%

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
