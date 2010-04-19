package com.mp

import com.mp.domain.*
import static com.mp.MenuConstants.*

class BootstrapService {

    boolean transactional = true

    public void populateCategory(Integer count) {
        (1..count).each {Integer index ->
            new Category(name: "Category-${index}").s()
        }
    }

    public void populateQuantities(Integer count) {
        (1..count).each {Integer index ->
            new Quantity(value: new Random().nextInt(10) + 1, unit: Unit.get(new Random().nextInt(Unit.count() - 4) + 3)).s()
        }
    }

    public void populateMeasurableProduct(Integer count) {
        (1..count).each {Integer index ->
            MeasurableProduct measurableProduct = new MeasurableProduct()
            measurableProduct.name = "Product-${index}"
            measurableProduct.preferredUnit = Unit.get(new Random().nextInt(Unit.count()) + 1)
            //measurableProduct.possibleUnits= Unit.get(new Random().nextInt(Unit.count())+1)
            measurableProduct.s()
//            def image = new File(ApplicationHolder.application.parentContext.servletContext.getRealPath("/bootstrapData/img13.jpg")).readBytes()
//            ProductImage productImage = new ProductImage(productId: measurableProduct.id, image: image).s()

        }
    }


    public void populateRecipes(Integer count) {
        (1..count).each {Integer index ->
            Recipe recipe = new Recipe()
            recipe.name = "Recipe${index}"
            recipe.difficulty = [RecipeDifficulty.EASY, RecipeDifficulty.MEDIUM, RecipeDifficulty.HARD].get(new Random().nextInt(3))
            recipe.shareWithCommunity = false
            recipe.servings = new Random().nextInt(5) + 1
            Quantity recipePreparationTime = new Quantity(value: (new Random().nextInt(15) + 1) * 10, unit: Unit.findByName(TIME_UNIT_MINUTES))
            recipePreparationTime.s()
            recipe.preparationTime = recipePreparationTime
            Quantity recipeCookTime = new Quantity(value: (new Random().nextInt(15) + 1) * 10, unit: Unit.findByName(TIME_UNIT_MINUTES))
            recipeCookTime.s()
            recipe.cookingTime = recipeCookTime
            recipe.s()
            populateRecipeCategories(recipe)
            recipe.ingredients = populateRecipeIngredient(recipe)
            recipe.directions = populateRecipeDirections(recipe)
            populateRecipeNutrient(recipe)
            recipe.s()
        }
    }

    public List<RecipeNutrient> populateRecipeNutrient(Recipe recipe) {
        List<RecipeNutrient> nutrients = []
        (1..new Random().nextInt(5)).each {Integer index ->
            RecipeNutrient nutrient = new RecipeNutrient()
            nutrient.recipe = recipe
            nutrient.nutrient = Nutrient.get(new Random().nextInt(Nutrient.count()) + 1)

            Quantity nutrientQuantity = new Quantity()
            nutrientQuantity.value = new Random().nextInt(10) + 1
            nutrientQuantity.unit = nutrient.nutrient.preferredUnit
            nutrientQuantity.s()
            nutrient.quantity = nutrientQuantity
            nutrients.add(nutrient)
        }
        return (nutrients.unique {it.nutrient})
    }

    public void populateRecipeCategories(Recipe recipe) {
        (1..new Random().nextInt(5)).each {Integer index ->
            Category category = Category.get(new Random().nextInt(Category.count()) + 1)
            RecipeCategory.link(recipe, category)
        }
    }

    public List<RecipeIngredient> populateRecipeIngredient(Recipe recipe) {
        List<RecipeIngredient> ingredients = []
        (1..3).each {Integer index ->
            RecipeIngredient ingredient = new RecipeIngredient()
            ingredient.recipe = recipe
            ingredient.ingredient = MeasurableProduct.get(new Random().nextInt(MeasurableProduct.count()) + 1)
            ingredient.quantity = Quantity.get(new Random().nextInt(Quantity.count()) + 1)
            ingredients.add(ingredient)
        }
        return (ingredients.unique {it.ingredient})
    }

    public List<RecipeDirection> populateRecipeDirections(Recipe recipe) {
        List<RecipeDirection> directions = []
        (1..5).each {Integer index ->
            directions.add(new RecipeDirection(step: "for ${recipe.name} step-${index}", recipe: recipe))
        }
        return directions
    }

    public void populateWeeks(Integer count) {
        (1..count).each {Integer index ->
            Week week = new Week(name: "Week-${index}").s()
            week.days = populateDays(week)
            week.s()
        }
    }

    public List<Day> populateDays(Week week) {
        List<Day> days = []
        (['Sunday', 'Monday', 'Tuesday', 'Wednesday', 'Thursday', 'Friday', 'Saturday']).each {String name ->
            Day day = new Day(name: name, week: week)
            day.meals = populateMeals(day)
            days.add(day)
        }
        return days
    }

    public List<Meal> populateMeals(Day day) {
        List<Meal> meals = []
        (MealType.values()).eachWithIndex {MealType type, Integer index ->
            Meal meal = new Meal(name: "Meal-${index + 1}", day: day, type: type)
            meal.items = populateMealItems(meal)
            meals.add(meal)
        }
        return meals?.toList()
    }

    public List<Item> populateMealItems(Meal meal) {
        List<Item> items = []
        (new Random().nextInt(2) + 1).times {
            Item item = Item.get(new Random().nextInt(Item.count() - 1) + 1)
            items.add(item)
        }
        return items
    }
}