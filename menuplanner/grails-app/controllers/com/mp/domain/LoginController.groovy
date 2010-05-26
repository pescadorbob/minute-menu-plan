package com.mp.domain

class LoginController {

    def filter = {
        verifyUserIsLoggedIn(controller: '*', action: '*') {
            before = {
                println "Application Access Log: ${new Date()} : ${params}"
                if ((!session.loggedUserId) && !(params.controller in ['auth', 'util', 'crs'])) {
                    if (!params.targetUri) {
                        String targetUri = request.forwardURI.toString() - request.contextPath.toString()
                        if (!(targetUri == null || targetUri == "/")) {
                            params.targetUri = targetUri
                        }
                    }
                    redirect(controller: 'auth', action: 'index', params: params)
                    return false
                }
            }
        }
    }

    def index = {
        if (session.loggedUserId) {
            redirect(controller: 'recipe', action: 'list')
        } else {
            render(view: 'home')
        }
    }

    def logout = {
        session.loggedUserId = null
        redirect(controller: 'login', action: 'index')
    }
    def login = {LoginCO loginCO ->
        if (loginCO.validate()) {
            User user = User.findByEmailAndPassword(loginCO?.email, loginCO?.password?.encodeAsBase64())
            if (user) {
                if (user.isEnabled) {
                    session.loggedUserId = user.id.toString()
                    redirect(controller: 'recipe', action: 'list')
                } else {
                    flash.message = "Your account has been disabled. Please contact System Admin"
                    render(view: 'home', model: [loginCO: loginCO])
                }
            } else {
                flash.message = "The username or password you entered is incorrect."
                render(view: 'home', model: [loginCO: loginCO])
            }
        } else {
            render(view: 'home', model: [loginCO: loginCO])
        }
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
