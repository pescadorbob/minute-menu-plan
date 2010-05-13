package com.mp.domain

class User {

    String name
    String userName
    Image image
    Integer mouthsToFeed
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
        userName(email: true, unique: true)
        image(nullable: true, blank: true)
        password(nullable: true, blank: true)
        joiningDate(nullable: true, blank: true)
        status(nullable: true, blank: true)
        introduction(max:1000)
    }
}
