import org.codehaus.groovy.grails.commons.ConfigurationHolder
import com.gargoylesoftware.htmlunit.html.HtmlPage

class MenuPlannerFunctionalTests extends functionaltestplugin.FunctionalTestCase {

    def messageSource = ConfigurationHolder.config.applicationContext.getBean("messageSource")

    Locale locale = new Locale('en', 'US')
    Object[] TARGET_ARGS_EMPTY = [].toArray()
    Object[] TARGET_ARGS_WITH_VALUES = [].toArray()

    //Test fails if a test file doesn't contains any test method.
    void testSomething(){}

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
            byId('serveWithItems2').setValue(createRecipeData.serveWith_1)
            println "************************************************************** ${createRecipeData.calories}"
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
            if(!userFormData.isEnabled){ byId('chk_Enable').click() }
            byId('chk_User').click()
            click('_action_save')
        }
    }

    /** * Helper method to return a message from the message bundle.     ***/
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

class CreateRecipeData {
    String name
    String step_1
    String productName_1
    String cookTime
    String prepTime
    String serveWith_1
    String serveWith_2
    String calories

    public static CreateRecipeData getDefaultCreateRecipeData() {
        CreateRecipeData createRecipeData = new CreateRecipeData()
        createRecipeData.name = 'New Recipe'
        createRecipeData.step_1 = 'Step One'
        createRecipeData.productName_1 = 'Product One'
        createRecipeData.cookTime = ''
        createRecipeData.prepTime = ''
        createRecipeData.serveWith_1 = "Item-${System.currentTimeMillis().toString().substring(1,5)}"
        createRecipeData.serveWith_2 = "Item-${System.currentTimeMillis().toString().substring(1,5)}"
        createRecipeData.calories = '350'
        return createRecipeData
    }
}

class LoginFormData {
    String email
    String password

    public static LoginFormData getDefaultLoginFormData() {
        LoginFormData loginFormData = new LoginFormData()
        loginFormData.email = "qa.menuplanner+user1@gmail.com"
        loginFormData.password = "1234"
        return loginFormData
    }
}

class UserFormData {
    String email
    String password
    String confirmPassword
    String name
    String city
    String mouthsToFeed
    String introduction
    boolean isEnabled

    public static UserFormData getDefaultUserFormData() {
        UserFormData userFormData = new UserFormData()
        userFormData.email = "qa.menuplanner+${System.currentTimeMillis()}@gmail.com"
        userFormData.password = "1234"
        userFormData.confirmPassword = "1234"
        userFormData.name = "testuser1"
        userFormData.city = "city"
        userFormData.mouthsToFeed = "6"
        userFormData.introduction = "Some description"
        userFormData.isEnabled = true
        return userFormData
    }
}