<%@ page import="com.mp.domain.Recipe" %>
<div class="product clearfix">
    <ul>
        <g:each in="${recipeList}" var="item" status="index">
            <g:render template="/recipe/showRecipe" model="['item':item]"/>
        </g:each>
    </ul>
</div>
<div class="paginateButtons">
    <util:remotePaginate controller="recipe" action="search" total="${recipeTotal}" params="[query: query]"
            max="15" offset="${params.offset}" update="rightContainer"/>
</div>
