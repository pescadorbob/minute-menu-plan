

<div class="ratingbox">
                <ul>
                <li class="clearfix">
                <ul>
                  <li><h3>Beef&nbsp;with&nbsp;Broccoli</h3>

                   <ul>
                    <li><img src="images/no-img.gif" class="imgbor"/></li>
                    <li><ul>
                        <li><g:render template="/rating/rating"/></li>
                          <li>3 hrs.</li>
                          <li>Easy</li>
                          <li>Product-...</li>
                        </ul></li>
                  </ul>
                  </li>
                </ul>
              </li>
              <li class="clearfix">
                <ul>
                  <li><h3>Beef&nbsp;with&nbsp;Broccoli</h3>

                   <ul>
                    <li><img src="images/no-img.gif" class="imgbor"/></li>
                    <li><ul>
                          <li><img src="images/star-dark.gif" /> <img src="images/star-dark.gif" /> <img src="images/star-dark.gif" /> <img src="images/star-dark.gif" /> <img src="images/star-light.gif" width="13" height="12" /></li>
                          <li>3 hrs.</li>
                          <li>Easy</li>
                          <li>Product-...</li>
                        </ul></li>
                  </ul>
                  </li>
                </ul>
              </li> <li class="clearfix">
                <ul>
                  <li><h3>Beef&nbsp;with&nbsp;Broccoli</h3>

                   <ul>
                    <li><img src="images/no-img.gif" class="imgbor"/></li>
                    <li><ul>
                          <li><img src="images/star-dark.gif" /> <img src="images/star-dark.gif" /> <img src="images/star-dark.gif" /> <img src="images/star-dark.gif" /> <img src="images/star-light.gif" width="13" height="12" /></li>
                          <li>3 hrs.</li>
                          <li>Easy</li>
                          <li>Product-...</li>
                        </ul></li>
                  </ul>
                  </li>
                </ul>
              </li>
               <li class="clearfix">
                <ul>
                  <li><h3>Beef&nbsp;with&nbsp;Broccoli</h3>

                   <ul>
                    <li><img src="images/no-img.gif" class="imgbor"/></li>
                    <li><ul>
                          <li><img src="images/star-dark.gif" /> <img src="images/star-dark.gif" /> <img src="images/star-dark.gif" /> <img src="images/star-dark.gif" /> <img src="images/star-light.gif" width="13" height="12" /></li>
                          <li>3 hrs.</li>
                          <li>Easy</li>
                          <li>Product-...</li>
                        </ul></li>
                  </ul>
                  </li>
                </ul>
              </li>
                </ul>
              </div>

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
//            jQuery('.recipe-detail-thumb').hover(function() {
//                jQuery(this).css('backgroundColor', 'orange')
//            }, function() {
//                jQuery(this).css('backgroundColor', '#329ca7')
//            })
        },
        over:function(event,ui){
          ui.helper=jQuery(ui.helper)
                  .removeClass("recipe-detail-thumb")
                  .css("width","50px")
                  .css("height","10px")
                  .html(jQuery("h3", jQuery(ui.helper)).text())
        },
        opacity:0.6,
//        tolerance: 'pointer',
        helper: 'clone',
        cursorAt: 'top',
        revert: true,
        scrollSensitivity: 40 ,
        connectWith: '.menuContainer',
        zIndex:10001
      });
      
    })

</script>