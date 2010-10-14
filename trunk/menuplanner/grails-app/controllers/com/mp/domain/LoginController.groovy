package com.mp.domain

import org.springframework.web.context.request.RequestContextHolder

class LoginController {

    def asynchronousMailService

    def index = {
        flash.message = ""
        if (LoginCredential.currentUser) {
            redirect(controller: 'recipe', action: 'list')
        } else {
            render(view: 'home', model: [homePage: HomePage.activePage, testimonials: Testimonial.findAllByShowOnHomepage(true)])
        }
    }

    def forgotPassword = {
        render(view: 'forgotPassword', params: [email: params.email])
    }

    def resetPassword = {
        LoginCredential loginCredential = UserLogin.findByEmail(params.email)
        if (loginCredential) {
            String newPassword = UUID?.randomUUID()?.toString()?.split('-')?.getAt(0)
            loginCredential.password = newPassword.encodeAsBase64()
            loginCredential.s()
            asynchronousMailService.sendAsynchronousMail {
                to loginCredential?.email
                subject "Your password for http://qa.menuplanner.intelligrape.net/ has been reset."
                html g.render(template: '/login/passwordChangedNotification', model: [loginCredential: loginCredential, password: newPassword])
            }
            flash.message = message(code: 'login.password.change.sucessfull')
            render(view: 'forgotPassword', model: [passwordChanged: true])
        } else {
            flash.message = message(code: 'login.password.change.unsucessfull')
            render(view: 'forgotPassword', model: [passwordChanged: false])
        }
    }

    def logout = {
        session.invalidate()
        redirect(controller: 'login', action: 'index', params: [fbLogout: true])
    }

    def login = {LoginCO loginCO ->
        if (loginCO.validate()) {
            LoginCredential loginCredential = UserLogin.findByEmailAndPassword(loginCO?.email, loginCO?.password?.encodeAsBase64())
            if (loginCredential) {
                if (loginCredential.party.isEnabled) {
                    session.loggedUserId = loginCredential?.party?.id?.toString()
                    loginCredential?.party?.lastLogin = new Date()
                    loginCredential?.party?.s()
                    if (params.targetUri?.size()) {
                        redirect(uri: params.targetUri)
                        params.remove('targetUri')
                    } else {
                        redirect(controller: 'login', action: 'index')
                    }
                } else {
                    flash.message = message(code: 'loginCO.user.disabled')
                    render(view: 'home', model: [loginCO: loginCO, homePage: HomePage.activePage, testimonials: Testimonial.findAllByShowOnHomepage(true)])
                }
            } else {
                flash.message = message(code: 'loginCO.email.password.Invalid')
                render(view: 'home', model: [loginCO: loginCO, homePage: HomePage.activePage, testimonials: Testimonial.findAllByShowOnHomepage(true)])
            }
        } else {
            render(view: 'home', model: [loginCO: loginCO, homePage: HomePage.activePage, testimonials: Testimonial.findAllByShowOnHomepage(true)])
        }
    }

    def isFacebookConnected = {
        if (request.method == "POST") {
            if (params.facebookUid) {
                LoginCredential loginCredential = FacebookAccount.findByUid(params.long("facebookUid"))
                if (loginCredential) {
                    if (loginCredential.party.isEnabled) {
                        SessionUtils.session.loggedUserId = loginCredential?.party?.id
                        loginCredential.party.lastLogin = new Date()
                        loginCredential.party.s()
                        render "true"
                        return
                    }
                }
            }
        }
        render "false"
    }
}

class LoginCO {
    String email
    String password

    static constraints = {
        email(blank: false, nullable: false, email: true)
        password(nullable: false, blank: false)
    }
}
