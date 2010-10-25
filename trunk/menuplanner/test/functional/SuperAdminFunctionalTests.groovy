import com.gargoylesoftware.htmlunit.html.HtmlAnchor
import static com.mp.domain.TestConstants.*
import com.mp.domain.*

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
        def firstElement = byName('changeStatus2')
        String initialText = firstElement.asText()
        byName('changeStatus2').click()
        Thread.sleep(1000)
        assertStatus 200
        assertFalse(initialText + ' link did not change', (initialText == byName('changeStatus2').asText()))
        byName('changeStatus2').click()
        Thread.sleep(1000)
        assertStatus 200
        assertTrue(initialText + ' link did not change again', (initialText == byName('changeStatus2').asText()))
    }

    /*This test validated the addition of roles(admin,subscriber) to the superAdmin account.
      The test fails if it is unable to add roles to the account or throes any exception
    */

    void testEditProfile_AddRoles() {
        loginBySuperAdmin()
        byClass('userProfileLinkFT').click()
        assertStatus 200
        assertTitle 'Minute Menu Plan : Show User'
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
        assertTitle 'Minute Menu Plan : Show User'
        ['Admin', 'Subscriber', 'Super Admin'].each {role ->
            assertTrue(role in byClass('userRolesFT')*.asText())
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
            assertEquals(userCount, totalUsersInDomain)
        } else {
            assertEquals(userCount, 10)
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
        assertTrue('Created a subscriber', (finalCount - initialCount == 0))
        assertTrue('Created a party', (finalPartyCount - initialPartyCount == 0))
        assertEquals('Error message for roles not displayed / not displayed on correct location', getMessage('userCO.blank.roles.error'), byId('displayUserCOErrors').asText())
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
        assertTitle 'Minute Menu Plan : Show User'
        assertTrue('Unable to create a party object for user', (finalPartyCount - initialPartyCount == 1))
        assertTrue('Unable to assign Administrator role to user', (finalAdminCount - initialAdminCount == 1))
        assertTrue('Unable to assign Subscriber role to user', (finalCount - initialCount == 1))
    }


    void testAddAffiliateBySuperAdmin() {
        javaScriptEnabled = false
        loginBySuperAdmin()
        Integer initialCount = Affiliate.count()
        Integer initialPartyCount = Party.count()
        UserFormData userFormData = UserFormData.getDefaultUserFormData()
        userFormData.email = "qa.menuplanner_${System.currentTimeMillis()}@gmail.com"
        userFormData.isUser = false
        userFormData.isAffiliate = true
        createUser(userFormData)
        Integer finalCount = Affiliate.count()
        Integer finalPartyCount = Party.count()
        assertStatus 200
        assertTrue('Unable to created a Affiliate', (finalCount - initialCount == 1))
        assertTrue('unable to created a party', (finalPartyCount - initialPartyCount == 1))
    }


    void testAdd_SubAffiliateBy_Affiliate() {
        javaScriptEnabled = false
        loginBySuperAdmin()
        Integer initialAffiliateCount = Affiliate.count()
        Integer initialPartyCount = Party.count()
        UserFormData userFormData = UserFormData.getDefaultUserFormData()
        userFormData.email = "qa.menuplanner_${System.currentTimeMillis()}@gmail.com"
        userFormData.isUser = false
        userFormData.isAffiliate = true
        createUser(userFormData)
        Integer finalAffiliateCount = Affiliate.count()
        Integer finalPartyCount = Party.count()
        assertStatus 200
        assertTrue('Unable to created a Affiliate', (finalAffiliateCount - initialAffiliateCount == 1))
        assertTrue('unable to created a party', (finalPartyCount - initialPartyCount == 1))
        logout()

        Integer initialSubscriberCount = Subscriber.count()
        Integer initialSubAffiliateCount = SubAffiliate.count()
        LoginFormData loginFormData = LoginFormData.getDefaultLoginFormData()
        loginFormData.email = userFormData.email
        loginFormData.password = '1234'
        loginToHomepage(loginFormData)
        SubAffiliateFormData subAffiliateFormData = SubAffiliateFormData.getDefaultSubAffiliateFormData()
        createSubAffiliate(subAffiliateFormData)
        Integer finalSubscriberCount = Subscriber.count()
        Integer finalSubAffiliateCount = SubAffiliate.count()
        assertTrue('Unable to created a Subscriber', (finalSubscriberCount - initialSubscriberCount == 1))
        assertTrue('unable to created a Sub-Affiliate', (finalSubAffiliateCount - initialSubAffiliateCount == 1))
    }
}
