<%@ page import="com.mp.domain.LoginCredential; com.mp.domain.Permission;" %>
<div id="navigation">
    <ul>
        <g:if test="${LoginCredential.currentUser}">
            <mp:menuPlanDropdown/>
            <mp:shoppingListDropDown/>
            <li><g:link controller="recipe" action="list" class="browse recipeListFT">Browse&nbsp;Recipes</g:link></li>
            <mp:loggedUserDropDown/>
            <mp:adminDropDown/>
        </g:if>
        <g:else>
            <li><g:link controller="user" action="createFreeUser">Create Your Own Menu Plan</g:link></li>
            <li><g:link controller="user" action="createFreeUser">Browse&nbsp;Recipes</g:link></li>
            <mp:loggedUserDropDown/>
        </g:else>
        <li><a href="http://blog.minutemenuplan.com/"><span>Blog</span></a>
            <ul>
                <li><a href=" http://support.minutemenuplan.com/">Support</a></li>
            </ul>
        </li>
    </ul>
</div>
