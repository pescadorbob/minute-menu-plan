package com.mp.domain

import org.codehaus.groovy.grails.commons.ConfigurationHolder

class UserController {

    static config = ConfigurationHolder.config
    def userService
    def asynchronousMailService

    def index = {
        redirect(action: 'list')
    }

    def changeStatus = {
            render "" + userService.changeStatus(params?.id?.toLong())
    }

    def list = {


        params.max = Math.min(params.max ? params.int('max') : 10, 100)
        String name = params.searchName

        def userList
        Integer total
        if (name || params.hideEnabled || params.hideDisabled) {
            userList = User.createCriteria().list(max: params.max, offset: 0) {
                if(name){
                    ilike('name', "%${name}%")
                }
                if(params.hideEnabled){
                    ne('isEnabled',true)
                }
                if(params.hideDisabled){
                    ne('isEnabled',false)
                }
            }
            total = userList.getTotalCount()
        } else {
            userList = User.list(params)
            total= User.count()
        }

        render(view: 'list', model: [userList: userList, total: total, searchName: name, hideEnabled:params.hideEnabled, hideDisabled:params.hideDisabled])
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
    def save = {UserCO userCO ->
        if (userCO.validate()) {
            User user = userCO.convertToUser()
            
//            asynchronousMailService.sendAsynchronousMail {
//                to user?.email
//                subject "Email verification for Minute Menu Plan"
//                html g.render(template: '/user/accountVerification', model: [user: user, password: userCO.password])
//            }

            redirect(action: 'show', id: user?.id)
        } else {
            println userCO.errors.allErrors.each {
                println it
            }
            render(view: 'create', model: [userCO: userCO])
        }
    }

    def show = {
        User user = User.get(params.id)
        render(view: 'show', model: [user: user])
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
