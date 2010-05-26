<g:if test="${isEditable}">
    <img src="${resource(dir: 'images', file: 'edit.gif')}"/>
    <g:link action="edit" id="${recipeId}">Edit</g:link>
</g:if>