package com.mp.domain

import com.mp.domain.party.Coach
import com.mp.domain.party.Director
import com.mp.domain.party.Party
import grails.util.GrailsUtil
import org.grails.comments.Comment
import static com.mp.MenuConstants.*
import com.mp.domain.*

class BootstrapService {

    boolean transactional = true
    def excelService
    def shoppingListService
    


    public void addAbusesOnCommentsAndRecipes() {
        (1..15).each {Integer count ->   //  Comment Abuses
            Comment comment = Comment.get(new Random().nextInt(Comment.count()) + 1)
            Party user = Party.get(new Random().nextInt(Party.count()) + 1)
            if ((!CommentAbuse.findByCommentAndReporter(comment, user)) && (comment) && (user)) {
                new CommentAbuse(comment: comment, reporter: user).s()
            }
        }
        (1..10).each {Integer count ->   //  Recipe Abuses
            Recipe recipe = Recipe.get(new Random().nextInt(Recipe?.count()) + 1)
            Party user = Party.get(new Random().nextInt(Party.count()) + 1)
            if ((!RecipeAbuse.findByRecipeAndReporter(recipe, user)) && (recipe) && (user)) {
                new RecipeAbuse(recipe: recipe, reporter: user).s()
            }
        }
    }

    public void addCommentsFavouriteAndContributed() {
        if (GrailsUtil.environment in ['qa', 'beta']) {
            Party user = Party.findByName('Marie Ricks')
            Recipe.list().each {Recipe recipe ->
                user.addToContributions(recipe)
            }
            user.s()
        } else {
            Recipe.list().each {Recipe recipe ->
                Party user = Party.get(new Random().nextInt(Party.count()) + 1)
                user.addToContributions(recipe)          // contributed Recipe

                (1..(new Random().nextInt(2) + 1)).each {       // comments on Recipe
                    user = Party.get(new Random().nextInt(Party.count()) + 1)
                    String commentText = "Recipe-${recipe.id} Comment-${it} Lorem ipsum  dolor sit amet, consectetur adipiscing elit. Donec ut sem felis, sed rhoncus purus. Donec mauris arcu, auctor sit amet tristique eget, egestas ut dui. Aenean quis eros sit amet tortor ullamcorper cursus ut nec urna. Proin scelerisque imperdiet lacus vel convallis. Morbi vehicula nisl eu mi tristique fringilla rhoncus sapien vulputate."
                    recipe?.addComment(user, commentText)
                }
                (1..(new Random().nextInt(2))).each {      // add to Favorite
                    user = Party.get(new Random().nextInt(Party.count()) + 1)
                    user.addToFavourites(recipe)
                }
                user.s()
            }
        }
    }

    public void populateUser(String name,  roles) {
        UserCO userCO = new UserCO()
        userCO.name = name
        userCO.isEnabled = true
        userCO.email = 'qa.menuplanner+' + name + '@gmail.com'
        userCO.password = '1234'
        userCO.city = 'city'
        userCO.mouthsToFeed = new Random().nextInt(10) + 1
        userCO.introduction = 'about ' + name
        userCO.roles = roles
        if (name == 'coach' ) { // this is the users name
            Director director = Director.list()?.first()
            Long directorId = director?.party?.id
            if (directorId) {
              userCO.directorId = directorId
            }
        }
        if (name == 'user1') { // this is the users name
          Coach coach = Coach.list()?.first()
          userCO.coachId = coach?.id
        }
        userCO.createParty()
    }

    public void populateBetaUsers() {
        UserCO userCO1 = new UserCO(isEnabled: true, name: 'Brent Fisher', email: 'bc.fisher@yahoo.com', password: 'L3uv3n!', roles: [PartyRoleType.SuperAdmin.name()])
        UserCO userCO2 = new UserCO(isEnabled: true, name: 'Colleen Fisher', email: 'colleen_fisher@yahoo.com', password: 'L3uv3n!', roles: [PartyRoleType.SuperAdmin.name()])
        UserCO userCO3 = new UserCO(isEnabled: true, name: 'Marie Ricks', email: 'marie@houseoforder.com', password: 'M4ri3#', roles: [PartyRoleType.Subscriber.name()], mouthsToFeed: 1)
        UserCO userCO4 = new UserCO(isEnabled: true, name: 'Aman Aggarwal', email: 'aman@intelligrape.com', password: '1234', roles: [PartyRoleType.Admin.name(), PartyRoleType.Subscriber.name()], mouthsToFeed: 1)
        UserCO userCO5 = new UserCO(isEnabled: true, name: 'Chandan Luthra', email: 'chandan@intelligrape.com', password: '1234', roles: [PartyRoleType.Admin.name(), PartyRoleType.Subscriber.name()], mouthsToFeed: 1,)
        [userCO1, userCO2, userCO3, userCO4, userCO5].each {it.createParty()}
    }

