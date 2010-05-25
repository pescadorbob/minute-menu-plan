<div class="content">
    <strong>Comments :</strong><br/>
    <g:each in="${comments}" var="comment">
        <span class="clearfix">
            ${comment?.body} - Posted by <g:link controller="user" action="show" id="${comment?.poster?.id}">${comment?.poster}</g:link>
             &nbsp;&nbsp;&nbsp;<mp:reportCommentAbuse comment="${comment}" />
            <br/>
        </span>
    </g:each>
    <br/>
    <g:uploadForm name="addCommentForm">
        <g:textArea class="inpbox" name="comment" rows="5" cols="50"/>
        <g:hiddenField name="recipeId" value="${recipe?.id}"/>
        <g:actionSubmit controller="recipe" action="addComment" value="Add Comment"/>
    </g:uploadForm>
</div>
