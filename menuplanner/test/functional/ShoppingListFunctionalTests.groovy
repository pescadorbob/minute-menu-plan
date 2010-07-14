import com.mp.domain.*

class ShoppingListFunctionalTests extends MenuPlannerFunctionalTests {

    /*
     * This test validates the creation of shopping list with week-2, week-3, week-4 selected.
     * It requires at-least one menuplan bootstrapped for the login user.
     * The test fails if a selected week does not appears on create page / unselected week appears on create page
     */

    void testGenerateNewShoppingList_Three_Weeks_Selected() {
        Integer initialCount = ShoppingList.count()
        goToGenerateShoppingListPage()
        assertStatus(200)
        ShoppingListFormData shoppingListFormData = ShoppingListFormData.getDefaultShoppingListFormData()
        createShoppingList_Three_Weeks_Selected(shoppingListFormData)
        assertStatus(200)
        Integer finalCount = ShoppingList.count()
        assertTrue('Generate shopping list created a list', (finalCount - initialCount == 0))
        assertTitle 'Minute Menu Plan : Shopping List'
        assertElementTextContains 'shoppingWeek1', 'Week 2'
        assertElementTextContains 'shoppingWeek2', 'Week 3'
        assertElementTextContains 'shoppingWeek3', 'Week 4'
        def week1 = byId('shoppingWeek0')
        def week2 = byId('shoppingWeek1')
        def week3 = byId('shoppingWeek2')
        def week4 = byId('shoppingWeek3')
        if (week1) {
            fail('Unexpected element found on page')
        }
        if (!(week2 && week3 && week4)) {
            fail('Expected element not found on page')
        }
    }

    /*
     * This test validates the generation of shopping list with all text fields blank.
     * The test fails if form validation doesn't works
     */

    void testGenerateNewShoppingList_Blank_Text_Fields() {
        Integer initialCount = ShoppingList.count()
        goToGenerateShoppingListPage()
        createShoppingList_All_Fields_Blank()
        assertTitle('Minute Menu Plan : Generate Shopping List')
        assertStatus(200)
        Integer finalCount = ShoppingList.count()
        assertTrue('Generate shopping list with blank fields created a shopping list', (finalCount - initialCount == 0))
        assertTrue(
                (byId('errorsDiv').asText().contains(getMessage('printShoppingListCO.servings.blank.servings'))) &&
                        (byId('errorsDiv').asText().contains(getMessage('printShoppingListCO.name.blank.error.com.mp.domain.PrintShoppingListCO.name')))
        )
    }

    /*
    * This test validates the generation of shopping list with all name field blank.
    * The test fails if validation for name field doesn't work
    */

    void testGenerateNewShoppingList_Blank_Name() {
        Integer initialCount = ShoppingList.count()
        goToGenerateShoppingListPage()
        ShoppingListFormData shoppingListFormData = ShoppingListFormData.getDefaultShoppingListFormData()
        shoppingListFormData.name = ""
        createShoppingList(shoppingListFormData)
        assertTitle('Minute Menu Plan : Generate Shopping List')
        assertStatus(200)
        Integer finalCount = ShoppingList.count()
        assertTrue('Generate shopping list with blank name created a shopping list', (finalCount - initialCount == 0))
        assertTrue(byId('errorsDiv').asText().contains(getMessage('printShoppingListCO.name.blank.error.com.mp.domain.PrintShoppingListCO.name')))
    }

    /*
        * This test validates the generation of shopping list with all weeks unchecked.
        * The test fails if validation for weeks doesn't work
        */

    void testGenerateNewShoppingList_Blank_Weeks() {
        Integer initialCount = ShoppingList.count()
        goToGenerateShoppingListPage()
        ShoppingListFormData shoppingListFormData = ShoppingListFormData.getDefaultShoppingListFormData()
        createShoppingList_Blank_Weeks(shoppingListFormData)
        assertTitle('Minute Menu Plan : Generate Shopping List')
        assertStatus(200)
        Integer finalCount = ShoppingList.count()
        assertTrue('Generate shopping list with blank weeks created a shopping list', (finalCount - initialCount == 0))
        assertTrue(byId('errorsDiv').asText().contains(getMessage('printShoppingListCO.weeks.nullable.error.weeks')))
    }

    /*
        * This test validates the generation of shopping list with servings field left blank.
        * The test fails if validation for servings field doesn't work
        */

    void testGenerateNewShoppingList_Blank_Servings() {
        Integer initialCount = ShoppingList.count()
        goToGenerateShoppingListPage()
        ShoppingListFormData shoppingListFormData = ShoppingListFormData.getDefaultShoppingListFormData()
        shoppingListFormData.servings = ""
        createShoppingList(shoppingListFormData)
        assertTitle('Minute Menu Plan : Generate Shopping List')
        assertStatus(200)
        Integer finalCount = ShoppingList.count()
        assertTrue('Generate shopping list with blank servings created a shopping list', (finalCount - initialCount == 0))
        assertTrue(byId('errorsDiv').asText().contains(getMessage('printShoppingListCO.servings.blank.servings')))
    }

