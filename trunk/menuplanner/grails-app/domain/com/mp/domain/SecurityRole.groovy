package com.mp.domain

class SecurityRole {
    Date dateCreated
    Date lastUpdated
    String name
    String description

    static hasMany = [permissionLevels: PermissionLevel]

    static constraints = {
        name(unique: true, blank: false)
        description(blank: false)
    }

    String toString(){
        name
    }

}

