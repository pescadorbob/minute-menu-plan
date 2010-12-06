package com.mp.domain

import grails.test.GrailsUnitTestCase
import com.mp.domain.party.Administrator
import com.mp.domain.party.Director
import com.mp.domain.party.Party
import com.mp.domain.party.Subscriber

class UserControllerTests extends GrailsUnitTestCase {
    def renderMap
    def redirectMap
    def controller

    protected void setUp() {

        super.setUp()
        createEnabledUser()
        createDisabledUser()
        controller = new UserController()

        controller.metaClass.render = {Map map ->
            renderMap = map
        }
        controller.metaClass.redirect = {Map map ->
            redirectMap = map
        }
    }

    protected void tearDown() {
        super.tearDown()
    }

    void test_Enabled_UserList() {
        controller.params.max = 10
        controller.params.offset = 0
        controller.params.userStatus = 'enabled'
        controller.list()
        assertEquals 10, renderMap.model.parties.size()

        List<Long> partiesId = renderMap.model.parties*.id
        assertEquals 10, partiesId.unique {it}.size()
    }

    void test_Disabled_UserList() {
        controller.params.max = 10
        controller.params.offset = 0
        controller.params.userStatus = 'disabled'
        controller.list()
        assertEquals 5, renderMap.model.parties.size()
        List<Long> partiesId = renderMap.model.parties*.id
        assertEquals 5, partiesId.unique {it}.size()
    }

    void test_UserList_By_Name() {
        controller.params.max = 10
        controller.params.offset = 0
        controller.params.searchName = 'test'
        controller.list()
        assertEquals 5, renderMap.model.parties.size()
        List<Long> partiesId = renderMap.model.parties*.id
        assertEquals 5, partiesId.unique {it}.size()
    }

    void test_UserList_By_Name_And_Status_Disable1() {
        controller.params.max = 10
        controller.params.offset = 0
        controller.params.searchName = 'test'
        controller.params.userStatus = 'disabled'
        controller.list()
        assertEquals 5, renderMap.model.parties.size()
        List<Long> partiesId = renderMap.model.parties*.id
        assertEquals 5, partiesId.unique {it}.size()
    }

    void test_UserList_By_Name_And_Status_Disable2() {
        controller.params.max = 10
        controller.params.offset = 0
        controller.params.searchName = 'User'
        controller.params.userStatus = 'disabled'
        controller.list()
        assertEquals 0, renderMap.model.parties.size()
    }

    void test_UserList_By_Name_And_Status_Enable1() {
        controller.params.max = 10
        controller.params.offset = 0
        controller.params.searchName = 'User'
        controller.params.userStatus = 'enabled'
        controller.list()
        assertEquals 10, renderMap.model.parties.size()
        List<Long> partiesId = renderMap.model.parties*.id
        assertEquals 10, partiesId.unique {it}.size()
    }

    void test_UserList_By_Name_And_Status_Enable2() {
        controller.params.max = 10
        controller.params.offset = 0
        controller.params.searchName = 'test'
        controller.params.userStatus = 'enabled'
        controller.list()
        assertEquals 0, renderMap.model.parties.size()
    }

    void createEnabledUser() {
        List enableUser = ['a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j']
        enableUser.each {
            UserCO userCO = new UserCO()
            userCO.name = "User ${it}"
            userCO.confirmPassword = "1234"
            userCO.email = "user_${it}@mymail.com"
            userCO.password = "1234"
            userCO.isEnabled = true
            Party party = userCO.createParty()
            party.addToRoles(new Subscriber())
            party.addToRoles(new Director())
            party.save(flush: true)
        }
    }

    void createDisabledUser() {
        List disableUser = ['k', 'l', 'm', 'n', 'o']
        disableUser.each {
            UserCO userCO = new UserCO()
            userCO.name = "test ${it}"
            userCO.confirmPassword = "1234"
            userCO.email = "user_${it}@mymail.com"
            userCO.password = "1234"
            userCO.isEnabled = false
            Party party = userCO.createParty()
            party.addToRoles(new Administrator())
            party.save(flush: true)
        }
    }
}
