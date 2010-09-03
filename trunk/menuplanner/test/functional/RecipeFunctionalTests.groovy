import com.mp.domain.*

class RecipeFunctionalTests extends MenuPlannerFunctionalTests {

    void testAddRecipe_VALID() {
        LoginFormData loginFormData = LoginFormData.getDefaultLoginFormData()
        loginToHomepage(loginFormData)
        CreateRecipeData createRecipeData = CreateRecipeData.getDefaultCreateRecipeData()
        Integer initialCount = Recipe.count()
        createRecipe(createRecipeData)
        Integer finalCount = Recipe.count()
        assertStatus 200
        assertTrue('Add Recipe with valid values is not saved', (finalCount - initialCount == 1))
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
        LoginFormData loginFormData = LoginFormData.getDefaultLoginFormData()
        loginToHomepage(loginFormData)
        Integer initialCount = Recipe.count()
        CreateRecipeData createRecipeData = CreateRecipeData.getDefaultCreateRecipeData()
        createRecipeData.prepTime = '0'
        createRecipe(createRecipeData)
        Integer finalCount = Recipe.count()
        assertStatus 200
        assertTrue('Add Recipe with valid preparation time is not saved', (finalCount - initialCount == 1))
        assertTrue(
                (byId('recipeNameTst').asText() == createRecipeData.name) &&
                        (byId('prepAndCookTimesTst').asText().contains('Prep - ' + createRecipeData.prepTime))
        )
    }

    void testAddRecipe_ServeWith() {
        LoginFormData loginFormData = LoginFormData.getDefaultLoginFormData()
        loginToHomepage(loginFormData)
        Integer initialCount = Recipe.count()
        CreateRecipeData createRecipeData = CreateRecipeData.getDefaultCreateRecipeData()
        createRecipeData.serveWith_1 = "some Item"
        createRecipeData.serveWith_2 = "another Item"
        createRecipe(createRecipeData)
        Integer finalCount = Recipe.count()
        assertStatus 200
        assertTrue('Add Recipe with valid serve items failed to save', (finalCount - initialCount == 1))
        assertTrue(
                (byId('recipeNameTst').asText() == createRecipeData.name) &&
                        (byId('showServeWithTst').asText().contains(createRecipeData.serveWith_1)) &&
                        (byId('showServeWithTst').asText().contains(createRecipeData.serveWith_2))
        )
    }

    void testAddRecipe_nutrients() {
        LoginFormData loginFormData = LoginFormData.getDefaultLoginFormData()
        loginToHomepage(loginFormData)
        Integer initialCount = Recipe.count()
        CreateRecipeData createRecipeData = CreateRecipeData.getDefaultCreateRecipeData()
        createRecipeData.calories = '100'
        createRecipe(createRecipeData)
        Integer finalCount = Recipe.count()
        assertStatus 200
        assertTrue('Add Recipe with valid nutrients is not saved', (finalCount - initialCount == 1))
        assertTrue(
                (byId('recipeNameTst').asText() == createRecipeData.name) &&
                        (byId('showNutrientsTst').asText().contains(createRecipeData.calories))
        )
    }

    void testAddRecipe_VALID_CookTime_0() {
        LoginFormData loginFormData = LoginFormData.getDefaultLoginFormData()
        loginToHomepage(loginFormData)
        Integer initialCount = Recipe.count()
        CreateRecipeData createRecipeData = CreateRecipeData.getDefaultCreateRecipeData()
        createRecipeData.cookTime = '0'
        createRecipe(createRecipeData)
        Integer finalCount = Recipe.count()
        assertStatus 200
        assertTrue('Add Recipe with valid cook time is not saved', (finalCount - initialCount == 1))
        assertTrue(
                (byId('recipeNameTst').asText() == createRecipeData.name) &&
                        (byId('prepAndCookTimesTst').asText().contains('Cook - ' + createRecipeData.cookTime))
        )
    }

    void testAddRecipe_EMPTY_FORM() {
        LoginFormData loginFormData = LoginFormData.getDefaultLoginFormData()
        loginToHomepage(loginFormData)
        Integer initialCount = Recipe.count()
        CreateRecipeData createRecipeData = CreateRecipeData.getDefaultCreateRecipeData()
        createRecipeData.name = ""
        createRecipeData.productName_1 = ""
        createRecipeData.step_1 = ""
        createRecipeData.serveWith_1 = ""
        createRecipeData.serveWith_2 = ""
        createRecipeData.calories = ""
        createRecipe(createRecipeData)
        Integer finalCount = Recipe.count()
        assertStatus 200
        assertTrue('Add Recipe empty values still adds a new recipe', (finalCount - initialCount == 0))
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
        assertEquals('Add to favorite link did not exist on show recipe page', 'Remove from favorite', byId('showFavorite').asText())
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
        assertEquals('Remove from favorite link does not exist on show recipe page', 'Add to favorite', byId('showFavorite').asText())
    }

