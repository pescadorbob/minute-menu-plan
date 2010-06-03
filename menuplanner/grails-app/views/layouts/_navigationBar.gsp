<%@ page import="com.mp.domain.User" %>
<div id="navigation">
    <ul>
        <g:if test="${User.currentUser}">
            <mp:menuPlanDropdown/>
            <li><g:link controller="recipe" action="list" class="browse">Browse&nbsp;Recipes</g:link></li>
            %{--<mp:actions/>--}%
            <mp:loggedUserDropDown/>
            %{--<li><a>Forums</a></li>--}%
            %{--<li><a>Conversions</a></li>--}%
            %{--<li><a>Help</a></li>--}%
            <mp:adminDropDown/>
        </g:if>
    </ul>
</div>
