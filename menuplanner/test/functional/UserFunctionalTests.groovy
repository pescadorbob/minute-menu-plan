import com.gargoylesoftware.htmlunit.html.*

class UserFunctionalTests extends MenuPlannerFunctionalTests {

    void testCreateUser_VALID() {
        loginBySuperAdmin()
        UserFormData userFormData = UserFormData.getDefaultUserFormData()
        userFormData.isUser = true
        createUser(userFormData)
        assertStatus 200
        assertTitle 'Minute Menu Plan : Show User'
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

/*
    void testEditUser() {
        UserFormData userFormData = UserFormData.getDefaultUserFormData()
        createUser(userFormData)
        byName('_action_edit').click()
        form('formUpdateUser') {
            byName('_action_update').click()
        }
        assertFalse(false)
    }
*/    
}