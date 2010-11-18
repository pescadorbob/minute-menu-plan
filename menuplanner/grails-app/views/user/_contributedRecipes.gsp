<g:each in="${party?.contributions}" var="recipe">
    <li><a href="${createLink(controller: 'recipe', action: 'show', id: recipe?.id)}">${recipe?.name}</a></li>
</g:each>