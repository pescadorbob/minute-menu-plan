<div id="navigation">
    <ul>
        <g:if test="${session?.loggedUserId}">
            <mp:menuPlanDropdown/>
        %{--<li><a href="#" class="browse"><span>Browse&nbsp;Recipes</span></a></li>--}%
            <li><g:link controller="recipe" action="list" class="browse">Browse&nbsp;Recipes</g:link></li>
            <li><g:link controller="recipe" action="create">Add New&nbsp;Recipe</g:link></li>
            <mp:loggedUserDropDown loggedUserId="${session?.loggedUserId}"/>
        </g:if>
    %{--<li><a href="#">Forums</a></li>--}%
    %{--<li><a href="#">Conversions</a></li>--}%
    %{--<li><a href="#">Help</a></li>--}%
    %{--<li><a href="#"><span>Admin</span></a></li>--}%
    </ul>
</div>
