package com.mp.domain

public enum UserType {

    SuperAdmin("Super Admin"),
    Admin("Admin"),
    Subscriber("Subscriber"),
    Affiliate("Affiliate"),
    SubAffiliate("Sub Affiliate"),
    Guest("Guest")

    String name

    UserType(String name) {
        this.name = name
    }

    public static List<UserType> list() {
        return [SuperAdmin, Admin, Subscriber, Affiliate, SubAffiliate,Guest]
    }

    String toString() {
        return name
    }

}