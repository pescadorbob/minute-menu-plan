<%@ page import="com.mp.domain.ElementLocation; com.mp.domain.Recipe" %>
<div class="product clearfix">
    <ul>
        <div class="clearfix">
        <g:each in="${recipeList}" var="item" status="index">

         <g:if test="${index %3==0}">
          </div><div class="clearfix">
         </g:if>
            <g:if test="${index>0 && index %15==0}">
                </div><div>
            </g:if>
          <analytics:profile name="recipe:${item.name}" details="">
            <g:render template="/recipe/showRecipeForRecipeCard" model="['item':item ,'index':index]"/>
          </analytics:profile>
        </g:each>
        <div class="browse-recipe-ads"><theme:gadget location="${ElementLocation.BROWSE_RECIPES}"/></div>
       </div>
    </ul>
</div>
<div class="paginateButtons">
    <util:remotePaginate controller="recipe" action="search" total="${recipeTotal}" params="[query: query]"
            max="15" offset="${params.offset}" update="rightContainer"/>
</div>
<script type="text/javascript">
//  jQuery(function(){
//		 jQuery('.corner').wrap('<div class="outer"></div>');
//		jQuery('.corner').corner("round 5px").parent().css('padding', '1px').corner("round 5px")
//	    });
</script>
