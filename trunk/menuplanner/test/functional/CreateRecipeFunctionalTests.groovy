class CreateRecipeFunctionalTests extends MenuPlannerFunctionalTests {

//TODO: Pick button to be clicked using Id instead of text.
//TODO: Validate page comparing title or some other unique text.
//TODO: Use text from message.properties

    void testAddRecipePage() {
        LoginFormData loginFormData = LoginFormData.getDefaultLoginFormData()
        userLogin(loginFormData)
        get('/recipe/create')
        assertStatus 200
        assertContentContains 'Add Recipe'
    }

    void testAddRecipe_VALID() {
        CreateRecipeData createRecipeData = CreateRecipeData.getDefaultCreateRecipeData()
        createRecipe(createRecipeData)
        assertStatus 200
        assertContentContains 'Edit'
    }
    void testAddRecipe_EMPTY_FORM() {
        CreateRecipeData createRecipeData = CreateRecipeData.getDefaultCreateRecipeData()
        createRecipeData.name = ""
        createRecipeData.productName_1 = ""
        createRecipeData.step_1 = ""
        createRecipe(createRecipeData)
        assertStatus 200
        assertContentContains 'Provide Recipe Name.'
    }
}
