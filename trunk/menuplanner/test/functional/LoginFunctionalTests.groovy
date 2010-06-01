class LoginFunctionalTests extends MenuPlannerFunctionalTests {
    void testWrongPassword() {
        LoginFormData loginFormData = LoginFormData.getDefaultLoginFormData()
        loginFormData.password = "Password"
        userLogin(loginFormData)
        assertStatus 200
        assertContentContains getMessage('loginCO.email.password.Invalid')
    }

    void testBlankUserName() {
        LoginFormData loginFormData = LoginFormData.getDefaultLoginFormData()
        loginFormData.email = ""
        userLogin(loginFormData)
        assertStatus 200
        assertContentContains getMessage('loginCO.email.blank.error.email')
    }

    void testInvalidUserName() {
        LoginFormData loginFormData = LoginFormData.getDefaultLoginFormData()
        loginFormData.email = "Usernane."
        userLogin(loginFormData)
        assertStatus 200
        assertContentContains getMessage('loginCO.email.email.invalid.email')
    }

/*
    void testValidLogin() {
        LoginFormData loginFormData = LoginFormData.getDefaultLoginFormData()
        userLogin(loginFormData)
        redirectEnabled = false
        followRedirect()
        assertContentContains 'Logout'
    }

    */
}