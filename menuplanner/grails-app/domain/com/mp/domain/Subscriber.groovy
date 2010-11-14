package com.mp.domain

import org.codehaus.groovy.grails.commons.ConfigurationHolder
import com.mp.domain.subscriptions.Subscription

class Subscriber extends PartyRole{

    static config = ConfigurationHolder.config

  List<Subscription> subscriptions = []

  static hasMany = [subscriptions: Subscription]
    UserType type = UserType.Subscriber

    Image image
    Integer mouthsToFeed
    String introduction
    String city
    String coachId

    static transients = ['imageDir', 'type']

    void deleteImage(){
        Image image = this?.image
        this.image = null
        image?.delete(flush: true)
    }

    String getImageDir(){
        return (config.usersRootDir + this?.id + '/')
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
        coachId(nullable: true, blank: false)
    }

    static mapping = {
        tablePerHierarchy false
    }

}
