<g:each in="${party?.favourites}" var="recipe">
    <li>
        <a href="${createLink(controller: 'recipe', action: 'show', id: recipe?.id)}">${recipe?.name}</a>
        <a href="#" onclick="return removeFromFavorite(this, '${createLink(controller: 'user', action: 'removeFavorite', id: recipe?.id)}')">remove</a>
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