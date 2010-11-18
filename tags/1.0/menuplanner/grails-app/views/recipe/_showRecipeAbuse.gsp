<g:if test="${reported}">
    <span id="showRecipeAbuseReported">Abuse reported</span>
</g:if>
<g:else>
    <g:link controller="recipe" action="reportRecipeAbuse" name="recipeAbuse" id="${recipeId}"><span>Report abuse</span></g:link>
</g:else>
