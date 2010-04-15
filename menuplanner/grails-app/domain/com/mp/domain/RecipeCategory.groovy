package com.mp.domain

class RecipeCategory {

    static searchable = {
        category component: [prefix:  'category_', maxDepth: 10]
    }
    Recipe recipe
    Category category

    static RecipeCategory link(recipe, category) {
        RecipeCategory recipeCategory = RecipeCategory.findByRecipeAndCategory(recipe, category)
        if (!recipeCategory) {
            recipeCategory = new RecipeCategory(recipe: recipe, category: category)
            recipeCategory.s()
        }
        return recipeCategory
    }

    static void unlink(recipe, category) {
        RecipeCategory recipeCategory = RecipeCategory.findByRecipeAndCategory(recipe, category)
        if (recipeCategory) {
            recipeCategory.delete()
        }
    }

    static constraints = {
        recipe(unique: 'category')
        category(unique: 'recipe')        
    }
}
