package com.mp.domain

class ProfileController {

    def manageProfileService

    def index = {
        render(view: 'create')
    }
    def create = {
        render(view: 'create')
    }
    def save = {ProfileCO profileCO->
        if (profileCO.validate()) {
            User user = profileCO.convertToUser()
            redirect(action: 'show', id: user?.id)
        } else {
            println profileCO.errors.allErrors.each {
                println it
            }
            render(view: 'create', model: [profileCO: profileCO])
        }
    }
    def show={
        User user = User.get(params.id)
        render(view:'show', model:[user:user])
    }
}
