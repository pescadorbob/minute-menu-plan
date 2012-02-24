<%@ page import="org.codehaus.groovy.grails.commons.ConfigurationHolder; com.mp.tools.UserTools; com.mp.domain.ElementLocation; com.mp.domain.LoginCredential; com.mp.domain.Permission;" %>
<analytics:recordIn name="nav-bar" details=""/>
<div id="navigation">
  <ul>
    <analytics:recordIn name="menu-plan" details=""/>
    <mp:menuPlanDropdown/>
    <analytics:recordOut name="menu-plan" />
    <analytics:recordIn name="shopping-list" details=""/>
    <mp:shoppingListDropDown/>
    <analytics:recordOut name="shopping-list" />
    <analytics:recordIn name="browse" details=""/>

    <li><g:link controller="recipe" action="list" class="browse recipeListFT">Browse&nbsp;Recipes</g:link>
      <theme:gadget location="${ElementLocation.BROWSE_RECIPES_MENU}"/></li>
    <analytics:recordOut name="browse" />

    <analytics:recordIn name="user" details=""/>
    <mp:loggedUserDropDown/>
    <analytics:recordOut name="user" />
    <mp:adminDropDown/>
    
    <li><a href="#" class="menuplan"><span>More</span></a>
      <ul>
        <li>
          <a href="http://blog.minutemenuplan.com/">Blog</a>
          <a href=" http://support.minutemenuplan.com/">Support Forum</a>
          <analytics:recordIn name="wcm" details=""/>

          <wcmhelp:eachPage var="content">

            <a href="${ConfigurationHolder.config.grails.serverURL}/${ConfigurationHolder.config.weceem.content.prefix}/${content.absoluteURI}">${content.title}</a>
          </wcmhelp:eachPage>
          <analytics:recordOut name="wcm" />

        </li>
      </ul>
    </li>
    <li><theme:gadget location="${ElementLocation.MENU}" contextRule="End Menu"/></li>
  </ul>
</div>
<analytics:recordOut name="nav-bar" />
