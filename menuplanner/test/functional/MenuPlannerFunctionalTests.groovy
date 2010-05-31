import org.codehaus.groovy.grails.commons.ConfigurationHolder

class MenuPlannerFunctionalTests extends functionaltestplugin.FunctionalTestCase {

//    def messageSource = ConfigurationHolder.config.applicationContext.getBean("messageSource")
    Locale locale = new Locale('en', 'US')
    Object[] TARGET_ARGS_EMPTY = [].toArray()
    Object[] TARGET_ARGS_WITH_VALUES = [].toArray()


    void userLogin(LoginFormData loginFormData) {
        get("/login/index")
        form('loginForm') {
            email = loginFormData.email
            password = loginFormData.password
            click "Login"
        }
    }

    /** * Helper method to return a message from the message bundle. ***/
    String getMessage(String key, def targetArgs = TARGET_ARGS_EMPTY) {
        def keyValue = messageSource.resolveCode(key, locale)
        return keyValue?.format(targetArgs)
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
