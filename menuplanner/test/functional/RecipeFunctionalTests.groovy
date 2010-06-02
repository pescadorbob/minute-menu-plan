class RecipeFunctionalTests extends MenuPlannerFunctionalTests {

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
        assertElementTextContains('displayRecipeErrors', getMessage('recipeCO.name.blank.error.name'))
    }

    void testAddToFavorite() {
        CreateRecipeData createRecipeData = CreateRecipeData.getDefaultCreateRecipeData()
        createRecipe(createRecipeData)
        byName('changeFavorite').click()
        redirectEnabled = false
        followRedirect()
        assertStatus 200
        assertEquals('Remove from favorite', byId('showFavorite').asText())
    }

    void testReportAbuseToRecipe() {
        CreateRecipeData createRecipeData = CreateRecipeData.getDefaultCreateRecipeData()
        createRecipe(createRecipeData)
        byName('recipeAbuse').click()
        redirectEnabled = false
        followRedirect()
        assertStatus 200
        assertEquals('Abuse reported', byId('showRecipeAbuseReported').asText())
    }

    void testAddComment() {
        CreateRecipeData createRecipeData = CreateRecipeData.getDefaultCreateRecipeData()
        createRecipe(createRecipeData)
        String commentText = 'Lorem Ipsum Lorem Ipsum Lorem Ipsum Lorem Ipsum'
        form('addCommentForm') {
            comment = commentText
        }
        def submitButton = byName('_action_addComment')
        submitButton.removeAttribute("disabled")
        submitButton.click()
        redirectEnabled = false
        followRedirect()
        assertElementTextContains('divComments', (commentText + ' - Posted by'))
    }

    void testReportAbuseToComment() {
        CreateRecipeData createRecipeData = CreateRecipeData.getDefaultCreateRecipeData()
        createRecipe(createRecipeData)
        String commentText = 'Lorem Ipsum Lorem Ipsum Lorem Ipsum Lorem Ipsum'
        form('addCommentForm') {
            comment = commentText
        }
        def submitButton = byName('_action_addComment')
        submitButton.removeAttribute("disabled")
        submitButton.click()
        redirectEnabled = false
        followRedirect()
        click('Report this')
        redirectEnabled = false
        followRedirect()
        assertEquals('Abuse reported', byId('showCommentAbuseReported').asText())
    }
}