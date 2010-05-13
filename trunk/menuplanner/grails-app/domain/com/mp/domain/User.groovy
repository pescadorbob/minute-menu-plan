package com.mp.domain

class User {

    String name
    String username
    Image image
    Integer mouthsToFeed
    String introduction
    String city
    Date joiningDate

    def beforeInsert = {
        joiningDate = new Date()
    }

    static hasMany = [favourites: Recipe, contributions: Recipe]

    static constraints = {
        username(email: true, unique: true)
    }
}
