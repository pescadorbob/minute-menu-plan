package com.mp.domain

class Aisle {

    String name
    List<Party> owners = []

    static hasMany = [owners: Party]

    String toString() {
        return name
    }

    static constraints = {
        name(unique: true)
    }

    public static List<Aisle> getAislesForCurrentUser() {
        List<Aisle> aisles = Aisle.list().findAll {(!it.owners) || (LoginCredential.currentUser?.party?.id in it.owners*.id)}
        return aisles.sort {it.name}
    }
}
