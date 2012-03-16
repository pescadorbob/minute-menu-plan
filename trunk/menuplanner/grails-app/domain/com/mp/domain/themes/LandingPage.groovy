package com.mp.domain.themes


class LandingPage {

    String name
    Date activeTo
    Date activeFrom
    String redirectURI
    Date lastModified =new Date()

    static constraints = {
        redirectURI(nullable: false,blank: false)
        name(unique: true, blank: false)
        lastModified(nullable: true, blank: true)
        activeTo(nullable: true, blank: true)
    }

}
