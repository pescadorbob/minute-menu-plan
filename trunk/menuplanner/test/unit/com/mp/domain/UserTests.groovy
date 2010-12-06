package com.mp.domain

import grails.test.GrailsUnitTestCase

class UserTests extends GrailsUnitTestCase {

    protected void setUp() {
        super.setUp()
    }

    protected void tearDown() {
        super.tearDown()
    }

    void test_UserCO_Valid_Roles() {
        mockForConstraintsTests(UserCO)
        UserCO uco = new UserCO()
        uco.roles = ['SuperAdmin', 'Admin', 'Subscriber']
        uco.validate()
        assertNull(uco.errors["roles"])
    }

    void test_UserCO_Roles_Blank() {
        mockForConstraintsTests(UserCO)
        UserCO uco = new UserCO()
        uco.validate()
        assertNotNull(uco.errors["roles"])
    }

    void test_UserCO_Single_Role() {
        mockForConstraintsTests(UserCO)
        UserCO uco = new UserCO()
        uco.roles =['Admin']
        uco.validate()
        assertNull(uco.errors["roles"])
    }
}
