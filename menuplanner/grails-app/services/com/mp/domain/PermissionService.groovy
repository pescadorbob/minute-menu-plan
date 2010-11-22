package com.mp.domain

import static com.mp.MenuConstants.*
import com.mp.domain.party.Party

class PermissionService {

    boolean transactional = true


    private Boolean isPermitted(Long level, Recipe recipe, Party party) {
        if (level == UNRESTRICTED_ACCESS_PERMISSION_LEVEL) {
            return true
        } else if ((level % ACCESS_IF_OWNS_RECIPE_PERMISSION_LEVEL) == 0) {
            return validateOwnsRecipe(recipe)
        } else if ((level % ACCESS_IF_OWNS_USER_PERMISSION_LEVEL) == 0) {
            return validateOwnsUser(party)
        }
        return false
    }

    private Boolean validateOwnsRecipe(Recipe recipe) {
        LoginCredential user = LoginCredential.currentUser
        return (user && (recipe in user?.party?.contributions))
    }

    private Boolean validateOwnsUser(Party party) {
        LoginCredential user = LoginCredential.currentUser
        return (user && (user.party == party))
    }

    public Boolean hasPermission(Permission permission, Recipe recipe = null, Party party = null) {
        LoginCredential user = LoginCredential.currentUser
        if (!user) {
            return false
        }
        List<SecurityRole> roles = SecurityRole.findAllByNameInList(user.party.roles*.type.name)
        Boolean result = roles?.any {SecurityRole role ->
            PermissionLevel permissionLevel = PermissionLevel.findByPermissionAndRole(permission, role)
            if (!permissionLevel) {return false}
            Long level = permissionLevel.level
            return isPermitted(level, recipe, party)
        }
        return result
    }

}
