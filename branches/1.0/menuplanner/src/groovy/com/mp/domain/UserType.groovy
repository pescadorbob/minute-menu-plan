package com.mp.domain

public enum UserType {

    SuperAdmin("Super Admin"),
    Admin("Admin"),
    Subscriber("Subscriber"),
    Affiliate("Affiliate"),
    SubAffiliate("Sub Affiliate")

    String name

    UserType(String name) {
        this.name = name
    }

    public static List<UserType> list() {
        return [SuperAdmin, Admin, Subscriber, Affiliate, SubAffiliate]
    }

    String toString() {
        return name
    }
}