import com.gargoylesoftware.htmlunit.html.*
import com.mp.domain.*

class UserFunctionalTests extends MenuPlannerFunctionalTests {

    /*
    *  This test validates the creation of new user.
    * The test fails if it admin is not able to create a new user
    */

    void testCreateUser_VALID() {
        Integer initialCount = User.count()
        loginBySuperAdmin()
        UserFormData userFormData = UserFormData.getDefaultUserFormData()
        userFormData.isUser = true
        createUser(userFormData)
        assertStatus 200
        Integer finalCount = User.count()
        assertTrue('Add User with valid values failed', (finalCount - initialCount == 1))
        assertTitle 'Minute Menu Plan : Show User'
        assertElementTextContains('flashMsgTst', getMessage('user.created.success'))
        logout()
    }

    /*
    *  This test fails if user form accepts invalid email address
    */

    void testCreateUser_INVALID_EMAIL() {
        Integer initialCount = User.count()
        loginBySuperAdmin()
        UserFormData userFormData = UserFormData.getDefaultUserFormData()
        userFormData.isUser = true
        userFormData.email = "invalidEmailType"
        createUser(userFormData)
        assertStatus 200
        Integer finalCount = User.count()
        assertTrue('Add User with invalid values failed', (finalCount - initialCount == 0))
        assertTitle 'Minute Menu Plan : Add User'
        assertElementTextContains('displayUserCOErrors', getMessage('userCO.email.email.error.com.mp.domain.UserCO.email'))
        logout()
    }

    /*
    *  This test validates the password and confirm password entered during the user creation process.
    *  It fails if form accepts unmatched passwords
    */

    void testCreateUser_PASSWORD_CONFIRMPASSWORD_NOT_MATCHED() {
        loginBySuperAdmin()
        Integer initialCount = User.count()
        UserFormData userFormData = UserFormData.getDefaultUserFormData()
        userFormData.isUser = true
        userFormData.password = "1234"
        userFormData.confirmPassword = "abcd"
        createUser(userFormData)
        assertStatus 200
        Integer finalCount = User.count()
        assertTrue('User added with password and confirm password with different values', (finalCount - initialCount == 0))
        assertTitle 'Minute Menu Plan : Add User'
        assertElementTextContains('displayUserCOErrors', getMessage('userCO.confirmPassword.validator.invalid.confirmPassword'))
        logout()
    }

    void testEditUser() {
        LoginFormData loginFormData = LoginFormData.getDefaultLoginFormData()
        loginToHomepage(loginFormData)
        byName('profileLinkTst').click()
        assertTitle 'Minute Menu Plan : Show User'
        byClass('editUserButtonFT').click()
        Thread.sleep(55000)
        assertTitle 'Minute Menu Plan : Edit User'
        byClass('updateUserButtonFT').click()
        redirectEnabled = false
        followRedirect()
        assertTitle 'Minute Menu Plan : Show User'
        assertElementTextContains('flashMsgTst', getMessage('user.updateded.success'))
        logout()
    }
}