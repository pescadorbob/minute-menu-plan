import com.mp.domain.HomePage

class HomePageFunctionalTests extends MenuPlannerFunctionalTests {

    void test_Homepage_Create_Valid_Name() {
        int initialCount = HomePage.count()
        loginBySuperAdmin()
        def homePageLinkFT = byClass('homePageLinkFT')
        homePageLinkFT.click()
        assertTitle 'Minute Menu Plan : Homepage List'
        byClass('addNewHomePageFT').click()
        assertTitle 'Minute Menu Plan : Create Homepage'
        form('homePageCreate') {
            name = "Home_${System.currentTimeMillis()}"
            click("_action_save")
        }
        int finalCount = HomePage.count()
        assertTrue('Unable to create a new homepage', (finalCount - initialCount == 1))
        logout()
    }

    void test_Homepage_Create_Name_Not_Unique() {
        int initialCount = HomePage.count()
        loginBySuperAdmin()
        def homePageLinkFT = byClass('homePageLinkFT')
        homePageLinkFT.click()
        assertTitle 'Minute Menu Plan : Homepage List'
        byClass('addNewHomePageFT').click()
        assertTitle 'Minute Menu Plan : Create Homepage'
        form('homePageCreate') {
            name = "Home_Unique"
            click("_action_save")
        }
        int intermediateCount = HomePage.count()
        assertTrue('Unable to create a new homepage', (intermediateCount - initialCount == 1))

        def homePageLink = byClass('homePageLinkFT')
        homePageLink.click()
        assertTitle 'Minute Menu Plan : Homepage List'
        byClass('addNewHomePageFT').click()
        assertTitle 'Minute Menu Plan : Create Homepage'
        form('homePageCreate') {
            name = "Home_Unique"
            click("_action_save")
        }
        assertTitle 'Minute Menu Plan : Create Homepage'
        int finalCount = HomePage.count()
        assertTrue('Created new homepage with Non-Unique name', (finalCount - intermediateCount == 0))
        logout()
    }

    void test_Homepage_Create_HomePage_Edit_HomePage() {
        int initialCount = HomePage.count()
        loginBySuperAdmin()
        def homePageLinkFT = byClass('homePageLinkFT')
        homePageLinkFT.click()
        assertTitle 'Minute Menu Plan : Homepage List'
        byClass('addNewHomePageFT').click()
        assertTitle 'Minute Menu Plan : Create Homepage'
        form('homePageCreate') {
            name = "Home_${System.currentTimeMillis()}"
            click("_action_save")
        }
        int intermediateCount = HomePage.count()
        assertTrue('Unable to create a new homepage', (intermediateCount - initialCount == 1))

        def editHomePage = byClass('editHomePageFT')
        editHomePage.click()
        assertTitle 'Minute Menu Plan : Edit Homepage'
        byClass('updateHomePageFT').click()
        redirectEnabled = false
        followRedirect()
        assertTitle 'Minute Menu Plan : Preview Homepage'
        int finalCount = HomePage.count()
        assertTrue('Created new homepage with while updating', (finalCount - intermediateCount == 0))
        logout()
    }
}
