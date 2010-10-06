package com.mp.domain

import org.codehaus.groovy.grails.commons.ConfigurationHolder

class HomePage {
    static config = ConfigurationHolder.config

    String leftBar
    String centralText
    String categories

    static constraints = {
        leftBar(nullable: true, blank: true, maxSize: 5000)
        categories(nullable: true, blank: true, maxSize: 5000)
        centralText(nullable: true, blank: true, maxSize: 5000)
    }
    static transients = ['imageDir']

    String getImageDir() {
        return (config.homepageRootDir + this?.id + '/')
    }

}
