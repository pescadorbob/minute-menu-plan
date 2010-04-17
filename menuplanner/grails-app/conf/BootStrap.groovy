import org.apache.commons.math.fraction.*
import java.text.FieldPosition
import grails.util.GrailsUtil
import grails.util.Environment

class BootStrap {

    def bootstrapService
    def masterDataBootStrapService
    def init = {servletContext ->

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

        Fraction.metaClass.constructor << {String stringToParse ->
            new ProperFractionFormat().parse(stringToParse)
        }

        Fraction.metaClass.myFormatUsingProperFractionFormat = {->
            new ProperFractionFormat().format(delegate, new StringBuffer(), new FieldPosition(0))
        }

        Fraction.metaClass.myFormatUsingFractionFormat = {->
            new FractionFormat().format(delegate)
        }

//        bootstrapMasterData()
        if (false) {
            bootstrapService.populateCategory(10)
            println "Populated Categories"
            bootstrapService.populateQuantities(20)
            println "Populated Quantities"
            bootstrapService.populateMeasurableProduct(50)
            println "Populated Products"
            bootstrapService.populateRecipes((GrailsUtil.isDevelopmentEnv()) ? 20 : 150)
            println "Populated Recipes"
            bootstrapService.populateWeeks(4)
            println "Populated Weeks"
        }

    }
    def destroy = {
    }

    private void bootstrapMasterData() {
        masterDataBootStrapService.populateSystemOfUnits()
        masterDataBootStrapService.populateTimeUnits()
        masterDataBootStrapService.populateUnitsAndStandardConversions()
        masterDataBootStrapService.populateNutrients()
    }
}