package com.mp.domain.access

import com.mp.domain.Permission
import com.mp.domain.access.SecurityRole

class PermissionLevel {
    Permission permission
    Long level

    static belongsTo = [role: SecurityRole]

    String toString(){
        return "${role} : ${permission} : ${level}"
    }

    static constraints = {
    }
}