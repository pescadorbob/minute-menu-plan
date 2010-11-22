<%@ page import="com.mp.domain.Permission" %>
<g:each in="${party?.favourites.sort{it.name}}" var="recipe">
    <li>
        <a href="${createLink(controller: 'recipe', action: 'show', id: recipe?.id)}">${recipe?.name}</a>
        <g:if test="${permission.hasPermission(permission: Permission.REMOVE_RECIPE_FROM_FAVOURITES, party: party)}">
            <a href="#" onclick="return removeFromFavorite(this, '${createLink(controller: 'user', action: 'removeFavorite', id: recipe?.id)}')">remove</a>
        </g:if>
    </li>
</g:each>
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