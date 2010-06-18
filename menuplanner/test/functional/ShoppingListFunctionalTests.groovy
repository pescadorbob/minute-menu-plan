import com.mp.domain.*

class ShoppingListFunctionalTests extends MenuPlannerFunctionalTests {


    void testShoppingListPage() {
        goToShoppingListPage()
        assertContentContains "Shopping List of Parameters"
        assertContentContains "Name of shopping list"
        assertContentContains "Choose MenuPlan "
    }

    void testGenerateNewShoppingList_Valid_Values() {
        goToShoppingListPage()
        ShoppingListFormData shoppingListFormData = ShoppingListFormData.getDefaultShoppingListFormData()
        createShoppingList(shoppingListFormData)
        assertContentContains shoppingListFormData.name
        assertContentContains "Export to Todo"
        assertContentContains "Print Shopping List "
    }

    void testGenerateNewShoppingList_One_Week_Selected() {
        goToShoppingListPage()
        ShoppingListFormData shoppingListFormData = ShoppingListFormData.getDefaultShoppingListFormData()
        createShoppingList_One_Week_Selected(shoppingListFormData)
        assertContentContains shoppingListFormData.name
        assertContentContains "Export to Todo"
        assertContentContains "Print Shopping List "
    }

    void testGenerateNewShoppingList_All_Blank_Fields() {
        goToShoppingListPage()
        createShoppingList_All_Fields_Blank()
        assertTrue(
                (byId('errorsDiv').asText().contains(getMessage('printShoppingListCO.servings.blank.servings'))) &&
                        (byId('errorsDiv').asText().contains(getMessage('printShoppingListCO.name.blank.error.com.mp.domain.PrintShoppingListCO.name')))
        )

    }

    void testGenerateNewShoppingList_Blank_Name() {
        goToShoppingListPage()
        ShoppingListFormData shoppingListFormData = ShoppingListFormData.getDefaultShoppingListFormData()
        shoppingListFormData.name = ""
        createShoppingList(shoppingListFormData)
        assertTrue(byId('errorsDiv').asText().contains(getMessage('printShoppingListCO.name.blank.error.com.mp.domain.PrintShoppingListCO.name')))
    }

    void testGenerateNewShoppingList_Blank_Weeks() {
        goToShoppingListPage()
        ShoppingListFormData shoppingListFormData = ShoppingListFormData.getDefaultShoppingListFormData()
        createShoppingList_Blank_Weeks(shoppingListFormData)
        assertTrue(byId('errorsDiv').asText().contains(getMessage('printShoppingListCO.weeks.nullable.error.weeks')))
    }

    void testGenerateNewShoppingList_Blank_Servings() {
        goToShoppingListPage()
        ShoppingListFormData shoppingListFormData = ShoppingListFormData.getDefaultShoppingListFormData()
        shoppingListFormData.servings = ""
        createShoppingList(shoppingListFormData)
        assertTrue(byId('errorsDiv').asText().contains(getMessage('printShoppingListCO.servings.blank.servings')))
    }

    void testGenerateNewShoppingList_Invalid_Servings_Value() {
        goToShoppingListPage()
        ShoppingListFormData shoppingListFormData = ShoppingListFormData.getDefaultShoppingListFormData()
        shoppingListFormData.servings = "abcd"
        createShoppingList(shoppingListFormData)
        assertTrue(byId('errorsDiv').asText().contains(getMessage('printShoppingListCO.servings.matches.error.servings')))
    }

    void testGenerateShoppingList_AddItem() {
        goToShoppingListPage()
        ShoppingListFormData shoppingListFormData = ShoppingListFormData.getDefaultShoppingListFormData()
        createShoppingList(shoppingListFormData)
        assertContentContains shoppingListFormData.name
        assertContentContains "Export to Todo"
        assertContentContains "Print Shopping List "
        byId('addItemTxt_0').setValue("Functional Test Item")
        byId('addItemBtn_0').click()
        assertElementTextContains 'groceries_0', 'Functional Test Item'
    }
}
