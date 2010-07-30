import org.apache.commons.math.fraction.*
import java.text.FieldPosition
import grails.util.GrailsUtil
import grails.util.Environment
import org.codehaus.groovy.grails.commons.ApplicationHolder
import org.codehaus.groovy.grails.commons.ConfigurationHolder
import com.mp.domain.*

class BootStrap {

    def bootstrapService
    def masterDataBootStrapService
    def excelService
    def searchableService
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
            Map<String, List<String>> userNames
            if(GrailsUtil.environment == 'qa'){
                userNames = ['superAdmin' : ['SuperAdmin'], 'admin1' : ['Admin'], 'admin2' : ['Admin'], 'user1' : ['Admin'], 'user2' : ['Subscriber']]
            } else {
                userNames = ['superAdmin': ['SuperAdmin'], 'user1': ['Subscriber']]
            }
            userNames.each {String name, List<String> roles ->
                println "Populating User - ${name}"
                bootstrapService.populateUser(name, roles)
            }
            println "Populated Users"


            String recipeFileName = (GrailsUtil.isDevelopmentEnv()) ? "/bootstrapData/recipeSpreadsheet_test.xls" : "/bootstrapData/recipeSpreadsheet.xls"
            File recipeExcelFile = new File(ApplicationHolder.application.parentContext.servletContext.getRealPath(recipeFileName))
            List<String> recipeLog = excelService.createLineItems(recipeExcelFile)
            println "Populated Recipes"
            bootstrapService.addCommentsFavouriteAndContributed()
            println "Added Comments Favourite And Contributed"
            bootstrapService.addAbusesOnCommentsAndRecipes()
            println "Added abuses on comments & recipes"
            List<Party> users = Party.list()
            users.each {Party user ->
                bootstrapService.populateMenuPlans(user)
            }
            println "Populated Menu Plans"
            bootstrapService.populateQuickFills(5)
            println "Populated Quick Fills"
            List<MenuPlan> menuPlans = MenuPlan.list()
            menuPlans.each {MenuPlan menuPlan ->
                bootstrapService.populateShoppingList(menuPlan)
            }
            println "Populated Shopping Lists"
        }

        Thread.start {
            searchableService.index()
        }
        config.bootstrapMode = false
    }
    def destroy = {
    }

    private void bootstrapMasterData() {
        if (!SystemOfUnit.count()) {
            masterDataBootStrapService.populateSystemOfUnits()
            masterDataBootStrapService.populateTimeUnits()
            masterDataBootStrapService.populateUnitsAndStandardConversions()
            masterDataBootStrapService.populateNutrients()
            masterDataBootStrapService.populateAisles()
            masterDataBootStrapService.populateCategories()
            String productsFileName = (GrailsUtil.isDevelopmentEnv()) ? "/bootstrapData/FOOD_DES_TEST.txt" : "/bootstrapData/FOOD_DES.txt"
            File productsFile = new File(ApplicationHolder.application.parentContext.servletContext.getRealPath(productsFileName))
            masterDataBootStrapService.populateProductsWithAisles(productsFile)
        }
        masterDataBootStrapService.populatePermissions()
    }
}