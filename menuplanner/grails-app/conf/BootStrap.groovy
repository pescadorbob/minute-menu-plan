import org.apache.commons.math.fraction.*
import java.text.FieldPosition
import grails.util.GrailsUtil
import grails.util.Environment
import org.codehaus.groovy.grails.commons.ApplicationHolder
import org.codehaus.groovy.grails.commons.ConfigurationHolder
import com.mp.domain.*
import liquibase.Liquibase
import liquibase.FileSystemFileOpener
import liquibase.database.DatabaseFactory
import static com.mp.MenuConstants.*
import org.apache.commons.lang.StringUtils
import com.mp.domain.subscriptions.Feature
import com.mp.domain.themes.HomePage
import com.mp.domain.themes.Theme
import com.mp.domain.accounting.Account
import com.mp.domain.party.Party
import com.mp.domain.party.Subscriber
import com.mp.domain.accounting.AccountTransaction

class BootStrap {

    def dataSource
    def grailsApplication
    def bootstrapService
    def masterDataBootStrapService
    def excelService
    def searchableService
    def recipeService
    static config = ConfigurationHolder.config

    def init = {servletContext ->
        config.bootstrapMode = true
        // Inject the helper s() method
        Object.metaClass.s = {
            def object = delegate.save(flush: true)
            if (!object) {
                delegate.errors.allErrors.each {
                    println it
                }
            }
            object
        }

        Object.metaClass.trimLength = {Integer stringLength ->

            String trimString = delegate?.toString()
            String concatenateString = "..."
            List separators = [".", " "]

            if (stringLength && (trimString?.length() > stringLength)) {
                trimString = trimString.substring(0, stringLength - concatenateString.length())
                String separator = separators.findAll {trimString.contains(it)}?.min {trimString.lastIndexOf(it)}
                if (separator) {
                    trimString = trimString.substring(0, trimString.lastIndexOf(separator))
                }
                trimString += concatenateString
            }
            return trimString
        }

        Fraction.metaClass.constructor << {String stringToParse ->
            new ProperFractionFormat().parse(stringToParse)
        }

        Fraction.metaClass.myFormatUsingProperFractionFormat = {->
            String f = new ProperFractionFormat().format(delegate, new StringBuffer(), new FieldPosition(0))?.toString()
            if (f && f.endsWith('0 / 1')) {
                f = f.tokenize(" ").first()
            }
            return f
        }

        Fraction.metaClass.myFormatUsingFractionFormat = {->
            new FractionFormat().format(delegate)
        }

        bootstrapMasterData()
//        if (false) {
        if ((GrailsUtil.environment != Environment.PRODUCTION) && !Subscriber.count()) {

            if (GrailsUtil.environment in ['qa', 'beta']) {
                bootstrapService.populateBetaUsers()
            } else {
                Map<String, List<String>> userNames
                if (GrailsUtil.environment == 'test') {
                    userNames = ['superAdmin': [PartyRoleType.SuperAdmin], 'user1': [PartyRoleType.Subscriber]]
                } else {
                    userNames = ['superAdmin': [PartyRoleType.SuperAdmin], 'admin': [PartyRoleType.Admin],
                            'director': [PartyRoleType.Director,PartyRoleType.Subscriber],
                            'coach': [PartyRoleType.Coach,PartyRoleType.Subscriber],'user1': [PartyRoleType.Subscriber]]
                }
                userNames.each {String name, roles ->
                    println "Populating User - ${name}"
                    bootstrapService.populateUser(name, roles)
                }
            }
            println "Populated Users"


            String recipeFileName = (GrailsUtil.environment in ['qa', 'beta']) ? "/bootstrapData/recipeSpreadsheet.xls" : "/bootstrapData/recipeSpreadsheet_test.xls"
            File recipeExcelFile = new File(ApplicationHolder.application.parentContext.servletContext.getRealPath(recipeFileName))
            List<String> recipeLog = excelService.createLineItems(recipeExcelFile)
            println "Populated Recipes"
            bootstrapService.addCommentsFavouriteAndContributed()
            println "Added Comments Favourite And Contributed"
            if (GrailsUtil.environment == 'test') {
                bootstrapService.addAbusesOnCommentsAndRecipes()
                println "Added abuses on comments & recipes"
                List<Party> users = Party.list()
                users.each {Party user ->
                    bootstrapService.populateMenuPlans(user)
                }
                println "Populated Menu Plans"
                List<MenuPlan> menuPlans = MenuPlan.list()
                menuPlans.each {MenuPlan menuPlan ->
                    bootstrapService.populateShoppingList(menuPlan)
                }
                println "Populated Shopping Lists"
            }
            bootstrapService.populateSubscriptions('user1','Full Access')
            bootstrapService.populateQuickFills(5)
            println "Populated Quick Fills"
        }

        if (!(Environment.current in [Environment.DEVELOPMENT, Environment.TEST])) {
            executeLiquibase()
        }

        Thread.start {
            searchableService.index()
        }


        config.bootstrapMode = false
    }
    def destroy = {
    }

    private void executeLiquibase() {

        Liquibase liquibase = null
        try {
            def c = dataSource.getConnection()
            if (c == null) {
                throw new RuntimeException("Connection could not be created.");
            }
            def fileOpener = new FileSystemFileOpener()
            def database = DatabaseFactory.getInstance().findCorrectDatabaseImplementation(c)
            database.setDefaultSchemaName(c.catalog)
            String filePath = grailsApplication.isWarDeployed() ? ApplicationHolder.application.parentContext.servletContext.getRealPath("migrations/changelog.xml") : "grails-app/migrations/changelog.xml"
            liquibase = new Liquibase(filePath, fileOpener, database);
            liquibase.update(null)
        }
        finally {
            if (liquibase && liquibase.database) {
                liquibase.database.close()
            }
        }
    }

    private void bootstrapMasterData() {
        masterDataBootStrapService.populateAlcoholicContentList()
        if (!SystemOfUnit.count()) {masterDataBootStrapService.populateSystemOfUnits()}
        if (!Time.count()) {masterDataBootStrapService.populateTimeUnits()}
        if (StandardConversion.count() < 3) {
            masterDataBootStrapService.populateUnitsAndStandardConversions()
        }
        if (!Nutrient.count()) {masterDataBootStrapService.populateNutrients()}
        if (!Category.count()) {masterDataBootStrapService.populateCategories()}
        if (!Product.count()) {masterDataBootStrapService.populateProductsWithAisles()}
        if (!SecurityRole.count()) {masterDataBootStrapService.populatePermissions()}
        if (!HomePage.count()) {masterDataBootStrapService.populateHomePageData()}
        if (!Feature.count()) {masterDataBootStrapService.populateSubscriptions()}
        if (!Testimonial.count()) {masterDataBootStrapService.populateTestimonials()}
        if (!Theme.count()) {masterDataBootStrapService.populateThemes()}
        if (!Account.count() && !AccountTransaction.count()) {masterDataBootStrapService.populateAccounts()}
    }
}
