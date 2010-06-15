import com.mp.domain.*

class ShoppingListFunctionalTests extends MenuPlannerFunctionalTests {


    void testShoppingListPage() {
        goToShoppingListPage()
        assertContentContains "Shopping List of Parameters"
        assertContentContains "Name of shopping list"
        assertContentContains "Choose MenuPlan "
    }

    void testGenerateNewShoppingList() {
        goToShoppingListPage()
        ShoppingListFormData shoppingListFormData = ShoppingListFormData.getDefaultShoppingListFormData()
        createShoppingList(shoppingListFormData)
        assertContentContains shoppingListFormData.name
        assertContentContains "Export to Todo"
        assertContentContains "Print Shopping List "
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
