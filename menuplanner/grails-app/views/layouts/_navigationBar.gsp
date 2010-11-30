<%@ page import="com.mp.tools.UserTools; com.mp.domain.ElementLocation; com.mp.domain.LoginCredential; com.mp.domain.Permission;" %>
<div id="navigation">
    <ul>
        <g:if test="${UserTools.currentUser}">
            <mp:menuPlanDropdown/>
            <mp:shoppingListDropDown/>
            <li><g:link controller="recipe" action="list" class="browse recipeListFT">Browse&nbsp;Recipes</g:link>
            <theme:gadget location="${ElementLocation.BROWSE_RECIPES}" /></li>
            <mp:loggedUserDropDown/>
            <mp:adminDropDown/>
        </g:if>
        <g:else>
            <li>
                <g:form controller="user" action="createFreeUser">
                    <input type="hidden" name="linkClicked" value="createYourMenuPlan">
                    <g:submitButton class="button-as-link" name="createMenuPlan" value="Create Your Own Menu Plan"/>
                </g:form>
            </li>
            <li>
                <g:form controller="user" action="createFreeUser">
                    <input type="hidden" name="linkClicked" value="browseRecipes">
                    <g:submitButton class="button-as-link" name="browseRecipes" value="Browse Recipes"/>
                </g:form>
              <theme:gadget location="${ElementLocation.BROWSE_RECIPES}" contextRule="Last Menu"/>
            </li>
            <mp:loggedUserDropDown/>
        </g:else>
        <li><a href="http://blog.minutemenuplan.com/"><span>Blog</span></a>
            <ul>
                <li><a href=" http://support.minutemenuplan.com/">Support Forum</a>
                <theme:gadget location="${ElementLocation.MENU}" contextRule="Last Menu"/></li>
            </ul>
        </li>
        <li><theme:gadget location="${ElementLocation.MENU}" contextRule="End Menu"/></li>
    </ul>
</div>
