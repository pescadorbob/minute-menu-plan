class CreateRecipeFunctionalTests extends MenuPlannerFunctionalTests {

    void testAddRecipePage() {
        LoginFormData loginFormData = LoginFormData.getDefaultLoginFormData()
        userLogin(loginFormData)
        click('Add New Recipe')
        assertContentContains 'Add Recipe'
    }

    void testAddRecipe() {
        LoginFormData loginFormData = LoginFormData.getDefaultLoginFormData()
        userLogin(loginFormData)
        click('Add New Recipe')
        form('formCreateRecipe') {
            name = 'New Recipe'
            optionIngredientProductIds = "eggs"
            click "btnAddIngredient"
            optionDirections = "something"
            click "btnAddDirection"
            click("Create")
        }
        assertContentContains 'Edit'
    }
    void testInvalidAddRecipe() {
        LoginFormData loginFormData = LoginFormData.getDefaultLoginFormData()
        userLogin(loginFormData)
        click('Add New Recipe')
        form('formCreateRecipe') {
            click("Create")
        }
        assertContentContains 'Provide Recipe Name.'
    }
}
