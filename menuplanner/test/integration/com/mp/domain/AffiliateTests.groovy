package com.mp.domain

import grails.test.*

class AffiliateTests extends GrailsUnitTestCase {

    static final String email = "affiliate_user@email.com"
    static final String newEmail = "affiliate_new_user@email.com"

    protected void setUp() {
        super.setUp()
        UserCO userCO = new UserCO()
        userCO.roles = [UserType.Affiliate.toString()]
        userCO.isEnabled = true
        userCO.email = email
        userCO.password = "1234"
        userCO.confirmPassword = "1234"
        userCO.name = "New Affiliate"
        userCO.createParty()
    }

    protected void tearDown() {
        super.tearDown()
    }

    void test_Create_New_Affiliate() {
        Integer initialAffiliateCount = Affiliate.count()
        Integer initialLoginCount = LoginCredential.count()
        Integer initialPartyCount = Party.count()
        UserCO userCO = new UserCO()
        userCO.roles = [UserType.Affiliate.toString()]
        userCO.isEnabled = true
        userCO.email = "test@mymail.com"
        userCO.password = "1234"
        userCO.confirmPassword = "1234"
        userCO.name = "New Affiliate"
        userCO.createParty()
        Integer finalAffiliateCount = Affiliate.count()
        Integer finalLoginCount = LoginCredential.count()
        Integer finalPartyCount = Party.count()
        assertEquals("Unable to create new affiliate", finalAffiliateCount, initialAffiliateCount + 1)
        assertEquals("Unable to create LoginCredential for affiliate", finalLoginCount, initialLoginCount + 1)
        assertEquals("Unable to create party for affiliate", finalPartyCount, initialPartyCount + 1)
    }

    void test_Create_User_With_All_Roles() {
        Integer initialAffiliateCount = Affiliate.count()
        Integer initialSubscriberCount = Subscriber.count()
        Integer initialAdminCount = Administrator.count()
        Integer initialLoginCount = LoginCredential.count()
        Integer initialPartyCount = Party.count()
        UserCO newUserCO = new UserCO()
        newUserCO.roles = [UserType.SuperAdmin.toString(), UserType.Affiliate.toString(), UserType.Admin.toString(), UserType.Subscriber.toString()]
        newUserCO.isEnabled = true
        newUserCO.email = newEmail
        newUserCO.password = "1234"
        newUserCO.confirmPassword = "1234"
        newUserCO.name = "New Affiliate"
        newUserCO.city = "testCity"
        newUserCO.mouthsToFeed = 2
        newUserCO.introduction = "New User with all roles"
        newUserCO.createParty()
        Integer finalAffiliateCount = Affiliate.count()
        Integer finalLoginCount = LoginCredential.count()
        Integer finalPartyCount = Party.count()
        Integer finalSubscriberCount = Subscriber.count()
        Integer finalAdminCount = Administrator.count()
        assertEquals("Unable to create new affiliate", finalAffiliateCount, initialAffiliateCount + 1)
        assertEquals("Unable to create LoginCredential for affiliate", finalLoginCount, initialLoginCount + 1)
        assertEquals("Unable to create party for affiliate", finalPartyCount, initialPartyCount + 1)
        assertEquals("Unable to create Subscriber for affiliate", finalSubscriberCount, initialSubscriberCount + 1)
        assertEquals("Unable to create Administrator for affiliate", finalAdminCount, initialAdminCount + 1)
    }

    void test_Update_Affiliate() {
        UserLogin userLogin = UserLogin.findByEmail(email)
        Integer initialAffiliateCount = Affiliate.count()
        Integer initialLoginCount = LoginCredential.count()
        Integer initialPartyCount = Party.count()
        Party party = userLogin?.party
        UserCO newUserCO = new UserCO(party)
        newUserCO.roles = [UserType.Affiliate.toString()]
        newUserCO.isEnabled = true
        newUserCO.email = newEmail
        newUserCO.password = "1234"
        newUserCO.confirmPassword = "1234"
        newUserCO.name = "Updated Affiliate"
        newUserCO.updateParty()
        Integer finalAffiliateCount = Affiliate.count()
        Integer finalLoginCount = LoginCredential.count()
        Integer finalPartyCount = Party.count()
        assertEquals("Created new affiliate", finalAffiliateCount, initialAffiliateCount)
        assertEquals("Created loginCredential for affiliate", finalLoginCount, initialLoginCount)
        assertEquals("Created party for affiliate", finalPartyCount, initialPartyCount)
    }

    void test_Update_Affiliate_Remove_Affiliate_Role() {
        UserLogin userLogin = UserLogin.findByEmail(email)
        Integer initialAffiliateCount = Affiliate.count()
        Integer initialLoginCount = LoginCredential.count()
        Integer initialPartyCount = Party.count()
        Integer initialSubscriberCount = Subscriber.count()
        Party party = userLogin?.party
        UserCO newUserCO = new UserCO(party)
        newUserCO.roles = [UserType.Subscriber.toString()]
        newUserCO.isEnabled = true
        newUserCO.email = newEmail
        newUserCO.password = "1234"
        newUserCO.confirmPassword = "1234"
        newUserCO.name = "Updated Affiliate"
        newUserCO.updateParty()
        Integer finalAffiliateCount = Affiliate.count()
        Integer finalLoginCount = LoginCredential.count()
        Integer finalPartyCount = Party.count()
        Integer finalSubscriberCount = Subscriber.count()
        assertEquals("Created new affiliate", finalAffiliateCount, initialAffiliateCount - 1)
        assertEquals("Created loginCredential for affiliate", finalLoginCount, initialLoginCount)
        assertEquals("Created party for affiliate", finalPartyCount, initialPartyCount)
        assertEquals("Unable to create subscriber for affiliate", finalSubscriberCount, initialSubscriberCount + 1)
    }

    void test_Update_Affiliate_Add_Roles() {
        UserLogin userLogin = UserLogin.findByEmail(email)
        Integer initialAffiliateCount = Affiliate.count()
        Integer initialLoginCount = LoginCredential.count()
        Integer initialPartyCount = Party.count()
        Integer initialSubCount = Subscriber.count()
        Integer initialAdminCount = Administrator.count()
        Party party = userLogin?.party
        UserCO newUserCO = new UserCO(party)
        newUserCO.roles = [UserType.Affiliate.toString(), UserType.Subscriber.toString(), UserType.Admin.toString()]
        newUserCO.isEnabled = true
        newUserCO.email = email
        newUserCO.password = "1234"
        newUserCO.confirmPassword = "1234"
        newUserCO.name = "Updated Affiliate"
        newUserCO.city = "Test_City"
        newUserCO.mouthsToFeed = 4
        newUserCO.introduction = "Test Introduction"
        newUserCO.updateParty()
        Integer finalAffiliateCount = Affiliate.count()
        Integer finalLoginCount = LoginCredential.count()
        Integer finalPartyCount = Party.count()
        Integer finalSubCount = Subscriber.count()
        Integer finalAdminCount = Administrator.count()
        assertEquals("Created new affiliate", finalAffiliateCount, initialAffiliateCount)
        assertEquals("Created loginCredential for affiliate", finalLoginCount, initialLoginCount)
        assertEquals("Created party for affiliate", finalPartyCount, initialPartyCount)
        assertEquals("Unable to create subscriber for affiliate", finalSubCount, initialSubCount + 1)
        assertEquals("Unable to create administrator for affiliate", finalAdminCount, initialAdminCount + 1)
    }
}
