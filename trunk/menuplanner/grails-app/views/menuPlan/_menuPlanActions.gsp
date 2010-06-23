<div id="viewmenu">
    <ul>
        %{--<li><a href="#">VIEW : Monthly</a></li>--}%
        %{--<li><a href="#">Weekly</a></li>--}%
        %{--<li class="noseprator"><a href="#">Shopping List</a></li>--}%
        <li class="noseprator1"><a href="#">
            <img src="${resource(dir: 'images', file: 'actions.gif')}" border="0"/>
            <ul>
                %{--<li><a href="">Print Monthly Menu Plan</a></li>--}%
                %{--<li><a href="">Print Weekly Menu Plan</a></li>--}%
                <li>
                    <g:link controller="shoppingList" action="generateShoppingList" id="${menuPlan?.id}">Create Shopping List</g:link>
                </li>
                %{--<li><a href="">Delete Menu Plan</a></li>--}%
            </ul>
        </a>
        </li>
    </ul>
    <script type="text/javascript">
        jQuery.each(jQuery('#viewmenu>ul>li'), function() {
            jQuery(this).mouseover(function() {
                jQuery(this).addClass("sfhover")
            })
            jQuery(this).mouseout(function() {
                jQuery(this).removeClass("sfhover")
            })
        });
    </script>
</div>
