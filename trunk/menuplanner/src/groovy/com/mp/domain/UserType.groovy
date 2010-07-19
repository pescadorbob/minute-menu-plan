package com.mp.domain

public enum UserType {

    SuperAdmin("Super Admin"),
    Admin("Admin"),
    Subscriber("Subscriber")

    String name

    UserType(String name) {
        this.name = name
    }

    public static List<UserType> list(){
        return [SuperAdmin, Admin, Subscriber]
    }

    String toString() {
        return name
    }

}