    /*
        * This test validates the generation of shopping list with servings field provided with invalid values i.e non-integers.
        * The test fails if exception occurs if servings is entered as string
        */

    void testGenerateNewShoppingList_Invalid_Servings_Value() {
        Integer initialCount = ShoppingList.count()
        goToGenerateShoppingListPage()
        ShoppingListFormData shoppingListFormData = ShoppingListFormData.getDefaultShoppingListFormData()
        shoppingListFormData.servings = "abcd"
        createShoppingList(shoppingListFormData)
        assertTitle('Minute Menu Plan : Generate Shopping List')
        assertStatus(200)
        Integer finalCount = ShoppingList.count()
        assertTrue('Generate shopping list with invalid servings value created a shopping list', (finalCount - initialCount == 0))
        assertTrue(byId('errorsDiv').asText().contains(getMessage('printShoppingListCO.servings.matches.error.servings')))
    }

    /*
        * This test validates the creation of shopping list with a new item added to list.
        * The test fails if new item is not added to the shopping List.
        */

    void testCreateShoppingList_AddItem() {
        goToGenerateShoppingListPage()
        ShoppingListFormData shoppingListFormData = ShoppingListFormData.getDefaultShoppingListFormData()
        createShoppingList(shoppingListFormData)
        assertTitle 'Minute Menu Plan : Shopping List'
        String itemText = "Functional Test Item=${System.currentTimeMillis()}"
        byId('aisleList_0').getOption(6).getValueAttribute()
        byId('addItemTxt_0').setValue(itemText)
        byId('addItemBtn_0').click()
        assertElementTextContains('shoppingWeek0', itemText)
    }
    /*
    * This test validates the addition of item in shopping list during creation of shopping list.
    * The test fails if added item is not saved in the shopping list.
    */

    void testCreateShoppingList_AddItem_And_Save() {
        Integer initialCount = ShoppingList.count()
        goToGenerateShoppingListPage()
        ShoppingListFormData shoppingListFormData = ShoppingListFormData.getDefaultShoppingListFormData()
        createShoppingList(shoppingListFormData)
        assertTitle 'Minute Menu Plan : Shopping List'
        String itemText = "Functional Test Item=${System.currentTimeMillis()}"
        byId('aisleList_0').getOption(6).getValueAttribute()
        byId('addItemTxt_0').setValue(itemText)
        byId('addItemBtn_0').click()
        def saveShoppingListButton = byName('_action_save')
        saveShoppingListButton.click()
        redirectEnabled = false
        followRedirect()
        assertTitle 'Minute Menu Plan : Show Shopping List'
        assertContentContains(itemText)
        assertStatus(200)
        Integer finalCount = ShoppingList.count()
        assertTrue('Create shopping list unables to create a new shopping list', (finalCount - initialCount == 1))
    }

    /*
    * This test validates the creation of shopping list with default items provided by menuplan.
    * The test fails if shopping list is not created.
    */

    void testCreateShoppingList_Default_Values() {
        Integer initialCount = ShoppingList.count()
        goToGenerateShoppingListPage()
        ShoppingListFormData shoppingListFormData = ShoppingListFormData.getDefaultShoppingListFormData()
        createShoppingList(shoppingListFormData)
        assertTitle 'Minute Menu Plan : Shopping List'
        def saveShoppingListButton = byName('_action_save')
        saveShoppingListButton.click()
        redirectEnabled = false
        followRedirect()
        assertTitle 'Minute Menu Plan : Show Shopping List'
        assertStatus(200)
        Integer finalCount = ShoppingList.count()
        assertTrue('Create shopping list unables to create a new shopping list', (finalCount - initialCount == 1))
    }

    /*
    * This test validates the edition of shopping list.
    * The test fails if edit shopping list page do not appears or link is not working.
    */

    void testEditShoppingList() {
        Integer initialCount = ShoppingList.count()
        goToGenerateShoppingListPage()
        ShoppingListFormData shoppingListFormData = ShoppingListFormData.getDefaultShoppingListFormData()
        createShoppingList(shoppingListFormData)
        assertTitle 'Minute Menu Plan : Shopping List'
        def saveShoppingListButton = byName('_action_save')
        saveShoppingListButton.click()
        redirectEnabled = false
        followRedirect()
        assertTitle 'Minute Menu Plan : Show Shopping List'
        assertStatus(200)
        Integer finalCount = ShoppingList.count()
        assertTrue('Create shopping list unables to create a new shopping list', (finalCount - initialCount == 1))
        def editShoppingList = byClass('editShoppingListButtonFT')
        editShoppingList.click()
        assertStatus 200
        assertTitle 'Minute Menu Plan : Generate Shopping List'
    }

