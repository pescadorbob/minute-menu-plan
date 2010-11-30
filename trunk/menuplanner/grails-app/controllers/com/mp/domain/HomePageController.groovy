package com.mp.domain

import org.codehaus.groovy.grails.commons.ConfigurationHolder
import org.springframework.web.multipart.commons.CommonsMultipartFile
import com.mp.domain.themes.HomePage
import com.mp.tools.ThemeTools

class HomePageController {
    def static config = ConfigurationHolder.config

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

    def index = {
        redirect(action: "list")
    }
    def list = {
        params.max = Math.min(params.max ? params.int('max') : 10, 100)
        render(view: 'list', model: [homePageList: HomePage.list(params), total: HomePage.count()])
    }

    def create = {
        HomePage homePage = new HomePage()
        homePage.properties = ThemeTools.getActivePage().properties
        render(view: 'create', model: [homePage: homePage])
    }

    def show = {
        HomePage homePage = HomePage.get(params.long('id'))
        render(view: 'show', model: [homePage: homePage])
    }

    def edit = {
        HomePage homePage = HomePage.get(params.long('id'))
        render(view: "edit", model: [homePage: homePage])
    }

    def save = {
        HomePage homePage = new HomePage(params)
        homePage.activeFrom = new Date(params?.activeFrom_value)
        homePage.activeTo = new Date(params?.activeTo_value)
        if (!homePage.hasErrors() && homePage.s()) {
            flash.message = "${message(code: 'homepage.save.successful')}"
            redirect(action: "show", id: homePage.id)
        }
        else {
            render(view: "create", model: [homePage: homePage])
        }
    }

    def update = {
        HomePage homePage = HomePage.get(params.long('id'))
        if (homePage) {
            if (params.version) {
                def version = params.version.toLong()
                if (homePage.version > version) {

                    homePage.errors.rejectValue("version", "default.optimistic.locking.failure", [message(code: 'homepage.label', default: 'Homepage')] as Object[], "Another user has updated this Homepage while you were editing")
                    render(view: "edit", model: [homePage: homePage])
                    return
                }
            }
            homePage.properties = params?.properties
            homePage.activeFrom = new Date(params?.activeFrom_value)
            homePage.activeTo = new Date(params?.activeTo_value)
            homePage.lastModified=new Date()
            if (!homePage.hasErrors() && homePage.s()) {
                flash.message = message(code: 'homepage.updated.successfully')
                redirect(action: "show", id: homePage.id)
            }
            else {
                render(view: "edit", model: [homePage: homePage])
            }
        }
        else {
            flash.message = message(code: 'homepage.not.found')
            redirect(action: "list")
        }
    }

    def delete = {
        HomePage homePage = HomePage.get(params.id)
        if (homePage) {
            try {
                homePage.delete(flush: true)
                flash.message = message(code: 'homepage.deleted.successfully')
                redirect(action: "list")
            }
            catch (org.springframework.dao.DataIntegrityViolationException e) {
                flash.message =message(code: 'homepage.cannot.deleted')
                redirect(action: "show", id: params.id)
            }
        }
        else {
            flash.message = message(code: 'homepage.not.found')
            redirect(action: "list")
        }
    }


    def addImage = {
        render(view: "addImage")
    }

    def uploadImage = {
        CommonsMultipartFile multipartFile = params?.file
        if (multipartFile) {
            byte[] fileContents = multipartFile?.getBytes()
            String fileName = multipartFile?.getOriginalFilename()
            Image image = Image.uploadHomePageImage(fileContents, fileName)
            if (image) {
                render(view: "addImage", model: [image: image])
            } else {
                flash.message = message(code: 'upload.file.failure')
                render(view: "addImage")
            }
        }
    }
}
