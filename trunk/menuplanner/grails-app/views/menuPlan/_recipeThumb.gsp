<li class="recipe-detail-thumb" id="draggableSearchItem_${index + 1}">

    <div class="ratingbox-left">
        <input type="hidden" name="menuItemId" value="${recipe.id}"/>
        <h3 class="recipeName">
            <g:if test="${recipe?.name?.length()<10}">
                ${recipe?.name}
            </g:if>
            <g:else>
                ${recipe?.name?.substring(0, 8)}...
            </g:else>
        </h3>
        <g:if test="${recipe.image}">
            <img height="80" width="80" src="${createLink(controller: 'recipe', action: 'showImage', id: recipe?.id)}"/>
        </g:if>
        <g:else>
            <img height="80" width="80" src="${resource(dir: 'images', file: 'no-img.gif')}"/>
        </g:else>
    %{--<img src="${resource(dir: 'images', file: 'vegetarian.gif')}" class="imgbor"/>--}%
    </div>
    <div class="ratingbox-right">
        <div class="star-container">
            <img src="${resource(dir: 'images', file: 'star.gif')}" width="14" height="14"/>
            <img src="${resource(dir: 'images', file: 'star.gif')}" width="14" height="14"/>
            <img src="${resource(dir: 'images', file: 'star.gif')}" width="14" height="14"/>
            <img src="${resource(dir: 'images', file: 'star-half.gif')}" width="14" height="14"/>
            <img src="${resource(dir: 'images', file: 'star-full.gif')}" width="14" height="14"/>
        </div>
        ${recipe?.totalTime}
        <br/>
        ${recipe?.difficulty}<br/>

        <g:each in="${recipe?.ingredients?.ingredient}" status="i" var="product">
            <g:if test="${i<3}">
                <g:if test="${product?.toString()?.length()<10}">
                    ${product?.toString()}
                </g:if>
                <g:else>
                    ${product?.toString()?.substring(0, 8)}...<br/>
                </g:else>
            </g:if>
        </g:each>
    </div>
</li>

