import com.gargoylesoftware.htmlunit.html.*
import com.mp.domain.*
import static com.mp.domain.TestConstants.*


class UserFunctionalTests extends MenuPlannerFunctionalTests {

    /*
    *  This test validates the creation of new user.
    * The test fails if it admin is not able to create a new user
    */

    void testCreateUser_VALID() {
        javaScriptEnabled = false
        Integer initialCount = Subscriber.count()
        loginBySuperAdmin()
        UserFormData userFormData = UserFormData.getDefaultUserFormData()
        userFormData.isUser = true
        createUser(userFormData)
        assertStatus 200
        Integer finalCount = Subscriber.count()
        assertTrue('Add User with valid values failed', (finalCount - initialCount == 1))
        assertTitle 'Minute Menu Plan : Show User'
        assertElementTextContains('flashMsgTst', getMessage('user.created.success'))
        logout()
    }

    /*
    *  This test fails if user form accepts invalid email address
    */

    void testCreateUser_INVALID_EMAIL() {
        javaScriptEnabled = false
        Integer initialCount = Subscriber.count()
        loginBySuperAdmin()
        UserFormData userFormData = UserFormData.getDefaultUserFormData()
        userFormData.isUser = true
        userFormData.email = "invalidEmailType"
        createUser(userFormData)
        assertStatus 200
        Integer finalCount = Subscriber.count()
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
        javaScriptEnabled = false
        loginBySuperAdmin()
        Integer initialCount = Subscriber.count()
        UserFormData userFormData = UserFormData.getDefaultUserFormData()
        userFormData.isUser = true
        userFormData.password = "1234"
        userFormData.confirmPassword = "abcd"
        createUser(userFormData)
        assertStatus 200
        Integer finalCount = Subscriber.count()
        assertTrue('User added with password and confirm password with different values', (finalCount - initialCount == 0))
        assertTitle 'Minute Menu Plan : Add User'
        assertElementTextContains('displayUserCOErrors', getMessage('userCO.confirmPassword.validator.invalid.confirmPassword'))
        logout()
    }

    void testEditUser() {
        LoginFormData loginFormData = LoginFormData.getDefaultLoginFormData()
        loginToHomepage(loginFormData)
        javaScriptEnabled = false
        byName('profileLinkTst').click()
        assertTitle 'Minute Menu Plan : Show User'
        byClass('editUserButtonFT').click()
        assertTitle 'Minute Menu Plan : Edit User'
        byClass('updateUserButtonFT').click()
        redirectEnabled = false
        followRedirect()
        assertTitle 'Minute Menu Plan : Show User'
        assertElementTextContains('flashMsgTst', getMessage('user.updateded.success'))
        logout()
    }

    void test_User_ChangePassword() {
        LoginFormData loginFormData = LoginFormData.getDefaultLoginFormData()
        javaScriptEnabled = false
        loginToHomepage(loginFormData)
        loginFormData.password = 'newpassword'
        changePasswordAndLogin(loginFormData.password)
        loginToHomepage(loginFormData)
        assertTitle 'Minute Menu Plan : List Recipe'
        loginFormData.password = USER_PASSWORD
        changePasswordAndLogin(loginFormData.password)
    }
//
//    void testAddUser_FromHomePage() {
//        get('/')
//        def createUserLink = byClass('createUserLinkFT')
//        createUserLink.click()
//        assertTitle 'Minute Menu Plan : Add User'
//        //TODO: Create user by filling the form
//    }
//
//    void testGoogleCheckOutLink() {
//        get('/')
//        def createUserLink = byClass('createUserLinkFT')
//        createUserLink.click()
//        def googleCheckoutLink = byClass('googleCheckoutLinkFT')
//        googleCheckoutLink.click()
//        redirectEnabled = false
//        followRedirect()
//        Thread.sleep(15000)
//        assertTitle 'Secure checkout with Google'
//        assertContentContains 'Monthly Subscription'
//        assertContentContains 'Monthly Subscription of MenuPlanner '
//        assertContentContains 'Shop confidently with Google Checkout '
//    }

    public void changePasswordAndLogin(String newPassword) {
        byName('profileLinkTst').click()
        assertTitle 'Minute Menu Plan : Show User'
        byClass('editUserButtonFT').click()
        assertTitle 'Minute Menu Plan : Edit User'
        byName('password').setValue(newPassword)
        byName('confirmPassword').setValue(newPassword)
        byClass('updateUserButtonFT').click()
        redirectEnabled = false
        followRedirect()
        assertTitle 'Minute Menu Plan : Show User'
        assertElementTextContains('flashMsgTst', getMessage('user.updateded.success'))
        logout()
    }


