package com.mp.domain

import org.codehaus.groovy.grails.commons.ConfigurationHolder

class Subscriber extends PartyRole{

    static config = ConfigurationHolder.config

    UserType type = UserType.Subscriber

    Image image
    Integer mouthsToFeed
    String introduction
    String city
    Party coach

    static transients = ['imageDir', 'type']

    void deleteImage(){
        Image image = this?.image
        this.image = null
        image?.delete(flush: true)
    }

    String getImageDir(){
        return (config.imagesRootDir + config.usersRootDir + this?.id + '/')
    }

    String toString() {
        return party.name
    }

    def beforeInsert = {
        mouthsToFeed = mouthsToFeed ? mouthsToFeed : 1
    }

    static constraints = {
        image(nullable: true, blank: true)
        city(nullable: true, blank: true)
        mouthsToFeed(nullable: true, blank: true)
        introduction(nullable: true, blank: true, maxSize: 1000)
        coach(nullable: true, blank: true)
    }

    static mapping = {
        tablePerHierarchy false
    }

}
