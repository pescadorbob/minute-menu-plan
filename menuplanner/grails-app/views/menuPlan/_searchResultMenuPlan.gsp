<%@ page import="com.mp.domain.Recipe" %>
<div class="ratingbox">
    <ul class="resultContainer">
        <g:each in="${itemList}" var="item" status="index">
            <g:render template="/recipe/showRecipeForRecipeCard" model="['item':item ,'index':index]"/>
        </g:each>
    </ul>
</div>
<div class="paginateButtons">
    <util:remotePaginate
            controller="menuPlan"
            action="search"
            total="${itemTotal}"
            params="[query: query, searchByDomainName: params.searchByDomainName]"
            max="4"
            offset="${params.offset}"
            update="searchResult"
            maxsteps="5"/>
</div>
<g:if test="${params.action!='show'}">
    <script type="text/javascript">
        bindSortableToSearchItems()
    </script>
</g:if>
<script type="text/javascript">
//  jQuery(function(){
//		 jQuery('.corner').wrap('<div class="outer"></div>');
//		jQuery('.corner').corner("round 5px").parent().css('padding', '1px').corner("round 5px")
//	    });
</script>