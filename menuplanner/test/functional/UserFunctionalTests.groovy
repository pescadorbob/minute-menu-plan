class UserFunctionalTests extends MenuPlannerFunctionalTests {
    void testCreateUser() {
        UserFormData userFormData = UserFormData.getDefaultUserFormData()
        createUser(userFormData)
        assertStatus 200
        assertContentContains 'Edit'
    }
}
