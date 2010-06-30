import com.mp.domain.*

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
        assertTrue(
                (byId('recipeNameTst').asText() == createRecipeData.name) &&
                        (byId('showAllIngredientsHereTst').asText().contains(createRecipeData.productName_1)) &&
                        (byId('showAllStepsHereTst').asText().contains(createRecipeData.step_1)) &&
                        (byId('showServeWithTst').asText().contains(createRecipeData.serveWith_1)) &&
                        (byId('showServeWithTst').asText().contains(createRecipeData.serveWith_2)) &&
                        (byId('showNutrientsTst').asText().contains(createRecipeData.calories))
        )
    }

    void testAddRecipe_VALID_PrepTime_0() {
        CreateRecipeData createRecipeData = CreateRecipeData.getDefaultCreateRecipeData()
        createRecipeData.prepTime = '0'
        createRecipe(createRecipeData)
        assertTrue(
                (byId('recipeNameTst').asText() == createRecipeData.name) &&
                        (byId('prepAndCookTimesTst').asText().contains('Prep - ' + createRecipeData.prepTime))
        )
    }

    void testAddRecipe_ServeWith() {
        CreateRecipeData createRecipeData = CreateRecipeData.getDefaultCreateRecipeData()
        createRecipeData.serveWith_1 = "some Item"
        createRecipeData.serveWith_2 = "another Item"
        createRecipe(createRecipeData)
        assertStatus 200
        assertTrue(
                (byId('recipeNameTst').asText() == createRecipeData.name) &&
                        (byId('showServeWithTst').asText().contains(createRecipeData.serveWith_1)) &&
                        (byId('showServeWithTst').asText().contains(createRecipeData.serveWith_2))
        )
    }

    void testAddRecipe_nutrients() {
        CreateRecipeData createRecipeData = CreateRecipeData.getDefaultCreateRecipeData()
        createRecipeData.calories = '100'
        createRecipe(createRecipeData)
        assertStatus 200
        assertTrue(
                (byId('recipeNameTst').asText() == createRecipeData.name) &&
                        (byId('showNutrientsTst').asText().contains(createRecipeData.calories))
        )
    }

    void testAddRecipe_VALID_CookTime_0() {
        CreateRecipeData createRecipeData = CreateRecipeData.getDefaultCreateRecipeData()
        createRecipeData.cookTime = '0'
        createRecipe(createRecipeData)
        assertTrue(
                (byId('recipeNameTst').asText() == createRecipeData.name) &&
                        (byId('prepAndCookTimesTst').asText().contains('Cook - ' + createRecipeData.cookTime))
        )
    }

    void testAddRecipe_EMPTY_FORM() {
        CreateRecipeData createRecipeData = CreateRecipeData.getDefaultCreateRecipeData()
        createRecipeData.name = ""
        createRecipeData.productName_1 = ""
        createRecipeData.step_1 = ""
        createRecipeData.serveWith_1 = ""
        createRecipeData.serveWith_2 = ""
        createRecipeData.calories = ""
        createRecipe(createRecipeData)
        assertStatus 200
        assertTrue(
                (byId('displayRecipeErrors').asText().contains(getMessage('recipeCO.name.blank.error.name'))) &&
                        (byId('displayRecipeErrors').asText().contains(getMessage('recipeCO.ingredient.not.Provided.message'))) &&
                        (byId('displayRecipeErrors').asText().contains(getMessage('recipeCO.directions.not.valid.message')))
        )
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
        assertElementTextContainsStrict('deleteRecipeFlashMessage', getMessage('recipe.deleted.success'))
    }
}