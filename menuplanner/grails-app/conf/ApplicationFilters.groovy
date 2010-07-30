import grails.util.GrailsUtil
import org.codehaus.groovy.grails.commons.GrailsApplication
import com.mp.domain.LoginCredential
import org.codehaus.groovy.grails.commons.ConfigurationHolder

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
                if (!((params.controller in ['util', 'login', 'image', 'subscription']) || ((params.controller == 'user') && (params.action in ['create', 'createUser', 'enableUser', 'newUserCheckout', 'welcome', 'facebookConnect'])))) {
                    if (!(request.getSession(false) && LoginCredential.currentUser)) {
                        if (request.xhr) {
                            String text = "The Session TimedOut url=" + ConfigurationHolder.config.grails.serverURL
                            render(text: text, contentType: 'text/plain')
                        } else {
                            if (!params.targetUri) {
                                String targetUri = request.forwardURI.toString() - request.contextPath.toString()
                                if (!(targetUri == null || targetUri == "/")) {
                                    params.targetUri = targetUri
                                }
                            }
                            redirect(controller: 'login', action: 'index', params: params)
                        }
                        return false
                    }
                }
            }
        }

    }

}