    void test_Add_User_Create_Custom_Product_Delete_User_Recipe_Not_Deleted() {
        javaScriptEnabled = false
        Integer initialCount = Subscriber.count()
        loginBySuperAdmin()
        UserFormData userFormData = UserFormData.getDefaultUserFormData()
        userFormData.email = 'user@email.com'
        userFormData.isUser = true
        createUser(userFormData)
        assertStatus 200
        Integer finalCount = Subscriber.count()
        assertTrue('Add User with valid values failed', (finalCount - initialCount == 1))
        logout()
        get('/')
        LoginFormData loginFormData = LoginFormData.getDefaultLoginFormData()
        loginFormData.email = userFormData.email
        loginToHomepage(loginFormData)
        CreateRecipeData createRecipeData = CreateRecipeData.getDefaultCreateRecipeData()
        createRecipeData.productName_1 = "NewCustomProduct-${System.currentTimeMillis()}"
        createRecipeData.serveWith_1 = "NewServeWithProduct-${System.currentTimeMillis()}"
        createRecipeData.serveWith_2 = "NewServeProduct-${System.currentTimeMillis()}"
        Integer initialProductCount = Product.count()
        createRecipe(createRecipeData)
        Integer finalProductCount = Product.count()
        assertEquals "Unable to create Product for Recipe ", initialProductCount + 3, finalProductCount
        logout()

        loginBySuperAdmin()
        UserLogin userLogin = UserLogin.findByEmail(loginFormData.email)
        get("/user/show/${userLogin?.party?.id}")
        Integer userInitialCount = Subscriber.count()
        Integer initCount = Product.count()
        def deleteUserButton = byClass('deleteUserButtonFT')
        deleteUserButton.click()
        Integer finCount = Product.count()
        Integer userFinalCount = Subscriber.count()
        assertEquals "Unable to Delete Product of User, when user is deleted", finCount + 2, initCount
        assertEquals "Unable to delete User", userFinalCount + 1, userInitialCount
    }

    void test_Add_User_Create_Custom_Product_Delete_Recipe_Delete_User_Product_Deleted() {
        javaScriptEnabled = false
        Integer initialCount = Subscriber.count()
        loginBySuperAdmin()
        UserFormData userFormData = UserFormData.getDefaultUserFormData()
        userFormData.email = 'user@email.com'
        userFormData.isUser = true
        createUser(userFormData)
        assertStatus 200
        Integer finalCount = Subscriber.count()
        assertTrue('Add User with valid values failed', (finalCount - initialCount == 1))
        logout()

        LoginFormData loginFormData = LoginFormData.getDefaultLoginFormData()
        loginFormData.email = userFormData.email
        loginToHomepage(loginFormData)
        CreateRecipeData createRecipeData = CreateRecipeData.getDefaultCreateRecipeData()
        createRecipeData.productName_1 = "NewCustomTestProduct-${System.currentTimeMillis()}"
        createRecipeData.serveWith_1 = "NewServeWithTestProduct-${System.currentTimeMillis()}"
        createRecipeData.serveWith_2 = "NewServeTestProduct-${System.currentTimeMillis()}"
        Integer initialProductCount = Product.count()
        createRecipe(createRecipeData)
        Integer finalProductCount = Product.count()
        assertEquals "Unable to create Product for Recipe ", initialProductCount + 3, finalProductCount
        click('Edit')
        def deleteRecipeButton = byName('_action_delete')
        deleteRecipeButton.click()
        redirectEnabled = false
        followRedirect()
        logout()

        loginBySuperAdmin()
        UserLogin userLogin = UserLogin.findByEmail(loginFormData.email)
        get("/user/show/${userLogin?.party?.id}")
        Integer userInitialCount = Subscriber.count()
        Integer initCount = Product.count()
        def deleteUserButton = byClass('deleteUserButtonFT')
        deleteUserButton.click()
        redirectEnabled = false
        followRedirect()
        Thread.sleep(10000)
        Integer finCount = Product.count()
        Integer userFinalCount = Subscriber.count()
        assertEquals "Unable to Delete Product of User, when user is deleted", finCount + 3, initCount
        assertEquals "Unable to delete User", userFinalCount + 1, userInitialCount
    }

}