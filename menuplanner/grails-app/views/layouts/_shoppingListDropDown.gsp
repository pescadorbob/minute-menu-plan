<%@ page import="com.mp.domain.ElementLocation" %><g:if test="${shoppingLists}">
    <li>
        <a href="#" class="menuplan"><span>Shopping&nbsp;List</span></a>
        <ul>
            <li><nobr>
                <g:each in="${shoppingLists}" var="shoppingList">
                    <g:link controller="shoppingList" action="show" id="${shoppingList?.id}">${shoppingList?.name}</g:link>
                </g:each>
              <theme:gadget location="${ElementLocation.SHOPPING_LIST}" />
            </nobr></li>
        </ul>
    </li>
</g:if>    
