package com.mp.domain

import org.codehaus.groovy.grails.commons.ConfigurationHolder

class UserService {

    boolean transactional = true

    public boolean changeStatus(Long userId) {
        User user = User.findById(userId)
        if (user) {
            (user.isEnabled = !(user.isEnabled))
            return true
        }
    }
}

class UserCO {

    def asynchronousMailService
    static config = ConfigurationHolder.config

    String email
    String password
    String confirmPassword
    String name
    Integer mouthsToFeed
    String city
    String introduction
    Date joiningDate
    List<String> type
    boolean isEnabled

    String id
    def selectUserImagePath

    UserCO(User user) {
        id = user?.id?.toString()
        email = user?.email
        password = user?.password
        confirmPassword = user?.password
        name = user?.name
        mouthsToFeed = user?.mouthsToFeed
        introduction = user?.introduction
        city = user?.city
        joiningDate = user?.joiningDate
        isEnabled = user?.isEnabled

        type = user?.type*.name()

        if (user?.image) {
            selectUserImagePath = user?.image?.path + user?.image?.storedName
        } else {
            selectUserImagePath = ''
        }
    }

    static constraints = {
        id(nullable: true)
        email(blank: false, nullable: false, email: true)
        password(nullable: false, blank: false, minSize: 4)
        confirmPassword(nullable: false, blank: false, validator: {val, obj ->
            obj.properties['password'] == val
        })
        name(nullable: false, blank: false, matches: /[a-zA-Z0-9\s\&]*/)
        mouthsToFeed(nullable: false, matches: /[0-9]*/)
        introduction(nullable: true, blank: true)
        city(nullable: false, blank: false)
        type(nullable: false, blank: false)
    }

    public boolean createUser(User user) {
        user?.name = name
        user?.email = email
        user?.mouthsToFeed = mouthsToFeed
        user?.introduction = introduction
        user?.city = city
        user.isEnabled = isEnabled
        if (user?.password != password) {
            user.password = password.encodeAsBase64()
        }
        return true
    }

    public boolean setRoles(User user) {
        List<UserType> userRoles = []
        type?.each {String role ->
            userRoles.add(UserType."${role}")
        }
        user?.type = userRoles
        return true
    }

    public User convertToUser() {
        User user = new User()
        createUser(user)
        setRoles(user)
        user?.s()
        attachImage(user, selectUserImagePath)
        user?.s()
        return user
    }

    public User updateUser() {
        User user = User.get(id?.toLong())
        createUser(user)
        setRoles(user)
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
