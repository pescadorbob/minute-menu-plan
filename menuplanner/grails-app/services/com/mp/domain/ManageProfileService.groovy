package com.mp.domain

class ManageProfileService {

    boolean transactional = true

}

class ProfileCO{
    String id
    String name
    String userName
    Integer mouthsToFeed
    String introduction
    String city

    ProfileCO(User user){
        id=user?.id?.toString()
        name=user?.name
        userName=user?.userName
        mouthsToFeed=user?.mouthsToFeed
        introduction=user?.introduction
        city=user?.city
    }

    static constraints = {
        id(nullable: true)
        name(blank: false, matches: /[a-zA-Z0-9\s\&]*/)
        userName(blank: false, nullable: false, email:true)
        mouthsToFeed(nullable: true, blank: true)
        introduction(nullable: true, blank: true)
        city(nullable: true, blank: true)
    }
   public User convertToUser(){
       User user = new User()
       user.name=name
       user.userName=userName
       user.mouthsToFeed=mouthsToFeed
       user.introduction=introduction
       user.city=city
       user.type=UserType.User
       user.s()
       VerificationToken verificationToken = new VerificationToken()
       verificationToken.user=user
       verificationToken.s()
       return user
   }
}
