<g:each in="${shoppingList?.weeklyShoppingLists}" var="weeklyShoppingList">
    <br/>
    <g:if test="${!shoppingList?.isWeeklyShoppingList}">
        <div c class="winterButton" ><h3>Complete List</h3></div>
    </g:if>
    <g:else>
        <div c class="winterButton" ><h3>Week ${weeklyShoppingList?.weekIndex + 1}</h3></div>
    </g:else>
    <g:each in="${weeklyShoppingList.aisles + null}" var="aisle">
        <g:if test="${aisle}">
            <div class="winter-week clearfix" >
                <div class='aisle'><strong>${(aisle ? aisle : 'Others')}</strong></div>
              <div class="items">
                <g:each in="${weeklyShoppingList?.getProductsByAisle(aisle)}" var="item" status="i">
                    <div class="column"><p class="${(i % 2 == 0) ? 'alternate' : ''}"><input class="productCheckBox" type="checkbox" name="product">&nbsp;&nbsp;${item}</p></div>
                </g:each>
                </div>
            </div>
            <div class="winterButton"  >
                <g:each in="${weeklyShoppingList?.getGroceriesByAisle(aisle)}" var="item" status="i">
                    <div class="column"><p class="${(i % 2 == 0) ? 'alternate' : ''}"><input class="productCheckBox" type="checkbox" name="grocery">&nbsp;&nbsp;${item}</p></div>
                </g:each>
            </div>
        </g:if>
        <g:else>
            <g:if test="${(weeklyShoppingList?.getProductsByAisle() || weeklyShoppingList?.getGroceriesByAisle())}">
                <div class="winter-week clearfix" >
                    <div class='aisle'><strong>${(aisle ? aisle : 'Others')}</strong></div>
              <div class="items">
                    <g:each in="${weeklyShoppingList?.getProductsByAisle(aisle)}" var="item" status="i">
                        <div class="column"><p class="${(i % 2 == 0) ? 'alternate' : ''}"><input class="productCheckBox" type="checkbox" name="product">&nbsp;&nbsp;${item}</p></div>
                    </g:each>
                </div>
                </div>
                <div class="winterButton" >
                    <g:each in="${weeklyShoppingList?.getGroceriesByAisle(aisle)}" var="item" status="i">
                        <div class="column"><p class="${(i % 2 == 0) ? 'alternate' : ''}"><input class="productCheckBox" type="checkbox" name="grocery">&nbsp;&nbsp;${item}</p></div>
                    </g:each>
                </div>
            </g:if>
        </g:else>
    </g:each>
</g:each>
