class RecipeFunctionalTests extends MenuPlannerFunctionalTests {

//TODO: Pick button to be clicked using Id instead of text.

    void testAddRecipePage() {
        gotoCreateRecipePage()
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
        gotoShowRecipePage()
        byName('changeFavorite').click()
        redirectEnabled = false
        followRedirect()
        assertStatus 200
        assertEquals('Remove from favorite', byId('showFavorite').asText())
    }

    void testRemoveFromFavorite() {
        gotoShowRecipePage()
        byName('changeFavorite').click()
        redirectEnabled = false
        followRedirect()
        byName('changeFavorite').click()
        redirectEnabled = false
        followRedirect()
        assertStatus 200
        assertEquals('Add to favorite', byId('showFavorite').asText())
    }

    void testReportAbuseToRecipe() {
        gotoShowRecipePage()
        byName('recipeAbuse').click()
        redirectEnabled = false
        followRedirect()
        assertStatus 200
        assertEquals('Abuse reported', byId('showRecipeAbuseReported').asText())
    }

    void testAddComment() {
        gotoShowRecipePage()
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
        gotoShowRecipePage()
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
        assertEquals('Abuse reported', byClass('showCommentAbuseReported').asText())
    }

    void testCancelCreateRecipe() {
        gotoCreateRecipePage()
        byName('_action_list').click()
        assertTitleContains 'Minute Menu Plan : List Recipe'
    }

    void testEditRecipeLink() {
        gotoEditRecipePage()
        assertTitleContains 'Minute Menu Plan : Edit Recipe'
    }

    void testCancelEditRecipe() {
        gotoEditRecipePage()
        byName('_action_show').click()
        assertTitleContains 'Minute Menu Plan : Show Recipe'
    }

    void testEditRecipe() {
        gotoEditRecipePage()
        CreateRecipeData createRecipeData = CreateRecipeData.getDefaultCreateRecipeData()
        createRecipeData.name = "Changed Recipe Name"
        form('formEditRecipe') {
            name = createRecipeData.name
            click("_action_update")
        }
        assertElementTextContainsStrict('recipeNameTst', createRecipeData.name)
    }
    void testDeleteRecipe() {
        gotoEditRecipePage()
        byName('_action_delete').click()
        redirectEnabled = false
        followRedirect()
        assertElementTextContainsStrict('deleteRecipeFlashMessage', getMessage('recipe.deleted.success'))
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
//        byClass('editRecipeLink').click()
        click('Edit')
    }
}