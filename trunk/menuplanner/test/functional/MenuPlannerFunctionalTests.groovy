import org.codehaus.groovy.grails.commons.ConfigurationHolder
import com.gargoylesoftware.htmlunit.html.HtmlPage
import com.mp.domain.*
import static com.mp.domain.TestConstants.*


class MenuPlannerFunctionalTests extends functionaltestplugin.FunctionalTestCase {

    def messageSource = ConfigurationHolder.config.applicationContext.getBean("messageSource")

    Locale locale = new Locale('en', 'US')
    Object[] TARGET_ARGS_EMPTY = [].toArray()
    Object[] TARGET_ARGS_WITH_VALUES = [].toArray()

    //Test fails if a test file doesn't contains any test method.
    /* These test requires atleast one menuplan created during boot-strap*/

    void testSomething() {}

    void login(LoginFormData loginFormData) {
        get("/login/index")
        form('loginForm') {
            email = loginFormData.email
            password = loginFormData.password
            byClass('userLoginLink').click()
        }
    }

    void loginToHomepage(LoginFormData loginFormData) {
        login(loginFormData)
        get('/recipe/list')
    }

    void logout() {
        def logoutLink = byClass('logoutLink')
        logoutLink.click()
    }

    /*
    *  Helper method to create a new recipe
    */

    void createRecipe(CreateRecipeData createRecipeData, Boolean isAlcoholic = false) {
        javaScriptEnabled = false
        get("/recipe/create")
        form('formCreateRecipe') {
            name = createRecipeData.name
            preparationTime = createRecipeData.prepTime
            cookTime = createRecipeData.cookTime
            makesServing = createRecipeData.servings
            hiddenIngredientProductNames = createRecipeData.productName_1
            directions = createRecipeData.step_1
            byId('shareWithCommunity').click()
            byId('serveWithItems1').setValue(createRecipeData.serveWith_1)
            byId('serveWithItems2').setValue(createRecipeData.serveWith_2)
            byId('txtCalories').setValue(createRecipeData.calories)
            if (isAlcoholic) {
                byId('isAlcoholic').click()
            }
            click("_action_save")
        }
    }

    void createRecipeWithNewAisle(CreateRecipeData createRecipeData, String aisle) {
        javaScriptEnabled = false
        get("/recipe/create")
        form('formCreateRecipe') {
            name = createRecipeData.name
            preparationTime = createRecipeData.prepTime
            cookTime = createRecipeData.cookTime
            makesServing = createRecipeData.servings
            hiddenIngredientProductNames = createRecipeData.productName_1
            hiddenIngredientAisleNames = aisle
            directions = createRecipeData.step_1
            byId('serveWithItems1').setValue(createRecipeData.serveWith_1)
            byId('serveWithItems2').setValue(createRecipeData.serveWith_2)
            byId('txtCalories').setValue(createRecipeData.calories)
            click("_action_save")
        }
    }

    /*
    *  Helper method to create a new user
    */

    void createUser(UserFormData userFormData) {
        get("/user/create")
        form('formCreateUser') {
            email = userFormData.email
            password = userFormData.password
            confirmPassword = userFormData.confirmPassword
            name = userFormData.name
            if (!userFormData.isEnabled) { byId('chk_Enable').click() }
            if (userFormData.isSuperAdmin) {byId('chk_SuperAdmin').click()}
            if (userFormData.isAdmin) { byId('chk_Admin').click()}
            if (userFormData.isUser) {byId('chk_Subscriber').click()}
            click('_action_save')
        }
    }

    /*
    *  Helper method to create a new shopping list
    */

    void createShoppingList(ShoppingListFormData shoppingListFormData) {
        form('formShoppingList') {
            name = shoppingListFormData.name
            menuPlanId = byId('menuPlanId').getOption(0).getValueAttribute()
            servings = shoppingListFormData.servings
            click('_action_createOriginal')
        }
    }

    void createShoppingListBlankServings(ShoppingListFormData shoppingListFormData) {
        form('formShoppingList') {
            name = shoppingListFormData.name
            menuPlanId = byId('menuPlanId').getOption(0).getValueAttribute()
            byId('servings').setValue("")
            byClass('listWithWeeksFT').click()
            byClass('shoppingWeekFT_1').click()
            byClass('shoppingWeekFT_2').click()
            byClass('shoppingWeekFT_3').click()
            byClass('shoppingWeekFT_4').click()
            click('_action_createScaled')
        }
    }

