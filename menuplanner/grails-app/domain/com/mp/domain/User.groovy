package com.mp.domain

class User {

    String name
    String username
    Image image
    Integer mouthsToFeded
    String introduction
    String city
    Date joiningDate
    UserType type
    String password
    AccountStatus status

    def beforeInsert = {
        joiningDate = new Date()
        status = AccountStatus.AWAITING_EMAIL_CONFIRMATION
    }

    static hasMany = [favourites: Recipe, contributions: Recipe]

    static constraints = {
        username(email: true, unique: true)
    }
}
