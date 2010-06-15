import com.gargoylesoftware.htmlunit.html.HtmlAnchor
import static com.mp.domain.TestConstants.*

class SuperAdminFunctionalTests extends MenuPlannerFunctionalTests {

    void goToAccountsPage() {
        loginBySuperAdmin()
        get('/recipe/list')
        def userAccountsLink = byClass('accountsLink')
        userAccountsLink.click()
    }

    void testSuperAdminLogin() {
        loginBySuperAdmin()
        get('/recipe/list')
        assertContentContains 'Hi! superAdmin'
        assertElementTextContains 'navigation', 'Hi! superAdmin'
    }

    void testSuperAdmin_Accounts() {
        goToAccountsPage()
        assertContentContains 'Accounts :'
        assertContentContains 'Filter Name :'
        assertContentContains 'Enabled'
        assertContentContains 'Disabled'
        assertContentContains 'Flagged'
    }

    void testSuperAdmin_UserList() {
        goToAccountsPage()
        def superAdminName = byId('userlist').getFirstChild().getFirstChild().getNextSibling().getFirstChild().getFirstChild().getFirstChild().getTextContent()
        assertEquals('superAdmin', superAdminName)
        def superAdminEmail = byId('userlist').getFirstChild().getFirstChild().getNextSibling().getFirstChild().getFirstChild().getNextSibling().getTextContent()
        assertEquals(SUPER_ADMIN, superAdminEmail)
    }
}
