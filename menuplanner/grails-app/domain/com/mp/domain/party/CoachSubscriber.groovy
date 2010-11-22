package com.mp.domain.party

class CoachSubscriber extends PartyRoleRelationship {

  float commission // the commission this coach receives for this 'coaching' this subscriber
    static constraints = {
    }

    static mapping = {
        tablePerHierarchy false
    }

}
