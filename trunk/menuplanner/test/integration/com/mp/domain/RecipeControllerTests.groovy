package com.mp.domain

import grails.test.GrailsUnitTestCase

class RecipeControllerTests extends GrailsUnitTestCase {
    def renderMap
    def redirectMap
    def controller
    def searchableService
    def recipeService

    protected void setUp() {
        super.setUp()
        controller = new RecipeController()

        controller.metaClass.render = {Map map ->
            renderMap = map
        }
        controller.metaClass.redirect = {Map map ->
            redirectMap = map
        }
        controller.searchableService = searchableService
        controller.recipeService = recipeService
    }

    protected void tearDown() {
        super.tearDown()
    }

    void testCreateRecipe_Recipe_Appears_In_Search() {
        controller.session.loggedUserId = Party.list()?.first()?.id?.toString()
        Integer initialCount = Recipe.count()
        Recipe recipe = new Recipe()
        recipe.name = "MyTestRecipe-${System.currentTimeMillis()}"
        recipe.description = "My Recipe Description"
        recipe.difficulty = RecipeDifficulty.EASY
        recipe.shareWithCommunity = true
        RecipeIngredient recipeIngredient = new RecipeIngredient(ingredient: Item.get(1))
        recipeIngredient.recipe = recipe
        recipe.addToIngredients(recipeIngredient)
        recipe.directions = ['directions to cook']
        if (recipe.hasErrors() || !recipe.save()) {
            recipe.errors.allErrors.each {
                println it
            }
        }
        Party party=LoginCredential?.currentUser?.party
        Integer finalCount = Recipe.count()
        searchableService.index(class: Recipe, recipe?.id)
        party.contributions=[recipe] as Set
        party.save()
        assertEquals("Created new recipe", finalCount, initialCount + 1)
        controller.params.query = "MyTestRecipe"
        controller.search()
        assertEquals 1, renderMap.model.recipeList.size()
    }
}
