import grails.util.GrailsUtil
import org.codehaus.groovy.grails.commons.GrailsApplication
import org.codehaus.groovy.grails.commons.ConfigurationHolder
import javax.servlet.http.Cookie
import com.mp.tools.UserTools
import com.mp.domain.PartyRoleType
import com.mp.domain.access.AccessFilterSet
import com.mp.domain.access.AccessFilterType
import com.mp.domain.access.AccessFilter
import com.mp.tools.AccessTools

class ApplicationFilters {

    def subscriptionService
    def permissionFilterService
    def filters = {
        cacheImage(controller: 'image', action: 'image') {
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

        verifyAccess(controller: '*', action: '*') {
            before = {
//                println("Verifying Access:${new Date()}:${params}");
                List<Cookie> cookies = request.cookies as List
                Cookie guestVisitor = cookies.find {it.name == 'guestVisitor'}
                if(params.controller != 'paypal'){
                if (params.controller in ['recipe', 'menuPlan', 'shoppingList'] && params.action in ['show'] && params.guestVisitor) {
                    if (!guestVisitor && !UserTools.currentUser) {
                        guestVisitor = new Cookie('guestVisitor', 'true');
                        guestVisitor.path = "/"
                        guestVisitor.maxAge = 365 * 24 * 60 * 60 //for 1 year/365 days
                        response.addCookie(guestVisitor)
                    }
                } else if (!AccessTools.isUnrestrictedAccess(params.controller, params.action, response, request, guestVisitor)) {
                    if (!(request.getSession(false) && UserTools.currentUser)) {
                        if (request.xhr) {
                            String text = "The Session TimedOut url=" + ConfigurationHolder.config.grails.serverURL
                            render(text: text, contentType: 'text/plain')
                        } else {
                            if (!params.targetUri) {
                                String targetUri = request.forwardURI.toString() - request.contextPath.toString()
                                if (!(targetUri == null || targetUri == "/")) { params.targetUri = targetUri }
                            }
                            flash.message = "The feature ${params.action} ${params.controller} you attempted to access requires an active account, and possibly a subscription and none was found."
                            redirect(controller: 'user', action: 'chooseSubscription', params: params)
                        }
                    }
                    if (!verifyUserHasPermission(params, request, response)) {
                        flash.message = "The feature ${params.action} ${params.controller} you attempted to access requires a permission and none was found."
                        redirect(controller: 'user', action: 'permissionError', params: params)
                    }
                    if (!verifyUserHasSubscription(params, request, response)) {
                        flash.message = "The feature ${params.action} ${params.controller} you attempted to access requires a subscription and none was found."
                        redirect(controller: 'user', action: 'chooseSubscription')
                    }
                }
                }
                if (log.isDebugEnabled()) log.debug("Unrestricted Access");
            }
            after = {model ->
                if (params.controller == 'recipe' && params.action == 'list') {
                    Cookie guestVisitor = new Cookie('guestVisitor', 'false');
                    guestVisitor.path = "/"
                    guestVisitor.version = -1
                    guestVisitor.maxAge = 0
                    response.addCookie(guestVisitor)
                }
            }
        }

        addCookie(controller: '*', action: '*') {
            before = {
                List<Cookie> cookies = request.cookies as List
                Cookie coachId = cookies.find {it.name == 'coachId'}
                if (params.coachId && !coachId) {
                    coachId = new Cookie('coachId', params.coachId);
                    coachId.maxAge = 60 * 60 * 24 * 30
                    coachId.path = "/"
                    response.addCookie(coachId)
                }
            }
        }

    }

    private boolean verifyUserHasPermission(params, request, response) {
        def now = new Date()
        def permissionFilters = AccessFilter.withCriteria {
            filterFor {
                eq('type', AccessFilterType.PERMISSION)
                gt('activeFrom', now)
                or {
                    isNull('activeTo')
                    lt('activeTo', now)
                }
            }
        }
        boolean requiresPermission = permissionFilters && permissionFilters.find {
            (!it.controllerFilter || params.controller ==~ it.controllerFilter) &&
                    (!it.actionFilter || params.action ==~ it.actionFilter) &&
                    (!it.uriFilter || request.requestURI ==~ it.uriFilter)
        }.size() > 0
        if (requiresPermission && !UserTools.hasRole(PartyRoletype.SuperAdmin)) {
            boolean isVerified = permissionFilterService.verifyPermission(UserTools.currentUser, params.controller, params.action, request.requestURI, now)
            return isVerified
        }
        return true
    }


    private boolean verifyUserHasSubscription(params, request, response) {
        def now = new Date()

        if (!UserTools.hasRole(PartyRoleType.SuperAdmin)) {
            boolean isVerified = subscriptionService.verifySubscription(UserTools.currentUser, params.controller, params.action, request.requestURI, now)
            return isVerified
        }
        return true
    }
}