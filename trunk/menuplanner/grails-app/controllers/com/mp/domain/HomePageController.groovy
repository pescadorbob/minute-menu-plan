package com.mp.domain

class HomePageController {

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
}
