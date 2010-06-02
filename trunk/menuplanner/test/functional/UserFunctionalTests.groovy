import com.gargoylesoftware.htmlunit.html.*

class UserFunctionalTests extends MenuPlannerFunctionalTests {
    void testCreateUser_VALID() {
        UserFormData userFormData = UserFormData.getDefaultUserFormData()
        createUser(userFormData)
        assertStatus 200
        assertTitle 'Minute Menu Plan : Show User'
    }
    void testCreateUser_INVALID_EMAIL() {
        UserFormData userFormData = UserFormData.getDefaultUserFormData()
        userFormData.email = "invalidEmailType"
        createUser(userFormData)
        assertStatus 200
        assertElementTextContains('displayUserCOErrors', getMessage('userCO.email.email.error.com.mp.domain.UserCO.email'))

    }
    void testCreateUser_PASSWORD_CONFIRMPASSWORD_NOT_MATCHED() {
        UserFormData userFormData = UserFormData.getDefaultUserFormData()
        userFormData.password = "1234"
        userFormData.confirmPassword = "abcd"
        createUser(userFormData)
        assertStatus 200
        assertElementTextContains('displayUserCOErrors', getMessage('userCO.confirmPassword.validator.invalid.confirmPassword'))
    }
}