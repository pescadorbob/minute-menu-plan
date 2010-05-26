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

        verifyUserIsLoggedIn(controller: '*', action: '*') {
            before = {
                println "Application Access Log: ${new Date()} : ${params}"
                if (!session.loggedUserId && params.controller!="login") {

                    if (!params.targetUri) {
                        String targetUri = request.forwardURI.toString() - request.contextPath.toString()
                        if (!(targetUri == null || targetUri == "/")) {
                            params.targetUri = targetUri
                    println ">>>>>>>"+params.targetUri        
                        }
                    }
                    redirect(controller: 'login', action: 'index', params: params)
                    return false
                }
            }
        }
    }
}