    void testReportAbuseToRecipe() {
        gotoShowRecipePage()
        byName('recipeAbuse').click()
        redirectEnabled = false
        followRedirect()
        assertStatus 200
        assertEquals('Report abuse to recipe link does not exist on show recipe page', 'Abuse reported', byId('showRecipeAbuseReported').asText())
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
        assertStatus 200
        assertTitleContains 'Minute Menu Plan : List Recipe'
    }

    void testEditRecipeLink() {
        gotoEditRecipePage()
        assertStatus 200
        assertTitleContains 'Minute Menu Plan : Edit Recipe'
    }

    void testCancelEditRecipe() {
        gotoEditRecipePage()
        assertStatus 200
        byName('_action_show').click()
        assertStatus 200
        assertTitleContains 'Minute Menu Plan : Show Recipe'
    }

    void testEditRecipe() {
        gotoEditRecipePage()
        form('formEditRecipe') {
            name = "Changed Recipe Name"
            click("_action_update")
        }
        assertElementTextContainsStrict('recipeNameTst', "Changed Recipe Name")
        assertStatus 200
        assertTitleContains 'Minute Menu Plan : Show Recipe'
    }

    void testShowRecipe_RecipeListPage() {
        LoginFormData loginFormData = LoginFormData.getDefaultLoginFormData()
        loginToHomepage(loginFormData)
        def recipeLink = byId('draggableSearchItem_1').getFirstChild()
        recipeLink.click()
        assertTitleContains 'Minute Menu Plan : Show Recipe '
        assertElementTextContains('contectElement', 'Comments')
    }


    void test_Links_To_Serve_With_On_Show_Recipe() {
        LoginFormData loginFormData = LoginFormData.getDefaultLoginFormData()
        loginToHomepage(loginFormData)
        CreateRecipeData createRecipeData = CreateRecipeData.getDefaultCreateRecipeData()
        String servings_1 = Recipe.list().first()
        createRecipeData.serveWith_1 = servings_1
        createRecipe(createRecipeData)
        assertTitleContains 'Minute Menu Plan : Show Recipe '
        def servingsLink = byClass('recipeServeWithFT')
        servingsLink.click()
        assertTitleContains 'Minute Menu Plan : Show Recipe '
        assertElementTextContains('leftpart', servings_1)
    }

    void testAddRecipeIngredientTableCount() {
        LoginFormData loginFormData = LoginFormData.getDefaultLoginFormData()
        loginToHomepage(loginFormData)
        byName('addNewRecipeLink').click()
        def unitInitialCount = byClass('iUnit')
        println ">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>><<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<Initial" + unitInitialCount.size()
        unitInitialCount[unitInitialCount.size() - 2].click()
        def unitFinalCount = byClass('iUnit')
        println ">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>><<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<Final" + unitFinalCount.size()
        assertEquals unitInitialCount.size() + 1, unitFinalCount.size()
    }

    void test_CreateRecipe_With_Different_Names() {
        // test that recipe name accepts any character other than single quote
        List<String> names = ['AAAAA "highlited Name"', '*name', '*()/&*', 'Ä_new_recipe', 'ÄËÎÆzqÅ']
        String currentString
        LoginFormData loginFormData = LoginFormData.getDefaultLoginFormData()
        loginToHomepage(loginFormData)
        Integer initialCount = Recipe.count()
        Integer intermediateCount = initialCount

        CreateRecipeData createRecipeData = CreateRecipeData.getDefaultCreateRecipeData()
        names.each {String name ->
            currentString = name
            createRecipeData.name = name
            createRecipe(createRecipeData)
            intermediateCount++
            assertEquals "Recipe creation failed with name input: " + currentString, intermediateCount, Recipe.count()
            assertTitleContains 'Minute Menu Plan : Show Recipe '
        }
        Integer finalCount = Recipe.count()
        assertEquals "Recipe creation failed with name input: " + currentString, finalCount, initialCount + names.size()
    }
}