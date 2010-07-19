<%@ page import="com.mp.domain.LoginCredential; com.mp.domain.Permission;" %>
<div id="navigation">
    <ul>
        <g:if test="${LoginCredential.currentUser}">
            <mp:menuPlanDropdown/>
            <mp:shoppingListDropDown/>
            <li><g:link controller="recipe" action="list" class="browse">Browse&nbsp;Recipes</g:link></li>
            <g:if test="${((params.controller =='menuPlan') && (params.action in ['show','saveAndUpdate']))}">
            </g:if>
            <mp:loggedUserDropDown/>
        %{--<li><a>Forums</a></li>--}%
        %{--<li><a>Conversions</a></li>--}%
        %{--<li><a>Help</a></li>--}%
            <mp:adminDropDown/>
        </g:if>
    </ul>
</div>
