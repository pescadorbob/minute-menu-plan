<%@ page import="com.mp.domain.MealType" %>
<html>
<head>
    <meta name="layout" content="menu"/>
    <title>Edit ${menuPlan.name}</title>
</head>

<body>
<style type="text/css">
.menuContainer div:hover {
    cursor: default;
}
.myHover{
  position:relative;
  z-index:10000;
  display:block;
  background-color:red;
  height:auto !important;
  min-height:55px;
}

.downArrow{
  background:#FCFCFC url(${resource(dir:'images',file:'arrows.gif')}) no-repeat scroll right top
}

.menuContainer img:hover {
    cursor: pointer;
}

.ui-sortable-placeholder {
    border: 1px dotted black;
    visibility: visible !important;
}

.ui-sortable-placeholder * {
    visibility: hidden;
}


</style>
<g:javascript library="ui.core"/>
<g:javascript library="ui.sortable"/>
<script type="text/javascript">
    jQuery(function() {
        jQuery(".menuContainer").sortable({
            update: function(event, ui) {
                if (jQuery("h3", jQuery(ui.item)).hasClass("recipeName")) {
                    var htmlString = " <div style='clear:both'><input type='hidden' value='"+ jQuery("input[name='menuItemId']",jQuery(ui.item)).val()+"' name='mealItems."+jQuery(this).attr("rel") +"'><span style='float:left'>" + jQuery("h3", jQuery(ui.item)).text() + "</span><img src='${resource(dir:'images',file:'delete.jpg')}' alt='' style='display:none;padding-left:2px;' class='deleteImage'></div>"
                    jQuery(ui.item).remove();
                    jQuery(this).append(htmlString);
                    bindHoverAndClick();
                } else {
                    jQuery(ui.item).find("input").attr("name","mealItems."+jQuery(this).attr("rel"))
                }
              if(jQuery(this).children().length>4){
                jQuery(this).addClass("downArrow")
              }else{
                jQuery(this).removeClass("downArrow")               
              }
            },
          over:function(event, ui) {
                jQuery(this).css("background-color","#B1BDC1")
          },
          out:function(event, ui) {
            jQuery(this).css("background-color","")
          },
            opacity:0.6,
            tolerance: 'pointer',
            helper:'clone',
//            cursorAt: 'top',
            revert: true,
            scrollSensitivity: 40,
            connectWith: '.menuContainer'
        });

        bindHoverAndClick();
      jQuery(".placeMyHover").hover(function(){
              jQuery(this).addClass("myHover");
            },function(){
              jQuery(this).removeClass("myHover");

            })


    })
    function bindHoverAndClick() {
        jQuery(".menuContainer>div").unbind();
        jQuery(".menuContainer>div .deleteImage").unbind();

        jQuery(".menuContainer>div").hover(function() {
            jQuery("img", jQuery(this)).css("display", "block");
        }, function() {
            jQuery("img", jQuery(this)).css("display", "none");
        })

        jQuery(".menuContainer>div .deleteImage").click(function() {
            jQuery(this).parent().remove();
        })
    }


</script>
<div id="content-wrapper" class="clearfix">
    <div id="left-panel">
    <g:render template="/menuPlan/actions"/>
    <g:render template="/menuPlan/quickFills"/>
    <g:form action="saveAndUpdate" name="editMenuPlanForm">
        <input type="hidden" name="id" value="${menuPlan.id}" />
    <div class="week">
            <ul>
                <mp:mealItems week="${menuPlan.weeks[0]}" type="${MealType.DINNER}" image="week1.gif" weekIndex="0"/>
                <mp:mealItems week="${menuPlan.weeks[1]}" type="${MealType.DINNER}" image="week2.gif" weekIndex="1"/>
                <mp:mealItems week="${menuPlan.weeks[2]}" type="${MealType.DINNER}" image="week3.gif" weekIndex="2"/>
                <mp:mealItems week="${menuPlan.weeks[3]}" type="${MealType.DINNER}" image="week4.gif" weekIndex="3"/>

                <li class="divider"><img src="${resource(dir: 'images', file: 'divider.gif')}"/></li>
                <mp:mealItems week="${menuPlan.weeks[0]}" type="${MealType.BREAKFAST}" image="breakfast.gif" weekIndex="0"/>
                <mp:mealItems week="${menuPlan.weeks[0]}" type="${MealType.LUNCH}" image="lunch.gif" weekIndex="0"/>
            </ul>
        </div>
        <div id="button">
            <ul>
                <li><g:submitButton class="button" name="update" value="Update"/></li>
                <li><g:actionSubmit class="button" action="show" value="Cancel"/></li>
            </ul>
        </div>
    </div>
   </g:form>
    <g:render template="/menuPlan/search"/>
</div>
</body>
</html>
