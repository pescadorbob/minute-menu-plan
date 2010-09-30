import grails.util.GrailsUtil
import org.codehaus.groovy.grails.commons.GrailsApplication
import com.mp.domain.LoginCredential
import org.codehaus.groovy.grails.commons.ConfigurationHolder
import javax.servlet.http.Cookie

class ApplicationFilters {

    def filters = {

        cacheImage(controller: 'image') {
            before = {
                response.setHeader('Connection', 'keep-alive')
                response.setHeader('CacheControl', "public")
                response.setDateHeader('Expires', (new Date() + (10 * 365)).time)
            }
        }

        debug(controller: '*', action: '*') {
            before = {
                if (GrailsUtil.environment != GrailsApplication.ENV_PRODUCTION) {
                    println("GrailsAccessLog:${new Date()}:${params}");
                }
            }
        }

        verifyUserIsLoggedIn(controller: '*', action: '*') {
            before = {
                List<Cookie> cookies = request.cookies as List
                Cookie guestVisitor = cookies.find {it.name == 'guestVisitor'}
                if (params.controller in ['recipe', 'menuPlan', 'shoppingList'] && params.action in ['show'] && params.guestVisitor) {
                    if (!guestVisitor && !LoginCredential.currentUser) {
                        guestVisitor = new Cookie('guestVisitor', 'true');
                        guestVisitor.path = "/"
                        guestVisitor.maxAge = 365 * 24 * 60 * 60 //for 1 year/365 days
                        response.addCookie(guestVisitor)
                    }
                } else {
                    if (!((params.controller in ['util', 'login', 'image', 'subscription']) ||
                            ((params.controller == 'user') &&
                                    (params.action in ['create', 'createUser', 'createFreeUser', 'newFreeUserSignUp', 'enableUser', 'newUserCheckout', 'welcome', 'facebookConnect'])))) {
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
                                if (guestVisitor) {
                                    redirect(controller: 'user', action: 'createFreeUser')
                                } else {
                                    redirect(controller: 'login', action: 'index', params: params)
                                }
                            }
                            return false
                        }
                    }
                }
            }

            after = {
                if (params.controller == 'recipe' && params.action == 'list') {
                    Cookie guestVisitor = new Cookie('guestVisitor', 'false');
                    guestVisitor.path = "/"
                    guestVisitor.version = -1
                    guestVisitor.maxAge = 0
                    response.addCookie(guestVisitor)
                }
            }
        }

    }

}
