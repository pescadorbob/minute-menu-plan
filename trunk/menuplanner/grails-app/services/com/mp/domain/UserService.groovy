package com.mp.domain

import org.codehaus.groovy.grails.commons.ConfigurationHolder
import grails.converters.JSON
import org.codehaus.groovy.grails.web.json.JSONElement
import javax.servlet.http.HttpSession
import static com.mp.MenuConstants.*
import groovy.sql.Sql


class UserService {

    boolean transactional = true
    def dataSource
    def facebookConnectService
    static config = ConfigurationHolder.config

    public boolean changeStatus(Long userId) {
        Party party = Party.findById(userId)
        if (party) {
            (party.isEnabled = !(party.isEnabled))
            if (!party.isEnabled && (party == LoginCredential.currentUser.party)) {
                SessionUtils?.session?.invalidate()
            }
            return true
        }
        return false
    }

    public Party updateUserFromFacebook(String redirectUrl, String code, Party party) {
        party = party ? party : new Party()
        if (code) {
            Long faceBookToken = code.tokenize("-|")[1]?.toLong()
            if (faceBookToken) {
                String urlString = "https://graph.facebook.com/oauth/access_token?client_id=${config.facebookConnect.apiKey}&redirect_uri=${redirectUrl}&client_secret=${config.facebookConnect.secretKey}&code=${code}"
                URL url = new URL(urlString)
                String token = url.getText()
                FacebookAccount facebookAccount = (party?.facebookAccount) ? party?.facebookAccount : new FacebookAccount()
                facebookAccount.uid = faceBookToken
                facebookAccount.oauthToken = (token - "access_token=")
                facebookAccount.party = party
                party?.facebookAccount = facebookAccount
                updateUserInfo(party)
                updateUserPhoto(party)
                party.s()
                return party
            }
        }
        return null
    }

    public void updateProfile(Party party) {
        updateUserPhoto(party)
        updateUserInfo(party)
    }

    private void updateUserPhoto(Party party) {
        if (party?.subscriber && party?.facebookAccount) {
            URL imageURL = new URL("http://graph.facebook.com/${party?.facebookAccount.uid}/picture?type=large")
            String filePath = config.tempDir
            File dirs = new File(filePath)
            dirs.mkdirs()
            String fileName = '/Img_' + System.currentTimeMillis()?.toString() + '.jpg'
            new File(dirs, fileName).withOutputStream {out ->
                imageURL.eachByte { out.write it }
            }
            File imageFile = new File(filePath + fileName)
            if (imageFile.exists()) {
                List<Integer> userImageSizes = USER_IMAGE_SIZES
                Image.updateOwnerImage(party.subscriber, imageFile.absolutePath, userImageSizes)
            }
        }
    }

    private void updateUserInfo(Party party) {
        if (party?.facebookAccount) {
            URL url = new URL("https://graph.facebook.com/${party?.facebookAccount?.uid}?access_token=${party?.facebookAccount?.oauthToken}&fields=name,location")
            JSONElement response = JSON.parse(url.newReader())
            party?.name = response.name
            if (party.subscriber && response?.location?.name) {
                party.subscriber.city = response?.location?.name
            }
            party.s()
        }
    }

    def fetchSqlConnection() {
        def sql = new Sql(dataSource.getConnection())
        return sql;
    }

