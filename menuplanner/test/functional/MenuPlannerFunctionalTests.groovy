import org.codehaus.groovy.grails.commons.ConfigurationHolder
import com.gargoylesoftware.htmlunit.html.HtmlPage
import com.mp.domain.*

class MenuPlannerFunctionalTests extends functionaltestplugin.FunctionalTestCase {

    def messageSource = ConfigurationHolder.config.applicationContext.getBean("messageSource")

    Locale locale = new Locale('en', 'US')
    Object[] TARGET_ARGS_EMPTY = [].toArray()
    Object[] TARGET_ARGS_WITH_VALUES = [].toArray()

    //Test fails if a test file doesn't contains any test method.
    void testSomething() {}

    void userLogin(LoginFormData loginFormData) {
        get("/login/index")
        form('loginForm') {
            email = loginFormData.email
            password = loginFormData.password
            byName('_action_login').click()
        }
    }

    void createRecipe(CreateRecipeData createRecipeData) {
        LoginFormData loginFormData = LoginFormData.getDefaultLoginFormData()
        userLogin(loginFormData)
        get("/recipe/create")
        form('formCreateRecipe') {
            name = createRecipeData.name
            preparationTime = createRecipeData.prepTime
            cookTime = createRecipeData.cookTime
            optionIngredientProductIds = createRecipeData.productName_1
            click "btnAddIngredient"
            optionDirections = createRecipeData.step_1
            click "btnAddDirection"
            byId('serveWithItems1').setValue(createRecipeData.serveWith_1)
            byId('serveWithItems2').setValue(createRecipeData.serveWith_2)
            byId('txtCalories').setValue(createRecipeData.calories)
            click("_action_save")
        }
    }

    void createUser(UserFormData userFormData) {
        get("/user/create")
        form('formCreateUser') {
            email = userFormData.email
            password = userFormData.password
            confirmPassword = userFormData.confirmPassword
            name = userFormData.name
            city = userFormData.city
            mouthsToFeed = userFormData.mouthsToFeed
            introduction = userFormData.introduction
            if (!userFormData.isEnabled) { byId('chk_Enable').click() }
            if (userFormData.isSuperAdmin) {byId('chk_SuperAdmin').click()}
            if (userFormData.isAdmin) {byId('chk_Admin').click()}
            if (userFormData.isUser) {byId('chk_User').click()}
            click('_action_save')
        }
    }

    void loginBySuperAdmin() {
        LoginFormData loginFormData = LoginFormData.getDefaultLoginFormData()
        loginFormData.email = "qa.menuplanner+superAdmin@gmail.com"
        loginFormData.password = "1234"
        userLogin(loginFormData)
    }

    void gotoCreateRecipePage() {
        LoginFormData loginFormData = LoginFormData.getDefaultLoginFormData()
        userLogin(loginFormData)
        get('/recipe/create')
    }

    void gotoShowRecipePage() {
        CreateRecipeData createRecipeData = CreateRecipeData.getDefaultCreateRecipeData()
        createRecipe(createRecipeData)
    }

    void gotoEditRecipePage() {
        CreateRecipeData createRecipeData = CreateRecipeData.getDefaultCreateRecipeData()
        createRecipe(createRecipeData)
        click('Edit')
    }

    /** * Helper method to return a message from the message bundle.       ***/
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
