package com.mp.domain

class User {

    String name
    String email
    Image image
    Integer mouthsToFeed
    String introduction
    String city
    Date joiningDate
    UserType type
    String password
    Boolean isEnabled = true

    static transients = ['isEnabledString']

    String getIsEnabledString(){
        return (isEnabled ? 'Enabled' : 'Disabled' )
    }

    def beforeInsert = {
        joiningDate = new Date()
    }

    static hasMany = [favourites: Recipe, contributions: Recipe]

    static constraints = {
        email(email: true, unique: true)
        image(nullable: true, blank: true)
        password(nullable: true, blank: true)
        mouthsToFeed(nullable:true, blank:true)
        joiningDate(nullable: true, blank: true)
        introduction(nullable: true, maxSize:1000)
    }
}
