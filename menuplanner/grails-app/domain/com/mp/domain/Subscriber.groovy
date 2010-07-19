package com.mp.domain

import org.codehaus.groovy.grails.commons.ConfigurationHolder

class Subscriber extends PartyRole{

    static config = ConfigurationHolder.config

    UserType userType = UserType.Subscriber

    String screenName
    Image image
    Integer mouthsToFeed
    String introduction
    String city

    static transients = ['imageDir', 'userType']

    void deleteImage(){
        Image image = this?.image
        this.image = null
        image?.delete(flush: true)
    }

    String getImageDir(){
        return (config.imagesRootDir + config.usersRootDir + this?.id + '/')
    }

    String toString() {
        return screenName
    }

    def beforeInsert = {
        mouthsToFeed = mouthsToFeed ? mouthsToFeed : 1
    }

    static constraints = {
        image(nullable: true, blank: true)
        city(nullable: true, blank: true)
        mouthsToFeed(nullable: true, blank: true)
        introduction(nullable: true, maxSize: 1000)
    }

    static mapping = {
        tablePerHierarchy false
    }

}
