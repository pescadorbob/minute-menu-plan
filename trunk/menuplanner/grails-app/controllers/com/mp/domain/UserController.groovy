package com.mp.domain

import org.codehaus.groovy.grails.commons.ConfigurationHolder

import javax.servlet.http.HttpSession
import com.mp.google.checkout.*
import javax.servlet.http.Cookie
import com.mp.domain.subscriptions.ProductOffering
import com.mp.domain.party.Party
import com.mp.domain.party.Subscriber
import com.mp.domain.orders.OrderStatus

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
        params.offset = params.offset ? params.offset : 0
        String name = params.searchName
        def userList
        Integer total
        if (name || params?.userStatus) {
            userList = Party.createCriteria().list(max: params.max, offset: params.offset) {
                if (name) {
                    ilike('name', "%${name}%")
                }
                if (params.userStatus == 'enabled') {
                    eq('isEnabled', true)
                }
                if (params.userStatus == 'disabled') {
                    eq('isEnabled', false)
                }
                if (params.userStatus == 'awaitingVerification') {
                    isNull('isEnabled')
                }
            }
            total = userList.getTotalCount()
        } else {
            userList = Party.list(params)
            total = Party.count()
        }
        if (!params.userStatus) {
            params.userStatus = 'all'
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
        UserCO userCO = new UserCO(isEnabled: true)
        render(view: 'create', model: [userCO: userCO])
    }

    def chooseSubscription = {
      String coachId = params?.coachId
      String linkClicked = params?.linkClicked
      String messageToPrint
      if (linkClicked && linkClicked == 'browseRecipes') {
          messageToPrint = message(code: 'browse.recipes.text')
      }
      if (linkClicked && linkClicked == 'createYourMenuPlan') {
          messageToPrint = message(code: 'create.own.menuplan')
      }
      UserCO userCO = new UserCO()
      if (coachId) {
          userCO.coachId = coachId
      }
      def availableProducts = ProductOffering.list()
      render(view: 'chooseSubscription', model: [availableProducts: availableProducts, userCO: userCO, messageToPrint: messageToPrint])

    }
    def createUser = {
        UserCO userCO = new UserCO()
        render(view: 'createUser', model: [userCO: userCO])
    }

    def createFreeUser = {
        String coachId = params?.coachId
        String linkClicked = params?.linkClicked
        String messageToPrint
        if (linkClicked && linkClicked == 'browseRecipes') {
            messageToPrint = message(code: 'browse.recipes.text')
        }
        if (linkClicked && linkClicked == 'createYourMenuPlan') {
            messageToPrint = message(code: 'create.own.menuplan')
        }
        UserCO userCO = new UserCO()
        if (coachId) {
            userCO.coachId = coachId
        }
        render(view: 'createFreeUser', model: [userCO: userCO, messageToPrint: messageToPrint])
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
        if (userCO.isEnabled == null) {
            userCO.isEnabled = false
        }
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
        Party currentUser = LoginCredential?.currentUser?.party
        if (party) {
            Map abusiveRecipesMap = party?.abusiveRecipesMap
            Map abusiveCommentsMap = party?.abusiveCommentsMap
            render(view: 'show', model: [abusiveCommentsMap: abusiveCommentsMap, abusiveRecipesMap: abusiveRecipesMap, party: party, currentUser: currentUser])
        } else {
            response.sendError(404)
        }
    }

    def facebookConnect = {
        Long userId = params.long('userId')
        String code = params.code
        Party party
        if (code) {
            if (!userId) {
                String coachUUID
                List<Cookie> cookies = request.cookies as List
                Cookie coachId = cookies.find {it.name == 'coachId'}
                if (coachId) {
                    coachUUID = coachId.value
                }
                String redirectUrl = "${createLink(controller: 'user', action: 'facebookConnect', absolute: true).encodeAsURL()}"
                party = userService.createUserFromFacebook(redirectUrl, code, coachUUID)
            } else {
                String redirectUrl = "${createLink(controller: 'user', action: 'facebookConnect', absolute: true, params: [userId: userId]).encodeAsURL()}"
                party = Party.get(userId)
                if (party) {
                    party = userService.updateUserFromFacebook(redirectUrl, party, code)
                }
            }
        }
        if (party) {
            if (params.long('userId')) {
                render "<script type='text/javascript'>window.opener.facebookConnectSuccess();window.close();</script>"
            } else {
                session.loggedUserId = party.id.toString()
                party.lastLogin = new Date()
                party.s()
                if (party?.facebookAccount) {
                    render "<script type='text/javascript'>window.opener.location.href='" + createLink(controller: 'recipe', action: 'list') + "';window.close();</script>"
                } else {
                    render "<script type='text/javascript'>window.opener.location.href='" + createLink(controller: 'user', action: 'show', id: party.id) + "';window.close();</script>"
                }
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
        newFreeUserSignUp(userCO)
    }

    def newFreeUserSignUp = {UserCO userCO ->
        userCO.roles.add(PartyRoleType.Subscriber.name())
        userCO.isEnabled = null
        String coachUUID = params?.coachId
        if (coachUUID) {
            userCO.coachId = coachUUID
        } else {
            List<Cookie> cookies = request.cookies as List
            Cookie coachId = cookies.find {it.name == 'coachId'}
            if (coachId) {
                userCO.coachId = coachId.value
            }
        }
        if (userCO.validate()) {
            Party party = userCO.createParty()
            VerificationToken verificationToken = new VerificationToken()
            verificationToken.party = party
            verificationToken.s()

            asynchronousMailService.sendAsynchronousMail {
                to party.userLogin.email
                subject "Email verification for Minute Menu Plan"
                html g.render(template: '/user/accountVerification', model: [party: party, email: userCO.email, token: verificationToken.token])
            }
            render(view: 'registrationAcknowledgement', model: [user: party])
        } else {
            userCO.errors.allErrors.each {
                println it
            }
            render(view: 'createFreeUser', model: [userCO: userCO, messageToPrint: params?.linkClicked])
        }
    }

    def verify = {
        VerificationToken token = VerificationToken.findByToken(params.token)
        if (token) {
            token.party.isEnabled = true
            token.party.s()
            token.delete(flush: true)
            String message = message(code: 'email.verification.success')
            render(view: 'verificationAcknowledgement', model: [message: message])
        } else {
            String message = message(code: 'email.verification.failure')
            render(view: 'verificationAcknowledgement', model: [message: message])
        }
    }

    def createCoach = {
        UserCO userCO = new UserCO()
        userCO.isEnabled = true
        render(view: 'createCoach', model: [userCO: userCO])
    }

    def saveCoach = {UserCO userCO ->
        Long directorId = LoginCredential?.currentUser?.party?.director?.id
        if (directorId) {
            userCO.roles = [PartyRoleType.Coach.name(), PartyRoleType.Subscriber.name()]
            userCO.directorId = directorId
            userCO.isEnabled = true
            if (userCO.validate()) {
                Party party = userCO.createParty()
                flash.message = message(code: 'coach.created.success')
                redirect(action: 'show', id: party?.id)
            } else {
                render(view: 'createCoach', model: [userCO: userCO])
            }
        } else {
            render(view: 'createCoach', model: [userCO: userCO])
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