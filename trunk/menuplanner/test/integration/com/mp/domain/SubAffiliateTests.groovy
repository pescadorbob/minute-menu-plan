package com.mp.domain

import grails.test.GrailsUnitTestCase

class SubAffiliateTests extends GrailsUnitTestCase {

    static final String email = "affiliate_user${System.currentTimeMillis()}@email.com"
    static final String sub_email = "sub_affiliate_user@email.com"

    protected void setUp() {
        super.setUp()
    }

    protected void tearDown() {
        super.tearDown()
    }

    void test_Create_New_Sub_Affiliate() {
        Integer initialSubAffiliateCount = SubAffiliate.count()
        Integer initialAffiliateCount = Affiliate.count()
        Integer initialSubscriberCount = Subscriber.count()
        /* -------creating affiliate--------------*/
        UserCO affiliateUserCO = new UserCO()
        affiliateUserCO.roles = [UserType.Affiliate.name()]
        affiliateUserCO.isEnabled = true
        affiliateUserCO.email = "affiliate_user${System.currentTimeMillis()}@email.com"
        affiliateUserCO.password = "1234"
        affiliateUserCO.confirmPassword = "1234"
        affiliateUserCO.name = "New Affiliate"
        Party affiliateParty = affiliateUserCO.createParty()
        Long affiliateId = affiliateParty?.id

        /* -------creating sub-affiliate--------------*/
        UserCO userCO = new UserCO()
        userCO.roles = [UserType.SubAffiliate.name(), UserType.Subscriber.name()]
        userCO.isEnabled = true
        userCO.affiliateId = affiliateId
        userCO.email = sub_email
        userCO.password = "1234"
        userCO.confirmPassword = "1234"
        userCO.name = "New Sub Affiliate"
        if (userCO.validate()) {
            Party party = userCO.createParty()
        }
        Integer finalAffiliateCount = Affiliate.count()
        Integer finalSubscriberCount = Subscriber.count()
        Integer finalSubAffiliateCount = SubAffiliate.count()

        assertEquals("Unable to create new Sub affiliate", finalSubAffiliateCount, initialSubAffiliateCount + 1)
        assertEquals("Unable to create new Subscriber", finalSubscriberCount, initialSubscriberCount + 1)
        assertEquals("Created new affiliate", finalAffiliateCount, initialAffiliateCount+1)
    }

}
