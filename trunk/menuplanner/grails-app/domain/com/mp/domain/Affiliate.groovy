package com.mp.domain

class Affiliate extends PartyRole {

    UserType type = UserType.Affiliate
    List<SubAffiliate> subAffiliates = []

    static hasMany = [subAffiliates: SubAffiliate]

    static transients = ['type']

    static mapping = {
        tablePerHierarchy false
    }

    static constraints = {
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
