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

class BootStrap {

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
//        if (false) {
    if ((GrailsUtil.environment != Environment.PRODUCTION) && !Subscriber.count()) {

            if (GrailsUtil.environment in ['qa', 'beta']) {
                bootstrapService.populateBetaUsers()
            } else {
                Map<String, List<PartyRoleType>> userNames = ['superAdmin': [PartyRoleType.SuperAdmin], 'admin': [PartyRoleType.Admin],
                            'director': [PartyRoleType.Director,PartyRoleType.Subscriber],
                            'coach': [PartyRoleType.Coach,PartyRoleType.Subscriber],'user1': [PartyRoleType.Subscriber]]
                userNames.each {String name, roles ->
                    println "Populating User - ${name} : ${roles}"
                    bootstrapService.populateUser(name, roles*.name())
                }
            }
            println "Populated Users"
            if (!Account.count() && !AccountTransaction.count()) {masterDataBootStrapService.populateAccounts()}



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
      bootstrapService.populateQuickFills(5)
      println "Populated Quick Fills"
    }

//      if ((GrailsUtil.environment != Environment.PRODUCTION) && !Subscription.count()) {
//        def now = new Date()
//        subscriptionService.generateSubscription(Party.findByName('user1').id,ProductOffering.findByName("1 Month Free Trial").id,now)
//        subscriptionService.generateSubscription(Party.findByName('coach').id,ProductOffering.findByName("Basic Monthly Subscription").id,now)
//        subscriptionService.generateSubscription(Party.findByName('director').id,ProductOffering.findByName("Basic Yearly Subscription").id,now)
//        println "Populated Subscriptions"
//      }
        if (!(GrailsUtil.environment in ['development', 'qa', 'test'])) {
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
        if (!Product.count() && !(GrailsUtil.environment in ['development', 'test'])) {masterDataBootStrapService.populateProductsWithAisles()}
        if (!SecurityRole.count()) {masterDataBootStrapService.populatePermissions()}
        if (!HomePage.count()) {masterDataBootStrapService.populateHomePageData()}
        if (!Feature.count()) {masterDataBootStrapService.populateProductOfferings()}
        if (!Testimonial.count()) {masterDataBootStrapService.populateTestimonials()}
        if (!Theme.count()) {masterDataBootStrapService.populateThemes()}
        if (!AccessFilterSet.count()) {masterDataBootStrapService.populateAccessFilterSets()}
    }
}
