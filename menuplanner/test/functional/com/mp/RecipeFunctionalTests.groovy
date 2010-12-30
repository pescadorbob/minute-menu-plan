package com.mp

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
        assertEquals('Add to favorite link did not exist on show recipe page', 'Remove from favorites', byId('showFavorite').asText())
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
        assertEquals('Remove from favorite link does not exist on show recipe page', 'Add to favorites', byId('showFavorite').asText())
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
        CreateRecipeData createRecipeData = CreateRecipeData.getDefaultCreateRecipeData()
        gotoEditRecipePage(createRecipeData)
        assertStatus 200
        assertTitleContains 'Minute Menu Plan : Edit Recipe'
    }

    void testCancelEditRecipe() {
        CreateRecipeData createRecipeData = CreateRecipeData.getDefaultCreateRecipeData()
        gotoEditRecipePage(createRecipeData)
        assertStatus 200
        byName('_action_show').click()
        assertStatus 200
        assertTitleContains "Minute Menu Plan : ${createRecipeData.name}"
    }

    void testEditRecipe() {
        CreateRecipeData createRecipeData = CreateRecipeData.getDefaultCreateRecipeData()
        gotoEditRecipePage(createRecipeData)
        form('formEditRecipe') {
            name = "Changed Recipe Name"
            click("_action_update")
        }
        assertElementTextContainsStrict('recipeNameTst', "Changed Recipe Name")
        assertStatus 200
        assertTitleContains "Minute Menu Plan : Changed Recipe Name"
    }

    void testShowRecipe_RecipeListPage() {
        LoginFormData loginFormData = LoginFormData.getDefaultLoginFormData()
        loginToHomepage(loginFormData)
        def recipeLink = byId('draggableSearchItem_1').getFirstChild()
        recipeLink.click()
        assertElementTextContains('contectElement', 'Comments')
    }


    void test_Links_To_Serve_With_On_Show_Recipe() {
        LoginFormData loginFormData = LoginFormData.getDefaultLoginFormData()
        loginToHomepage(loginFormData)
        CreateRecipeData createRecipeData = CreateRecipeData.getDefaultCreateRecipeData()
        String servings_1 = Recipe.list().first()
        createRecipeData.serveWith_1 = servings_1
        createRecipe(createRecipeData)
        assertTitleContains "Minute Menu Plan : ${createRecipeData.name}"
        def servingsLink = byClass('recipeServeWithFT')
        servingsLink.click()
        assertTitleContains "Minute Menu Plan : ${servings_1}"
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
        }
        Integer finalCount = Recipe.count()
        assertEquals "Recipe creation failed with name input: " + currentString, finalCount, initialCount + names.size()
    }

    void test_Add_Recipe_Add_Custom_Serving_Visible_To_Other_User() {
        LoginFormData loginFormData = LoginFormData.getDefaultLoginFormData()
        loginToHomepage(loginFormData)
        CreateRecipeData createRecipeData = CreateRecipeData.getDefaultCreateRecipeData()
        createRecipeData.name = "MySharableRecipe"
        String servings_1 = "Custom_Serve_With-${System.currentTimeMillis()}"
        createRecipeData.serveWith_1 = servings_1
        createRecipe(createRecipeData)
        assertTitleContains "Minute Menu Plan : ${createRecipeData.name}"
        Recipe recipe = Recipe.list().last()
        logout()

        loginBySuperAdmin()
        get("/recipe/show/${recipe?.id}")
        assertElementTextContains('recipeNameTst', createRecipeData.name)
        String riInList = byId('showServeWithTst').asText()
        if (!riInList.contains(servings_1)) {
            fail("Expected Ingredient not found in Ingredient list...")
        }
    }

    void test_Add_Recipe_Add_Custom_Ingredient_Visible_To_Other_User() {
        LoginFormData loginFormData = LoginFormData.getDefaultLoginFormData()
        loginToHomepage(loginFormData)
        CreateRecipeData createRecipeData = CreateRecipeData.getDefaultCreateRecipeData()
        createRecipeData.name = "MySharableRecipeWithCustomIngredient"
        String productName_1 = "Custom_Ingredient-${System.currentTimeMillis()}"
        createRecipeData.productName_1 = productName_1
        createRecipe(createRecipeData)
        assertTitleContains "Minute Menu Plan : ${createRecipeData.name}"
        Recipe recipe = Recipe.list().last()
        logout()

        loginBySuperAdmin()
        get("/recipe/show/${recipe?.id}")
        assertElementTextContains('recipeNameTst', createRecipeData.name)
        List<String> riInList = []
        byId('showAllIngredientsHereTst').getChildElements().each {
            riInList.add(it.asText())
        }
        if (!riInList.contains(productName_1)) {
            fail("Expected Ingredient not found in Ingredient list...")
        }
    }

    void test_Add_Recipe_With_AlcoholicName_Recipe_Invisible_To_User() {
        LoginFormData loginFormData = LoginFormData.getDefaultLoginFormData()
        loginToHomepage(loginFormData)
        Integer initialCount = Recipe.count()
        CreateRecipeData createRecipeData = CreateRecipeData.getDefaultCreateRecipeData()
        createRecipeData.name = "Alcohol"
        createRecipe(createRecipeData)
        assertTitleContains "Minute Menu Plan : ${createRecipeData.name}"
        Integer finalCount = Recipe.count()
        assertEquals('Unable to create Recipe', finalCount, initialCount + 1)
        byClass('recipeListFT').click()
        List<String> riInList = []
        byId('rightContainer').getChildElements().each {
            riInList.add(it.asText())
        }
        if (riInList.contains(createRecipeData.name)) {
            fail("Unexpected Recipe found in Recipe list...")
        }
    }

    void test_Add_Recipe_With_AlcoholicIngredient_Recipe_Invisible_To_User() {
        LoginFormData loginFormData = LoginFormData.getDefaultLoginFormData()
        loginToHomepage(loginFormData)
        Integer initialCount = Recipe.count()
        CreateRecipeData createRecipeData = CreateRecipeData.getDefaultCreateRecipeData()
        createRecipeData.name = "New_Test_Recipe_${System.currentTimeMillis()}"
        createRecipeData.productName_1 = "Vodka"
        createRecipe(createRecipeData)
        assertTitleContains "Minute Menu Plan : ${createRecipeData.name}"
        Integer finalCount = Recipe.count()
        assertEquals('Unable to create Recipe', finalCount, initialCount + 1)
        byClass('recipeListFT').click()
        List<String> riInList = []
        byId('rightContainer').getChildElements().each {
            riInList.add(it.asText())
        }
        if (riInList.contains(createRecipeData.name)) {
            fail("Unexpected Recipe found in Recipe list...")
        }
    }

    void test_Add_Recipe_With_Alcoholic_Content_Checked_Recipe_Invisible_To_User() {
        LoginFormData loginFormData = LoginFormData.getDefaultLoginFormData()
        loginToHomepage(loginFormData)
        Integer initialCount = Recipe.count()
        CreateRecipeData createRecipeData = CreateRecipeData.getDefaultCreateRecipeData()
        createRecipeData.name = "New_Test_Check_Recipe_${System.currentTimeMillis()}"
        createRecipe(createRecipeData, true)
        assertTitleContains "Minute Menu Plan : ${createRecipeData.name}"
        Integer finalCount = Recipe.count()
        assertEquals('Unable to create Recipe', finalCount, initialCount + 1)
        byClass('recipeListFT').click()
        List<String> riInList = []
        byId('rightContainer').getChildElements().each {
            riInList.add(it.asText())
        }
        if (riInList.contains(createRecipeData.productName_1)) {
            fail("Unexpected Recipe found in Recipe list...")
        }
    }

    // This test won't run independently...it depends on the data produced by above tests
    //TODO: find a better way to validate this test
