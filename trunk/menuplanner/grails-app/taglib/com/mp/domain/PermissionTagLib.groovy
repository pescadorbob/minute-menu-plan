package com.mp.domain

class PermissionTagLib {

    def permissionService
    static namespace = "permission"

    def hasPermission = {attrs, body ->
        Permission permission = attrs['permission']
        if (permission && permissionService.hasPermission(permission)) {
            out << true
        }
    }

    def isPermissionChecked = {attrs ->
        SecurityRole role = attrs['role']
        String permission = attrs['permission']
        Long value = attrs['value']

        PermissionLevel permissionLevel = (role?.permissionLevels?.find {it?.permission?.name == permission})
        Boolean isChecked = (permissionLevel && ((permissionLevel?.level % value) == 0))
        if (isChecked) {
            out << true
        }
    }


}
