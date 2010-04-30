package com.mp.domain

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
            Quantity quantity = new Quantity()
            Integer intVal = ((new Random().nextInt(10) + 1) * 10)
            quantity.value = intVal.toBigDecimal().toString()
            quantity.unit = Unit.get(new Random().nextInt(Unit.count() - 4) + 3)
            quantity.savedUnit = Unit.findByName(UNIT_GRAM)
            quantity.s()
        }
    }

    public void populateMeasurableProduct(Integer count) {
        (1..count).each {Integer index ->
            MeasurableProduct measurableProduct = new MeasurableProduct()
            measurableProduct.name = "Product-${index}"
            measurableProduct.preferredUnit = Unit.get(new Random().nextInt(Unit.count()) + 1)
            measurableProduct.s()
        }
    }


    public void populateRecipes(Integer count) {
        (1..count).each {Integer index ->
            Recipe recipe = new Recipe()
            recipe.name = "Recipe${index}"
            recipe.difficulty = [RecipeDifficulty.EASY, RecipeDifficulty.MEDIUM, RecipeDifficulty.HARD].get(new Random().nextInt(3))
            recipe.shareWithCommunity = false
            recipe.servings = new Random().nextInt(5) + 1
            Quantity recipePreparationTime = new Quantity(value: ((new Random().nextInt(6) + 1) * 10).toBigDecimal().toString(), unit: Unit.findByName(TIME_UNIT_MINUTES))
            recipePreparationTime.s()
            recipe.preparationTime = recipePreparationTime
            Quantity recipeCookTime = new Quantity(value: ((new Random().nextInt(6) + 1) * 10).toBigDecimal().toString(), unit: Unit.findByName(TIME_UNIT_MINUTES))
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
            nutrientQuantity.value = (new Random().nextInt(500) + 1).toBigDecimal().toString()
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

    public List<String> populateRecipeDirections(Recipe recipe) {
        List<String> directions = []
        (1..5).each {Integer index ->
            directions.add("for " + recipe.name + "step-" + index)
        }
        return directions
    }

    public void populateMenuPlans(Integer count) {
        (1..count).each {Integer index ->
            MenuPlan menuPlan = new MenuPlan(name: "MenuPlan-${index}").s()
            menuPlan.weeks = populateWeeks(menuPlan)
            menuPlan.s()
        }
    }

    public List<Week> populateWeeks(MenuPlan menuPlan) {
        List<Week> weeks = []
        4.times {
            Week week = new Week(menuPlan: menuPlan)
            week.days = populateDays(week)
            weeks.add(week)
        }
        return weeks
    }

    public List<Day> populateDays(Week week) {
        List<Day> days = []
        7.times {
            Day day = new Day(week: week)
            day.meals = populateMeals(day)
            days.add(day)
        }
        return days
    }

    public List<Meal> populateMeals(Day day) {
        List<Meal> meals = []
        (MealType.values()).eachWithIndex {MealType type, Integer index ->
            Meal meal = new Meal(day: day, type: type)
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