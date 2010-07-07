import com.gargoylesoftware.htmlunit.html.HtmlAnchor
import static com.mp.domain.TestConstants.*

class SuperAdminFunctionalTests extends MenuPlannerFunctionalTests {

    void goToAccountsPage() {
        loginBySuperAdmin()
        byClass('accountsLink').click()
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
}
