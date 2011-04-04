<li class="product"><ul >
<g:each in="${party?.contributions?.sort { it.name }}" var="recipe">
  <li class="user-recipes"><g:render template="/recipe/showRecipeDetailForRecipeCard" model="['item':recipe ,'openInNewWindow':true]"/></li>
</g:each>
</ul></li>