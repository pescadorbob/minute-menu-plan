package com.mp.domain

public enum UserType {

    Admin("Admin"),
    User("User")

    String name

    UserType(String name) {
        this.name = name
    }

    String toString() {
        return name
    }

}