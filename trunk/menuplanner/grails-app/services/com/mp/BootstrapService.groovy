package com.mp

import com.mp.domain.*
import org.codehaus.groovy.grails.commons.ApplicationHolder

class BootstrapService {

    boolean transactional = true

    public void populateCategory(Integer count) {
        (1..count).each {Integer index ->
            new Category(name: "Category-${index}").s()
        }
    }

    public void populateUnits(Integer count) {
        (1..count).each {Integer index ->
            new Unit(name: "Unit-${index}", symbol: "Symbol-${index}", definition: "This is definition for Units-${index}").s()
        }
    }

    public void populateQuantities(Integer count) {
        (1..count).each {Integer index ->
            new Quantity(amount: new Random().nextInt(10) + 1, unit: Unit.get(new Random().nextInt(Unit.count()) + 1)).s()
        }
    }

    public void populateMeasuredProduct(Integer count) {
        (1..count).each {Integer index ->
            MeasuredProduct measuredProduct = new MeasuredProduct()
            measuredProduct.name = "Product-${index}"
            measuredProduct.preferredUnit = Unit.get(new Random().nextInt(Unit.count()) + 1)
            //measuredProduct.possibleUnits= Unit.get(new Random().nextInt(Unit.count())+1)
            measuredProduct.s()
            def image = new File(ApplicationHolder.application.parentContext.servletContext.getRealPath("/bootstrapData/img13.jpg")).readBytes()
            ProductImage productImage = new ProductImage(productId: measuredProduct.id, image: image).s()

        }
    }

    public void createRecipes(Integer count) {
        (1..count).each {Integer index ->
            Recipe recipe = new Recipe()
            recipe.name = "Recipe-${index}"
            recipe.difficulty= [RecipeDifficulty.EASY, RecipeDifficulty.MEDIUM, RecipeDifficulty.HARD].get(new Random().nextInt(3))
            recipe.shareWithCommunity=false
            recipe.makesServing=new Random().nextInt(5)
            recipe.preparationTime=new Random().nextInt(5)*5
            recipe.cookTime=new Random().nextInt(5)*5
            recipe.s()
            def image = new File(ApplicationHolder.application.parentContext.servletContext.getRealPath("/bootstrapData/coffee.jpg")).readBytes()
            new RecipeImage(recipeId: recipe.id, image: image).s()
            populateRecipeCategories(recipe)
            populateRecipeIngredient(recipe)
            populateRecipeDirections(recipe)
        }
    }

    public void populateRecipeCategories(Recipe recipe) {
        (1..new Random().nextInt(5)).each {Integer index ->
            Category category = Category.get(new Random().nextInt(Category.count()) + 1)
            RecipeCategory.link(recipe, category)
        }
    }

    public void populateRecipeIngredient(Recipe recipe) {
        (1..3).each {Integer index ->
            RecipeIngredient ingredient = new RecipeIngredient()
            ingredient.recipe = recipe
            ingredient.sequence = index
            ingredient.ingredient = MeasuredProduct.get(new Random().nextInt(MeasuredProduct.count()) + 1)
            ingredient.quantity = Quantity.get(new Random().nextInt(Quantity.count()) + 1)
            ingredient.s()
        }
    }

    public void populateRecipeDirections(Recipe recipe) {
        (1..5).each {Integer index ->
            RecipeDirection recipeDirection = new RecipeDirection(step: "for ${recipe.name} step-${index}", sequence: index, recipe: recipe).s()
            def image = new File(ApplicationHolder.application.parentContext.servletContext.getRealPath("/bootstrapData/img11.jpg")).readBytes()
            RecipeDirectionImage recipeDirectionImage = new RecipeDirectionImage(recipeDirectionId: recipeDirection.id, image: image).s()
        }
    }
}