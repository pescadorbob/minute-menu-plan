package com.mp.domain.party

import com.mp.domain.party.Party
import com.mp.domain.party.PartyRole
import com.mp.domain.PartyRoleType

class Coach extends PartyRole {

    PartyRoleType type = PartyRoleType.Coach

    static transients = ['type']
    float defaultCommission = 0.20f // the default commission for coaching

    static constraints = {
      defaultCommission(nullable:true)
    }
    static mapping = {
        tablePerHierarchy false
    }

    String toString() {
        return party?.name
    }

    boolean equals(final Object o) {
        if (this.is(o)) return true;
        if (o == null) return false;
        if (!getClass().isAssignableFrom(o.getClass())) return false;
        final Party other = Party.class.cast(o);
        return id == null ? false : id.equals(other.id);
    }
}
