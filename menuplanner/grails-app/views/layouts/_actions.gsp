<li>
    <a href="#"><span>Actions</span></a>
    <ul>
        <li>
            %{--<a>Print Monthly Menu Plan</a>--}%
            %{--<a>Print Weekly Menu Plan</a>--}%
            <g:link controller="shoppingList" action="printShoppingList">Print Shopping List</g:link>
            <g:link controller="recipe" action="selectRecipesToPrint" id="${menuPlan?.id}">Print Recipe Book</g:link>
            %{--<a>Delete Menu Plan</a>--}%
        </li>
    </ul>
</li>