    public void deleteParty(Party party) {
        Long partyId = party.id
        def sql = fetchSqlConnection()

        CommentAbuse.executeUpdate("delete from CommentAbuse as ca where ca.reporter = (:party)", [party: party])
        RecipeAbuse.executeUpdate("delete from RecipeAbuse as ra where ra.reporter = (:party)", [party: party])

        String selectQuery = "select product_id from party_product where party_ingredients_id=${partyId} and not product_id in ( select product_id from party_product where party_ingredients_id !=${partyId}) and not product_id in (select ingredient_id from recipe_ingredient)"
        String deleteQuery = "delete from recipe_item where item_id in (${selectQuery})"
        sql.executeUpdate(deleteQuery)
            deleteQuery = "delete from item where id in (${selectQuery})"
            sql.executeUpdate(deleteQuery)

        def aisleIdsToBeDeleted = []
        sql.eachRow("select aisle_id from party_aisle where party_aisles_id=${partyId} and not aisle_id in ( select aisle_id from party_aisle where party_aisles_id !=${partyId});", {
            aisleIdsToBeDeleted.add(it.aisle_id)
        })
        if (aisleIdsToBeDeleted) {
            List<Aisle> aisleToRemove = Aisle.getAll(aisleIdsToBeDeleted)
            RecipeIngredient.findAllByAisleInList(aisleToRemove).each {RecipeIngredient ri ->
                ri.aisle = null
                ri.s()
            }
            Item.executeUpdate("update Item i set i.suggestedAisle=NULL where i.suggestedAisle in (:aislesToRemove)", [aislesToRemove: aisleToRemove])
            List shoppingIngredients = ShoppingIngredient.findAllByAisleInList(aisleToRemove)
            shoppingIngredients.each {ShoppingIngredient si ->
                si.aisle = null
                si.s()
            }
            party.aisles = []
            aisleToRemove*.delete()
        }
        party.delete(flush: true)
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

    UserCO(Party party) {
        id = party?.id?.toString()
        if (party?.email) {
            email = party?.email
            password = party?.password
            confirmPassword = party?.password
        }
        name = party?.name
        if (party?.subscriber) {
            mouthsToFeed = party?.subscriber?.mouthsToFeed
            introduction = party?.subscriber?.introduction
            city = party?.subscriber?.city
        }
        joiningDate = party?.joiningDate
        isEnabled = party?.isEnabled

        roles = party?.roleTypes*.name()

        if (party?.subscriber?.image) {
            int firstIndex = party?.subscriber?.image?.storedName?.indexOf('.')
            String name = party?.subscriber?.image?.storedName?.substring(0, firstIndex)
            selectUserImagePath = party?.subscriber?.image?.path + name + "_200.jpg"
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
        mouthsToFeed(nullable: true, blank: true, matches: /[0-9]*/)
        introduction(nullable: true, blank: true)
        city(nullable: true, blank: true)
        roles(nullable: false, blank: false)
    }

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
        Party party = Party.get(id?.toLong())

        //Delete unchecked roles first
        if (party.subscriber && !(UserType.Subscriber.name() in roles)) {
            Subscriber subscriber = party.subscriber
            party.removeFromRoles(subscriber)
            subscriber.delete(flush: true)
        }

        if (party.superAdmin && !(UserType.SuperAdmin.name() in roles)) {
            SuperAdmin superAdmin = party.superAdmin
            party.removeFromRoles(superAdmin)
            superAdmin.delete(flush: true)
        }

        if (party.administrator && !(UserType.Admin.name() in roles)) {
            Administrator administrator = party.administrator
            party.removeFromRoles(administrator)
            administrator.delete(flush: true)
        }

        party.name = name
        if ((UserType.Subscriber.name() in roles)) {
            Subscriber subscriber = party.subscriber ? party.subscriber : new Subscriber()
            subscriber.city = city
            subscriber.mouthsToFeed = mouthsToFeed
            subscriber.introduction = introduction
            attachImage(subscriber, selectUserImagePath)
            subscriber.party = party
            subscriber.s()
        }

        if ((UserType.Admin.name() in roles) && !party.administrator) {
            new Administrator(party: party).s()
        }

        if (UserType.SuperAdmin.name() in roles && !party.superAdmin) {
            new SuperAdmin(party: party).s()
        }

        if (party.userLogin) {
            UserLogin login = party.userLogin
            login.email = email
            if (login.password != password) {
                login.password = password.encodeAsBase64()
            }
            login.s()
        } else if (email) {
            new UserLogin(email: email, password: password.encodeAsBase64(), party: party).s()
        }

        party.isEnabled = isEnabled
        return party
    }

    public boolean attachImage(Subscriber user, def imagePath) {
        List<Integer> imageSizes = USER_IMAGE_SIZES
        return Image.updateOwnerImage(user, imagePath, imageSizes)
    }

    public Party createParty() {
        Party party = new Party(name: name)
        party.isEnabled = isEnabled
        LoginCredential loginCredential = new UserLogin(email: email, password: password.encodeAsBase64(), party: party)
        party.loginCredentials = [loginCredential] as Set
        party.s()

        if (UserType.Subscriber.name() in roles) {
            Subscriber subscriber = new Subscriber()
            subscriber.city = city
            subscriber.mouthsToFeed = mouthsToFeed
            subscriber.introduction = introduction
            subscriber.party = party
            attachImage(subscriber, selectUserImagePath)
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
        currentSession.userId = null
        currentSession.loggedUserId = party?.loginCredentials?.toList()?.first()?.id?.toString()

    }

}
