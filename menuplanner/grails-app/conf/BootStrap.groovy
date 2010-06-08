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
        if (!GrailsUtil.environment != Environment.PRODUCTION && !User.count()) {
            bootstrapService.populateUsers()
            println "Populated Users"
            bootstrapService.populateCategory()
            println "Populated Categories"

/*
            File recipeExcelFile
            if (GrailsUtil.isDevelopmentEnv()) {
                recipeExcelFile = new File(ApplicationHolder.application.parentContext.servletContext.getRealPath("/bootstrapData/recipeSpreadsheet_test.xls"))
            } else {
                recipeExcelFile = new File(ApplicationHolder.application.parentContext.servletContext.getRealPath("/bootstrapData/recipeSpreadsheet.xls"))
            }
            List<String> recipeLog
            recipeExcelFile.withInputStream {inputStream ->
                recipeLog = excelService.createLineItems(inputStream)
                println "Populated Recipes"
            }
            bootstrapService.addCommentsFavouriteAndContributed()
            println "Added Comments Favourite And Contributed"
            bootstrapService.addAbusesOnCommentsAndRecipes()
            println "Added abuses on comments & recipes"
            bootstrapService.populateMenuPlans()
            println "Populated Menu Plans"
*/
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
            masterDataBootStrapService.populatePermissions()
        }
    }
}