    void createShoppingList_Three_Weeks_Selected(ShoppingListFormData shoppingListFormData) {
        form('formShoppingList') {
            name = shoppingListFormData.name
            menuPlanId = byId('menuPlanId').getOption(0).getValueAttribute()
            servings = shoppingListFormData.servings
            byClass('listWithWeeksFT').click()
            byClass('shoppingWeekFT_2').click()
            byClass('shoppingWeekFT_3').click()
            byClass('shoppingWeekFT_4').click()
            click('_action_createScaled')
        }
    }

    void createShoppingList_All_Fields_Blank() {
        form('formShoppingList') {
            name = ""
            servings = ""
            byClass('listWithWeeksFT').click()
            click('_action_createScaled')
        }
    }

    void createShoppingList_Blank_Weeks(ShoppingListFormData shoppingListFormData) {
        form('formShoppingList') {
            name = shoppingListFormData.name
            menuPlanId = byId('menuPlanId').getOption(0).getValueAttribute()
            servings = shoppingListFormData.servings
            byClass('listWithWeeksFT').click()
            click('_action_createScaled')
        }
    }

    /*
    *  Helper method to create user from home-page
    */

    void createUserFromLandingPage(NewUserFormData newUserFormData) {
        get('/user/createFreeUser')
        form('freeSignupForm') {
            name = newUserFormData.name
            city = newUserFormData.city
            email = newUserFormData.email
            password = newUserFormData.password
            confirmPassword = newUserFormData.confirmPassword
            mouthsToFeed = newUserFormData.mouthsToFeed
        }
        def createFreeUserLink = byClass('createFreeUserFT')
        createFreeUserLink.click()
    }

    /*
    *  Helper method to login using superadmin userId and password
    */

    void loginBySuperAdmin() {
        LoginFormData loginFormData = LoginFormData.getDefaultLoginFormData()
        loginFormData.email = SUPER_ADMIN
        loginFormData.password = USER_PASSWORD
        loginToHomepage(loginFormData)
    }

    /*
    *  Helper method to go directly on create recipe page
    */

    void gotoCreateRecipePage() {
        LoginFormData loginFormData = LoginFormData.getDefaultLoginFormData()
        loginToHomepage(loginFormData)
        javaScriptEnabled = false
        get('/recipe/create')
    }

    /*
    *  Helper method to create a new recipe and go to its show page
    */

    void gotoShowRecipePage() {
        LoginFormData loginFormData = LoginFormData.getDefaultLoginFormData()
        loginToHomepage(loginFormData)
        CreateRecipeData createRecipeData = CreateRecipeData.getDefaultCreateRecipeData()
        createRecipe(createRecipeData)
    }

    /*
    *  Helper method to create a new recipe and go to its edit page
    */

    void gotoEditRecipePage(CreateRecipeData createRecipeData) {
        LoginFormData loginFormData = LoginFormData.getDefaultLoginFormData()
        loginToHomepage(loginFormData)
        createRecipe(createRecipeData)
        click('Edit')
    }

    /*
    *  Helper method to go to generate user's shopping list created in bootstrap
    */

    void goToGenerateShoppingListPage() {
        LoginFormData loginFormData = LoginFormData.getDefaultLoginFormData()
        loginToHomepage(loginFormData)
        LoginCredential credential = UserLogin.findByEmail(loginFormData.email)
        ShoppingList shoppingList = ShoppingList.findByParty(credential.party)
        get("/shoppingList/generateShoppingList/${shoppingList?.id}")
    }

    /*
    *  Helper method to go to create user's shopping list created in bootstrap
    */

    void goToCreateShoppingListPage() {
        LoginFormData loginFormData = LoginFormData.getDefaultLoginFormData()
        loginToHomepage(loginFormData)
        LoginCredential credential = UserLogin.findByEmail(loginFormData.email)
        Subscriber user = Subscriber.findByParty(credential.party)
        ShoppingList shoppingList = ShoppingList.findByParty(user.party)
        get("/shoppingList/generateShoppingList/${shoppingList?.id}")
        def createLink = byName('_action_create')
        createLink.click()
    }

    /** * Helper method to return a message from the message bundle.                                                           ***/
    String getMessage(String key, def targetArgs = TARGET_ARGS_EMPTY) {
        def keyValue = messageSource.resolveCode(key, locale)
        return keyValue?.format(targetArgs)
    }

    void waitForElementToAppear(String elementName, int numberOfSeconds = 600) {
        int counter = 0;
        while (counter < numberOfSeconds) {
            Thread.sleep(1000)
            def element = byId(elementName)
            if (!element) {
                element = byName(elementName)
            }
            if (element) {
                return;
            }
            counter = counter + 1;
        }
        fail("Element with the name ${elementName} not found on page.")
    }
}
