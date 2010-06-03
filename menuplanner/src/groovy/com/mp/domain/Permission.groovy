package com.mp.domain

enum Permission {

    CREATE_RECIPE('CREATE_RECIPE'),
    UPDATE_RECIPE('UPDATE_RECIPE'),
    DELETE_RECIPE('DELETE_RECIPE'),

    CREATE_SECURITY_ROLE('CREATE_SECURITY_ROLE'),
    READ_SECURITY_ROLE('READ_SECURITY_ROLE'),
    UPDATE_SECURITY_ROLE('UPDATE_SECURITY_ROLE'),
    DELETE_SECURITY_ROLE('DELETE_SECURITY_ROLE'),

    UPDATE_USER_ROLES('UPDATE_USER_ROLES')

    private final String name

    Permission(String name) {
        this.name = name
    }

    public String toString() {
        return name
    }

}
