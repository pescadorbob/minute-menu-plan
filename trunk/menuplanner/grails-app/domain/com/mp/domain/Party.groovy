package com.mp.domain

import org.grails.comments.Comment

class Party {
    String name
    Date joiningDate
    FacebookAccount facebookAccount
    Boolean isEnabled = true

    Set<PartyRole> roles = []
    Set<LoginCredential> loginCredentials = []

    static transients = ['isEnabledString']

    static hasMany = [favourites: Recipe, contributions: Recipe, menuPlans:MenuPlan,
            roles: PartyRole, loginCredentials: LoginCredential]

    def beforeInsert = {
        joiningDate = new Date()
    }

    static constraints = {
        facebookAccount(nullable: true)
        joiningDate(nullable: true, blank: true)
    }

    String getIsEnabledString() {
        return (isEnabled ? 'Enabled' : 'Disabled')
    }

    def getRoleTypes() {
        return (roles*.userType)
    }

    def getAbusiveCommentsMap() {
        List<Comment> allComments = Comment.findAllByPoster(this)
        List<Comment> abusiveCommentsList = []
        if (allComments) {
            List<CommentAbuse> x = CommentAbuse.findAllByCommentInList(allComments)
            abusiveCommentsList = x*.comment
        }

        Map map = (abusiveCommentsList) ? abusiveCommentsList.groupBy{it} : [:]
        if(map){
            map.keySet().each{key ->
                map[key] = map[key].size()
            }
        }
        return map
    }

    def getAbusiveRecipesMap() {
        List<Recipe> abusiveRecipesList = []
        if (this.contributions) {
            List<RecipeAbuse> recipeAbuses = RecipeAbuse.findAllByRecipeInList(this.contributions)
            abusiveRecipesList = recipeAbuses*.recipe
        }
        Map map = (abusiveRecipesList) ? abusiveRecipesList.groupBy{it} : [:]
        if(map){
            map.keySet().each{key ->
                map[key] = map[key].size()
            }
        }
        return map
    }

    def getInappropriateFlagsCount(){
        Integer total = 0
        if(abusiveRecipesMap){ total +=abusiveRecipesMap.values()?.sum{it}}
        if(abusiveCommentsMap){ total +=abusiveCommentsMap.values()?.sum{it}}
        return total
    }

    static mapping = {
        tablePerHierarchy false
    }

}
