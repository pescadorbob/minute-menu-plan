package com.mp.domain

import org.codehaus.groovy.grails.commons.ConfigurationHolder

import javax.servlet.http.HttpSession
import com.mp.google.checkout.*
import javax.servlet.http.Cookie
import com.mp.domain.subscriptions.ProductOffering
import com.mp.domain.party.Party
import com.mp.domain.party.Subscriber
import com.mp.domain.orders.GoogleOrderStatus
import com.mp.tools.UserTools
import com.mp.domain.party.DirectorCoach
import com.mp.domain.party.Coach
import com.mp.domain.party.Director
import com.mp.domain.party.CoachSubscriber
import com.mp.domain.party.Administrator
import com.mp.domain.party.SuperAdmin
import static com.mp.MenuConstants.USER_IMAGE_SIZES

class UserController {

    static config = ConfigurationHolder.config
    def userService
    def asynchronousMailService
    def facebookConnectService
    def googleCheckoutService

    def index = {
        redirect(action: 'list')
    }
    def emailNote = {
      Party party = Party.get(params?.partyId)
      String note = params?.note
      def emails;
      String role = params?.role;
      if(role.equals('coach')) emails = getClientEmails(party)
      if(role.equals('director')) emails = getCoachesEmails(party)
      emails.each { email ->
        asynchronousMailService.sendAsynchronousMail {
          from party?.email
          to email
          subject "A Note from your ${role} : ${party}"
          html g.render(template: '/user/note', model: [party: party, note: note])
        }
      }
      render "Emails sent to clients"
    }
    def getCoachesEmails = {party ->
      def coaches = []
      DirectorCoach.withSession {
        def now = new Date();
        coaches = DirectorCoach.findAllByFrumAndActiveFromLessThan(party.director, now)?.collect {it.to}
      }
      def emails = coaches.collect {
        it?.party?.email
      }
      emails
    }
    def getClientEmails = {party ->
      def clients = []
      CoachSubscriber.withSession {
        def now = new Date();
        clients = CoachSubscriber.findAllByFrumAndActiveFromLessThan(party.coach, now)?.collect {it.to}
      }
      def emails = clients.collect {
        it?.party?.email
      }
      emails
    }
    def delete = {
        Party party = Party.get(params.long('id'))
        if (party) {
            try {
                Boolean deletingCurrentUser = (party == UserTools.currentUser?.party)
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
            if (!party.isEnabled && (party == UserTools.currentUser.party)) {
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
        LoginCredential user = UserTools.currentUser
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
        LoginCredential user = UserTools.currentUser
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
        params.offset = params.offset ? params.int('offset') : 0
        String name = params.searchName
        def userList
        Integer total
        def criteriaClosure = {
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
            order('name')
            projections{
                distinct('id')
            }
        }
            def criteria1 = Party.createCriteria()
            criteriaClosure.delegate = criteria1
            def userIdsList = criteria1.listDistinct() {
                criteriaClosure()
                maxResults(params.max)
                firstResult(params.offset)
            }
            userList = userIdsList ? Party.getAll(userIdsList) : []
            def criteria2 = Party.createCriteria()
            criteriaClosure.delegate = criteria2
            total = criteria2.count() {
                criteriaClosure()
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
        UserCO userCO = new UserCO()
        if (coachId) {
            userCO.coachId = coachId
        }
        render(view: 'chooseSubscription', model: [availableProducts: ProductOffering.list(), userCO: userCO])

    }

    def clickBankPromotion = {
        Long clickBankFeaturedPlanId = ConfigurationHolder.config.grails.clickBank.featuredPlanId
        if(clickBankFeaturedPlanId && ProductOffering.exists(clickBankFeaturedPlanId)) {
            render(view: 'clickBankLink')
        } else {
            render(view: 'noClickBankPromotion')
        }
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
        Party currentUser = UserTools.currentUser?.party
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

        GoogleOrderStatus orderStatus = GoogleOrderStatus.findByOrderId(orderNumber)

        println "********************Order: " + transactionId
        println "********************Financial Order State: " + financialOrderState
        println "********************User: " + user

        response.contentType = 'text/xml'
        response.setStatus(200)
        if (userId) {
            if (user) {
                if (!orderStatus && (financialOrderState == FinancialState.REVIEWING.name)) {
                    orderStatus = new GoogleOrderStatus()
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

//    def newClickBankSubscription = {UserCO userCO ->
//        println "Inside action newClickBankSubscription"
////        Party currentParty = UserTools.currentUser?.party
////        if (currentParty) {
////            userCO = new UserCO(currentParty)
////                forward(action: 'createClickBankSubscription', controller: 'subscription', params: [userId: currentParty.id])
////        } else {
////            userCO.isEnabled = null
//
////            if (coachId) {
////                userCO.coachId = coachId.value
////            }
////            if (!userCO.hasErrors()) {
////                Party party = userCO.createParty()
////                println "Created PArty: " + party?.id
////                forward(action: 'createClickBankSubscription', controller: 'subscription', params: [userId: party.id])
////            } else {
////                userCO.errors.allErrors.each {
////                    println it
////                }
//                render(view: 'clickBankPromotion', model: [userCO: userCO])
////            }
////        }
//    }

    def newUserCheckout = {UserCO userCO ->
        Party currentParty = UserTools.currentUser?.party
        if (currentParty) {
            userCO = new UserCO(currentParty)
            if (params.productId) {
                forward(action: 'createSubscription', controller: 'subscription', params: [userId: currentParty.uniqueId, productId: params.productId])
            } else {
                forward(action: 'chooseSubscription', availableProducts: ProductOffering.list(), userCO: userCO)
            }
        } else {
            userCO.isEnabled = null
            List<Cookie> cookies = request.cookies as List
            Cookie coachId = cookies.find {it.name == 'coachId'}
            if (coachId) {
                userCO.coachId = coachId.value
            }
            if (!userCO.hasErrors()) {
                Party party = userCO.createParty()
                forward(action: 'createSubscription', controller: 'subscription', params: [userId: party.uniqueId, productId: params.productId])
            } else {
                userCO.errors.allErrors.each {
                    println it
                }
                render(view: 'chooseSubscription', model: [availableProducts: ProductOffering.list(), userCO: userCO, productId: params.productId])
            }
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
        Long directorId = UserTools.currentUser?.party?.director?.id
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

class UserCO {

    def asynchronousMailService
    static config = ConfigurationHolder.config

    String email
    String password
    String confirmPassword
    String name
    Integer mouthsToFeed
    String city
    String introduction
    Date joiningDate
    Long directorId
    Long coachDbId
    List<String> roles = []
    Boolean isEnabled
    Boolean showAlcoholicContent = false
    String coachId
    String uniqueId

    String id
    def selectUserImagePath

    UserCO() {

    }

    UserCO(Party party) {
        id = party?.id?.toString()
        if (party?.email) {
            email = party?.email
            password = party?.password
            confirmPassword = party?.password
        }
        name = party?.name
        if (party?.subscriber) {
            mouthsToFeed = party?.subscriber?.mouthsToFeed
            introduction = party?.subscriber?.introduction
            city = party?.subscriber?.city
            CoachSubscriber cs = CoachSubscriber.withCriteria(uniqueResult: true) {
                to {
                    eq('id', party.subscriber.id)
                }
            }
            if (cs){
              coachDbId = cs.frum?.id
              coachId = cs?.frum?.party?.uniqueId
            }
        }
        if (party?.coach) {
            Coach coach = party?.coach
            DirectorCoach dc = DirectorCoach.withCriteria(uniqueResult: true) {
                to {
                    eq('id', coach.id)
                }
            }
            directorId = dc?.frum?.id
            uniqueId = party.uniqueId
        }

        joiningDate = party?.joiningDate
        isEnabled = party?.isEnabled
        showAlcoholicContent = party?.showAlcoholicContent

        roles = party?.roleTypes*.name()

        if (party?.subscriber?.image) {
            int firstIndex = party?.subscriber?.image?.storedName?.indexOf('.')
            String name = party?.subscriber?.image?.storedName?.substring(0, firstIndex)
            selectUserImagePath = party?.subscriber?.image?.path + name + "_200.jpg"
        } else {
            selectUserImagePath = ''
        }
    }

    static constraints = {
        id(nullable: true)
        email(blank: false, nullable: false, email: true, validator: {val, obj ->
            if (val && !obj.id) {
                LoginCredential credential = UserLogin.findByEmail(val)
                if (credential && (credential?.id != obj?.id?.toLong())) {
                    return "userCO.email.unique.error"
                }
            }
        })
        password(nullable: false, blank: false, minSize: 4)
        confirmPassword(nullable: false, blank: false, validator: {val, obj ->
            obj.properties['password'] == val
        })
        name(nullable: false, blank: false, matches: /[a-zA-Z0-9\s\&]*/)
        mouthsToFeed(nullable: true, blank: true, matches: /[0-9]*/)
        introduction(nullable: true, blank: true)
        city(nullable: true, blank: true)
        isEnabled(nullable: true)
        roles(validator: {val, obj ->
            if ((val.size() < 1 || val.value.size() < 1)) {
                return 'userCO.blank.roles.error'
            }
        })
        coachId(nullable: true, blank: true)
        directorId(validator: {val, obj ->
            String coach = PartyRoleType.Coach.toString().replaceAll(" ", "")
            if (!val && (coach in obj.roles)) {
                return "userCO.coach.director.blank.error"
            }
        })
    }

    //TODO: Change this implementation

    public boolean assignRoles(Subscriber user) {
//        List<UserType> userRoles = []
//        roles?.each {String role ->
//            userRoles.add(UserType."${role}")
//        }
//        user?.roles = userRoles
        return true
    }

    public Party updateParty() {
        Party party
        Party.withTransaction {
            party = Party.get(id?.toLong())
            //Delete unchecked roles first
            deleteUncheckedRoles(party, roles)

            updateCheckedRoles(party, roles, coachId, showAlcoholicContent,
                    selectUserImagePath, introduction, mouthsToFeed, directorId, city, name)

            if (party.userLogin) {
                UserLogin login = party.userLogin
                login.email = email
                if (login.password != password) {
                    login.password = password.encodeAsBase64()
                }
                login.s()
            } else if (email) {
                new UserLogin(email: email, password: password.encodeAsBase64(), party: party).s()
            }

            party.isEnabled = isEnabled
        }
        return party
    }

    def updateCheckedRoles(party, roles, coachId, showAlcoholicContent,
                           selectUserImagePath, introduction, mouthsToFeed, directorId, city, name) {
        party.name = name
        if ((PartyRoleType.Subscriber.name() in roles)) {
            Subscriber subscriber = party.subscriber ? party.subscriber : new Subscriber()
            subscriber.city = city
            subscriber.mouthsToFeed = mouthsToFeed
            subscriber.introduction = introduction
            attachImage(subscriber, selectUserImagePath)
            subscriber.party = party
            subscriber.party.showAlcoholicContent = showAlcoholicContent
            subscriber.s()
            if (coachId) {
                Party coach = Party.findByUniqueId(coachId)
                if (coach) {
                    def cs = CoachSubscriber.withCriteria(uniqueResult: true) {
                        to {
                            eq('id', subscriber.id)
                        }
                        or {
                            isNull("activeTo")
                            gt("activeTo", new Date())
                        }
                    }
                    if (!cs) new CoachSubscriber(frum: coach.coach, to: subscriber).s()
                }
            }
            if (coachDbId) {
                Coach coach = Coach.get(coachDbId)
                if (coach) {
                    CoachSubscriber cs = CoachSubscriber.withCriteria(uniqueResult: true) {
                        to {
                            eq('id', subscriber.id)
                        }
                        or {
                            isNull("activeTo")
                            gt("activeTo", new Date())
                        }
                    }
                    if (!cs) new CoachSubscriber(frum: coach, to: subscriber).s()
                    else {
                      cs.frum = coach
                      cs.s()
                    }
                }
            }
        }

        if ((PartyRoleType.Admin.name() in roles) && !party.administrator) {
            new Administrator(party: party).s()
        }

        if ((PartyRoleType.Director.name() in roles) && !party.director) {
            new Director(party: party).s()
        }

        if ((PartyRoleType.Coach.name() in roles) && !party.coach) {
            Director director = Director.get(directorId)
            if (director) {
                Coach coach = Coach.findByParty(party)
                if (!coach) coach = new Coach(party: party).s()
                new DirectorCoach(frum: director, to: coach).s()
            }
        }
        if ((PartyRoleType.Coach.name() in roles) && party.coach) {
            Director director = Director.get(directorId)
            if (director && (directorId != party?.coach?.id)) {
                Coach coach = party?.coach
                DirectorCoach dc = DirectorCoach.findByTo(coach)
                if (!dc) dc = new DirectorCoach(frum: director, to: coach, activeFrom: new Date()).s()
            }
        }

        if (PartyRoleType.SuperAdmin.name() in roles && !party.superAdmin) {
            new SuperAdmin(party: party).s()
        }
    }

    private def deleteUncheckedRoles(Party party, List<String> roles) {
        Date now = new Date()
        if (party.subscriber && !(PartyRoleType.Subscriber.name() in roles)) {
            Subscriber subscriber = party.subscriber
            subscriber.activeTo = now
        }

        if (party.director && !(PartyRoleType.Director.name() in roles)) {
            Director director = party.director
            director.activeTo = now
            director.s();
        }

        if (party.coach && !(PartyRoleType.Coach.name() in roles)) {
            Coach coach = party.coach
            coach.activeTo = now
            coach.s()
        }

        if (party.superAdmin && !(PartyRoleType.SuperAdmin.name() in roles)) {
            SuperAdmin superAdmin = party.superAdmin
            superAdmin.activeTo = now
            superAdmin.s()
        }

        if (party.administrator && !(PartyRoleType.Admin.name() in roles)) {
            Administrator administrator = party.administrator
            administrator.activeTo = now
            administrator.s()
        }
    }

    public boolean attachImage(Subscriber user, def imagePath) {
        List<Integer> imageSizes = USER_IMAGE_SIZES
        return Image.updateOwnerImage(user, imagePath, imageSizes)
    }

    public Party createParty() {
        Party party
        Party.withTransaction {
            party = new Party(name: name)
            party.isEnabled = isEnabled
            LoginCredential loginCredential = new UserLogin(email: email, password: password.encodeAsBase64(), party: party)
            party.loginCredentials = [loginCredential] as Set
            party.s()

            if (PartyRoleType.Subscriber.name() in roles) {
                Subscriber subscriber = new Subscriber()
                subscriber.city = city
                subscriber.mouthsToFeed = mouthsToFeed
                subscriber.introduction = introduction
                subscriber.party = party
                subscriber.party.showAlcoholicContent = showAlcoholicContent
                attachImage(subscriber, selectUserImagePath)
                subscriber.s()
                if (coachId) {
                    Coach coach = Party.findByUniqueId(coachId)?.coach
                    if (coach) {
                        def cs = new CoachSubscriber(to: subscriber, frum: coach, commission: coach.defaultCommission).s()
                        assert cs
                    }
                }
            }
            if (PartyRoleType.Admin.name() in roles) {
                new Administrator(party: party).s()
            }
            if (PartyRoleType.SuperAdmin.name() in roles) {
                new SuperAdmin(party: party).s()
            }
            if (PartyRoleType.Director.name() in roles) {
                new Director(party: party).s()
            }
            if (PartyRoleType.Coach.name() in roles) {
                Coach coach = new Coach(party: party).s()
                assert coach.id
                Director director = Director.get(directorId)

                if (director) {
                    DirectorCoach dc = new DirectorCoach(frum: director, to: coach).s()
                    assert dc && dc.id
                }
            }
        }
        party = party.refresh()
        return party
    }

    public void enableAndLoginUser(Party party) {
        party?.isEnabled = true
        party?.s()
        HttpSession currentSession = ConfigurationHolder.config.sessions.find {it.userId == party.id}
        currentSession.userId = null
        currentSession.loggedUserId = party?.loginCredentials?.toList()?.first()?.id?.toString()
        party.lastLogin = new Date()
        party.s()
    }


}
