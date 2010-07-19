package com.mp.domain

import org.codehaus.groovy.grails.commons.ConfigurationHolder
import grails.converters.JSON
import org.codehaus.groovy.grails.web.json.JSONElement

class UserService {

    boolean transactional = true
    def facebookConnectService
    static config = ConfigurationHolder.config

    public boolean changeStatus(Long userId) {
        Subscriber user = Subscriber.findById(userId)
        if (user) {
            (user.isEnabled = !(user.isEnabled))
            return true
        }
        return false
    }

    public Subscriber updateUserFromFacebook(String redirectUrl, String code, Subscriber user) {
        user = user ? user : new Subscriber()
        if (code) {
            Long faceBookToken = code.tokenize("-|")[1]?.toLong()
            if (faceBookToken) {
                String urlString = "https://graph.facebook.com/oauth/access_token?client_id=${config.facebookConnect.apiKey}&redirect_uri=${redirectUrl}&client_secret=${config.facebookConnect.secretKey}&code=${code}"
                URL url = new URL(urlString)
                String token = url.getText()
                FacebookAccount facebookAccount = (user.facebookAccount) ? user.facebookAccount : new FacebookAccount()
                facebookAccount.uid = faceBookToken
                facebookAccount.oauthToken = (token - "access_token=")
                user.facebookAccount = facebookAccount
                updateUserInfo(user)
                updateUserPhoto(user)
                user.s()
                return user
            }
        }
        return null
    }

    public void updateProfile(Subscriber user) {
        updateUserPhoto(user)
        updateUserInfo(user)
    }

    private void updateUserPhoto(Subscriber user) {
        if (user.facebookAccount) {
            URL imageURL = new URL("http://graph.facebook.com/${user.facebookAccount.uid}/picture?type=large")
            String filePath = config.tempDir
            File dirs = new File(filePath)
            dirs.mkdirs()
            String fileName = '/Img_' + System.currentTimeMillis()?.toString() + '.jpg'
            new File(dirs, fileName).withOutputStream {out ->
                imageURL.eachByte { out.write it }
            }
            File imageFile = new File(filePath + fileName)
            if (imageFile.exists()) {
                Image.updateOwnerImage(user, imageFile.absolutePath)
            }
        }
    }

    private void updateUserInfo(Subscriber user) {
        if (user.facebookAccount) {
            URL url = new URL("https://graph.facebook.com/${user.facebookAccount.uid}?access_token=${user.facebookAccount.oauthToken}&fields=name,location")
            JSONElement response = JSON.parse(url.newReader())
            user.name = response.name
            if (response?.location?.name) {
                user.city = response?.location?.name
            }
            user.s()
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
    List<String> roles = []
    boolean isEnabled

    String id
    def selectUserImagePath

    UserCO() {

    }

    UserCO(Subscriber user) {
        id = user?.id?.toString()
        email = user?.loginCredential?.email
        password = user?.loginCredential?.password
        confirmPassword = user?.loginCredential?.password
        name = user?.name
        mouthsToFeed = user?.mouthsToFeed
        introduction = user?.introduction
        city = user?.city
        joiningDate = user?.joiningDate
        isEnabled = user?.isEnabled

        roles = user?.roles*.name()

        if (user?.image) {
            selectUserImagePath = user?.image?.path + user?.image?.storedName
        } else {
            selectUserImagePath = ''
        }
    }

    static constraints = {
        id(nullable: true)
        email(blank: false, nullable: false, validator: {val, obj ->
            if (val) {
                LoginCredential credential = LoginCredential.findByEmail(val)
                if (credential && (credential?.id != obj?.id?.toLong())) {
                    return "userCO.email.unique.error"
                }
            }
        })
        password(nullable: false, blank: false, minSize: 4)
        confirmPassword(nullable: false, blank: false, validator: {val, obj ->
            obj.properties['password'] == val
        })
        name(nullable: false, blank: false, matches: /[a-zA-Z0-9\s\&]*/)
        mouthsToFeed(nullable: false, matches: /[0-9]*/)
        introduction(nullable: true, blank: true)
        city(nullable: false, blank: false)
        roles(nullable: false, blank: false)
    }

    public boolean createUser(Subscriber user) {
        user?.name = name
        if (!user.loginCredential) {
            user?.loginCredential = new LoginCredential()
        }
        user?.loginCredential?.email = email
        user?.mouthsToFeed = mouthsToFeed
        user?.introduction = introduction
        user?.city = city
        user.isEnabled = isEnabled
        if (user?.loginCredential?.password != password) {
            user.loginCredential?.password = password.encodeAsBase64()
        }
        return true
    }

    public boolean assignRoles(Subscriber user) {
        List<UserType> userRoles = []
        roles?.each {String role ->
            userRoles.add(UserType."${role}")
        }
        user?.roles = userRoles
        return true
    }

    public Subscriber convertToUser() {
        Subscriber user = new Subscriber()
        createUser(user)
        assignRoles(user)
        user?.s()
        attachImage(user, selectUserImagePath)
        user?.s()
        return user
    }

    public Subscriber updateUser() {
        Subscriber user = Subscriber.get(id?.toLong())
        createUser(user)
        assignRoles(user)
        user.s()
        attachImage(user, selectUserImagePath)
        user.s()
        return user
    }

    public boolean attachImage(Subscriber user, def imagePath) {
        return Image.updateOwnerImage(user, imagePath)
    }
}