    public void populateQuantities(Integer count) {
        (1..count).each {Integer index ->
            Quantity quantity = new Quantity()
            Integer intVal = ((new Random().nextInt(10) + 1) * 10)
            quantity.value = intVal.toFloat()
            quantity.unit = Unit.get(new Random().nextInt(Unit.count() - 4) + 3)
            quantity.savedUnit = Unit.findByName(UNIT_GRAM)
            quantity.s()
        }
    }

    public void populateMeasurableProduct() {
        List<String> products = ['Soda', 'Beverage', 'Wheat Bread', 'Butter', 'honey', 'Ranch Dressing', 'Fry Sauce', 'Rice', 'Syrup', 'Whipped Cream', 'eggs', 'Toast', 'yogurt']
        products.each {String product ->
            MeasurableProduct measurableProduct = new MeasurableProduct()
            measurableProduct.name = "${product}"
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
            Quantity recipePreparationTime = new Quantity(value: ((new Random().nextInt(6) + 1) * 10), unit: Unit.findByName(TIME_UNIT_MINUTES))
            recipePreparationTime.s()
            recipe.preparationTime = recipePreparationTime
            Quantity recipeCookTime = new Quantity(value: ((new Random().nextInt(6) + 1) * 10), unit: Unit.findByName(TIME_UNIT_MINUTES))
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
        int nutrientCount = Nutrient.count()
        (1..new Random().nextInt(5)).each {Integer index ->
            RecipeNutrient nutrient = new RecipeNutrient()
            nutrient.recipe = recipe
            nutrient.nutrient = Nutrient.get(new Random().nextInt(nutrientCount) + 1)

            Quantity nutrientQuantity = new Quantity()
            nutrientQuantity.value = (new Random().nextInt(500) + 1)
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
            Quantity quantity = StandardConversion.getQuantityToSave((new Random().nextInt(5) + 1).toString(), Unit.findByName(UNIT_FIFTH), recipe.density)
            quantity?.s()

            ingredient.quantity = quantity
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

    public void populateMenuPlans(Party user) {
        (1..1).each {Integer i ->
            MenuPlan menuPlan = new MenuPlan(name: "${user.name}'s MenuPlan-${i}", owner: user).s()
            menuPlan.weeks = populateWeeks(menuPlan)
            menuPlan.s()
            user.addToMenuPlans(menuPlan)
            user.s()
            println "----- Created menuPlan: ${menuPlan.name}."
        }
    }

    public void populateShoppingList(MenuPlan menuPlan) {
        ShoppingList shoppingList = new ShoppingList()
        shoppingList.name = menuPlan.name + '-Shopping List'
        shoppingList.menuPlan = menuPlan
        shoppingList.servings = 4
        shoppingList.party = menuPlan.owner
        ['0', '1', '2', '3'].each {String weekIndex ->
            WeeklyShoppingList weeklyShoppingList = shoppingListService.createWeeklyShoppingList(shoppingList, menuPlan, weekIndex, true)
            shoppingList.addToWeeklyShoppingLists(weeklyShoppingList)
        }
        shoppingList.s()
        println "----- Created ShoppingList: ${shoppingList.name}."

    }

    public void populateQuickFills(Integer count) {
        (1..count).each {Integer index ->
            QuickFill quickFill = new QuickFill(name: "QuickFill-${index}")
            Meal meal1 = new Meal(type: MealType.DINNER, items: Item.getAll(1..3))
            Meal meal2 = new Meal(type: MealType.DINNER, items: Item.getAll(2..4))
            Meal meal3 = new Meal(type: MealType.DINNER, items: Item.getAll(3..5))
            Meal meal4 = new Meal(type: MealType.DINNER, items: Item.getAll(4..6))
            Meal meal5 = new Meal(type: MealType.BREAKFAST, items: Item.getAll(1..2))
            Meal meal6 = new Meal(type: MealType.LUNCH, items: Item.getAll(3..4))
            quickFill.addToMealItems(meal1)
            quickFill.addToMealItems(meal2)
            quickFill.addToMealItems(meal3)
            quickFill.addToMealItems(meal4)
            quickFill.addToMealItems(meal5)
            quickFill.addToMealItems(meal6)
            quickFill.s()
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
//            Meal meal = new Meal(day: day, type: type)
            Meal meal = new Meal(type: type)
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
