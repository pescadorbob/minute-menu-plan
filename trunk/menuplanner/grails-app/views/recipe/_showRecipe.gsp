<%@ page import="com.mp.domain.Recipe" %>
<li class="draggableItem corner" id="draggableSearchItem_${index + 1}">
    <g:if test="${item?.instanceOf(Recipe)}">
        <g:render template="/recipe/showRecipeDetail" model="['item':item]"/>
    </g:if>
    <g:else>
        <g:render template="/recipe/showProduct" model="['item':item]"/>
    </g:else>
</li>