package com.mp

import com.mp.domain.*
import com.mp.domain.party.Administrator
import com.mp.domain.party.Director
import com.mp.domain.party.Party
import com.mp.domain.party.Coach
import com.mp.domain.party.Subscriber

import com.mp.tools.UserTools

class SuperAdminFunctionalTests extends MenuPlannerFunctionalTests {

    void goToAccountsPage() {
        loginBySuperAdmin()
        byClass('accountsLinkFT').click()
    }

    void testSuperAdminLogin() {
        loginBySuperAdmin()
        assertElementTextContains 'navigation', 'superAdmin'
        assertTitle 'Minute Menu Plan : List Recipe'
        logout()
    }

    void testSuperAdmin_AccountsLink() {
        goToAccountsPage()
        assertTitle 'Minute Menu Plan : User List'
        logout()
    }

    void testSuperAdmin_AddUserLink() {
        goToAccountsPage()
        javaScriptEnabled = false
        assertTitle 'Minute Menu Plan : User List'
        def addUserLink = byClass('addUserButtonFT')
        addUserLink.click()
        assertTitle 'Minute Menu Plan : Add User'
        logout()
    }

    void testEnabled_Disabled_RadioButton() {
        goToAccountsPage()
        String linkName = 'changeStatus3'
        def firstElement = byName(linkName)
        assertNotNull('No Enable/Disable link found', firstElement)
        String initialText = firstElement.asText()
        byName(linkName).click()
        Thread.sleep(1000)
        assertStatus 200
        junit.framework.Assert.assertFalse(initialText + ' link did not change', (initialText == byName(linkName).asText()))
        byName(linkName).click()
        Thread.sleep(1000)
        assertStatus 200
        junit.framework.Assert.assertTrue(initialText + ' link did not change again', (initialText == byName(linkName).asText()))
    }

    /*This test validated the addition of roles(admin,subscriber) to the superAdmin account.
      The test fails if it is unable to add roles to the account or throes any exception
    */

    void testEditProfile_AddRoles() {
        loginBySuperAdmin()
        byClass('userProfileLinkFT').click()
        assertStatus 200
        assertTitle 'Minute Menu Plan : SuperAdmin Profile'
        def editUserButton = byClass('editUserButtonFT')
        editUserButton.click()
        def subscriberCheckbox = byId('chk_Subscriber')
        subscriberCheckbox.click()
        def adminCheckbox = byId('chk_Admin')
        adminCheckbox.click()
        def updateUserButton = byClass('updateUserButtonFT')
        updateUserButton.click()
        redirectEnabled = false
        followRedirect()
        assertTitle 'Minute Menu Plan : SuperAdmin Profile'
        ['Admin', 'Subscriber', 'Super Admin'].each {role ->
            junit.framework.Assert.assertTrue(role in byClass('userRolesFT')*.asText())
        }
    }
    /*
           * This test validated the no of user shown to superAdmin.
           * The test fails if no of users shown to superadmin are more or less than the total number of users */

    void testShowAllUsersList() {
        loginBySuperAdmin()
        byClass('accountsLinkFT').click()
        Integer userCount = byClass('allUsersInDomainFT').size()
        Integer totalUsersInDomain = Party.count()
        if (totalUsersInDomain <= 10) {
            junit.framework.Assert.assertEquals(userCount, totalUsersInDomain)
        } else {
            junit.framework.Assert.assertEquals(userCount, 10)
        }
    }


    void testAddUserBySuperAdmin_Without_Assigning_Roles() {
        javaScriptEnabled = false
        loginBySuperAdmin()
        Integer initialCount = Subscriber.count()
        Integer initialPartyCount = Party.count()
        UserFormData userFormData = UserFormData.getDefaultUserFormData()
        userFormData.email = "qa.menuplanner_${System.currentTimeMillis()}@gmail.com"
        userFormData.isUser = false
        createUser(userFormData)
        Integer finalCount = Subscriber.count()
        Integer finalPartyCount = Party.count()
        assertStatus 200
        assertTitle 'Minute Menu Plan : Add User'
        junit.framework.Assert.assertTrue('Created a subscriber', (finalCount - initialCount == 0))
        junit.framework.Assert.assertTrue('Created a party', (finalPartyCount - initialPartyCount == 0))
        groovy.util.GroovyTestCase.assertEquals('Error message for roles not displayed / not displayed on correct location', getMessage('userCO.blank.roles.error'), byId('displayUserCOErrors').asText())
    }

    void testAddUserBySuperAdmin_With_Assigning_Roles() {
        javaScriptEnabled = false
        loginBySuperAdmin()
        Integer initialCount = Subscriber.count()
        Integer initialPartyCount = Party.count()
        Integer initialAdminCount = Administrator.count()
        UserFormData userFormData = UserFormData.getDefaultUserFormData()
        userFormData.email = "qa.menuplanner_${System.currentTimeMillis()}@gmail.com"
        userFormData.isAdmin = true
        userFormData.isUser = true
        createUser(userFormData)
        Integer finalCount = Subscriber.count()
        Integer finalPartyCount = Party.count()
        Integer finalAdminCount = Administrator.count()
        assertStatus 200
        assertTitle 'Minute Menu Plan : Testuser1 Profile'
        junit.framework.Assert.assertTrue('Unable to create a party object for user', (finalPartyCount - initialPartyCount == 1))
        junit.framework.Assert.assertTrue('Unable to assign Administrator role to user', (finalAdminCount - initialAdminCount == 1))
        junit.framework.Assert.assertTrue('Unable to assign Subscriber role to user', (finalCount - initialCount == 1))
    }


