package com.mp.domain

class UserController {

    def userService

    def index = {
        render(view: 'create')
    }
    def create = {
        render(view: 'create')
    }
    def save = {UserCO userCO->
        if (userCO.validate()) {
            User user = userCO.convertToUser()
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
            render "Activated"
        } else {
            render "Invalid link/already active"
        }
    }
    def show={
        User user = User.get(params.id)
        render(view:'show', model:[user:user])
    }
}
