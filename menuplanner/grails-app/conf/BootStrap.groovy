import com.mp.domain.*
import org.codehaus.groovy.grails.commons.ApplicationHolder

class BootStrap {

    def bootstrapService

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
        bootstrapService.populateMetrics(10)
        bootstrapService.populateQuantities(20)
        bootstrapService.populateProduct(35)
        bootstrapService.populateMeasuredProduct(25)
        bootstrapService.populateRecipe(5)
    }
    def destroy = {
    }
}