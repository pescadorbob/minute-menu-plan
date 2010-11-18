package com.mp.domain

class Aisle {

    String name
    Boolean ownedByUser = false

    String toString() {
        return name
    }

    static constraints = {
        name(unique: true, blank: false, nullable: false)
    }

    public static List<Aisle> getAislesForCurrentUser() {
        Party party = LoginCredential.currentUser?.party
        List<Aisle> aislesByUser = getAislesForUser(party)
        return aislesByUser
    }

    public static List<Aisle> getAislesForUser(Party party) {
        List<Aisle> aisles = Aisle.list().findAll {(!it.ownedByUser)} as List
        List<Aisle> aislesByUser = party?.aisles
        aislesByUser.each {Aisle aisle ->
            aisles.add(aisle)
        }
        return aisles.sort {it.name}
    }
}
