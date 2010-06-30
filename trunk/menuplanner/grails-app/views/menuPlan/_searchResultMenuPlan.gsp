<%@ page import="com.mp.domain.Recipe" %>
<div class="ratingbox">
    <ul class="resultContainer">
        <g:each in="${itemList}" var="item" status="index">
            <g:render template="/recipe/showRecipe" model="['item':item ,'index':index]"/>
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