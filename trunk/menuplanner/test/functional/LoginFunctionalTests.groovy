class LoginFunctionalTests extends MenuPlannerFunctionalTests {
    
    void testWrongPassword() {
        LoginFormData loginFormData = LoginFormData.getDefaultLoginFormData()
        loginFormData.password = "Password"
        userLogin(loginFormData)
        assertStatus 200
        assertEquals(getMessage('loginCO.email.password.Invalid'), byId('display_WrongPassword_DisabledUser_Error').asText())
    }

    void testBlankUserName() {
        LoginFormData loginFormData = LoginFormData.getDefaultLoginFormData()
        loginFormData.email = ""
        userLogin(loginFormData)
        assertStatus 200
        assertEquals(getMessage('loginCO.email.blank.error.email'), byId('displayEmailError').asText())
    }

    void testInvalidUserName() {
        LoginFormData loginFormData = LoginFormData.getDefaultLoginFormData()
        loginFormData.email = "Usernane."
        userLogin(loginFormData)
        assertStatus 200
        assertEquals(getMessage('loginCO.email.email.invalid.email'), byId('displayEmailError').asText())
    }

    void testValidLogin() {
        LoginFormData loginFormData = LoginFormData.getDefaultLoginFormData()
        userLogin(loginFormData)
        get('/recipe/list')
        assertEquals('Logout', byClass('logoutLink').asText())
    }

    void testLogin_BY_DISABLED_USER(){
        UserFormData userFormData = UserFormData.getDefaultUserFormData()
        userFormData.isEnabled = false
        createUser(userFormData)
        LoginFormData loginFormData = LoginFormData.getDefaultLoginFormData()
        loginFormData.email=userFormData.email
        loginFormData.password=userFormData.password
        userLogin(loginFormData)
        assertEquals(getMessage('loginCO.user.disabled'), byId('display_WrongPassword_DisabledUser_Error').asText())        
    }

    void testLogout() {
        LoginFormData loginFormData = LoginFormData.getDefaultLoginFormData()
        userLogin(loginFormData)
        get('/recipe/list')
        byClass('logoutLink').click()
        redirectEnabled = false
        followRedirect()
        assertEquals('Login', byName('_action_login').asText())
    }
}