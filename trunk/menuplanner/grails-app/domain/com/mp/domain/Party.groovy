package com.mp.domain

import org.grails.comments.Comment

class Party {
    String name
    Date joiningDate
    FacebookAccount facebookAccount
    Boolean isEnabled = true

    Set<PartyRole> roles = []
    Set<LoginCredential> loginCredentials = []
    Set<Recipe> contributions = []
    Set<MenuPlan> menuPlans = []
    Set<Recipe> favourites = []
    Set<ShoppingList> shoppingLists = []

    static transients = ['isEnabledString', 'email', 'password', 'userLogin', 'role', 'administrator', 'superAdmin', 'subscriber']

    static hasMany = [favourites: Recipe, contributions: Recipe, menuPlans: MenuPlan,
            roles: PartyRole, loginCredentials: LoginCredential, shoppingLists: ShoppingList]

    def beforeInsert = {
        joiningDate = new Date()
    }

    String getEmail() {
        return UserLogin.findByParty(this).email
    }

    String getPassword() {
        return UserLogin.findByParty(this).password
    }

    UserLogin getUserLogin() {
        UserLogin userLogin = loginCredentials?.toList()?.find {LoginCredential loginCredential ->
            return (loginCredential instanceof UserLogin)
        }
        return userLogin
    }

    static constraints = {
        facebookAccount(nullable: true)
        joiningDate(nullable: true, blank: true)
    }

    String getIsEnabledString() {
        return (isEnabled ? 'Enabled' : 'Disabled')
    }

    Subscriber getSubscriber() {
        return getRole(UserType.Subscriber)
    }

    Administrator getAdministrator() {
        return getRole(UserType.Admin)
    }

    SuperAdmin getSuperAdmin() {
        return getRole(UserType.SuperAdmin)
    }

    PartyRole getRole(UserType type) {
        PartyRole partyRole = roles?.find {it.type == type}
        return partyRole
    }

    def getRoleTypes() {
        return (roles*.type)
    }

    def getAbusiveCommentsMap() {
        List<Comment> allComments = Comment.findAllByPoster(this)
        List<Comment> abusiveCommentsList = []
        if (allComments) {
            List<CommentAbuse> x = CommentAbuse.findAllByCommentInList(allComments)
            abusiveCommentsList = x*.comment
        }

        Map map = (abusiveCommentsList) ? abusiveCommentsList.groupBy {it} : [:]
        if (map) {
            map.keySet().each {key ->
                map[key] = map[key].size()
            }
        }
        return map
    }

    def getAbusiveRecipesMap() {
        List<Recipe> abusiveRecipesList = []
        if (this.contributions) {
            List<RecipeAbuse> recipeAbuses = RecipeAbuse.findAllByRecipeInList(this.contributions?.toList())
            abusiveRecipesList = recipeAbuses*.recipe
        }
        Map map = (abusiveRecipesList) ? abusiveRecipesList.groupBy {it} : [:]
        if (map) {
            map.keySet().each {key ->
                map[key] = map[key].size()
            }
        }
        return map
    }

    def getInappropriateFlagsCount() {
        Integer total = 0
        if (abusiveRecipesMap) { total += abusiveRecipesMap.values()?.sum {it}}
        if (abusiveCommentsMap) { total += abusiveCommentsMap.values()?.sum {it}}
        return total
    }

    static mapping = {
        tablePerHierarchy false
    }

}
