package com.mp.domain

import static com.mp.MenuConstants.*

class PermissionService {

    boolean transactional = true


    private Boolean isPermitted(Long level, Recipe recipe) {
        if (level == UNRESTRICTED_ACCESS_PERMISSION_LEVEL) {
            return true
        } else if (level == ACCESS_IF_OWNS_RECIPE_PERMISSION_LEVEL) {
            return validateOwnsRecipe(recipe)
        }
        return false
    }

    private Boolean validateOwnsRecipe(Recipe recipe) {
        LoginCredential user = LoginCredential.currentUser
        return (user && (recipe in user?.party?.contributions))
    }

    public Boolean hasPermission(Permission permission, Recipe recipe = null) {
        LoginCredential user = LoginCredential.currentUser
        if (!user) {
            return false
        }
        List<SecurityRole> roles = SecurityRole.findAllByNameInList(user.party.roles*.userType.name)
        Boolean result = roles?.any {SecurityRole role ->
            PermissionLevel permissionLevel = PermissionLevel.findByPermissionAndRole(permission, role)
            if (!permissionLevel) {return false}
            Long level = permissionLevel.level
            return isPermitted(level, recipe)
        }
        return result
    }

}
