class LoginFunctionalTests extends MenuPlannerFunctionalTests {
    void testWrongPassword() {
        LoginFormData loginFormData=LoginFormData.getDefaultLoginFormData()
        loginFormData.password="Password"
        userLogin(loginFormData)
        assertStatus 200
        assertContentContains 'The username or password you entered is incorrect.'
    }

    void testBlankUserName() {
        LoginFormData loginFormData=LoginFormData.getDefaultLoginFormData()
        loginFormData.email=""
        userLogin(loginFormData)
        assertStatus 200
        assertContentContains 'Provide Username.'
    }

    void testInvalidUserName() {
        LoginFormData loginFormData=LoginFormData.getDefaultLoginFormData()
        loginFormData.email="Usernane."
        userLogin(loginFormData)
        assertStatus 200
        assertContentContains 'Invalid Usernane.'
    }

    void testValidLogin() {
        LoginFormData loginFormData=LoginFormData.getDefaultLoginFormData()
        userLogin(loginFormData)
        assertStatus 200
        assertContentContains 'Logout'
    }
}