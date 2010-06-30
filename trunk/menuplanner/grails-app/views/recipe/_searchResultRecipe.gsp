<%@ page import="com.mp.domain.Recipe" %>
<div class="product clearfix">
    <ul>
        <g:each in="${recipeList}" var="item" status="index">
         <g:if test="${index %3==0}">
           <div class="clearfix">&nbsp;</div>
         </g:if>

          <g:render template="/recipe/showRecipe" model="['item':item ,'index':index]"/>
        </g:each>
    </ul>
</div>
<div class="paginateButtons">
    <util:remotePaginate controller="recipe" action="search" total="${recipeTotal}" params="[query: query]"
            max="15" offset="${params.offset}" update="rightContainer"/>
</div>
