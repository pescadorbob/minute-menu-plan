package com.mp.domain

class User {

    String name
    String email
    Image image
    Integer mouthsToFeed
    String introduction
    String city
    Date joiningDate
    String password
    Boolean isEnabled = true
    List<UserType> roles = []

    static hasMany = [roles: UserType, favourites: Recipe, contributions: Recipe]
    static transients = ['isEnabledString']

    static User getCurrentUser() {
        Long userId = SessionUtils.session.loggedUserId?.toLong()
        return ((userId) ? User.get(userId) : null)
    }

    String toString() {
        return name
    }

    String getIsEnabledString() {
        return (isEnabled ? 'Enabled' : 'Disabled')
    }

    def beforeInsert = {
        joiningDate = new Date()
    }

    static constraints = {
        email(email: true, unique: true)
        image(nullable: true, blank: true)
        password(nullable: true, blank: true)
        mouthsToFeed(nullable: true, blank: true)
        joiningDate(nullable: true, blank: true)
        introduction(nullable: true, maxSize: 1000)
    }
}
