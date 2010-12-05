<%@ page import="com.mp.tools.UserTools; com.mp.domain.ElementLocation; com.mp.domain.LoginCredential; com.mp.domain.Permission;" %>
<div id="navigation">
  <ul>
    <mp:menuPlanDropdown/>
    <mp:shoppingListDropDown/>
    <li><g:link controller="recipe" action="list" class="browse recipeListFT">Browse&nbsp;Recipes</g:link>
      <theme:gadget location="${ElementLocation.BROWSE_RECIPES}"/></li>
    <mp:loggedUserDropDown/>
    <mp:adminDropDown/>
    <li><a href="http://blog.minutemenuplan.com/"><span>Blog</span></a>
      <ul>
        <li><a href=" http://support.minutemenuplan.com/">Support Forum</a>
          <theme:gadget location="${ElementLocation.MENU}" contextRule="Last Menu"/></li>
      </ul>
    </li>
    <li><theme:gadget location="${ElementLocation.MENU}" contextRule="End Menu"/></li>
  </ul>
</div>
