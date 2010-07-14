import com.mp.domain.*

class MenuCalendarFunctionalTests extends MenuPlannerFunctionalTests {

    /* This plan validates the creation of monthly menuPlan
    *  This test fails if menuPlan is not saved.
    */

    void testCreateMenuPlanCalendar() {
        LoginFormData loginFormData = LoginFormData.getDefaultLoginFormData()
        loginToHomepage(loginFormData)
        get("/menuPlan/create")
        Integer initialCount = MenuPlan.count()
        assertStatus 200
        assertTitleContains 'Minute Menu Plan : Create MenuPlan'
        def menuPlanName = System.currentTimeMillis()
        byName("menuPlan.name").setValue("TestMenuPlan${menuPlanName}")
        byName('create').click()
        redirectEnabled = false
        followRedirect()
        assertStatus 200
        assertTitle("Minute Menu Plan : TestMenuPlan${menuPlanName.toString()}")
        Integer finalCount = MenuPlan.count()
        assertTrue('Unable to create a menu-plan from the menu calendar', (finalCount - initialCount == 1))
    }

    /* This plan validates the update of monthly menuPlan
    *  This test fails if menuPlan is not updated.
    */

    void testEditMenuPlanCalendar() {
        LoginFormData loginFormData = LoginFormData.getDefaultLoginFormData()
        loginToHomepage(loginFormData)
        get("/menuPlan/create")
        Integer initialCount = MenuPlan.count()
        assertStatus 200
        assertTitleContains 'Minute Menu Plan : Create MenuPlan'
        def menuPlanName = System.currentTimeMillis()
        byName("menuPlan.name").setValue("TestMenuPlan${menuPlanName}")
        byName('create').click()
        redirectEnabled = false
        followRedirect()
        assertStatus 200
        assertTitle("Minute Menu Plan : TestMenuPlan${menuPlanName.toString()}")
        Integer intermediateCount = MenuPlan.count()
        assertTrue('Edit MenuPlan unables to create a new menuplan', (intermediateCount - initialCount == 1))
        byClass('editMenuPlanButtonFT').click()
        assertStatus 200
        assertTitle("Minute Menu Plan : Edit TestMenuPlan${menuPlanName.toString()}")
        byName("menuPlan.name").setValue("ChangedMenuPlanName${menuPlanName}")
        byName('update').click()
        redirectEnabled = false
        followRedirect()
        assertStatus 200
        assertTitleContains "ChangedMenuPlanName"
        Integer finalCount = MenuPlan.count()
        assertTrue('Edit MenuPlan unables to update a menuplan list', (finalCount - intermediateCount == 0))
    }

    /* This plan validates the generation of shopping list from the monthly menuPlan
    *  This test fails if shopping list is not generated from the menuplan.
    */

    void testGenerateShoppingList_FromMenuPLan() {
        LoginFormData loginFormData = LoginFormData.getDefaultLoginFormData()
        loginToHomepage(loginFormData)
        get("/menuPlan/create")
        assertStatus 200
        assertTitleContains 'Minute Menu Plan : Create MenuPlan'
        def menuPlanName = System.currentTimeMillis()
        byName("menuPlan.name").setValue("TestMenuPlan${menuPlanName}")
        byName('create').click()
        redirectEnabled = false
        followRedirect()
        assertStatus 200
        assertTitle("Minute Menu Plan : TestMenuPlan${menuPlanName.toString()}")
        def shoppingListLink = byClass('createShoppingListLinkFT')
        shoppingListLink.click()
        assertTitle 'Minute Menu Plan : Generate Shopping List'
    }
}
