<span>
    <a href="#" onclick="return unlinkFacebookAccount(this, '${createLink(controller: 'user', action: 'unlinkFacebookAccount', id: party?.id)}')">Unlink Account</a>
</span>
<script type="text/javascript">
    function unlinkFacebookAccount(elementToRemove, ajaxUrl) {
        jQuery.get(ajaxUrl,
        { ajax: 'true'}, function(data) {
            jQuery(elementToRemove).parent().parent().html(data)
        });
        return false;
    }
</script>
