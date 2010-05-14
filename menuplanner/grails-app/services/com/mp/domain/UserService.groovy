package com.mp.domain

class UserService {

    boolean transactional = true

}

class UserCO {

    def asynchronousMailService

    String id
    String name
    String password
    String confirmPassword
    String email
    Integer mouthsToFeed
    String introduction
    String city

    UserCO(User user) {
        id = user?.id?.toString()
        name = user?.name
        email = user?.email
        mouthsToFeed = user?.mouthsToFeed
        introduction = user?.introduction
        city = user?.city
    }

    static constraints = {
        id(nullable: true)
        name(blank: false, matches: /[a-zA-Z0-9\s\&]*/)
        password(nullable:false, blank: false, minSize:4)
        confirmPassword(nullable:false, blank: false, validator: {val, obj ->
            obj.properties['password'] == val
        })
        email(blank: false, nullable: false, email: true)
        mouthsToFeed(nullable:false, matches: /[0-9]*/)
        introduction(nullable: true, blank: true)
        city(nullable: false, blank: false)
    }

    public User convertToUser() {
        User user = new User()
        user.name = name
        user.email = email
        user.mouthsToFeed = mouthsToFeed
        user.introduction = introduction
        user.city = city
        user.type = UserType.User
        user.s()
        return user
    }
}
