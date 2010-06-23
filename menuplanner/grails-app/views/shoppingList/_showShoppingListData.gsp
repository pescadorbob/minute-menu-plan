<g:each in="${shoppingList?.weeklyShoppingLists}" var="weeklyShoppingList">
    <div class="winterButton" style="float:left;"><h3>Week ${weeklyShoppingList?.weekIndex + 1}</h3><p></p></div>
    <g:each in="${weeklyShoppingList.aisles + null}" var="aisle">
        <g:if test="${aisle}">
            <div class="winter-week clearfix" style="width:406px;">
                <strong>${(aisle ? aisle : 'Others')}</strong>
                <ul>
                    <li class="grocery">
                        <g:each in="${weeklyShoppingList?.getProductsByAisle(aisle)}" var="item">
                            <p><input class="productCheckBox" type="checkbox" name="product">&nbsp;&nbsp;${item}</p>
                        </g:each>
                    </li>
                </ul>
            </div>
            <div class="winterButton">
                <ul>
                    <li class="grocery">
                        <g:each in="${weeklyShoppingList?.getGroceriesByAisle(aisle)}" var="item">
                            <p><input class="productCheckBox"  type="checkbox" name="grocery">&nbsp;&nbsp;${item}</p>
                        </g:each>
                    </li>
                </ul>
            </div>
        </g:if>
        <g:else>
            <g:if test="${(weeklyShoppingList?.getProductsByAisle() || weeklyShoppingList?.getGroceriesByAisle())}">
                <div class="winter-week clearfix" style="width:406px;">
                    <strong>&nbsp;&nbsp;&nbsp;&nbsp;${(aisle ? aisle : 'Others')}</strong>
                    <ul>
                        <li class="grocery">
                            <g:each in="${weeklyShoppingList?.getProductsByAisle(aisle)}" var="item">
                                <p><input  class="productCheckBox"  type="checkbox" name="product">&nbsp;&nbsp;${item}</p>
                            </g:each>
                        </li>
                    </ul>
                </div>
                <div class="winterButton">
                    <ul>
                        <li class="grocery">
                            <g:each in="${weeklyShoppingList?.getGroceriesByAisle(aisle)}" var="item">
                                <p><input  class="productCheckBox"  type="checkbox" name="grocery">&nbsp;&nbsp;${item}</p>
                            </g:each>
                        </li>
                    </ul>
                </div>
            </g:if>
        </g:else>
    </g:each>
</g:each>