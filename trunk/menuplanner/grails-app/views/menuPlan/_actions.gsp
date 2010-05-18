<div id="wintermenu">
    <div id="winterplan">
        <g:if test="${params.action=='show'}">
          ${menuPlan?.name}
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
