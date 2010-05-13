package com.mp.domain

class UserController {

    def userService
    def asynchronousMailService

    def index = {
        render(view: 'create')
    }
    def create = {
        render(view: 'create')
    }
    def save = {UserCO userCO->
        if (userCO.validate()) {
            User user = userCO.convertToUser()
            VerificationToken verificationToken = new VerificationToken()
            verificationToken.user = user
            verificationToken.s()

            asynchronousMailService.sendAsynchronousMail {
                to user?.email
                subject "Email verification for Minute Menu Plan"
                html g.render(template: '/user/accountVerification', model: [user: user, token: verificationToken.token])
            }

            redirect(action: 'show', id: user?.id)
        } else {
            println userCO.errors.allErrors.each {
                println it
            }
            render(view: 'create', model: [userCO: userCO])
        }
    }

    def verify = {
        VerificationToken token = VerificationToken.findByToken(params.token)
        if(token){
            token.user.status = AccountStatus.ACTIVE
            token.user.s()
            token.delete(flush: true)
            render "Thanks for confirming your email address. Your account is now active"
        } else {
            render "You have either opened an invalid link or your account has already been actived"
        }
    }
    def show={
        User user = User.get(params.id)
        render(view:'show', model:[user:user])
    }
}
