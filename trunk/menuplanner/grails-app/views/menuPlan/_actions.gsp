<div id="wintermenu">
    <div id="winterplan">
        <g:if test="${params.action in ['show','saveAndUpdate']}">${menuPlan?.name}</g:if>
        <g:else>
            <input type="text" name="menuPlan.name" id="menuPlanNameTextbox" title="Enter Menu Plan Name" value="${menuPlan?.name}">
        </g:else>
    </div>
    <div class="menuPlanActions">
        <mp:menuPlanActions/>
    </div>
   
</div>
<script type="text/javascript">
    jQuery(document).ready(function() {
        jQuery.each(jQuery(".menuContainer"), function() {
            if (jQuery(this).children().last().show()[0].offsetTop >= 60) {
                jQuery(this).addClass("downArrow")
            } else {
                jQuery(this).removeClass("downArrow")
            }
            jQuery('.farji', jQuery(this)).hide()
        });

        jQuery(".placeMyHover").hover(function() {
            jQuery(this).addClass("myHover");
        }, function() {
            jQuery(this).removeClass("myHover");

        })

    });
</script>
<script type="text/javascript">
    function checkMenuPlanAction(){
        alert('Hello')
    }
</script>
