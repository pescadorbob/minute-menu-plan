import grails.util.GrailsUtil
import org.codehaus.groovy.grails.commons.GrailsApplication

class ApplicationFilters {
	
	def filters = {
		
		debug(controller: '*', action: '*') {
			before = {
				if (GrailsUtil.environment != GrailsApplication.ENV_PRODUCTION) {
					println("GrailsAccessLog:${new Date()}:${params}");
				}
			}
		}
		
	}
}
