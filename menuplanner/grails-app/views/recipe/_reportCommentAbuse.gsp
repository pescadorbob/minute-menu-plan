<g:if test="${alreadyReported}">
    <span id="showCommentAbuseReported">Abuse reported</span>
</g:if>
<g:else>
    <g:link controller="recipe" action="reportCommentAbuse" id="${comment?.id}" params="[recipeId: recipe.id]">Report this</g:link>
</g:else>