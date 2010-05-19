package com.mp.domain

public enum UserType {

    SuperAdmin("Super Admin"),
    Admin("Admin"),
    User("User")

    String name

    UserType(String name) {
        this.name = name
    }

    public static List<UserType> list(){
        return [SuperAdmin, Admin, User] 
    }

    String toString() {
        return name
    }

}