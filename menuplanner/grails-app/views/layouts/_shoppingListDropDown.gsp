<%@ page import="com.mp.domain.ElementLocation" %><g:if test="${shoppingLists}">
  <analytics:recordIn name="shopping-list-bar-render" details=""/>
    <li>
        <a href="#" class="menuplan"><span>Shopping&nbsp;List</span></a>
        <ul>
            <li><nobr>
                <g:each in="${shoppingLists}" var="shoppingList">
                    <g:link controller="shoppingList" action="show" id="${shoppingList[0]}">${shoppingList[1]}</g:link>
                </g:each>
              <theme:gadget location="${ElementLocation.SHOPPING_LIST}" />
            </nobr></li>
        </ul>
    </li>
  <analytics:recordOut name="shopping-list-bar-render" />
</g:if>
