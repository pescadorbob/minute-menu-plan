<%@ page import="com.mp.domain.LoginCredential; com.mp.domain.Permission;" %>
<div id="navigation">
    <ul>
        <g:if test="${LoginCredential.currentUser}">
            <mp:menuPlanDropdown/>
            <mp:shoppingListDropDown/>
            <li><g:link controller="recipe" action="list" class="browse recipeListFT">Browse&nbsp;Recipes</g:link></li>
            <g:if test="${((params.controller =='menuPlan') && (params.action in ['show','saveAndUpdate']))}">
            </g:if>
            <mp:loggedUserDropDown/>
        %{--<li><a>Forums</a></li>--}%
        %{--<li><a>Conversions</a></li>--}%
        %{--<li><a>Help</a></li>--}%
            <mp:adminDropDown/>
            <li><a href="http://blog.minutemenuplan.com/">Blog</a></li>
        </g:if>
        <g:else>
            <li><g:link controller="user" action="createFreeUser">Create Your Own Menu Plan</g:link></li>
            <li><g:link controller="user" action="createFreeUser">Browse&nbsp;Recipes</g:link></li>
            <li><a href="http://blog.minutemenuplan.com/">Blog</a></li>
        </g:else>
    </ul>
</div>
