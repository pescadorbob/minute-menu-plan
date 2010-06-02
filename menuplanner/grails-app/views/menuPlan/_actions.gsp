<div id="wintermenu">
    <div id="winterplan">
        <g:if test="${params.action in ['show','saveAndUpdate']}">
          ${menuPlan?.name} <g:link controller="menuPlan" action="create" params="['menuPlanId':menuPlan.id]">Copy Menu Plan</g:link>
        </g:if>
        <g:else>
            <input type="text" name="menuPlan.name"  value="${menuPlan?.name}">
        </g:else>
    </div>
    <div id="viewmenu">
        %{--<ul>--}%
            %{--<li>VIEW:--}%
                %{--<a href="menuPlan.gsp#">Monthly</a>--}%
            %{--</li>--}%
            %{--<li>--}%
                %{--<a href="menuPlan.gsp#">Weekly</a>--}%
            %{--</li>--}%
            %{--<li class="noseprator">--}%
                %{--<a href="menuPlan.gsp#">Shopping List</a>--}%
            %{--</li>--}%
            %{--<li class="noseprator1">--}%
                %{--<img src="${resource(dir: 'images', file: 'actions.gif')}"/>--}%
            %{--</li>--}%
        %{--</ul>--}%
    </div>
</div>
<script type="text/javascript">
  $(function(){
    $.each(jQuery(".menuContainer"),function(){
        if (jQuery(this).children().last().show()[0].offsetTop >= 60) {
            jQuery(this).addClass("downArrow")
        } else {
            jQuery(this).removeClass("downArrow")
        }
        jQuery('.farji', jQuery(this)).hide()
    })

    jQuery(".placeMyHover").hover(function() {
      jQuery(this).addClass("myHover");
    }, function() {
      jQuery(this).removeClass("myHover");

    })

  })
</script>
