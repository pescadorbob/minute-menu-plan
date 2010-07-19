import grails.util.GrailsUtil
import org.codehaus.groovy.grails.commons.GrailsApplication
import com.mp.domain.Subscriber
import com.mp.domain.LoginCredential

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
                if (!LoginCredential.currentUser && !(params.controller in ['util', 'user', 'login', 'image', 'subscription'])) {

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

