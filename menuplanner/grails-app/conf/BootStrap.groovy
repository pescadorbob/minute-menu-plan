import com.mp.domain.*
import org.codehaus.groovy.grails.commons.ApplicationHolder

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