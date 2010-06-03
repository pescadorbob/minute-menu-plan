package com.mp.domain
import static com.mp.MenuConstants.*

class PermissionService {

    boolean transactional = true


        private Boolean isPermitted(Long level) {
            if (level == NOT_AUTHORIZED_PERMISSION_LEVEL) {
                return false
            }else if (level == UNRESTRICTED_ACCESS_PERMISSION_LEVEL) {
                return true
            }
            return false
        }

        public Boolean hasPermission(Permission permission) {
            User user = User.currentUser
            if(!user){
                return false
            }
            List<SecurityRole> roles = SecurityRole.findAllByNameInList(user.roles*.name)
            Boolean result = roles?.any {SecurityRole role ->
                PermissionLevel permissionLevel = PermissionLevel.findByPermissionAndRole(permission, role)
                if (!permissionLevel) {return false}
                Long level = permissionLevel.level
                return isPermitted(level)
            }
            return result
        }

}
