package com.mp.domain

public enum AccountStatus {

    AWAITING_EMAIL_CONFIRMATION("Awaiting Email Confirmation"),
    ACTIVE("Active"),
    INACTIVE("Inactive")

    String name

    AccountStatus(String name) {
        this.name = name
    }

    String toString() {
        return name
    }

}