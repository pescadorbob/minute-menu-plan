package com.mp.domain.party

import com.mp.domain.PartyRoleType
import com.mp.domain.PartyRoleType

class SuperAdmin extends PartyRole{

    PartyRoleType type = PartyRoleType.SuperAdmin

    static transients = ['type']
    static mapping = {
        tablePerHierarchy false
    }

    static constraints = {
    }
}
