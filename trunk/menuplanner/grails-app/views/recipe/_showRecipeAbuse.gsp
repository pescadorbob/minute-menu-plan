<g:if test="${reported}">
    Reported abuse
</g:if>
<g:else>
    <g:link controller="recipe" action="reportRecipeAbuse" name="recipeAbuse" id="${recipeId}">
        Report abuse
    </g:link>
</g:else>
