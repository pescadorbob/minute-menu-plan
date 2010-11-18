package com.mp.domain

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