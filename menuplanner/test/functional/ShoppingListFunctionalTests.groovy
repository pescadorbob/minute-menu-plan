import com.mp.domain.*

class ShoppingListFunctionalTests extends MenuPlannerFunctionalTests {
    void testSomething() {}

    void testCreateShoppingListPage() {
        goToCreateShoppingListPage()
        assertContentContains "Shopping List of Parameters"
        assertContentContains "Name of shopping list"
        assertContentContains "Choose MenuPlan"
        assertContentContains "Servings"
        assertContentContains "Week1"
        assertContentContains "Week2"
        assertContentContains "Week3"
        assertContentContains "Week4"
    }

    void testGenerateNewShoppingList_Valid_Values() {
        goToCreateShoppingListPage()
        ShoppingListFormData shoppingListFormData = ShoppingListFormData.getDefaultShoppingListFormData()
        createShoppingList(shoppingListFormData)
        assertContentContains shoppingListFormData.name
        assertContentContains "Week 1"
        assertContentContains "Week 2"
        assertContentContains "Week 3"
        assertContentContains "Week 4"
        assertContentContains "Add items to Shopping List"
    }

    void testGenerateNewShoppingList_Three_Weeks_Selected() {
        goToCreateShoppingListPage()
        ShoppingListFormData shoppingListFormData = ShoppingListFormData.getDefaultShoppingListFormData()
        createShoppingList_Three_Weeks_Selected(shoppingListFormData)
        assertContentContains shoppingListFormData.name
        assertContentContains "Week 2"
        assertContentContains "Week 3"
        assertContentContains "Week 4"
        assertContentContains "Add items to Shopping List"
    }

    void testGenerateNewShoppingList_All_Blank_Fields() {
        goToCreateShoppingListPage()
        createShoppingList_All_Fields_Blank()
        assertTrue(
                (byId('errorsDiv').asText().contains(getMessage('printShoppingListCO.servings.blank.servings'))) &&
                        (byId('errorsDiv').asText().contains(getMessage('printShoppingListCO.name.blank.error.com.mp.domain.PrintShoppingListCO.name')))
        )

    }

    void testGenerateNewShoppingList_Blank_Name() {
        goToCreateShoppingListPage()
        ShoppingListFormData shoppingListFormData = ShoppingListFormData.getDefaultShoppingListFormData()
        shoppingListFormData.name = ""
        createShoppingList(shoppingListFormData)
        assertTrue(byId('errorsDiv').asText().contains(getMessage('printShoppingListCO.name.blank.error.com.mp.domain.PrintShoppingListCO.name')))
    }

    void testGenerateNewShoppingList_Blank_Weeks() {
        goToCreateShoppingListPage()
        ShoppingListFormData shoppingListFormData = ShoppingListFormData.getDefaultShoppingListFormData()
        createShoppingList_Blank_Weeks(shoppingListFormData)
        //TODO: Implement blank validation on weeks
//        assertTrue(byId('errorsDiv').asText().contains(getMessage('printShoppingListCO.weeks.nullable.error.weeks')))
    }

    void testGenerateNewShoppingList_Blank_Servings() {
        goToCreateShoppingListPage()
        ShoppingListFormData shoppingListFormData = ShoppingListFormData.getDefaultShoppingListFormData()
        shoppingListFormData.servings = ""
        createShoppingList(shoppingListFormData)
        assertTrue(byId('errorsDiv').asText().contains(getMessage('printShoppingListCO.servings.blank.servings')))
    }

    void testGenerateNewShoppingList_Invalid_Servings_Value() {
        goToCreateShoppingListPage()
        ShoppingListFormData shoppingListFormData = ShoppingListFormData.getDefaultShoppingListFormData()
        shoppingListFormData.servings = "abcd"
        createShoppingList(shoppingListFormData)
        assertTrue(byId('errorsDiv').asText().contains(getMessage('printShoppingListCO.servings.matches.error.servings')))
    }

    void testGenerateShoppingList_AddItem() {
        goToCreateShoppingListPage()
        ShoppingListFormData shoppingListFormData = ShoppingListFormData.getDefaultShoppingListFormData()
        createShoppingList(shoppingListFormData)
        assertContentContains shoppingListFormData.name
        byId('aisleList_0').getOption(6).getValueAttribute()
        byId('addItemTxt_0').setValue("Functional Test Item")
        byId('addItemBtn_0').click()
    }

    void testCreateShoppingList() {
        goToCreateShoppingListPage()
        ShoppingListFormData shoppingListFormData = ShoppingListFormData.getDefaultShoppingListFormData()
        createShoppingList(shoppingListFormData)
        assertContentContains shoppingListFormData.name
        assertContentContains "Add items to Shopping List"
        def saveShoppingListButton = byName('_action_save')
        saveShoppingListButton.click()
        get('/shoppingList/show/1')
        assertContentContains "Week 1"
        assertContentContains "Week 2"
        assertContentContains "Week 3"
        assertContentContains "Week 4"
        assertContentContains "snacks"
        assertContentContains "condiments"
        assertContentContains "canned foods"
        assertContentContains "Print Shopping List"
        assertContentContains "Email the Shopping List"
    }
}
