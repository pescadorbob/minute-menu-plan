<g:if test="${isPrintable}"><div class="content" id="divComments">
    <strong>Comments :</strong><br/>
    <g:each in="${comments}" var="comment">
        <span class="clearfix">
            ${comment?.body?.encodeAsHTML()} - Posted by <a href="#">${comment?.poster}</a>
            &nbsp;&nbsp;
            <br/>
        </span>
    </g:each>
    <br/>
</div>
</g:if>
<g:else>
    <div class="content" id="divComments">
        <strong>Comments :</strong><br/>
        <g:each in="${comments}" var="comment">
            <span class="clearfix">
                ${comment?.body?.encodeAsHTML()} - Posted by <g:link controller="user" action="show" id="${comment?.poster?.id}">${comment?.poster}</g:link>
                &nbsp;&nbsp;<mp:reportCommentAbuse comment="${comment}" recipeId="${recipe.id}"/>
                <br/>
            </span>
        </g:each>
        <br/>
        <g:uploadForm name="addCommentForm">
            <g:textArea class="inpbox" name="comment" rows="5" cols="50" id="comment"/>
            <g:hiddenField name="recipeId" value="${recipe?.id}"/>
            <g:actionSubmit controller="recipe" action="addComment" value="Add Comment" disabled="disabled"/>
        </g:uploadForm>
    </div>
    <script type="text/javascript">
        jQuery(document).ready(function() {
            jQuery('#comment').keyup(function() {
                if (jQuery.trim(jQuery(this).val()).length > 0) {
                    jQuery('input[type="submit"]').removeAttr('disabled')
                } else {
                    jQuery('input[type="submit"]').attr('disabled', 'disabled')
                }
            })
        })
    </script>
</g:else>
