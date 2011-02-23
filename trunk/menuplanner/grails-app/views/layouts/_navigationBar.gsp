<%@ page import="org.codehaus.groovy.grails.commons.ConfigurationHolder; com.mp.tools.UserTools; com.mp.domain.ElementLocation; com.mp.domain.LoginCredential; com.mp.domain.Permission;" %>
<div id="navigation">
  <ul>
    <mp:menuPlanDropdown/>
    <mp:shoppingListDropDown/>
    <li><g:link controller="recipe" action="list" class="browse recipeListFT">Browse&nbsp;Recipes</g:link>
      <theme:gadget location="${ElementLocation.BROWSE_RECIPES}"/></li>
    <mp:loggedUserDropDown/>
    <mp:adminDropDown/>
    
    <li><a href="#" class="menuplan"><span>More</span></a>
      <ul>
        <li>
          <a href="http://blog.minutemenuplan.com/">Blog</a>
          <a href=" http://support.minutemenuplan.com/">Support Forum</a>
          <wcmhelp:eachPage var="content">
            
            <a href="${ConfigurationHolder.config.grails.serverURL}/${ConfigurationHolder.config.weceem.content.prefix}/${content.absoluteURI}">${content.title}</a>            
          </wcmhelp:eachPage>
        </li>
      </ul>
    </li>
    <li><theme:gadget location="${ElementLocation.MENU}" contextRule="End Menu"/></li>
  </ul>
</div>