//    void test_Add_AlcoholicRecipe_Invisible_To_User_Change_User_Preference_Recipe_Visible() {
//        javaScriptEnabled = false
//        LoginFormData loginFormData = LoginFormData.getDefaultLoginFormData()
//        loginToHomepage(loginFormData)
//        Integer initialCount = Recipe.count()
//        CreateRecipeData createRecipeData = CreateRecipeData.getDefaultCreateRecipeData()
//        createRecipeData.name = "Test_Check_Recipe_${System.currentTimeMillis()}"
//        createRecipe(createRecipeData, true)
//        assertTitleContains 'Minute Menu Plan : Show Recipe '
//        Integer finalCount = Recipe.count()
//        assertEquals('Unable to create Recipe', finalCount, initialCount + 1)
//        byClass('recipeListFT').click()
//        List<String> riInList = []
//        byId('rightContainer').getChildElements().each {
//            riInList.add(it.asText())
//        }
//        if (riInList.contains(createRecipeData.productName_1)) {
//            fail("Unexpected Recipe found in Recipe list...")
//        }
//        logout()
//
//        loginBySuperAdmin()
//        UserLogin userLogin = UserLogin.findByEmail(loginFormData.email)
//        get("/user/show/${userLogin?.party?.id}")
//        byClass('editUserButtonFT').click()
//        byId('showAlcoholicContent').click()
//        byClass('updateUserButtonFT').click()
//        redirectEnabled = false
//        followRedirect()
//        logout()
//
//        loginToHomepage(loginFormData)
//        get('/recipe/list?offset=30&max=15&query=')
//        if (!byId('draggableSearchItem_1').asText().contains(createRecipeData.name)) {
//            fail("Expected Recipe not found in Recipe list...")
//        }
//    }

    // #203  Recipe Ingredients-Need to be capitalized on Final recipe

    void test_RecipeIngredient_To_Be_Capitalize() {
        LoginFormData loginFormData = LoginFormData.getDefaultLoginFormData()
        loginToHomepage(loginFormData)
        CreateRecipeData createRecipeData = CreateRecipeData.getDefaultCreateRecipeData()
        Integer initialCount = Recipe.count()
        createRecipeData.productName_1 = "eggs"
        createRecipe(createRecipeData)
        Integer finalCount = Recipe.count()
        assertStatus 200
        assertTrue('Add Recipe with valid values is not saved', (finalCount - initialCount == 1))
        String ingredients = byId('showAllIngredientsHereTst').asText()
        if (!ingredients.contains("Eggs")) {
            fail('Unable to show ingredients in capitalized format')
        }
        assertElementTextContainsStrict 'showAllIngredientsHereTst', 'Eggs'
    }

    void test_Recipe_Category_Preview() {
        LoginFormData loginFormData = LoginFormData.getDefaultLoginFormData()
        loginToHomepage(loginFormData)
        def newRecipeLink = byName('addNewRecipeLink')
        newRecipeLink.click()
        byId('category_0').setDefaultValue('18')
        def previewButton = byClass('previewButtonFT')
        previewButton.click()
        String optionSelected = byId('category_0').getOptionByValue('18').asText()
        String categorySelected = byId('displayCategory').asText()
        if (!categorySelected.contains(optionSelected)) {
            fail('Category not found on preview page')
        }
    }

    void test_Create_Recipe_Search_Recipe() {
        LoginFormData loginFormData = LoginFormData.getDefaultLoginFormData()
        loginToHomepage(loginFormData)
        CreateRecipeData createRecipeData = CreateRecipeData.getDefaultCreateRecipeData()
        createRecipeData.name = "MyTestSearchRecipe${System.currentTimeMillis()}"
        createRecipe(createRecipeData)
        assertStatus 200
        get('/recipe/list')
        byName('query').setValue(createRecipeData.name)
        byName('searchForm').submit()
        waitForElementToAppear('draggableSearchItem_1')
        String recipeDivText = byId('draggableSearchItem_1').asText()
        if (!recipeDivText.contains(createRecipeData.name)) {
            fail('Recipe not found in search')
        }
    }
}
