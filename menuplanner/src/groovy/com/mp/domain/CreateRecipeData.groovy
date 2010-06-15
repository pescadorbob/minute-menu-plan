package com.mp.domain

class CreateRecipeData {
    String name
    String step_1
    String productName_1
    String cookTime
    String prepTime
    String serveWith_1
    String serveWith_2
    String calories

    public static CreateRecipeData getDefaultCreateRecipeData() {
        CreateRecipeData createRecipeData = new CreateRecipeData()
        createRecipeData.name = 'New Recipe'
        createRecipeData.step_1 = 'Step One'
        createRecipeData.productName_1 = 'Product One'
        createRecipeData.cookTime = ''
        createRecipeData.prepTime = ''
        createRecipeData.serveWith_1 = "Item-${System.currentTimeMillis().toString().substring(1, 5)}"
        createRecipeData.serveWith_2 = "Item-${System.currentTimeMillis().toString().substring(1, 4)}"
        createRecipeData.calories = "350"
        return createRecipeData
    }
}