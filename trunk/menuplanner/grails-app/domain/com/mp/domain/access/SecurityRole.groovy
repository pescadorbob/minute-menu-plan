package com.mp.domain.access

import com.mp.domain.access.PermissionLevel

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

