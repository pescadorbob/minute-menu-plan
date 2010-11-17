package com.mp.domain.themes

import org.codehaus.groovy.grails.commons.ConfigurationHolder

class HomePage {
    static config = ConfigurationHolder.config

    String name
    String leftBar
    String centralText
    String categories
    Date activeTo
    Date activeFrom
    Date lastModified =new Date()

    static constraints = {
        name(unique: true, blank: false)
        leftBar(nullable: true, blank: true, maxSize: 5000)
        categories(nullable: true, blank: true, maxSize: 5000)
        centralText(nullable: true, blank: true, maxSize: 5000)
        lastModified(nullable: true, blank: true)
        activeTo(nullable: true, blank: true)
    }
    static transients = ['imageDir', 'activePage']

    String getImageDir() {
        return (config.homepageRootDir + this?.id + '/')
    }

    static HomePage getActivePage() {
        HomePage homePage = HomePage.list().find {
            (it.activeFrom <= new Date()) && (!it.activeTo || (it.activeTo > new Date()))
        }
        return homePage
    }
}
