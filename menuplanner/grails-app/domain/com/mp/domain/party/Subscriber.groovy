package com.mp.domain.party

import org.codehaus.groovy.grails.commons.ConfigurationHolder
import com.mp.domain.subscriptions.Subscription
import com.mp.domain.party.PartyRole
import com.mp.domain.PartyRoleType
import com.mp.domain.Image

class Subscriber extends PartyRole{

    static config = ConfigurationHolder.config

  static hasMany = [subscriptions: Subscription]
    PartyRoleType type = PartyRoleType.Subscriber

    Image image
    Integer mouthsToFeed
    String introduction
    String city

    static transients = ['imageDir', 'type', 'coach']

    Coach getCoach(){
        return (CoachSubscriber.findByTo(this)?.frum as Coach)
    }

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
    }

    static mapping = {
        tablePerHierarchy false
    }

}
