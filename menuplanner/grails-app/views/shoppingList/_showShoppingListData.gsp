<g:each in="${shoppingList?.weeklyShoppingLists}" var="weeklyShoppingList">
    <br/>
    <g:if test="${!shoppingList?.isWeeklyShoppingList}">
        <div c class="winterButton" style="width:406px;"><h3>Complete List</h3></div>
    </g:if>
    <g:else>
        <div c class="winterButton" style="width:406px;"><h3>Week ${weeklyShoppingList?.weekIndex + 1}</h3></div>
    </g:else>
    <g:each in="${weeklyShoppingList.aisles + null}" var="aisle">
        <g:if test="${aisle}">
            <div class="winter-week clearfix" style="width:406px;">
                <strong>${(aisle ? aisle : 'Others')}</strong>
                <ul>
                    <li class="grocery" style="list-style:none;">
                        <g:each in="${weeklyShoppingList?.getProductsByAisle(aisle)}" var="item" status="i">
                            <p class="${(i % 2 == 0) ? 'alternate' : ''}"><input class="productCheckBox" type="checkbox" name="product">&nbsp;&nbsp;${item}</p>
                        </g:each>
                    </li>
                </ul>
            </div>
            <div class="winterButton"  style="width:406px;">
                <ul>
                    <li class="grocery" style="list-style:none;">
                        <g:each in="${weeklyShoppingList?.getGroceriesByAisle(aisle)}" var="item" status="i">
                            <p class="${(i % 2 == 0) ? 'alternate' : ''}"><input class="productCheckBox" type="checkbox" name="grocery">&nbsp;&nbsp;${item}</p>
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
                        <li class="grocery" style="list-style:none;">
                            <g:each in="${weeklyShoppingList?.getProductsByAisle(aisle)}" var="item" status="i">
                                <p class="${(i % 2 == 0) ? 'alternate' : ''}"><input class="productCheckBox" type="checkbox" name="product">&nbsp;&nbsp;${item}</p>
                            </g:each>
                        </li>
                    </ul>
                </div>
                <div class="winterButton"  style="width:406px;">
                    <ul>
                        <li class="grocery" style="list-style:none;">
                            <g:each in="${weeklyShoppingList?.getGroceriesByAisle(aisle)}" var="item" status="i">
                                <p class="${(i % 2 == 0) ? 'alternate' : ''}"><input class="productCheckBox" type="checkbox" name="grocery">&nbsp;&nbsp;${item}</p>
                            </g:each>
                        </li>
                    </ul>
                </div>
            </g:if>
        </g:else>
    </g:each>
</g:each>