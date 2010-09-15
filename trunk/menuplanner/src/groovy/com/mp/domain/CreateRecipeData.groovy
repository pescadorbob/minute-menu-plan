package com.mp.domain

class CreateRecipeData {
    String name
    String step_1
    String productName_1
    String cookTime
    String servings
    String prepTime
    String serveWith_1
    String serveWith_2
    String calories

    public static CreateRecipeData getDefaultCreateRecipeData() {
        CreateRecipeData createRecipeData = new CreateRecipeData()
        createRecipeData.name = "Recipe-${System.currentTimeMillis().toString()}"
        createRecipeData.step_1 = 'Step One'
        createRecipeData.productName_1 = 'Product One'
        createRecipeData.cookTime = ''
        createRecipeData.prepTime = ''
        createRecipeData.servings = 2
        createRecipeData.serveWith_1 = "Item_1-${System.currentTimeMillis().toString()}"
        createRecipeData.serveWith_2 = "Item_2-${System.currentTimeMillis().toString()}"
        createRecipeData.calories = "350"
        return createRecipeData
    }
}