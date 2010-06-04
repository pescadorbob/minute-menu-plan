class MenuCalendarFunctionalTests extends MenuPlannerFunctionalTests {

    void testCreateMenuPlanCalendar() {
        LoginFormData loginFormData = LoginFormData.getDefaultLoginFormData()
        userLogin(loginFormData)
        get("/menuPlan/create")
        assertStatus 200
        assertTitleContains 'Minute Menu Plan : Create MenuPlan'

        def menuPlanName=System.currentTimeMillis()
        form('editMenuPlanForm') {
            menuPlan.name = "TestMenuPlan${menuPlanName}"
        }
        byName('create').click()
        redirectEnabled = false
        followRedirect()
        assertStatus 200
        assertTitleContains menuPlanName
    }

    void testEditMenuPlanCalendar() {
        LoginFormData loginFormData = LoginFormData.getDefaultLoginFormData()
        userLogin(loginFormData)
        get("/menuPlan/create")
        assertStatus 200
        assertTitleContains 'Minute Menu Plan : Create MenuPlan'

        def menuPlanName=System.currentTimeMillis()
        form('editMenuPlanForm') {
            menuPlan.name = "TestMenuPlan${menuPlanName}"
        }
        byName('create').click()
        redirectEnabled = false
        followRedirect()
        assertStatus 200
        assertTitleContains menuPlanName

        byName('editMenuPlan').click()
        redirectEnabled = false
        followRedirect()
        assertTitleContains "Edit ${menuPlanName}"
        form('editMenuPlanForm') {
            menuPlan.name = "ChangedMenuPlanName${menuPlanName}"
        }
        byName('create').click()
        redirectEnabled = false
        followRedirect()
        assertStatus 200
        assertTitleContains "ChangedMenuPlanName"

    }


}
