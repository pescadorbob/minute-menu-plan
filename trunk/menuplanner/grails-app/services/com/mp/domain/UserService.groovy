package com.mp.domain

import com.mp.domain.party.Coach
import com.mp.domain.party.CoachSubscriber
import com.mp.domain.party.Party
import com.mp.domain.party.Subscriber
import com.mp.tools.UserTools
import grails.converters.JSON
import groovy.sql.Sql
import org.codehaus.groovy.grails.commons.ConfigurationHolder
import org.codehaus.groovy.grails.web.json.JSONElement
import static com.mp.MenuConstants.USER_IMAGE_SIZES
import com.mp.domain.*

class UserService {

    boolean transactional = true
    def dataSource
    def facebookConnectService
    static config = ConfigurationHolder.config

    public boolean changeStatus(Long userId) {
        Party party = Party.findById(userId)
        if (party) {
            (party.isEnabled = !(party.isEnabled))
            if (!party.isEnabled && (party == UserTools.currentUser.party)) {
                SessionUtils?.session?.invalidate()
            }
            return true
        }
        return false
    }

    public Party createUserFromFacebook(String redirectUrl, String code, String coachUUIDFromCookies) {
        Long faceBookToken = code.tokenize("-|")[1]?.toLong()
        String urlString = "https://graph.facebook.com/oauth/access_token?client_id=${config.facebookConnect.apiKey}&redirect_uri=${redirectUrl}&client_secret=${config.facebookConnect.secretKey}&code=${code}"
        URL url = new URL(urlString)
        String token = url.getText()
        String oauthToken = (token - "access_token=")

        url = new URL("https://graph.facebook.com/${faceBookToken}?access_token=${oauthToken}&fields=name,location,email")
        JSONElement response = JSON.parse(url.newReader())
        if (response) {
            String email = response?.email
            String name = response?.name
            String location = response?.location?.name
            Party party
            if (UserLogin.countByEmail(email)) {
                party = UserLogin.findByEmail(email).party
            } else if (FacebookAccount.countByUid(faceBookToken)) {
                party = FacebookAccount.findByUid(faceBookToken).party
            } else {
                party = new Party()
                String coachUUID = SessionUtils?.session?.coachUniqueId
                party.name = name
                party.isEnabled = true
                party.save()
                Subscriber subscriber = location ? new Subscriber(party: party, city: location) : new Subscriber(party: party)
                subscriber.save()
                if (!coachUUID && coachUUIDFromCookies) {
                    coachUUID = coachUUIDFromCookies
                }
                if (coachUUID) {
                    Coach coach = Coach.withCriteria(uniqueResult: true) {
                        party {
                            eq('uniqueId', coachUUID)
                        }
                    }
                    if (coach) {
                        CoachSubscriber cs = new CoachSubscriber(activeFrom: new Date(), client: coach, supplier: subscriber,
                                commission: coachRole.defaultCommission,)
                        cs.s()
                    }
                }
                party.s()
            }
            if (!party.facebookAccount) {
                FacebookAccount facebookAccount = new FacebookAccount(party: party, uid: faceBookToken, oauthToken: oauthToken)
                party.facebookAccount = facebookAccount
                facebookAccount.s()
                party.s()
            }
            updateProfile(party)
            return party
        }
        return null
    }

    public Party updateUserFromFacebook(String redirectUrl, Party party, String code) {
        Long faceBookToken = code.tokenize("-|")[1]?.toLong()
        String urlString = "https://graph.facebook.com/oauth/access_token?client_id=${config.facebookConnect.apiKey}&redirect_uri=${redirectUrl}&client_secret=${config.facebookConnect.secretKey}&code=${code}"
        URL url = new URL(urlString)
        String token = url.getText()
        FacebookAccount facebookAccount = (party.facebookAccount) ? party.facebookAccount : new FacebookAccount()
        String oauthToken = (token - "access_token=")
        facebookAccount.uid = faceBookToken
        facebookAccount.oauthToken = oauthToken
        facebookAccount.party = party
        party?.facebookAccount = facebookAccount
        party?.isEnabled = true
        party.s()
        facebookAccount.s()
        updateProfile(party)
        return party
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
        FacebookAccount facebookAccount = party.facebookAccount
        if (facebookAccount) {
            URL url = new URL("https://graph.facebook.com/${party?.facebookAccount?.uid}?access_token=${party?.facebookAccount?.oauthToken}&fields=name,location,email")
            JSONElement response = JSON.parse(url.newReader())
            if (response) {
                String email = response?.email
                String name = response?.name
                String location = response?.location?.name
                party?.name = name
                party.s()
                if (!party?.userLogin && email) {
                    if (!UserLogin.countByEmail(email)) {
                        UserLogin userLogin = new UserLogin()
                        String password = "1234"
                        userLogin.email = email
                        userLogin.password = password.encodeAsBase64()
                        userLogin.party = party
                        userLogin.s()
                    }
                }
                if (location && party.subscriber) {
                    party.subscriber.city = location
                    party.subscriber.s()
                }
            }
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
        if (party?.subscriber) {
            CoachSubscriber cs = CoachSubscriber.findBySupplier(party.subscriber)
            if (cs) cs.delete()
        }
        party.delete(flush: true)
    }
}

