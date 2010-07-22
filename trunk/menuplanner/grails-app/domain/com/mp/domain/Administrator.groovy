package com.mp.domain

class Administrator extends PartyRole {

    UserType type = UserType.Admin

    static transients = ['type']
    static mapping = {
        tablePerHierarchy false
    }

    static constraints = {
    }
}
