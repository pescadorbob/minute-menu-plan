<div class="contributor-image">
    <ul><li class="clearfix">By:&nbsp;<strong><g:link controller="user" action="show" id="${recipe?.contributor?.id}">${recipe.contributor}</g:link></strong>
        <g:if test="${recipe?.contributor?.subscriber?.city}">
           ,${recipe?.contributor?.subscriber?.city}
        </g:if>
    </li>
        <li class="clearfix showAllStepsHere">
            <div id="photo50">
                <mp:image width="50" size="50" id="${recipe?.contributor?.subscriber?.image?.id}"/>
            </div>
            <span id="recipeDescription">${recipe?.description}</span>
        </li>
    </ul>
</div>