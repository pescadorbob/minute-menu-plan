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
import com.mp.domain.subscriptions.Subscription
import com.mp.domain.access.SecurityRole
import com.mp.domain.access.AccessFilterSet
import com.mp.MenuConstants
import com.mp.domain.accounting.OperationalAccount
import com.mp.domain.accounting.AccountRole
import com.mp.domain.accounting.AccountRoleType
import com.mp.domain.party.SuperAdmin
import com.mp.domain.ndb.NDBFileInfo
import com.mp.domain.ndb.NDBFood

class BootStrap {

  def utilService
    def dataSource
    def grailsApplication
    def bootstrapService
    def subscriptionService
    def masterDataBootStrapService
    def excelService
    def searchableService
    def recipeService
    def wcmSecurityService
    static config = ConfigurationHolder.config


    def init = {servletContext ->
//    wcmSecurityService.securityDelegate = WcmSecurityDelegate.delegate
        config.bootstrapMode = true
        // Inject the helper s() method
        Object.metaClass.s = MenuConstants.s

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

        if (!(GrailsUtil.environment in [Environment.PRODUCTION, 'qa']) && !Subscriber.count()) {
                Map<String, List<PartyRoleType>> userNames = ['superAdmin': [PartyRoleType.SuperAdmin], 'admin': [PartyRoleType.Admin],
                        'director': [PartyRoleType.Director, PartyRoleType.Subscriber],
                        'coach': [PartyRoleType.Coach, PartyRoleType.Subscriber], 'user1': [PartyRoleType.Subscriber]]
                userNames.each {String name, roles ->
                    println "Populating User - ${name} : ${roles}"
                    bootstrapService.populateUser(name, roles*.name())
                }
            println "Populated Users"
            if (!Account.count() && !AccountTransaction.count()) {masterDataBootStrapService.populateAccounts()}

            String recipeFileName = (GrailsUtil.environment in ['qa', 'beta']) ? "/bootstrapData/recipeSpreadsheet.xls" : "/bootstrapData/recipeSpreadsheet_test.xls"
            File recipeExcelFile = new File(ApplicationHolder.application.parentContext.servletContext.getRealPath(recipeFileName))
            excelService.createLineItems(recipeExcelFile)
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
            bootstrapService.populateQuickFills(5)
            println "Populated Quick Fills"
        }

        allocateTrialSubscriptions()

        Thread.start {
//            searchableService.index()
        }

        config.bootstrapMode = false
    }
    def destroy = {
    }

    void allocateTrialSubscriptions(){
        println "Allocating Trial Subscriptions."

      long startTime = System.currentTimeMillis()

//      Book.findAll("from Book as b where not exists (from Borrow as br where br.book = b)")
      def subscribers = Subscriber.findAll("from Subscriber s where not exists (from Subscription sub where sub.subscriptionFor = s)")
        Set<Party> parties = subscribers*.party as Set
        parties.each {Party party ->
            utilService.allocateTrialSubscription(party)
        }
        long endTime = System.currentTimeMillis()
        println "Trial subscription allocated to ${parties.size()} users in ${((endTime - startTime).toFloat() / 1000)} Seconds."

    }

    private void bootstrapMasterData() {
      masterDataBootStrapService.populateAlcoholicContentList()
      // prime StandardConversions, Units, Time, Categories,
      // NDBFileInfo,NDBFood, NDBWeight
      println 'loading caches'
      def start = System.currentTimeMillis()
      def cachedConversions = StandardConversion.findAll(
              'from StandardConversion as s \
      inner join fetch s.sourceUnit  source \
      inner join fetch s.targetUnit  target \
      inner join fetch source.systemOfUnits sourceSystem  \
      inner join fetch target.systemOfUnits targetSystem ')
      def cachedUnits = Unit.findAll(
              'from Unit as s \
      inner join fetch s.systemOfUnits system ')

      def cachedTimeUnits = Time.findAll(
              'from Time as t \
      inner join fetch t.systemOfUnits system ')

      def cachedCategories = Category.findAll(
              'from Category c \
      inner join fetch c.subCategories subcategories ')

      def nutritionalValues = NDBFood.findAll(
              'from NDBFood food \
      inner join fetch food.weights weights \
      inner join fetch food.fileInfo file ')
      def end = System.currentTimeMillis()
      println 'Caches loaded in ${(end-start)}ms'

        if (!SystemOfUnit.count()) {masterDataBootStrapService.populateSystemOfUnits()}
        if (!Time.count()) {masterDataBootStrapService.populateTimeUnits()}
        if (StandardConversion.count() < 3) {
            masterDataBootStrapService.populateUnitsAndStandardConversions()
        }
        if (!Nutrient.count()) {masterDataBootStrapService.populateNutrients()}
        if (!Category.count()) {masterDataBootStrapService.populateCategories()}
        if (!Product.count() && !(GrailsUtil.environment in ['development', 'test'])) {masterDataBootStrapService.populateProductsWithAisles()}
        if (!SecurityRole.count()) {masterDataBootStrapService.populatePermissions()}
        if (!HomePage.count()) {masterDataBootStrapService.populateHomePageData()}
        if (!Feature.count()) {masterDataBootStrapService.populateProductOfferings()}
        if (!Testimonial.count()) {masterDataBootStrapService.populateTestimonials()}
        if (!Theme.count()) {masterDataBootStrapService.populateThemes()}
        if (!AccessFilterSet.count()) {masterDataBootStrapService.populateAccessFilterSets()}
        if (!NDBFileInfo.findByFileVersion("23")&& !(GrailsUtil.environment in ['development', 'test'])) {
          masterDataBootStrapService.populateNDBFood()
          assert NDBFood.count() > 7600
        }
        if (!OperationalAccount.count() && SuperAdmin.count()) {
            OperationalAccount opAcct = new OperationalAccount(name: MMP_OPERATIONAL_ACCOUNT).s()
            def superAdmin = SuperAdmin.list().first().party
            new AccountRole(roleFor: superAdmin, describes: opAcct, type: AccountRoleType.OWNER).s()
        }
    }
}
