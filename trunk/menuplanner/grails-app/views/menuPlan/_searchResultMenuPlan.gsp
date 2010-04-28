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

<ul class="resultContainer">
    <g:each in="${recipeList}" var="recipe" status="index">
        <g:render template="/menuPlan/recipeThumb" model="[recipe:recipe, index: index]"/>
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

    jQuery(".resultContainer").sortable({
      remove:function(event,ui){
        var elemId=jQuery(ui.item).attr("id")
        var elemNo= elemId.split("_")[1]
        if(parseInt(elemNo)==1){
          jQuery(this).prepend("<li id='"+elemId+"'>"+jQuery(ui.item).clone().html()+"</li>")
        }else{
          var prevElemNo=parseInt(elemNo)-1
          jQuery("#draggableSearchItem_"+prevElemNo).after("<li id='"+elemId+"'>"+jQuery(ui.item).clone().html()+"</li>")
        }
      },
      opacity:0.6,
      tolerance: 'pointer',
      helper: 'clone',
      cursorAt: 'top',
      revert: true,
      scrollSensitivity: 40 ,
      connectWith: '.menuContainer'
    });



</script>