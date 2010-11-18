package com.mp.domain

class SecurityRoleController {

    def index = {
        redirect(action: list)
    }

    def show = {
        SecurityRole role = SecurityRole.get(params.long("id"))
        if (role) {
            render(view: 'show', model: [role: role])
        } else {
            flash.message = "No such role found"
            redirect(action: list)
        }
    }

    def list = {
        List<SecurityRole> roles = SecurityRole.list()
        render(view: 'list', model: [roles: roles])
    }

    def create = {
        List<SecurityRole> roles = SecurityRole.list()
        SecurityRole role = new SecurityRole()
        render(view: 'create', model: [roles: roles, role: role])
    }

    def save = {
        SecurityRole securityRole = new SecurityRole()
        securityRole.name = params.name
        securityRole.description = params.description
        if (!securityRole.hasErrors() && securityRole.s()) {
            params?.each {key, value ->
                Permission permission = Permission.values().find {it.name() == key}
                if (permission) {
                    Long permissionLevel = 1L
                    value.each {permissionLevel *= it?.toLong()}
                    new PermissionLevel(role: securityRole, permission: permission, level: permissionLevel).s()
                }
            }
            redirect(action: show, id: securityRole.id)
        }
        else {
            List<SecurityRole> roles = SecurityRole.list()
            flash.message = "Please enter values in the required fileds"
            render(view: 'create', model: [roles: roles, role: securityRole])
        }
    }

    def edit = {
        List<SecurityRole> roles = SecurityRole.list()
        SecurityRole role = SecurityRole.get(params.long('id'))
        if (role) {
            render(view: 'edit', model: [role: role, roles: roles])
        } else {
            flash.message = "No such role found"
            redirect(action: list)
        }
    }

    def update = {
        SecurityRole securityRole = SecurityRole.get(params.long("id"))
        if (securityRole) {
            securityRole.name = params.name
            securityRole.description = params.description
            securityRole.s()
            if (securityRole.permissionLevels) {
                Set<PermissionLevel> permissionLevelsToRemove = securityRole.permissionLevels
                securityRole.permissionLevels = []
                permissionLevelsToRemove?.each {it.delete(flush: true)}
            }
            params?.each {key, value ->
                Permission permission = Permission.values().find {it.name() == key}
                if (permission) {
                    Long permissionLevel = 1L
                    value.each {permissionLevel *= it?.toLong()}
                    new PermissionLevel(role: securityRole, permission: permission, level: permissionLevel).s()
                }
            }

            redirect(action: show, id: securityRole.id)
        } else {
            flash.message = "No such role found"
            redirect(action: list)
        }
    }

    def delete = {
        SecurityRole securityRole = SecurityRole.get(params.long("id"))
        if (securityRole) {
            String name = securityRole.name
            securityRole.delete(flush: true)
            flash.message = "Role ${name} deleted"
            redirect(action: list)
        } else {
            flash.message = "No such role found"
            redirect(action: list)
        }
    }
    def getPermissionsForSecurityRole = {
        SecurityRole securityRole = SecurityRole.get(params.long("roleId"))
        render(template: 'permissions', model: [role: securityRole])
    }
}

class SecurityRoleVO {
    String name
    String description
    Set<PermissionVO> permissions = []
}

class PermissionVO {
    Permission permission
    Long value
}
