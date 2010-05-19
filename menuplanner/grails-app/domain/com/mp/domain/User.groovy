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
    List<UserType> type = []

    static hasMany = [type: UserType,favourites: Recipe, contributions: Recipe]
    static transients = ['isEnabledString']

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
