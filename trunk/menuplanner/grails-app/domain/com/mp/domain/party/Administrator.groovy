package com.mp.domain.party

import com.mp.domain.PartyRoleType
import com.mp.domain.PartyRoleType

class Administrator extends PartyRole {

    PartyRoleType type = PartyRoleType.Admin

    static transients = ['type']
    static mapping = {
        tablePerHierarchy false
    }

    static constraints = {
    }
}
