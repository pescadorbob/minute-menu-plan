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
        User user = User.currentUser
        Recipe recipe = Recipe.get(recipeId)
        out << g.render(template: '/recipe/showAddToFavorite', model: [isAdded: (recipe in user?.favourites)])
    }
    def menuPlanDropdown = {
        User user = User.currentUser
        List<MenuPlan> menuPlans = MenuPlan.findAllByOwner(user)
        out << g.render(template: '/layouts/menuPlanDropdown', model: [menuPlans: menuPlans])
    }

    def loggedUserDropDown = {attrs ->
        out << g.render(template: '/layouts/loggedUserDropDown', model: [loggedUser: User.currentUser])
    }

    def shoppingListDropDown = {
        User user = User.currentUser
        List<ShoppingList> shoppingLists = ShoppingList.findAllByUser(user)
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
        if(Image.exists(id)) {
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
        Recipe recipe = attrs['recipe']
        List<Comment> nonAbusiveComments = recipe.comments
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
        Recipe recipe = attrs['recipe']
        User user = User.currentUser
        Boolean reported = false

        List<RecipeAbuse> recipeAbuses = RecipeAbuse.findAllByReporterAndRecipe(user, recipe)
        if (!recipeAbuses) {
            out << g.render(template: "/recipe/showRecipeAbuse", model: [reported: reported, recipeId: recipe?.id])
        } else {
            reported = true
            out << g.render(template: "/recipe/showRecipeAbuse", model: [reported: reported])
        }
    }

    def reportCommentAbuse = {attrs ->
        Comment comment = attrs['comment']
        User user = User.currentUser
        Boolean alreadyReported = CommentAbuse.countByCommentAndReporter(comment, user) as Boolean
        out << g.render(template: "/recipe/reportCommentAbuse", model: [comment: comment, user: user, alreadyReported: alreadyReported])
    }

    def firstTimeUser = {attrs, body ->
        if (session.loggedUserId) {
            User user = User.get(session.loggedUserId.toLong())
            if (user.menuPlans.size() == 0) {
                out << body()
            }
        }
    }

    def menuPlanActions = {
        List<String> menuplanActions = ['Print Monthly Menu Plan', 'Print Weekly Menu Plan', 'Create Shopping List', 'Delete Menu Plan']
        out << g.render(template: "/menuPlan/menuPlanActions", model: [menuplanActions: menuplanActions])
    }
}
