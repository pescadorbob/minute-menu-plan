package com.mp.domain

import org.codehaus.groovy.grails.commons.ConfigurationHolder
import grails.converters.JSON
import org.codehaus.groovy.grails.web.json.JSONElement
import javax.servlet.http.HttpSession

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

    public Subscriber updateUserFromFacebook(String redirectUrl, String code, PartyRole user) {
        user = user ? user : new Subscriber()
        if (code) {
            Long faceBookToken = code.tokenize("-|")[1]?.toLong()
            if (faceBookToken) {
                String urlString = "https://graph.facebook.com/oauth/access_token?client_id=${config.facebookConnect.apiKey}&redirect_uri=${redirectUrl}&client_secret=${config.facebookConnect.secretKey}&code=${code}"
                URL url = new URL(urlString)
                String token = url.getText()
                if(!user?.party){
                    user?.party=new Party()
                }
                FacebookAccount facebookAccount = (user?.party?.facebookAccount) ? user?.party?.facebookAccount : new FacebookAccount()
                facebookAccount.uid = faceBookToken
                facebookAccount.oauthToken = (token - "access_token=")
                if(user?.party){
                    facebookAccount.party=user?.party
                }
                user?.party?.facebookAccount = facebookAccount
                updateUserInfo(user)
                updateUserPhoto(user)
                user.s()
                return user
            }
        }
        return null
    }

    public void updateProfile(PartyRole user) {
        updateUserPhoto(user)
        updateUserInfo(user)
    }

    private void updateUserPhoto(PartyRole user) {
        if (user?.party?.facebookAccount) {
            URL imageURL = new URL("http://graph.facebook.com/${user?.party?.facebookAccount.uid}/picture?type=large")
            String filePath = config.tempDir
            File dirs = new File(filePath)
            dirs.mkdirs()
            String fileName = '/Img_' + System.currentTimeMillis()?.toString() + '.jpg'
            new File(dirs, fileName).withOutputStream {out ->
                imageURL.eachByte { out.write it }
            }
            File imageFile = new File(filePath + fileName)
            if (imageFile.exists()) {
                Image.updateOwnerImage((Subscriber)user, imageFile.absolutePath)
            }
        }
    }

    private void updateUserInfo(PartyRole user) {
        if (user?.party?.facebookAccount) {
            URL url = new URL("https://graph.facebook.com/${user?.party?.facebookAccount?.uid}?access_token=${user?.party?.facebookAccount?.oauthToken}&fields=name,location")
            JSONElement response = JSON.parse(url.newReader())
            user?.party?.name = response.name
            if (user.instanceOf(Subscriber.class)){
                user?.screenName = response.name
                if (response?.location?.name) {
                    user?.city = response?.location?.name
                }
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

    UserCO(PartyRole user) {
        id = user?.id?.toString()
        if (user?.party?.email) {
            email = user?.party?.email
            password = user?.party?.password
            confirmPassword = user?.party?.password
        }
        name = user?.party?.name
        if(user?.party?.subscriber){
            mouthsToFeed = user?.party?.subscriber?.mouthsToFeed
            introduction = user?.party?.subscriber?.introduction
            city = user?.party?.subscriber?.city
        }
        joiningDate = user?.party?.joiningDate
        isEnabled = user?.party?.isEnabled

        roles = user?.party?.roleTypes*.name()

        if (user?.party?.subscriber?.image) {
            selectUserImagePath = user?.party?.subscriber?.image?.path + user?.party?.subscriber?.image?.storedName
        } else {
            selectUserImagePath = ''
        }
    }

    static constraints = {
        id(nullable: true)
        email(blank: false, nullable: false, email: true, validator: {val, obj ->
            if (val && !obj.id) {
                LoginCredential credential = UserLogin.findByEmail(val)
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
        mouthsToFeed(nullable: true, matches: /[0-9]*/, validator: {val, obj ->
            if (!val && (UserType.Subscriber.name() in obj.roles)) {
                return 'default.blank.message'
            }
        })
        introduction(nullable: true, blank: true,validator: {val, obj ->
            if (!val && (UserType.Subscriber.name() in obj.roles)) {
                return 'default.blank.message'
            }
        })
        city(nullable: true, blank: true,validator: {val, obj ->
            if (!val && (UserType.Subscriber.name() in obj.roles)) {
                return 'default.blank.message'
            }
        })
        roles(nullable: false, blank: false)
    }

//    public boolean createUser(Subscriber subscriber) {
//        subscriber?.screenName = name
//        if (!subscriber?.party?.loginCredentials) {
//            subscriber?.party?.loginCredentials = [new UserLogin(email:email,password:password,party:subscriber.party)] as Set
//        }
//
//        subscriber?.party?.email = email
//        subscriber?.mouthsToFeed = mouthsToFeed
//        subscriber?.introduction = introduction
//        subscriber?.city = city
//        subscriber?.party?.isEnabled = isEnabled
//        if (subscriber?.party?.password != password) {
//            subscriber?.party?.password = password.encodeAsBase64()
//        }
//        return true
//    }
//
    //TODO: Change this implementation

    public boolean assignRoles(Subscriber user) {
//        List<UserType> userRoles = []
//        roles?.each {String role ->
//            userRoles.add(UserType."${role}")
//        }
//        user?.roles = userRoles
        return true
    }

//    public Subscriber convertToUser() {
//        Subscriber user = new Subscriber()
//        user.party = new Party()
//        user.party.name = name
//        createParty(user)
//        assignRoles(user)
//        user?.s()
//        attachImage(user, selectUserImagePath)
//        user?.s()
//        return user
//    }
//
    public Party updateParty() {
        PartyRole user = Subscriber.get(id?.toLong())
        if(!user){
            user = Administrator.get(id?.toLong())
        }
        if(!user){
            user = SuperAdmin.get(id?.toLong())
        }
        Party party = user?.party
        party.name=name
        if ((UserType.Subscriber.name() in roles) && !party.subscriber) {
            user.screenName = name
            user.city = city
            user.mouthsToFeed = mouthsToFeed
            user.introduction = introduction
            attachImage(user,selectUserImagePath)
            party.addToRoles(user)
            party.s()
            user.s()
        }

        if (UserType.Admin.name() in roles && !party.administrator) {
            Administrator admin = new Administrator()
            party.addToRoles(admin)
            party.s()
            admin.s()
        }else if (!(UserType.Admin.name() in roles) && party.administrator){
            Administrator administrator = party.administrator
            party.removeFromRoles(party.administrator)
            administrator.delete()            
            party.s()
        }

        if (UserType.SuperAdmin.name() in roles &&  !party.superAdmin) {
            SuperAdmin superAdmin = new SuperAdmin()
            party.addToRoles(superAdmin)
            party.s()
            superAdmin.s()
        }else if(!(UserType.SuperAdmin.name() in roles) && party.superAdmin){
            SuperAdmin superAdmin = party.superAdmin
            party.removeFromRoles(party.superAdmin)
            superAdmin.delete()
            party.s()
        }
        party.isEnabled=isEnabled        
        return party
    }

    public boolean attachImage(Subscriber user, def imagePath) {
        return Image.updateOwnerImage(user, imagePath)
    }

    public Party createParty() {
        Party party = new Party(name: name)
        party.isEnabled = isEnabled
        LoginCredential loginCredential = new UserLogin(email: email, password: password.encodeAsBase64(), party: party)
        party.loginCredentials = [loginCredential] as Set
        party.s()

        if (UserType.Subscriber.name() in roles) {
            Subscriber subscriber = new Subscriber(screenName: name)
            subscriber.screenName = name
            subscriber.city = city
            subscriber.mouthsToFeed = mouthsToFeed
            subscriber.introduction = introduction
            subscriber.party = party
            attachImage(subscriber,selectUserImagePath)
            party.addToRoles(subscriber)
            party.s()
            subscriber.s()
        }

        if (UserType.Admin.name() in roles) {
            Administrator admin = new Administrator()
            party.addToRoles(admin)
            party.s()
            admin.s()
        }

        if (UserType.SuperAdmin.name() in roles) {
            SuperAdmin superAdmin = new SuperAdmin()
            party.addToRoles(superAdmin)
            party.s()
            superAdmin.s()
        }
        return party
    }

    public void enableAndLoginUser(Party party) {
        party?.isEnabled = true
        party?.s()
        HttpSession currentSession = ConfigurationHolder.config.sessions.find {it.userId == subscriber.id}
        try{
            currentSession.userId = null
            currentSession.loggedUserId = party?.loginCredentials?.toList()?.first()?.id?.toString()
        } catch(Exception e){
            println "Cannot login user. Session has already invalidated"
        }

    }

}
