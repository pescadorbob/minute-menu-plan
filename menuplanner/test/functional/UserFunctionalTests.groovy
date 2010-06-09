import com.gargoylesoftware.htmlunit.html.*

class UserFunctionalTests extends MenuPlannerFunctionalTests {

    void testCreateUser_VALID() {
        loginBySuperAdmin()
        UserFormData userFormData = UserFormData.getDefaultUserFormData()
        userFormData.isUser = true
        createUser(userFormData)
        assertStatus 200
        assertElementTextContains('flashMsgTst', getMessage('user.created.success'))
    }

    void testCreateUser_INVALID_EMAIL() {
        loginBySuperAdmin()
        UserFormData userFormData = UserFormData.getDefaultUserFormData()
        userFormData.isUser = true
        userFormData.email = "invalidEmailType"
        createUser(userFormData)
        assertStatus 200
        assertElementTextContains('displayUserCOErrors', getMessage('userCO.email.email.error.com.mp.domain.UserCO.email'))

    }

    void testCreateUser_PASSWORD_CONFIRMPASSWORD_NOT_MATCHED() {
        loginBySuperAdmin()
        UserFormData userFormData = UserFormData.getDefaultUserFormData()
        userFormData.isUser = true
        userFormData.password = "1234"
        userFormData.confirmPassword = "abcd"
        createUser(userFormData)
        assertStatus 200
        assertElementTextContains('displayUserCOErrors', getMessage('userCO.confirmPassword.validator.invalid.confirmPassword'))
    }

    void testEditUser() {
        LoginFormData loginFormData = LoginFormData.getDefaultLoginFormData()
        userLogin(loginFormData)
        get('/recipe/list')
        byName('profileLinkTst').click()
        byName('_action_edit').click()
        byName('_action_update').click()
        redirectEnabled = false
        followRedirect()
        assertElementTextContains('flashMsgTst', getMessage('user.updateded.success'))
    }
}