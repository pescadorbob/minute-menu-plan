package com.mp.domain

import grails.test.*
import com.mp.domain.party.Party

class AisleTests extends GrailsUnitTestCase {

    protected void setUp() {
        super.setUp()
        def partyInstances = []
        mockDomain(Party, partyInstances)
        mockDomain(Aisle)
        Party party1 = new Party(name: "newParty_1")
        party1.save()
        Party party2 = new Party(name: "newParty_2")
        party2.save()
    }

    protected void tearDown() {
        super.tearDown()
    }

    void test_getAislesForUser_One_Party() {
        Party party1 = Party.get(1)
        Party party2 = Party.get(2)
        List<Aisle> aislesWithoutUser = getAisleObjects(5)
        List<Aisle> aislesWithUser = getAisleObjectWithUser(5, party1)
        List<Aisle> aislesToTestForUser1 = Aisle.getAislesForUser(party1)
        assertEquals "Aisles for party are not as expected", 10, aislesToTestForUser1.size()
    }


    void test_getAislesForUser_Two_Different_Parties() {
        Party party1 = Party.get(1)
        Party party2 = Party.get(2)
        List<Aisle> aislesWithoutUser = getAisleObjects(5)
        List<Aisle> aislesWithUser_1 = getAisleObjectWithUser(5, party1)
        List<Aisle> aislesWithUser_2 = getAisleObjectWithUser(10, party2)
        List<Aisle> aislesToTestForUser1 = Aisle.getAislesForUser(party1)
        List<Aisle> aislesToTestForUser2 = Aisle.getAislesForUser(party2)
        assertEquals "Aisles for party one are not as expected", 10, aislesToTestForUser1.size()
        assertEquals "Aisles are party two are not as expected", 15, aislesToTestForUser2.size()
    }

    void test_getAislesForUser_Two_Different_Parties_Sharing_same_Aisle() {
        Party party1 = Party.get(1)
        Party party2 = Party.get(2)
        List<Aisle> aislesWithoutUser = getAisleObjects(5)
        List<Aisle> aislesWithUser_1 = getAisleObjectWithUser(5, party1)
        List<Aisle> aislesWithUser_2 = getAisleObjectWithUser(10, party2)
        List<Aisle> aislesWithCommonOwners = getAisleObjectWithCommonParties(5, party1, party2)
        List<Aisle> aislesToTestForUser1 = Aisle.getAislesForUser(party1)
        List<Aisle> aislesToTestForUser2 = Aisle.getAislesForUser(party2)
        assertEquals "Aisles for user one are not equal as expected", 15, aislesToTestForUser1.size()
        assertEquals "Aisles are user two are not equal as expected", 20, aislesToTestForUser2.size()
    }

    void test_Aisle_Of_One_User_Invisible_to_Another_User() {
        Party party1 = Party.get(1)
        Party party2 = Party.get(2)
        List<Aisle> aislesWithoutUser = getAisleObjects(5)
        List<Aisle> aislesWithUser_1 = getAisleObjectWithUser(10, party1)
        List<Aisle> aislesToTestForUser1 = Aisle.getAislesForUser(party1)
        List<Aisle> aislesToTestForUser2 = Aisle.getAislesForUser(party2)
        assertEquals "Aisles for user one are not equal as expected", 15, aislesToTestForUser1.size()
        assertEquals "Aisles are user two are not equal as expected", 5, aislesToTestForUser2.size()
    }


    public List<Aisle> getAisleObjects(Integer total) {
        List<Aisle> aisles = []
        (1..total).each {
            Aisle aisle = new Aisle(name: "Aisle_" + it.toString())
            aisle.save()
            aisles.add(aisle)
        }
        return aisles
    }

    public List<Aisle> getAisleObjectWithUser(Integer total, Party party) {
        List<Aisle> aislesNew = []
        (1..total).each {
            Aisle aisle = new Aisle(name: "AisleWithParty_" + it.toString(), ownedByUser: true)
            party.aisles.add(aisle)
            aislesNew.add(aisle)
        }
        return aislesNew
    }

    public List<Aisle> getAisleObjectWithCommonParties(Integer total, Party party1, Party party2) {
        List<Aisle> aislesNew = []
        (1..total).each {
            Aisle aisle = new Aisle(name: "AisleWithPartyCommon_" + it.toString(), ownedByUser: true)
            party1.aisles.add(aisle)
            party2.aisles.add(aisle)
            aislesNew.add(aisle)
        }
        return aislesNew
    }
}
