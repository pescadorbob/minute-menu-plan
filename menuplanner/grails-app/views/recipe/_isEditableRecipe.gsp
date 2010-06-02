<g:if test="${isEditable}">
    <img src="${resource(dir: 'images', file: 'edit.gif')}"/>
    <g:link action="edit" id="${recipeId}"><span class="editRecipeLink">Edit</span></g:link>
</g:if>