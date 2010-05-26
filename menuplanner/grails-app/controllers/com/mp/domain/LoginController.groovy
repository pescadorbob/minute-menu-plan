package com.mp.domain

class LoginController {

    def index = {
        println ">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>> " + params
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
                    if(params.targetUri?.size()){
                        redirect(uri:params.targetUri)
                        params.remove('targetUri')
                    }else{
                        redirect(controller: 'login', action: 'index')                        
                    }
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
