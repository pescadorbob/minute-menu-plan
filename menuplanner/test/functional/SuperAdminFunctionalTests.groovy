import com.gargoylesoftware.htmlunit.html.HtmlAnchor
import static com.mp.domain.TestConstants.*
import com.mp.domain.Party

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
        Thread.sleep(10000)
        assertStatus 200
        assertFalse(initialText + ' link did not change', (initialText == byName('changeStatus2').asText()))
        byName('changeStatus2').click()
        Thread.sleep(10000)
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
        assertEquals(userCount, totalUsersInDomain)
    }
}