    void testAddDirectorBySuperAdmin() {
        javaScriptEnabled = false
        loginBySuperAdmin()
        Integer initialCount = Director.count()
        Integer initialPartyCount = Party.count()
        UserFormData userFormData = UserFormData.getDefaultUserFormData()
        userFormData.email = "qa.menuplanner_${System.currentTimeMillis()}@gmail.com"
        userFormData.isUser = false
        userFormData.isDirector = true
        createUser(userFormData)
        Integer finalCount = Director.count()
        Integer finalPartyCount = Party.count()
        assertStatus 200
        junit.framework.Assert.assertTrue('Unable to created a Director', (finalCount - initialCount == 1))
        junit.framework.Assert.assertTrue('unable to created a party', (finalPartyCount - initialPartyCount == 1))
    }


    void testAdd_CoachBy_Director() {
        javaScriptEnabled = false
        loginBySuperAdmin()
        Integer initialDirectorCount = Director.count()
        Integer initialPartyCount = Party.count()
        UserFormData userFormData = UserFormData.getDefaultUserFormData()
        userFormData.email = "qa.menuplanner_${System.currentTimeMillis()}@gmail.com"
        userFormData.isUser = false
        userFormData.isDirector = true
        createUser(userFormData)
        Integer finalDirectorCount = Director.count()
        Integer finalPartyCount = Party.count()
        assertStatus 200
        junit.framework.Assert.assertTrue('Unable to created a Director', (finalDirectorCount - initialDirectorCount == 1))
        junit.framework.Assert.assertTrue('unable to created a party', (finalPartyCount - initialPartyCount == 1))
        logout()

        Integer initialSubscriberCount = Subscriber.count()
        Integer initialCoachCount = Coach.count()
        LoginFormData loginFormData = LoginFormData.getDefaultLoginFormData()
        loginFormData.email = userFormData.email
        loginFormData.password = '1234'
        loginToHomepage(loginFormData)
        CoachFormData coachFormData = CoachFormData.getDefaultCoachFormData()
        createCoach(coachFormData)
        Integer finalSubscriberCount = Subscriber.count()
        Integer finalCoachCount = Coach.count()
        junit.framework.Assert.assertTrue('Unable to created a Subscriber', (finalSubscriberCount - initialSubscriberCount == 1))
        junit.framework.Assert.assertTrue('unable to created a Sub-Director', (finalCoachCount - initialCoachCount == 1))
    }


    void testAdd_Coach_By_Director_Edit_Coach() {
        javaScriptEnabled = false
        loginBySuperAdmin()
        UserFormData userFormData = UserFormData.getDefaultUserFormData()
        userFormData.email = "qa.menuplanner_${System.currentTimeMillis()}@gmail.com"
        userFormData.isUser = false
        userFormData.isDirector = true
        createUser(userFormData)
        assertStatus 200
        logout()

        Integer initialSubscriberCount = Subscriber.count()
        Integer initialCoachCount = Coach.count()
        LoginFormData loginFormData = LoginFormData.getDefaultLoginFormData()
        loginFormData.email = userFormData.email
        loginFormData.password = '1234'
        loginToHomepage(loginFormData)
        CoachFormData coachFormData = CoachFormData.getDefaultCoachFormData()
        createCoach(coachFormData)

        Integer intermediateSubscriberCount = Subscriber.count()
        Integer intermediateCoachCount = Coach.count()
        assertTitle 'Minute Menu Plan : Sub Director User Profile'
        junit.framework.Assert.assertTrue('Unable to created a Subscriber', (intermediateSubscriberCount - initialSubscriberCount == 1))
        junit.framework.Assert.assertTrue('unable to created a Sub-Director', (intermediateCoachCount - initialCoachCount == 1))
        byClass('editUserButtonFT').click()
        assertTitle 'Minute Menu Plan : Edit User'
        byClass('updateUserButtonFT').click()
        redirectEnabled = false
        followRedirect()
        assertTitle 'Minute Menu Plan : Sub Director User Profile'
        assertElementTextContains('flashMsgTst', getMessage('user.updateded.success'))

        Integer finalSubscriberCount = Subscriber.count()
        Integer finalCoachCount = Coach.count()
        assertTitle 'Minute Menu Plan : Sub Director User Profile'
        junit.framework.Assert.assertTrue('Unable to created a Subscriber', (finalSubscriberCount - initialSubscriberCount == 1))
        junit.framework.Assert.assertTrue('unable to created a Sub-Director', (finalCoachCount - initialCoachCount == 1))
        logout()
    }
}
