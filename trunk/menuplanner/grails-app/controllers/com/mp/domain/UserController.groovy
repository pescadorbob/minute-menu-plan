package com.mp.domain

import org.codehaus.groovy.grails.commons.ConfigurationHolder

import javax.servlet.http.HttpSession
import com.mp.google.checkout.*

class UserController {

    static config = ConfigurationHolder.config
    def userService
    def asynchronousMailService
    def facebookConnectService
    def googleCheckoutService

    def index = {
        redirect(action: 'list')
    }

    def delete = {
        Party user = Party.get(params?.id)
        if (user) {
            try {
                Boolean deletingCurrentUser = (user == LoginCredential.currentUser?.party)
                Party.withTransaction {
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
        LoginCredential user = LoginCredential.currentUser
        user?.party?.removeFromFavourites(recipe)
        user.s()
        recipe.reindex()
        render(view: 'show', model: [user: user])
    }

    def alterFavorite = {
        Recipe recipe = Recipe.get(params.id)
        LoginCredential user = LoginCredential.currentUser
        if (recipe in user?.party?.favourites) {
            user?.party?.removeFromFavourites(recipe)
            user.s()
        } else {
            user?.party?.addToFavourites(recipe)
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
            userList = Subscriber.createCriteria().list(max: params.max, offset: 0) {
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
            userList = Subscriber.list(params)
            total = Subscriber.count()
        }

        render(view: 'list', model: [userList: userList, total: total, searchName: name, userStatus: params.userStatus])
    }

    def edit = {
        if (params.id) {
            Subscriber user = Subscriber.get(params.long('id'))
            UserCO userCO = new UserCO(user)
            render(view: 'edit', model: [userCO: userCO])
        }
    }
    def create = {
        UserCO userCO = new UserCO(roles: [UserType.Subscriber.toString()], isEnabled: true)
        render(view: 'create', model: [userCO: userCO])
    }

    def createUser = {
        UserCO userCO = new UserCO()
        render(view: 'createUser', model: [userCO: userCO])
    }
    def update = {UserCO userCO ->
        if (userCO.validate()) {
            userCO.updateUser()
            flash.message = message(code: 'user.updateded.success')
            redirect(action: 'show', id: userCO?.id)
        } else {
            println userCO.errors.allErrors.each {
                println it
            }
            render(view: 'edit', model: [userCO: userCO])
        }
    }
    def save = {UserCO userCO ->
        if (userCO.validate()) {
            Subscriber user = userCO.convertToUser()
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
        Subscriber user = Subscriber.get(params.id)
        Map abusiveRecipesMap = user.party.abusiveRecipesMap
        Map abusiveCommentsMap = user.party.abusiveCommentsMap
        if (params?.message) {
            flash.message = params.message
        }
        render(view: 'show', model: [user: user, abusiveCommentsMap: abusiveCommentsMap, abusiveRecipesMap: abusiveRecipesMap])
    }

    def facebookConnect = {
        Long userId = params.long('userId') ? params.long('userId') : 0L
        Subscriber user = userId ? Subscriber.get(userId) : null
        String redirectUrl = "${createLink(controller: 'user', action: 'facebookConnect', absolute: true, params: [userId: userId]).encodeAsURL()}"
        user = userService.updateUserFromFacebook(redirectUrl, params.code, user)
        if (user) {
            if (params.long('userId')) {
                render "<script type='text/javascript'>window.opener.facebookConnectSuccess();window.close();</script>"
            } else {
                user.addToRoles(UserType.Subscriber)
                user.s()
                session.loggedUserId = user.id.toString()
                render "<script type='text/javascript'>window.opener.location.href='" + createLink(controller: 'user', action: 'show', id: user.id) + "';window.close();</script>"
            }
        }
        else {
            render "<script type='text/javascript'>window.close()</script>"
        }
    }

    def enableUser = {
        Long userId = params.long('shopping-cart.items.item-1.merchant-item-id')
        Subscriber user = userId ? Subscriber.findById(userId) : null

        String transactionId = params['serial-number']
        String orderNumber = params['order-summary.google-order-number']

        String financialOrderState = params['order-summary.financial-order-state']
        String responseXML = '<?xml version="1.0" encoding="UTF-8"?><notification-acknowledgment xmlns="http://checkout.google.com/schema/2" serial-number="' + transactionId + '"/>'

        OrderStatus orderStatus = OrderStatus.findByOrderId(orderNumber)

        println "********************Order: " + transactionId
        println "********************Financial Order State: " + financialOrderState
        println "********************User: " + user

        response.contentType = 'text/xml'
        response.setStatus(200)
        if (userId) {
            if (user) {
                if (!orderStatus && (financialOrderState == FinancialState.REVIEWING.name)) {

                    orderStatus = new OrderStatus()
                    orderStatus.orderId = orderNumber
                    orderStatus.transactionId = transactionId
                    orderStatus.s()

                    user?.party?.isEnabled = true
                    user?.party?.s()
                    try{
                        HttpSession currentSession = ConfigurationHolder.config.sessions.find {it.userId == userId}
                        currentSession.userId = null
                        currentSession.loggedUserId = user?.party?.loginCredentials?.toList()?.first()?.id?.toString()
                    } catch(Exception e){
                        e.printStackTrace()
                    }
                    render responseXML
                } else {
                    println "************************ Unexpected Status: ${financialOrderState} ************************"
                    render responseXML
                }
            }
            else {
                println "*********** No User Found ***********"
                render responseXML
            }
        } else {
            switch(financialOrderState){
                case FinancialState.CHARGEABLE.name:
                    googleCheckoutService.updateFinancialState(orderStatus, FinancialState.CHARGEABLE, transactionId)
//                    googleCheckoutService.updateFulfillmentState(orderStatus, FulfillmentState.PROCESSING, transactionId)
                    render responseXML
                    break;
                case FinancialState.CHARGED.name:
                    googleCheckoutService.updateFinancialState(orderStatus, FinancialState.CHARGED, transactionId)
//                    googleCheckoutService.updateFulfillmentState(orderStatus, FulfillmentState.DELIVERED, transactionId)
                    render responseXML
                    break;
                default:
                    println "************************ Unhandled Status: ${financialOrderState} ************************"
                    response.setStatus(500)
                    render responseXML
            }
        }
    }

    def newUserCheckout = {UserCO userCO ->
        userCO.roles.add(UserType.Subscriber.name())
        userCO.isEnabled = false
        if (userCO.validate()) {
            Subscriber user = userCO.createSubscriber()
            Map data = [:]
            data['userId'] = user.id
            session.userId = user.id
            println "Session Id: " + session.id
            session.setMaxInactiveInterval(600)
            redirect(action: 'createSubscription', controller: 'subscription', params: data)
        } else {
            userCO.errors.allErrors.each {
                println it
            }
            render(view: 'createUser', model: [userCO: userCO])
        }
    }
}