    /*
    * This test validates the modification of shopping list.
    * The test fails if generation of shopping list page is not working.
    */

    void testModifyShoppingList() {
        Integer initialCount = ShoppingList.count()
        goToGenerateShoppingListPage()
        ShoppingListFormData shoppingListFormData = ShoppingListFormData.getDefaultShoppingListFormData()
        createShoppingList(shoppingListFormData)
        assertTitle 'Minute Menu Plan : Shopping List'
        def saveShoppingListButton = byName('_action_save')
        saveShoppingListButton.click()
        redirectEnabled = false
        followRedirect()
        assertTitle 'Minute Menu Plan : Show Shopping List'
        assertStatus(200)
        Integer finalCount = ShoppingList.count()
        assertTrue('Create shopping list unables to create a new shopping list', (finalCount - initialCount == 1))
        def editShoppingList = byClass('editShoppingListButtonFT')
        editShoppingList.click()
        assertStatus 200
        assertTitle 'Minute Menu Plan : Generate Shopping List'
        def modifyList = byClass('modifyShoppingListButtonFT')
        modifyList.click()
        assertStatus 200
        assertTitle 'Minute Menu Plan : Shopping List'

    }
    /*
    * This test validates the updation of shopping list.
    * The test fails if shopping list is not saved after updating.
    */

    void testUpdateShoppingList_Default_Values() {
        Integer initialCount = ShoppingList.count()
        goToGenerateShoppingListPage()
        ShoppingListFormData shoppingListFormData = ShoppingListFormData.getDefaultShoppingListFormData()
        createShoppingList(shoppingListFormData)
        assertTitle 'Minute Menu Plan : Shopping List'
        def saveShoppingListButton = byName('_action_save')
        saveShoppingListButton.click()
        redirectEnabled = false
        followRedirect()
        assertStatus(200)
        assertTitle 'Minute Menu Plan : Show Shopping List'
        Integer intermediateCount = ShoppingList.count()
        assertTrue('Create shopping list unables to create a new shopping list', (intermediateCount - initialCount == 1))
        def editShoppingList = byClass('editShoppingListButtonFT')
        editShoppingList.click()
        assertStatus 200
        assertTitle 'Minute Menu Plan : Generate Shopping List'
        def modifyList = byClass('modifyShoppingListButtonFT')
        modifyList.click()
        assertStatus 200
        assertTitle 'Minute Menu Plan : Shopping List'
        def updateList = byClass('updateShoppingListButtonFT')
        updateList.click()
        redirectEnabled = false
        followRedirect()
        assertTitle 'Minute Menu Plan : Show Shopping List'
        assertStatus(200)
        Integer finalCount = ShoppingList.count()
        assertTrue('Update shopping list unables to update a shopping list', (finalCount - intermediateCount == 0))
    }
    /*
    * This test validates the updation of shopping list.
    * The test fails if shopping list is not saved after updating or if the new item is added to shopping list is not saved.
    */

    void testUpdateShoppingList_With_New_Item_Added() {
        Integer initialCount = ShoppingList.count()
        goToGenerateShoppingListPage()
        ShoppingListFormData shoppingListFormData = ShoppingListFormData.getDefaultShoppingListFormData()
        createShoppingList(shoppingListFormData)
        assertTitle 'Minute Menu Plan : Shopping List'
        def saveShoppingListButton = byName('_action_save')
        saveShoppingListButton.click()
        redirectEnabled = false
        followRedirect()
        assertStatus(200)
        assertTitle 'Minute Menu Plan : Show Shopping List'
        Integer intermediateCount = ShoppingList.count()
        assertTrue('Create shopping list unables to create a new shopping list', (intermediateCount - initialCount == 1))
        def editShoppingList = byClass('editShoppingListButtonFT')
        editShoppingList.click()
        assertStatus 200
        assertTitle 'Minute Menu Plan : Generate Shopping List'
        def modifyList = byClass('modifyShoppingListButtonFT')
        modifyList.click()
        assertStatus 200
        assertTitle 'Minute Menu Plan : Shopping List'
        String itemText = "Functional Test Item_${System.currentTimeMillis()}"
        byId('aisleList_0').getOption(6).getValueAttribute()
        byId('addItemTxt_0').setValue(itemText)
        byId('addItemBtn_0').click()
        def updateList = byClass('updateShoppingListButtonFT')
        updateList.click()
        redirectEnabled = false
        followRedirect()
        assertTitle 'Minute Menu Plan : Show Shopping List'
        assertStatus(200)
        Integer finalCount = ShoppingList.count()
        assertContentContains(itemText)
        assertTrue('Update shopping list unables to update a shopping list', (finalCount - intermediateCount == 0))
    }
}
