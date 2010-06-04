package com.mp.domain

class LoginController {

    def asynchronousMailService
    def index = {
        flash.message = ""
        if (User.currentUser) {
            redirect(controller: 'recipe', action: 'list')
        } else {
            render(view: 'home')
        }
    }

    def forgotPassword = {
        render(view: 'forgotPassword', params:[email:params.email])
    }

    def resetPassword = {
        User user = User.findByEmail(params.email)
        if (user) {
            String newPassword = UUID?.randomUUID()?.toString()?.split('-')?.getAt(0)
            user.password = newPassword.encodeAsBase64()
            user.s()
            asynchronousMailService.sendAsynchronousMail {
                to user?.email
                subject "Your password for http://qa.menuplanner.intelligrape.net/ has been reset."
                html g.render(template: '/login/passwordChangedNotification', model: [user: user, password: newPassword])
            }
            flash.message = message(code: 'login.password.change.sucessfull')
            render(view: 'forgotPassword', model:[passwordChanged:true])
        } else {
            flash.message = message(code: 'login.password.change.unsucessfull')
            render(view: 'forgotPassword', model:[passwordChanged:false])
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
                    if (params.targetUri?.size()) {
                        redirect(uri: params.targetUri)
                        params.remove('targetUri')
                    } else {
                        redirect(controller: 'login', action: 'index')
                    }
                } else {
                    flash.message = message(code: 'loginCO.user.disabled')
                    render(view: 'home', model: [loginCO: loginCO])
                }
            } else {
                flash.message = message(code: 'loginCO.email.password.Invalid')
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
