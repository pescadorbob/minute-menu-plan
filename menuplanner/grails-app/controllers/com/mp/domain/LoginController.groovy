package com.mp.domain

class LoginController {

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
            println loginCO.password
            User user = User.findByEmailAndPassword(loginCO?.email, loginCO?.password?.encodeAsBase64())
            if (user) {
                session.loggedUserId = user.id.toString()
                flash.welcomeMessage = 'Welcome ' + user?.name + '!'
                redirect(controller: 'recipe', action: 'list', id: user?.id)
            } else {
                flash.message="The username or password you entered is incorrect."
                render(view: 'home', model: [loginCO: loginCO])
            }
        } else {
            if(!loginCO?.email && !loginCO?.password){
                flash.message = 'Username and Password can not be blank.'
            }else if(!loginCO?.email){
                flash.message = 'Provide Username.'
            }else if(!loginCO.password){
                flash.message = 'Provide Password.'
            }else{
                flash.message="The username or password you entered is incorrect."
            }
            render(view: 'home', model: [loginCO: loginCO])
        }
    }
}

class LoginCO {
    String email
    String password

    static constraints = {
        email(blank: false, nullable: false, email: true)
        password(nullable: false, blank: false, minSize: 4)
    }
}
