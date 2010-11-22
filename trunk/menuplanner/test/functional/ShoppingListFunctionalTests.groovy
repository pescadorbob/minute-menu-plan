import com.mp.domain.*
import static com.mp.domain.TestConstants.*
import com.mp.domain.party.Subscriber

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
        createShoppingListBlankServings(shoppingListFormData)
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
        def modifyList = byClass('modifyShoppingListScaledFT')
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
        def modifyList = byClass('modifyShoppingListScaledFT')
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
        def modifyList = byClass('modifyShoppingListScaledFT')
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

    void test_Add_New_AisleWithUser() {
        LoginFormData loginFormData = LoginFormData.getDefaultLoginFormData()
        loginToHomepage(loginFormData)
        CreateRecipeData createRecipeData = CreateRecipeData.getDefaultCreateRecipeData()
        String aisle = "NewAisle-${System.currentTimeMillis()}"
        Integer initialCount = Recipe.count()
        Integer initialAisleCount = Aisle.count()
        createRecipeWithNewAisle(createRecipeData, aisle)
        Integer finalCount = Recipe.count()
        Integer finalAisleCount = Aisle.count()

        assertEquals 'Unable to create Aisle ', finalAisleCount, initialAisleCount + 1
        LoginCredential credential = UserLogin.findByEmail(loginFormData.email)
        ShoppingList shoppingList = ShoppingList.findByParty(credential.party)
        get("/shoppingList/generateShoppingList/${shoppingList?.id}")
        ShoppingListFormData shoppingListFormData = ShoppingListFormData.getDefaultShoppingListFormData()
        createShoppingList(shoppingListFormData)

        List<String> aislesInList = []
        byId('aisleList_0').getChildElements().each {
            aislesInList.add(it.asText())
        }
        assertTrue(aislesInList.contains(aisle))
    }

    void test_Added_New_Aisle_Invisible_To_Another_User() {
        LoginFormData loginFormData = LoginFormData.getDefaultLoginFormData()
        loginToHomepage(loginFormData)
        CreateRecipeData createRecipeData = CreateRecipeData.getDefaultCreateRecipeData()
        String aisle = "NewTestAisle-${System.currentTimeMillis()}"
        Integer initialCount = Recipe.count()
        Integer initialAisleCount = Aisle.count()
        createRecipeWithNewAisle(createRecipeData, aisle)
        Integer finalCount = Recipe.count()
        Integer finalAisleCount = Aisle.count()
        assertEquals "Unable to create Recipe ", initialCount + 1, finalCount
        assertEquals "Unable to create Recipe ", initialAisleCount + 1, finalAisleCount
        logout()
        loginBySuperAdmin()
        LoginCredential credential = UserLogin.findByEmail(SUPER_ADMIN)
        ShoppingList shoppingList = ShoppingList.findByParty(credential.party)
        get("/shoppingList/generateShoppingList/${shoppingList?.id}")
        ShoppingListFormData shoppingListFormData = ShoppingListFormData.getDefaultShoppingListFormData()
        createShoppingList(shoppingListFormData)
        List<String> aislesInList = []
        byId('aisleList_0').getChildElements().each {
            aislesInList.add(it.asText())
        }
        if (aislesInList.contains(aisle)) {
            fail("Unexpected Aisle found in user Aisle list...")
        }
    }

    void test_Add_User_Create_Aisle_Delete_User_Aisle_Deleted() {
        javaScriptEnabled = false
        Integer initialCount = Subscriber.count()
        loginBySuperAdmin()
        UserFormData userFormData = UserFormData.getDefaultUserFormData()
        userFormData.email = 'userTest@email.com'
        userFormData.isUser = true
        createUser(userFormData)
        assertStatus 200
        Integer finalCount = Subscriber.count()
        assertTrue('Add User with valid values failed', (finalCount - initialCount == 1))
        logout()

        LoginFormData loginFormData = LoginFormData.getDefaultLoginFormData()
        loginFormData.email = userFormData.email
        loginToHomepage(loginFormData)
        CreateRecipeData createRecipeData = CreateRecipeData.getDefaultCreateRecipeData()
        String aisle = "NewUserTestAisle-${System.currentTimeMillis()}"
        Integer initialAisleCount = Aisle.count()
        createRecipeWithNewAisle(createRecipeData, aisle)
        Integer finalAisleCount = Aisle.count()
        assertEquals "Unable to create Aisle for Recipe ", initialAisleCount + 1, finalAisleCount
        logout()

        loginBySuperAdmin()
        UserLogin userLogin = UserLogin.findByEmail(loginFormData.email)
        get("/user/show/${userLogin?.party?.id}")
        Integer userInitialCount = Subscriber.count()
        Integer initCount = Aisle.count()
        def deleteUserButton = byClass('deleteUserButtonFT')
        deleteUserButton.click()
        Integer finCount = Aisle.count()
        Integer userFinalCount = Subscriber.count()
        assertEquals "Unable to Delete Aisle of User, when user is deleted", finCount + 1, initCount
        assertEquals "Unable to delete User", userFinalCount + 1, userInitialCount
    }
}
