package com.mp.domain

class SubAffiliate extends PartyRole {

    UserType type = UserType.SubAffiliate

    static transients = ['type']


    static belongsTo = [affiliate: Affiliate]
    static constraints = {
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
