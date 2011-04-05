<%@ page import="com.mp.domain.Permission" %>
<li class="product"><ul >
<g:each in="${party?.favourites?.sort{it.name}}" var="recipe" status="index">
    <g:if test="${index %4==0}">
      </ul><ul class="clearfix">
    </g:if>
    <li  class="user-recipes">
      <g:render template="/recipe/showRecipeDetailForRecipeCard" model="['item':recipe ,'openInNewWindow':true]"/>
        <g:if test="${permission.hasPermission(permission: Permission.REMOVE_RECIPE_FROM_FAVOURITES, party: party)}">
            <a href="#" onclick="return removeFromFavorite(this, '${createLink(controller: 'user', action: 'removeFavorite', id: recipe?.id)}')">remove</a>
        </g:if>
    </li>
</g:each>
  </ul></li>
<script type="text/javascript">
    function removeFromFavorite(elementToRemove, ajaxUrl) {
        jQuery.get(ajaxUrl,
        { ajax: 'true'}, function(data) {
            if (data == 'true') {
                jQuery(elementToRemove).parent().remove()
            }
        });
        return false;
    }
</script>