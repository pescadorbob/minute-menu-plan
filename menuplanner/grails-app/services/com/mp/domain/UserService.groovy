package com.mp.domain

import org.codehaus.groovy.grails.commons.ConfigurationHolder

class UserService {

    boolean transactional = true

}

class UserCO {

    def asynchronousMailService
    static config = ConfigurationHolder.config

    String id
    def selectUserImagePath
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
        password = user?.password
        confirmPassword = user?.password
        city = user?.city
        if (user?.image) {
            selectUserImagePath = user?.image?.path + user?.image?.storedName
        } else {
            selectUserImagePath = ''
        }
    }

    static constraints = {
        id(nullable: true)
        name(blank: false, matches: /[a-zA-Z0-9\s\&]*/)
        password(nullable: false, blank: false, minSize: 4)
        confirmPassword(nullable: false, blank: false, validator: {val, obj ->
            obj.properties['password'] == val
        })
        email(blank: false, nullable: false, email: true)
        mouthsToFeed(nullable: false, matches: /[0-9]*/)
        introduction(nullable: true, blank: true)
        city(nullable: false, blank: false)
    }

    public User convertToUser() {
        User user = new User()
        user?.name = name
        user?.email = email
        user?.mouthsToFeed = mouthsToFeed
        user?.introduction = introduction
        user?.city = city
        user?.type = UserType.User
        user?.password = password.encodeAsBase64()
        user?.s()
        attachImage(user, selectUserImagePath)
        user?.s()
        return user
    }

    public User updateUser() {
        User user = User.get(id)
        user?.name = name
        user?.email = email
        user?.mouthsToFeed = mouthsToFeed
        user?.introduction = introduction
        user?.city = city
        user?.type = UserType.User

        if (user?.password != password) {
            user.password = password.encodeAsBase64()
        }

        user.s()
        attachImage(user, selectUserImagePath)
        user.s()
        return user
    }

    public boolean attachImage(User user, def imagePath) {

        if (!imagePath) {
            Image image = user?.image
            user.image = null
            image?.delete(flush: true)
            return false
        } else {
            File sourceImage = new File(imagePath)
            String userImageDirectory = config.imagesRootDir + "/users/" + user?.id + '/'
            String targetImagePath = userImageDirectory + user?.id + '.' + sourceImage.name.tokenize('.').tail().join('.')
            if (sourceImage && (!(imagePath == targetImagePath))) {
                File file = new File(userImageDirectory)
                file.mkdirs()

                Image tempImage = user?.image
                user.image = null
                tempImage?.delete(flush: true)

                new File(targetImagePath).withOutputStream {out ->
                    out.write sourceImage.readBytes()
                }
                com.mp.domain.Image image = new com.mp.domain.Image(imagePath, userImageDirectory, user?.id?.toString(), "")
                user.image = image
                image.s()
            }
        }
    }
}
