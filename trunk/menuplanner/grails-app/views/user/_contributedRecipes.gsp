<li class="product"><ul>
  <g:each in="${party?.contributions?.sort { it.name }}" var="recipe" status="index">
    <g:if test="${index %4==0}">
      </ul><ul class="clearfix">
    </g:if>
    <li class="user-recipes"><g:render template="/recipe/showRecipeDetailForRecipeCard" model="['item':recipe ,'openInNewWindow':true]"/></li>
  </g:each>
</ul></li>