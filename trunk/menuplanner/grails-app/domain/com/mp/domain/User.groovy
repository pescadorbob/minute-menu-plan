package com.mp.domain

import org.grails.comments.Comment
import org.codehaus.groovy.grails.commons.ConfigurationHolder

class User {

    static config = ConfigurationHolder.config

    String name
    Image image
    Integer mouthsToFeed
    String introduction
    String city
    Date joiningDate
    LoginCredential loginCredential = new LoginCredential()
    FacebookAccount facebookAccount
    Boolean isEnabled = true
    List<UserType> roles = []

    static hasMany = [roles: UserType, favourites: Recipe, contributions: Recipe, menuPlans:MenuPlan]
    static transients = ['isEnabledString', 'imageDir']

    static User getCurrentUser() {
        Long userId = SessionUtils.session.loggedUserId?.toLong()
        return ((userId) ? User.get(userId) : null)
    }

    void deleteImage(){
        Image image = this?.image
        this.image = null
        image?.delete(flush: true)
    }

    String getImageDir(){
        return (config.imagesRootDir + config.usersRootDir + this?.id + '/')
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

    String toString() {
        return name
    }

    String getIsEnabledString() {
        return (isEnabled ? 'Enabled' : 'Disabled')
    }

    def beforeInsert = {
        joiningDate = new Date()
    }

    static constraints = {
        image(nullable: true, blank: true)
        city(nullable: true, blank: true)
        mouthsToFeed(nullable: true, blank: true)
        facebookAccount(nullable: true)
        joiningDate(nullable: true, blank: true)
        introduction(nullable: true, maxSize: 1000)
    }

}
