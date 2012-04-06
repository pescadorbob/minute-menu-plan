package com.mp.domain.themes


class LandingPage {

    String name
    String description
    Date activeTo
    Date activeFrom
    String redirectURI
    String controllerName
    String actionName
    Date lastModified =new Date()

    static constraints = {
        redirectURI(nullable: true,blank: true)
        controllerName(nullable: true,blank: true)
        actionName(nullable: true,blank: true)
        name(unique: true, blank: false)
        lastModified(nullable: true, blank: true)
        activeTo(nullable: true, blank: true)
    }

}
