<%@ page import="com.mp.domain.Recipe" %>
<li class="draggableItem corner" id="draggableSearchItem_${index + 1}">
    <g:if test="${item?.instanceOf(Recipe)}">
        <g:render template="/recipe/showRecipeDetailForRecipeCard" model="['avePrice':avePrice,'item':item ,'openInNewWindow':openInNewWindow]"/>
    </g:if>
    <g:else>
        <g:render template="/recipe/showProduct" model="['item':item,'avePrice':avePrice]"/>
    </g:else>
</li>