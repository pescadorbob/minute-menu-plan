import com.mp.domain.*
import org.codehaus.groovy.grails.commons.ApplicationHolder
import org.apache.commons.math.fraction.Fraction
import org.apache.commons.math.fraction.ProperFractionFormat
import java.text.FieldPosition
import org.apache.commons.math.fraction.FractionFormat

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

        Fraction.metaClass.constructor << { String stringToParse ->
            new ProperFractionFormat().parse(stringToParse)
        }

        Fraction.metaClass.myFormatUsingProperFractionFormat = {  ->
            new ProperFractionFormat().format(delegate,new StringBuffer(),new FieldPosition(0))
        }
        
        Fraction.metaClass.myFormatUsingFractionFormat = {  ->
            new FractionFormat().format(delegate)
        }

        bootstrapMasterData()
        bootstrapService.populateCategory(10)
        bootstrapService.populateQuantities(20)
        bootstrapService.populateMeasuredProduct(50)

        bootstrapService.createRecipes(5)

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