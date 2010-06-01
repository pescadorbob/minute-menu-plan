import com.gargoylesoftware.htmlunit.html.*

class CreateRecipeFunctionalTests extends MenuPlannerFunctionalTests {

//TODO: Pick button to be clicked using Id instead of text.

    void testAddRecipePage() {
        LoginFormData loginFormData = LoginFormData.getDefaultLoginFormData()
        userLogin(loginFormData)
        get('/recipe/create')
        assertStatus 200
        assertTitle 'Minute Menu Plan : Add Recipe'
    }

    void testAddRecipe_VALID() {
        CreateRecipeData createRecipeData = CreateRecipeData.getDefaultCreateRecipeData()
        createRecipe(createRecipeData)
        assertStatus 200
        assertTitleContains 'Minute Menu Plan : Show Recipe'
    }
    void testAddRecipe_EMPTY_FORM() {
        CreateRecipeData createRecipeData = CreateRecipeData.getDefaultCreateRecipeData()
        createRecipeData.name = ""
        createRecipeData.productName_1 = ""
        createRecipeData.step_1 = ""
        createRecipe(createRecipeData)
        assertStatus 200
        assertContentContains getMessage('recipeCO.name.blank.error.name')
    }
}
