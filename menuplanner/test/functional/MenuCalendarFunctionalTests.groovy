import com.mp.domain.*

class MenuCalendarFunctionalTests extends MenuPlannerFunctionalTests {

    void testCreateMenuPlanCalendar() {
        LoginFormData loginFormData = LoginFormData.getDefaultLoginFormData()
        userLogin(loginFormData)
        get("/menuPlan/create")
        assertStatus 200
        assertTitleContains 'Minute Menu Plan : Create MenuPlan'

        def menuPlanName=System.currentTimeMillis()
        byName("menuPlan.name").setValue("TestMenuPlan${menuPlanName}")
        byName('create').click()
        redirectEnabled = false
        followRedirect()
        assertStatus 200
        assertTitleContains menuPlanName.toString()
    }

    void testEditMenuPlanCalendar() {
        LoginFormData loginFormData = LoginFormData.getDefaultLoginFormData()
        userLogin(loginFormData)
        get("/menuPlan/create")
        assertStatus 200
        assertTitleContains 'Minute Menu Plan : Create MenuPlan'

        def menuPlanName=System.currentTimeMillis()
        byName("menuPlan.name").setValue("TestMenuPlan${menuPlanName}")
        byName('create').click()
        redirectEnabled = false
        followRedirect()
        assertStatus 200
        assertTitleContains menuPlanName.toString()

        byName('editMenuPlan').click()
        assertTitleContains "Edit"
        assertTitleContains menuPlanName.toString()
        byName("menuPlan.name").setValue("ChangedMenuPlanName${menuPlanName}")
        byName('update').click()
        redirectEnabled = false
        followRedirect()
        assertStatus 200
        assertTitleContains "ChangedMenuPlanName"
    }
}
