package com.mp.domain

import org.codehaus.groovy.grails.commons.ConfigurationHolder

class UserController {

    static config = ConfigurationHolder.config
    def userService
    def asynchronousMailService

    def index = {
        render(view: 'create')
    }
    def list = {
        params.max = Math.min(params.max ? params.int('max') : 10, 100)
        String name = params.searchName
        List<User> userList = (name) ? User.findAllByNameIlike("%${name}%", params): User.list(params)
        Integer total = (name) ? User.countByNameIlike("%${name}%") : User.count()
        render(view: 'list', model: [userList:userList, total:total])
    }


    def edit = {
        if (params.id) {
            User user = User.get(params.long('id'))
            UserCO userCO = new UserCO(user)
            render(view: 'edit', model: [userCO: userCO])
        }
    }
    def create = {
        render(view: 'create')
    }
    def update = {UserCO userCO ->
        if (userCO.validate()) {
            userCO.updateUser()
            redirect(action: 'show', id: userCO?.id)
        } else {
            println userCO.errors.allErrors.each {
                println it
            }
            render(view: 'edit', model: [userCO: userCO])
        }
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
                html g.render(template: '/user/accountVerification', model: [user: user, password: userCO.password, token: verificationToken.token])
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
        VerificationToken token = VerificationToken.findByToken(params?.token)
        if(token){
            token.user.status = AccountStatus.ACTIVE
            token.user.s()
            token.delete(flush: true)
            flash.message = g.message(code:'valid.User.Account.Verification')
        } else {
            flash.message = g.message(code:'invalid.User.Account.Verification')
        }
        render(view: 'verify')
    }
    def show={
        User user = User.get(params.id)
        render(view:'show', model:[user:user])
    }
    def uploadImage = {
        String relativePath = "/tempUser"
        def fileContents = params.Filedata.bytes
        String filePath = config.imagesRootDir + relativePath
        File file = new File(filePath)
        file.mkdirs()
        File actualFile = new File(file, 'Img_' + System.currentTimeMillis()?.toString() + '.' + params.Filename.tokenize('.').tail().join('.'))
        actualFile.withOutputStream {out ->
            out.write fileContents
        }
        render actualFile.absolutePath as String
    }
}
