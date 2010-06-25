import grails.util.GrailsUtil
import org.codehaus.groovy.grails.commons.GrailsApplication
import com.mp.domain.User

class ApplicationFilters {

    def filters = {

        debug(controller: '*', action: '*') {
            before = {
                if (GrailsUtil.environment != GrailsApplication.ENV_PRODUCTION) {
                    println("GrailsAccessLog:${new Date()}:${params}");
                }
            }
        }

        verifyUserIsLoggedIn(controller: '*', action: '*') {
            before = {
                if (!User.currentUser &&  !(params.controller in ['util', 'user', 'login', 'image'])) {

                    if (!params.targetUri) {
                        String targetUri = request.forwardURI.toString() - request.contextPath.toString()
                        if (!(targetUri == null || targetUri == "/")) {
                            params.targetUri = targetUri
                        }
                    }
                    redirect(controller: 'login', action: 'index', params: params)
                    return false
                }
            }
        }
    }
}

