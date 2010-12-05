package com.mp.domain

import grails.test.GrailsUnitTestCase
import com.mp.domain.party.Director
import com.mp.domain.party.Party
import com.mp.domain.party.Coach
import com.mp.domain.party.Subscriber
import com.mp.domain.party.Director
import com.mp.domain.party.UserCO

class CoachTests extends GrailsUnitTestCase {

    static final String email = "director_user${System.currentTimeMillis()}@email.com"
    static final String sub_email = "sub_director_user@email.com"

    protected void setUp() {
        super.setUp()
    }

    protected void tearDown() {
        super.tearDown()
    }

    void test_Create_New_Sub_Director() {
        Integer initialCoachCount = Coach.count()
        Integer initialDirectorCount = Director.count()
        Integer initialSubscriberCount = Subscriber.count()
        /* -------creating director--------------*/
        UserCO directorUserCO = new UserCO()
        directorUserCO.roles = [PartyRoleType.Director.name()]
        directorUserCO.isEnabled = true
        directorUserCO.email = "director_user${System.currentTimeMillis()}@email.com"
        directorUserCO.password = "1234"
        directorUserCO.confirmPassword = "1234"
        directorUserCO.name = "New Director"
        Party directorParty = directorUserCO.createParty()
        Long directorId = directorParty?.director?.id

        /* -------creating coach--------------*/
        UserCO userCO = new UserCO()
        userCO.roles = [PartyRoleType.Coach.name(), PartyRoleType.Subscriber.name()]
        userCO.isEnabled = true
        userCO.directorId = directorId
        userCO.email = sub_email
        userCO.password = "1234"
        userCO.confirmPassword = "1234"
        userCO.name = "New Sub Director"
        if (userCO.validate()) {
            Party party = userCO.createParty()
        }
        Integer finalDirectorCount = Director.count()
        Integer finalSubscriberCount = Subscriber.count()
        Integer finalCoachCount = Coach.count()

        assertEquals("Unable to create new Sub director", finalCoachCount, initialCoachCount + 1)
        assertEquals("Unable to create new Subscriber", finalSubscriberCount, initialSubscriberCount + 1)
        assertEquals("Created new director", finalDirectorCount, initialDirectorCount+1)
    }

}
