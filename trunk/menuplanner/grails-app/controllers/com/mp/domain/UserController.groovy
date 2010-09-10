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
        Party party = Party.get(params.long('id'))
        if (party) {
            try {
                Boolean deletingCurrentUser = (party == LoginCredential.currentUser?.party)
                party = Party.get(party.id)
                userService.deleteParty(party)
                flash.message = message(code: 'user.delete.successful')
                if (deletingCurrentUser) {
                    SessionUtils?.session?.invalidate()
                    redirect(uri: '/')
                } else {
                    redirect(controller: 'user', action: "list")
                }
            } catch (org.springframework.dao.DataIntegrityViolationException e) {
                e.printStackTrace()
                flash.message = message(code: 'user.delete.unsuccessful')
                redirect(action: "show", id: params.id)
            }
        } else {
            flash.message = message(code: 'no.such.user.exists')
            redirect(action: "list")
        }
    }

    def changeStatus = {
        Party party = Party.findById(params.long('id'))
        if (party) {
            (party.isEnabled = !(party.isEnabled))
            if (!party.isEnabled && (party == LoginCredential.currentUser.party)) {
                SessionUtils?.session?.invalidate()
                String text = "The Session TimedOut url=" + ConfigurationHolder.config.grails.serverURL
                render(text: text, contentType: 'text/plain')
            } else {
                render "true"
            }
        } else {
            render "false"
        }
    }

    def removeFavorite = {
        Recipe recipe = Recipe.get(params.id)
        LoginCredential user = LoginCredential.currentUser
        if (recipe && user) {
            user?.party?.removeFromFavourites(recipe)
            user.s()
            recipe.reindex()
            render "true"
        } else {
            render "false"
        }
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
            userList = Party.createCriteria().list(max: params.max, offset: 0) {
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
            userList = Party.list(params)
            total = Party.count()
        }
        render(view: 'list', model: [parties: userList, total: total, searchName: name, userStatus: params.userStatus])
    }

    def edit = {
        if (params.id) {
            Party party = Party.get(params.long('id'))
            UserCO userCO = new UserCO(party)
            render(view: 'edit', model: [userCO: userCO, party: party])
        } else {
            response.sendError(404)
        }
    }
    def create = {
        UserCO userCO = new UserCO(roles: [UserType.Subscriber.toString()], isEnabled: true)
        Party party = new Party()
        party.addToRoles(new Subscriber())
        render(view: 'create', model: [userCO: userCO, party: party])
    }

    def createUser = {
        UserCO userCO = new UserCO()
        render(view: 'createUser', model: [userCO: userCO])
    }
    def update = {UserCO userCO ->
        if (userCO.validate()) {
            userCO.updateParty()
            flash.message = message(code: 'user.updateded.success')
            redirect(action: 'show', id: userCO?.id)
        } else {
            userCO.errors.allErrors.each {
                println it
            }
            UserLogin userLogin = UserLogin.findByEmail(userCO?.email)
            render(view: 'edit', model: [userCO: userCO, party: userLogin?.party])
        }
    }
    def save = {UserCO userCO ->
        if (userCO.validate()) {
            Party party = userCO.createParty()
            flash.message = message(code: 'user.created.success')
            redirect(action: 'show', id: party?.id)
        } else {
            Party party
            userCO.errors.allErrors.each {
                println it
            }
            UserLogin userLogin = UserLogin.findByEmail(userCO?.email)
            if (userLogin) {
                party = userLogin.party
            } else {
                party = new Party()
                party.addToRoles(new Subscriber())
            }
            render(view: 'create', model: [userCO: userCO, party: party])
        }
    }

    def show = {
        Party party = Party.get(params.long('id'))
        if (party) {
            Map abusiveRecipesMap = party?.abusiveRecipesMap
            Map abusiveCommentsMap = party?.abusiveCommentsMap
            render(view: 'show', model: [abusiveCommentsMap: abusiveCommentsMap, abusiveRecipesMap: abusiveRecipesMap, party: party])
        } else {
            response.sendError(404)
        }
    }

    def facebookConnect = {
        Long userId = params.long('userId') ? params.long('userId') : 0L
        Party party = Party.get(userId)
        String redirectUrl = "${createLink(controller: 'user', action: 'facebookConnect', absolute: true, params: [userId: userId]).encodeAsURL()}"
        party = userService.updateUserFromFacebook(redirectUrl, params.code, party)
        if (party) {
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
        Party user = userId ? Party.findById(userId) : null

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
                    orderStatus.party = user
                    orderStatus.transactionId = transactionId
                    orderStatus.s()
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
            switch (financialOrderState) {
                case FinancialState.CHARGEABLE.name:
                    googleCheckoutService.updateFinancialState(orderStatus, FinancialState.CHARGEABLE, transactionId)
                    user = orderStatus.party
                    user?.isEnabled = true
                    user?.s()
                    HttpSession currentSession = ConfigurationHolder.config.sessions.find {it.userId == user.id}
                    currentSession.userId = null
                    currentSession.loggedUserId = user?.loginCredentials?.toList()?.first()?.id?.toString()
                    response.setStatus(200)
                    render responseXML
                    break;
                case FinancialState.CHARGED.name:
                    googleCheckoutService.updateFinancialState(orderStatus, FinancialState.CHARGED, transactionId)
                    orderStatus.fulfillmentStatus = FulfillmentState.DELIVERED
                    response.setStatus(200)
                    render responseXML
                    break;
                case FinancialState.CANCELLED.name:
                    googleCheckoutService.updateFinancialState(orderStatus, FinancialState.CANCELLED, transactionId)
                    orderStatus.fulfillmentStatus = FulfillmentState.WILL_NOT_DELIVER
                    response.setStatus(200)
                    render responseXML
                    break;
                case FinancialState.PAYMENT_DECLINED.name:
                    googleCheckoutService.updateFinancialState(orderStatus, FinancialState.PAYMENT_DECLINED, transactionId)
                    orderStatus.fulfillmentStatus = FulfillmentState.WILL_NOT_DELIVER
                    response.setStatus(200)
                    render responseXML
                    break;
                case FinancialState.CANCELLED_BY_GOOGLE.name:
                    googleCheckoutService.updateFinancialState(orderStatus, FinancialState.CANCELLED_BY_GOOGLE, transactionId)
                    orderStatus.fulfillmentStatus = FulfillmentState.WILL_NOT_DELIVER
                    response.setStatus(200)
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
            Party party = userCO.createParty()
            Map data = [:]
            data['userId'] = party?.id
            session.userId = party?.id
            println "Session UserId: " + session.userId
            println "Session Id: " + session.id
            session.setMaxInactiveInterval(3600)
            redirect(action: 'createSubscription', controller: 'subscription', params: data)
        } else {
            userCO.errors.allErrors.each {
                println it
            }
            render(view: 'createUser', model: [userCO: userCO])
        }
    }

    def welcome = {
        if (session.userId) {
            Subscriber user = Subscriber.get(session.userId)
            render(view: 'registrationAcknowledgement', model: [user: user])
        }
        else if (session.loggedUserId) {
            Subscriber user = Subscriber.get(session.loggedUserId)
            redirect(action: 'show', id: user?.id)
        } else {
            redirect(uri: '/')
        }
    }

    def unlinkFacebookAccount = {
        Party party = Party.get(params.id.toLong())
        if (party) {
            FacebookAccount facebookAccount = party.facebookAccount
            party.facebookAccount = null
            facebookAccount.delete(flush: true)
            render "Your account has been unlinked"
        } else {
            render "Unable to unlinked the account"
        }
    }
}