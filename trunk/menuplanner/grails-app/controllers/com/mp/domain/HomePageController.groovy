package com.mp.domain

import org.codehaus.groovy.grails.commons.ConfigurationHolder

class HomePageController {
    def static config = ConfigurationHolder.config

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

    def index = {
        redirect(action: "show")
    }

    def show = {
        HomePage homePage = HomePage.get(1)
        render(view: 'show', model: [homePage: homePage])
    }

    def edit = {
        HomePage homePage = HomePage.get(1)
        render(view: "edit", model: [homePage: homePage])
    }

    def save = {
        HomePage homePage = HomePage.get(1)
        homePage.properties = params.properties
        if (!homePage.hasErrors() && homePage.s()) {
            flash.message = "${message(code: 'homepage.updated.message')}"
            redirect(action: "show")
        }
        else {
            render(view: "edit", model: [homePage: homePage])
        }
    }

    def addImage = {
        render(view: "addImage")
    }

    def uploadImage = {
        byte[] fileContents = params?.file?.getBytes()
        HomePage homePage = HomePage.get(1)
        String filePath = config.tempDir
        String fileName = 'Img_' + System.currentTimeMillis()?.toString() + '.jpg'
        File file = new File(filePath)
        file.mkdirs()
        File actualFile = new File(file, fileName)
        actualFile.withOutputStream {out -> out.write fileContents }
        Image image = com.mp.domain.Image.addHomePageImage(homePage, actualFile.absolutePath)
        if (image) {
            render(view: "addImage", model: [image: image])
        } else {
            flash.message = "Unable to process,please upload again."
            render(view: "addImage")
        }
    }
}
