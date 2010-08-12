package com.mp.domain

import grails.converters.JSON
import org.grails.comments.Comment


class MenuplannerTagLib {

    static namespace = 'mp'
    def permissionService

    def showEditRecipe = {attrs ->
        Long recipeId = attrs['recipeId']?.toLong()
        Recipe recipe = Recipe.get(recipeId)
        if (permission.hasPermission(permission: Permission.UPDATE_RECIPE, recipe: recipe)) {
            out << g.render(template: '/recipe/isEditableRecipe', model: [isEditable: true, recipeId: recipeId])
        }
    }

    def showFavorite = {attrs ->
        Long recipeId = attrs['recipeId']?.toLong()
        LoginCredential user = LoginCredential.currentUser
        Recipe recipe = Recipe.get(recipeId)
        out << g.render(template: '/recipe/showAddToFavorite', model: [isAdded: (recipe in user?.party?.favourites)])
    }
    def menuPlanDropdown = {
        LoginCredential user = LoginCredential.currentUser
        List<MenuPlan> menuPlans = MenuPlan.findAllByOwner(user.party)
        out << g.render(template: '/layouts/menuPlanDropdown', model: [menuPlans: menuPlans])
    }

    def loggedUserDropDown = {attrs ->
        out << g.render(template: '/layouts/loggedUserDropDown', model: [loggedUser: LoginCredential.currentUser])
    }

    def shoppingListDropDown = {
        LoginCredential user = LoginCredential.currentUser
        List<ShoppingList> shoppingLists = ShoppingList.findAllByParty(user?.party)
        out << g.render(template: '/layouts/shoppingListDropDown', model: [shoppingLists: shoppingLists])
    }

    def adminDropDown = {
        out << g.render(template: '/layouts/adminDropDown')
    }

    def checkGeneralInfoTabError = {attrs ->
        def bean = attrs['bean']
        def fieldsToCheck = attrs['fields']

        if (bean) {

            def fieldsHavingError = bean?.errors?.allErrors.collect {it.properties['field']}
            if (fieldsHavingError.any {it.toString() in fieldsToCheck}) {
                out << "color:red;"
            } else {
                out << ""
            }
        }
    }

    def image = {attrs ->
        String height = attrs['height']
        String width = attrs['width']
        Long id = attrs['id']
        String noImage = (attrs['noImage']) ? attrs['noImage'] : 'no-img.gif'
        String clas = attrs['class']
        if (Image.exists(id)) {
            out << "<img class='${clas}' height='${height}' width='${width}' src='" + createLink(controller: 'image', action: 'image', params: [id: id]) + "'/>"
        } else {
            out << "<img class='${clas}' height='${height}' width='${width}' src='" + createLink(controller: 'image', action: 'imageByPath', params: [noImage: noImage]) + "'/>"
        }
    }

    def getSelectedCategoriesAsJSON = {attrs ->
        if (attrs['prePopulated']) {
            List<Category> categories = Category.getAll([attrs['prePopulated']].flatten()*.toLong())
            List categoriesJson = categories.collect { [id: it.id, name: it.name] }
            println categoriesJson as JSON
            out << categoriesJson
        }
    }

    def mealItems = {attrs ->
        String weekIndex = attrs['weekIndex']
        MealType mealType = attrs['type']
        Week week = attrs['week']
        String image = attrs['image']
        out << g.render(template: '/menuPlan/mealItems', model: [week: week, weekIndex: weekIndex, mealType: mealType, image: image])
    }


    def comments = {attrs ->
        println "Id: " + attrs['recipeId']
        Recipe recipe = Recipe.get(attrs['recipeId'])
        println "Recipe: " + recipe
        List<Comment> nonAbusiveComments = recipe?.comments
        List<Comment> abusiveComments = []
        if (recipe.comments) {
            abusiveComments = CommentAbuse.findAllByCommentInList(recipe.comments)
            if (abusiveComments) {
                Map x = abusiveComments.groupBy {it.comment.id}
                abusiveComments = abusiveComments?.findAll {(x[it.id]?.size()) >= 3}
                nonAbusiveComments = recipe.comments.findAll {(!(it.id in abusiveComments.id))}
            }
        }
        out << g.render(template: "/recipe/comments", model: [recipe: recipe, comments: nonAbusiveComments])
    }

    def showRecipeAbuse = {attrs ->

        Recipe recipe = Recipe.get(attrs['recipeId'])
        LoginCredential user = LoginCredential.currentUser
        Boolean reported = false

        List<RecipeAbuse> recipeAbuses = RecipeAbuse.findAllByReporterAndRecipe(user?.party, recipe)
        if (!recipeAbuses) {
            out << g.render(template: "/recipe/showRecipeAbuse", model: [reported: reported, recipeId: recipe?.id])
        } else {
            reported = true
            out << g.render(template: "/recipe/showRecipeAbuse", model: [reported: reported])
        }
    }

    def reportCommentAbuse = {attrs ->
        Comment comment = attrs['comment']
        LoginCredential user = LoginCredential.currentUser
        Boolean alreadyReported = CommentAbuse.countByCommentAndReporter(comment, user?.party) as Boolean
        out << g.render(template: "/recipe/reportCommentAbuse", model: [comment: comment, user: user, alreadyReported: alreadyReported])
    }

    def firstTimeUser = {attrs, body ->
        LoginCredential user = LoginCredential.currentUser
        if (user && (user?.party?.menuPlans?.size() == 0)) {
            out << body()
        }
    }

    def menuPlanActions = {
        List<String> menuplanActions = ['Print Monthly Menu Plan', 'Print Weekly Menu Plan', 'Create Shopping List', 'Delete Menu Plan']
        out << g.render(template: "/menuPlan/menuPlanActions", model: [menuplanActions: menuplanActions])
    }

    def recipeIngredients = {attrs ->
        Recipe recipe = Recipe.get(attrs['recipeId'])
        Integer customServings = attrs['customServings']
        List<RecipeIngredient> recipeIngredients = []
        if (recipe) {
            recipe.ingredients.each {RecipeIngredient recipeIngredient ->
                RecipeIngredient recipeIngredientNew = new RecipeIngredient()
                recipeIngredientNew.ingredient = recipeIngredient?.ingredient
                recipeIngredientNew.aisle = recipeIngredient.aisle
                recipeIngredientNew.quantity = recipeIngredient?.quantity
                recipeIngredientNew.preparationMethod = recipeIngredient?.preparationMethod
                if (customServings && recipeIngredient.quantity && recipeIngredient.quantity.value) {
                    Float value = (recipeIngredientNew?.quantity?.value) ? recipeIngredientNew.quantity.value : 1.0f
                    Integer servings = recipeIngredient.recipe.servings
                    recipeIngredientNew.quantity.value = ((customServings * value) / servings).toFloat()
                    if (!recipeIngredientNew.quantity.savedUnit) {
                        recipeIngredientNew.quantity.value = Math.ceil(recipeIngredientNew.quantity.value)
                    }
                    recipeIngredients.add(recipeIngredientNew)
                } else {
                    recipeIngredients.add(recipeIngredient)
                }
            }
            out << g.render(template: "/recipe/recipeIngredients", model: [ingredients: recipeIngredients])
        }
    }
}