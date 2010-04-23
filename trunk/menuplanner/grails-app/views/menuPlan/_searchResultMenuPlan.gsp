<style type="text/css">
.paginateButtons {
    border: 0px;
    color: #666;
    font-size: 10px;
    overflow: hidden;
    padding: 10px 3px;
    margin-top: 10px;
}

.paginateButtons a {
    font-weight: bold;
    font-size: 11px;
    color: royalblue;
    margin: 0 3px;
}

.paginateButtons span {
    padding: 2px 3px;
}
</style>

<ul>
    <g:each in="${recipeList}" var="recipe">
        <g:render template="/menuPlan/recipeThumb" model="[recipe:recipe]"/>
    </g:each>
</ul>

<div class="paginateButtons">
    <util:remotePaginate
            controller="menuPlan"
            action="search"
            total="${recipeTotal}"
            params="[query: query]"
            max="4"
            offset="${params.offset}"
            update="searchResult"
            maxsteps="5"/>
</div>


<script type="text/javascript">

    jQuery(document).ready(function() {
        jQuery('.recipe-detail-thumb').hover(function() {
            jQuery(this).css('backgroundColor', 'orange')
        }, function() {
            jQuery(this).css('backgroundColor', '#329ca7')
        })
    })

</script>