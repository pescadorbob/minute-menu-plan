package com.mp.domain

enum Permission {

    CREATE_RECIPE('CREATE_RECIPE'),
    UPDATE_RECIPE('UPDATE_RECIPE'),
    DELETE_RECIPE('DELETE_RECIPE'),

    CREATE_SECURITY_ROLE('CREATE_SECURITY_ROLE'),
    READ_SECURITY_ROLE('READ_SECURITY_ROLE'),
    UPDATE_SECURITY_ROLE('UPDATE_SECURITY_ROLE'),
    DELETE_SECURITY_ROLE('DELETE_SECURITY_ROLE'),

    UPDATE_USER_ROLES('UPDATE_USER_ROLES'),
    LIST_USERS('LIST_USERS'),
    UPDATE_USERS('UPDATE_USERS'),
    DELETE_USERS('DELETE_USERS'),

    ENABLE_DISABLE_USER('ENABLE_DISABLE_USER'),

    MANAGE_SUPER_ADMIN('MANAGE_SUPER_ADMIN'),
    MANAGE_ADMIN('MANAGE_ADMIN'),
    MANAGE_SUBSCRIBER('MANAGE_SUBSCRIBER'),
    MANAGE_AFFILIATE('MANAGE_AFFILIATE'),
    MANAGE_SUB_AFFILIATE('MANAGE_SUB_AFFILIATE'),

    MANAGE_OWN_SUBSCRIBER('MANAGE_OWN_SUBSCRIBER'),
    MANAGE_OWN_SUB_AFFILIATE('MANAGE_OWN_SUB_AFFILIATE'),

    MANAGE_HOME_PAGE('MANAGE_HOME_PAGE'),
    MANAGE_TESTIMONIAL('MANAGE_TESTIMONIAL'),

    CAN_ASSIGN_SUPER_ADMIN_ROLE('CAN_ASSIGN_SUPER_ADMIN_ROLE'),
    CAN_ASSIGN_ADMIN_ROLE('CAN_ASSIGN_ADMIN_ROLE'),
    CAN_ASSIGN_SUBSCRIBER_ROLE('CAN_ASSIGN_SUBSCRIBER_ROLE'),
    CAN_ASSIGN_AFFILIATE_ROLE('CAN_ASSIGN_AFFILIATE_ROLE'),
    CAN_ASSIGN_SUB_AFFILIATE_ROLE('CAN_ASSIGN_SUB_AFFILIATE_ROLE'),

    CAN_VIEW_INVITATION_URL('CAN_VIEW_INVITATION_URL'),
    CAN_VIEW_SUB_AFFILIATES('CAN_VIEW_SUB_AFFILIATES'),
    CAN_VIEW_CLIENTS('CAN_VIEW_CLIENTS'),

    REMOVE_RECIPE_FROM_FAVOURITES('REMOVE_RECIPE_FROM_FAVOURITES'),
    REMOVE_RECIPE_ABUSE('REMOVE_RECIPE_ABUSE'),
    REMOVE_COMMENT_ABUSE('REMOVE_COMMENT_ABUSE')


    final String name

    Permission(String name) {
        this.name = name
    }

    public String toString() {
        return name
    }

}