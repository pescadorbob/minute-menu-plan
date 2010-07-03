package com.mp.domain

import org.codehaus.groovy.grails.commons.ConfigurationHolder
import org.grails.comments.Comment

class UserController {

    static config = ConfigurationHolder.config
    def userService
    def asynchronousMailService
    def facebookConnectService

    def index = {
        redirect(action: 'list')
    }

    def delete = {
        User user = User.get(params?.id)
        if (user) {
            try {
                Boolean deletingCurrentUser = (user == User.currentUser)
                User.withTransaction {
                    List commentAbuses = CommentAbuse.findAllByReporter(user)
                    List recipeAbuses = RecipeAbuse.findAllByReporter(user)
                    commentAbuses*.delete(flush: true)
                    recipeAbuses*.delete(flush: true)
                    user.delete(flush: true)
                    flash.message = message(code: 'user.delete.successful')
                }
                if (deletingCurrentUser) {
                    redirect(action: "logout", controller: 'login')
                } else {
                    redirect(controller: 'user', action: "list")
                }
                return
            } catch (org.springframework.dao.DataIntegrityViolationException e) {
                flash.message = message(code: 'user.delete.unsuccessful')
                redirect(action: "show", id: params.id)
                return
            }
        } else {
            flash.message = message(code: 'no.such.user.exists')
            redirect(action: "list")
            return
        }
    }

    def changeStatus = {
        render "" + userService.changeStatus(params?.id?.toLong())
    }

    def removeFavorite = {
        Recipe recipe = Recipe.get(params.id)
        User user = User.currentUser
        user?.removeFromFavourites(recipe)
        user.s()
        recipe.reindex()
        render(view: 'show', model: [user: user])
    }

    def alterFavorite = {
        Recipe recipe = Recipe.get(params.id)
        User user = User.currentUser
        if (recipe in user?.favourites) {
            user?.removeFromFavourites(recipe)
            user.s()
        } else {
            user?.addToFavourites(recipe)
            user.s()
        }
        recipe.reindex()
        redirect(controller: 'recipe', action: 'show', id: params?.id)
    }

    def list = {
        params.max = Math.min(params.max ? params.int('max') : 10, 100)
        String name = params.searchName
        def userList
        Integer total
        if (name || params?.userStatus) {
            userList = User.createCriteria().list(max: params.max, offset: 0) {
                if (name) {
                    ilike('name', "%${name}%")
                }
                if (params.userStatus == 'enabled') {
                    eq('isEnabled', true)
                }
                if (params.userStatus == 'disabled') {
                    eq('isEnabled', false)
                }
            }
            total = userList.getTotalCount()
        } else {
            params.userStatus = 'all'
            userList = User.list(params)
            total = User.count()
        }

        render(view: 'list', model: [userList: userList, total: total, searchName: name, userStatus: params.userStatus])
    }

    def edit = {
        if (params.id) {
            User user = User.get(params.long('id'))
            UserCO userCO = new UserCO(user)
            render(view: 'edit', model: [userCO: userCO])
        }
    }
    def create = {
        UserCO userCO = new UserCO(roles: [UserType.User.toString()], isEnabled: true)
        render(view: 'create', model: [userCO: userCO])
    }
    def update = {UserCO userCO ->
        if (userCO.validate()) {
            userCO.updateUser()
            String message = message(code: 'user.updateded.success')
            redirect(action: 'show', id: userCO?.id, params: [message: message])
        } else {
            println userCO.errors.allErrors.each {
                println it
            }
            render(view: 'edit', model: [userCO: userCO])
        }
    }
    def save = {UserCO userCO ->
        if (userCO.validate()) {
            User user = userCO.convertToUser()
            String message = message(code: 'user.created.success')
            redirect(action: 'show', id: user?.id, params: [message: message])
        } else {
            userCO.errors.allErrors.each {
                println it
            }
            render(view: 'create', model: [userCO: userCO])
        }
    }

    def show = {
        User user = User.get(params.id)
        Map abusiveRecipesMap = user.abusiveRecipesMap
        Map abusiveCommentsMap = user.abusiveCommentsMap
        if (params?.message) {
            flash.message = params.message
        }
        render(view: 'show', model: [user: user, abusiveCommentsMap: abusiveCommentsMap, abusiveRecipesMap: abusiveRecipesMap])
    }

    def facebookConnect = {
        String redirectUrl = "${createLink(controller: 'user', action: 'facebookConnect', absolute: true)}"
        userService.updateUserFromFacebook(redirectUrl, params.code)
        render "<script type='text/javascript'>window.close()</script>"
    }

    def facebookConnectCancel = {
        render "<script type='text/javascript'>window.close()</script>"
    }

}