package com.mp.domain

class SuperAdmin extends PartyRole{

    UserType type = UserType.SuperAdmin

    static transients = ['type']
    static mapping = {
        tablePerHierarchy false
    }

    static constraints = {
    }
}
