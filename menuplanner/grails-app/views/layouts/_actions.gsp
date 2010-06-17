<li>
    <a href="#"><span>Actions</span></a>
    <ul>
        <li>
            %{--<a>Print Monthly Menu Plan</a>--}%
            %{--<a>Print Weekly Menu Plan</a>--}%
            <g:if test="${shoppingList}">
                <g:link controller="shoppingList" action="show" id="${shoppingList?.id}">${shoppingList?.name}</g:link>
            </g:if>
            <g:else>
                <g:link controller="shoppingList" action="printShoppingList" id="${menuPlan?.id}">Print Shopping List</g:link>
            </g:else>
            <g:link controller="recipe" action="selectRecipesToPrint" id="${menuPlan?.id}">Print Recipe Book</g:link>
            %{--<a>Delete Menu Plan</a>--}%
        </li>
    </ul>
</li>