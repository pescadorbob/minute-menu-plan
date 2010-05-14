<li class="recipe-detail-thumb" id="draggableSearchItem_${index + 1}">
    <div class="ratingbox-left">
        <input type="hidden" name="menuItemId" value="${recipe.id}"/>
        <h3 class="recipeName">${recipe?.trimLength(15)}</h3>
        <mp:recipeImage id="${recipe?.image?.id}" noImage="no-img.gif" height="80" width="80"/>
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
                ${product?.trimLength(15)}
            </g:if>
        </g:each>
    </div>
</li>

