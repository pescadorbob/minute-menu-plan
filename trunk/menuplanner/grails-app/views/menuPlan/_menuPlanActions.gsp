<div id="viewmenu">

    <ul>
        <g:render template="${params.action}SubNavbarButtons"/>
        %{--<li><a href="#" class="btn">Weekly</a></li>--}%
        %{--<li class="noseprator"><a href="#">Shopping List</a></li>--}%
      <g:if test="${params.action!='create'}">
        <li class="noseprator1">
          <a href="#">
            <img src="${resource(dir: 'images', file: 'actions.gif')}" border="0"/>
            <ul>
              <li id="printMonthlyMenuPlanBtn"><a href="">Print Monthly Menu Plan</a></li>
              %{--<li><a href="">Print Weekly Menu Plan</a></li>--}%
              <li>
                <g:link controller="shoppingList" class="createShoppingListLinkFT" action="generateShoppingList" id="${menuPlan?.id}">Create Shopping List</g:link>
              </li>
            %{--<li><a href="">Delete Menu Plan</a></li>--}%
            </ul>
          </a>
        </li>
      </g:if>
    </ul>
    <script type="text/javascript">
        jQuery.each(jQuery('#viewmenu>ul>li.noseprator1'), function() {
            jQuery(this).mouseover(function() {
                jQuery(this).addClass("sfhover")
            })
            jQuery(this).mouseout(function() {
                jQuery(this).removeClass("sfhover")
            })
        });
    </script>
</div>
