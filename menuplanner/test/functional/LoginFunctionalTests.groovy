import com.mp.domain.*

class LoginFunctionalTests extends MenuPlannerFunctionalTests {

    /*
    * This test validates the login of user with wrong password.
    * The test fails if user is able to login with an incorrect password
    */

    void testLogin_WrongPassword() {
        LoginFormData loginFormData = LoginFormData.getDefaultLoginFormData()
        loginFormData.password = "WrongPassword"
        login(loginFormData)
        assertStatus 200
        assertEquals('Invalid password message not displayed / not displayed on correct location', getMessage('loginCO.email.password.Invalid'), byId('display_WrongPassword_DisabledUser_Error').asText())
        assertTitle 'Minute Menu Plan : Minute Menu Plan'
    }

    /*
    * This test validates the blank validation of username field.
    * The test fails if validation for username doesn't work.
    */

    void testBlankUserName() {
        LoginFormData loginFormData = LoginFormData.getDefaultLoginFormData()
        loginFormData.email = ""
        login(loginFormData)
        assertStatus 200
        assertEquals('Blank username message not displayed / not displayed on correct location', getMessage('loginCO.email.blank.error.email'), byId('displayEmailError').asText())
        assertTitle 'Minute Menu Plan : Minute Menu Plan'
    }

    /*
    * This test validates the invalid username used for login the application.
    * The test fails if the user is able to login with an incorrect username.
    */

    void testInvalidUserName() {
        LoginFormData loginFormData = LoginFormData.getDefaultLoginFormData()
        loginFormData.email = "InvalidUsernane."
        login(loginFormData)
        assertStatus 200
        assertEquals('Invalid Username message not displayed / not displayed on correct location', getMessage('loginCO.email.email.invalid.email'), byId('displayEmailError').asText())
        assertTitle 'Minute Menu Plan : Minute Menu Plan'
    }

    /*
    * This test validates the login in the application.
    * The test fails if the is not able to login with valid login credentials.
    */

    void testValidLogin() {
        LoginFormData loginFormData = LoginFormData.getDefaultLoginFormData()
        loginToHomepage(loginFormData)
        assertTitle 'Minute Menu Plan : List Recipe'
        assertEquals('Logout link not found on the recipe list page', 'Logout', byClass('logoutLink').asText())
    }

    /*
    * This test validates the login in the application by a disabled user.
    * The test fails if a disabled user is able to login..
    */

    void testLogin_BY_DISABLED_USER() {
        javaScriptEnabled = false
        loginBySuperAdmin()
        UserFormData userFormData = UserFormData.getDefaultUserFormData()
        userFormData.isEnabled = false
        userFormData.isUser = true
        createUser(userFormData)
        byClass('logoutLink').click()
        redirectEnabled = false
        followRedirect()

        LoginFormData loginFormData = LoginFormData.getDefaultLoginFormData()
        loginFormData.email = userFormData.email
        loginFormData.password = userFormData.password
        login(loginFormData)
        assertEquals('Subscriber disabled message not displayed / not displayed on correct location', getMessage('loginCO.user.disabled'), byId('display_WrongPassword_DisabledUser_Error').asText())
    }

    /*
    * This test validates the logout functionality of the application.
    * The test fails if user is not able to logout.
    */

    void testLogout() {
        LoginFormData loginFormData = LoginFormData.getDefaultLoginFormData()
        loginToHomepage(loginFormData)
        assertTitle 'Minute Menu Plan : List Recipe'
        byClass('logoutLink').click()
        redirectEnabled = false
        followRedirect()
        assertEquals('Login button not found on login page', 'Login', byName('_action_login').asText())
        assertTitle 'Minute Menu Plan : Minute Menu Plan'
    }

    /*
    * This test validates the Urls of the application.
    * The test fails when any Url throws an exception.
    */

    void testAllUrls() {
        loginBySuperAdmin()
        javaScriptEnabled = false
        List<String> urls = ["/user/list", "/recipe/create", "/menuPlan/create", "/recipe/list", "/user/create"]
        Recipe recipe = Recipe.list().first()
        ShoppingList shoppingList = ShoppingList.list().first()
        Subscriber user = Subscriber.list().first()
        MenuPlan menuPlan = MenuPlan.list().first()
        urls.add("/recipe/show/${recipe.id}")
        urls.add("/recipe/edit/${recipe.id}")
        urls.add("/shoppingList/show/${shoppingList.id}")
        urls.add("/shoppingList/edit/${shoppingList.id}")
        urls.add("/user/show/${user.id}")
        urls.add("/menuPlan/show/${menuPlan.id}")
        urls.add("/menuPlan/edit/${menuPlan.id}")
        urls.each {String url ->
            get(url)
            assertStatus(200)
        }
    }
}