package com.mp.domain

import com.mp.domain.party.Party
import com.mp.domain.access.PermissionLevel
import com.mp.domain.access.SecurityRole

class PermissionTagLib {

    def permissionService
    static namespace = "permission"

    def hasPermission = {attrs, body ->
        Permission permission = attrs['permission']
        Recipe recipe = attrs['recipe']
        Party party = attrs['party']
        if (permission && permissionService.hasPermission(permission, recipe, party)) {
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
