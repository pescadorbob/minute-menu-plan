package com.mp.domain

import grails.test.*
import com.mp.domain.party.Administrator
import com.mp.domain.party.Director
import com.mp.domain.party.Party
import com.mp.domain.party.Subscriber
import com.mp.domain.party.Director
import com.mp.domain.party.Director

class DirectorTests extends GrailsUnitTestCase {

    static final String email = "director_user@email.com"
    static final String newEmail = "director_new_user@email.com"

    protected void setUp() {
        super.setUp()
        UserCO userCO = new UserCO()
        userCO.roles = [PartyRoleType.Director.name()]
        userCO.isEnabled = true
        userCO.email = email
        userCO.password = "1234"
        userCO.confirmPassword = "1234"
        userCO.name = "New Director"
        Party party = userCO.createParty()
        party = party.refresh()
    }

    protected void tearDown() {
        super.tearDown()
    }

    void test_Create_New_Director() {
        Integer initialDirectorCount = Director.count()
        Integer initialLoginCount = LoginCredential.count()
        Integer initialPartyCount = Party.count()
        UserCO userCO = new UserCO()
        userCO.roles = [PartyRoleType.Director.name()]
        userCO.isEnabled = true
        userCO.email = "test@mymail.com"
        userCO.password = "1234"
        userCO.confirmPassword = "1234"
        userCO.name = "New Director"
        userCO.createParty()
        Integer finalDirectorCount = Director.count()
        Integer finalLoginCount = LoginCredential.count()
        Integer finalPartyCount = Party.count()
        assertEquals("Unable to create new director", finalDirectorCount, initialDirectorCount + 1)
        assertEquals("Unable to create LoginCredential for director", finalLoginCount, initialLoginCount + 1)
        assertEquals("Unable to create party for director", finalPartyCount, initialPartyCount + 1)
    }

    void test_Create_User_With_All_Roles() {
        Integer initialDirectorCount = Director.count()
        Integer initialSubscriberCount = Subscriber.count()
        Integer initialAdminCount = Administrator.count()
        Integer initialLoginCount = LoginCredential.count()
        Integer initialPartyCount = Party.count()
        UserCO newUserCO = new UserCO()
        newUserCO.roles = [PartyRoleType.SuperAdmin.name(), PartyRoleType.Director.name(), PartyRoleType.Admin.name(), PartyRoleType.Subscriber.name()]
        newUserCO.isEnabled = true
        newUserCO.email = newEmail
        newUserCO.password = "1234"
        newUserCO.confirmPassword = "1234"
        newUserCO.name = "New Director"
        newUserCO.city = "testCity"
        newUserCO.mouthsToFeed = 2
        newUserCO.introduction = "New User with all roles"
        newUserCO.createParty()
        Integer finalDirectorCount = Director.count()
        Integer finalLoginCount = LoginCredential.count()
        Integer finalPartyCount = Party.count()
        Integer finalSubscriberCount = Subscriber.count()
        Integer finalAdminCount = Administrator.count()
        assertEquals("Unable to create new director", finalDirectorCount, initialDirectorCount + 1)
        assertEquals("Unable to create LoginCredential for director", finalLoginCount, initialLoginCount + 1)
        assertEquals("Unable to create party for director", finalPartyCount, initialPartyCount + 1)
        assertEquals("Unable to create Subscriber for director", finalSubscriberCount, initialSubscriberCount + 1)
        assertEquals("Unable to create Administrator for director", finalAdminCount, initialAdminCount + 1)
    }

    void test_Update_Director() {
        UserLogin userLogin = UserLogin.findByEmail(email)
        Integer initialDirectorCount = Director.count()
        Integer initialLoginCount = LoginCredential.count()
        Integer initialPartyCount = Party.count()
        Party party = userLogin?.party
        UserCO newUserCO = new UserCO(party)
        newUserCO.roles = [PartyRoleType.Director.name()]
        newUserCO.isEnabled = true
        newUserCO.email = newEmail
        newUserCO.password = "1234"
        newUserCO.confirmPassword = "1234"
        newUserCO.name = "Updated Director"
        newUserCO.updateParty()
        Integer finalDirectorCount = Director.count()
        Integer finalLoginCount = LoginCredential.count()
        Integer finalPartyCount = Party.count()
        assertEquals("Created new director, should have updated old director", finalDirectorCount, initialDirectorCount)
        assertEquals("Created loginCredential for director, should have updated old credentials", finalLoginCount, initialLoginCount)
        assertEquals("Created party for director, should have updated old party", finalPartyCount, initialPartyCount)
    }

    void test_Update_Director_Remove_Director_Role() {
        UserLogin userLogin = UserLogin.findByEmail(email)
        Integer initialDirectorCount = Director.count()
        Integer initialLoginCount = LoginCredential.count()
        Integer initialPartyCount = Party.count()
        Integer initialSubscriberCount = Subscriber.count()
        Party party = userLogin?.party
        UserCO newUserCO = new UserCO(party)
        newUserCO.roles = [PartyRoleType.Subscriber.name()]
        newUserCO.isEnabled = true
        newUserCO.email = newEmail
        newUserCO.password = "1234"
        newUserCO.confirmPassword = "1234"
        newUserCO.name = "Updated Director"
        party = newUserCO.updateParty()
        party = party.refresh()
        Integer finalDirectorCount = Director.count()
        Integer finalLoginCount = LoginCredential.count()
        Integer finalPartyCount = Party.count()
        Integer finalSubscriberCount = Subscriber.count()
        assertEquals("Created new director", initialDirectorCount, finalDirectorCount)
        assertEquals("Created loginCredential for director", initialLoginCount, finalLoginCount)
        assertEquals("Created party for director", initialPartyCount, finalPartyCount)
        assertEquals("Unable to create subscriber for director", initialSubscriberCount + 1, finalSubscriberCount)
    }

    void test_Update_Director_Add_Roles() {
        UserLogin userLogin = UserLogin.findByEmail(email)
        Integer initialDirectorCount = Director.count()
        Integer initialLoginCount = LoginCredential.count()
        Integer initialPartyCount = Party.count()
        Integer initialSubCount = Subscriber.count()
        Integer initialAdminCount = Administrator.count()
        Party party = userLogin?.party
        UserCO newUserCO = new UserCO(party)
        newUserCO.roles = [PartyRoleType.Director.name(), PartyRoleType.Subscriber.name(), PartyRoleType.Admin.name()]
        newUserCO.isEnabled = true
        newUserCO.email = email
        newUserCO.password = "1234"
        newUserCO.confirmPassword = "1234"
        newUserCO.name = "Updated Director"
        newUserCO.city = "Test_City"
        newUserCO.mouthsToFeed = 4
        newUserCO.introduction = "Test Introduction"
        newUserCO.updateParty()
        Integer finalDirectorCount = Director.count()
        Integer finalLoginCount = LoginCredential.count()
        Integer finalPartyCount = Party.count()
        Integer finalSubCount = Subscriber.count()
        Integer finalAdminCount = Administrator.count()
        assertEquals("Created new director", finalDirectorCount, initialDirectorCount)
        assertEquals("Created loginCredential for director", finalLoginCount, initialLoginCount)
        assertEquals("Created party for director", finalPartyCount, initialPartyCount)
        assertEquals("Unable to create subscriber for director", finalSubCount, initialSubCount + 1)
        assertEquals("Unable to create administrator for director", finalAdminCount, initialAdminCount + 1)
    }
}
