import grails.util.GrailsUtil
import org.codehaus.groovy.grails.commons.GrailsApplication
import org.codehaus.groovy.grails.commons.ConfigurationHolder
import javax.servlet.http.Cookie
import com.mp.tools.UserTools
import com.mp.domain.PartyRoleType
import com.mp.domain.access.AccessFilterType
import com.mp.domain.access.AccessFilter
import com.mp.tools.AccessTools
import com.mp.analytics.AppRequestCO
import javax.servlet.http.HttpServletRequest
import com.mp.analytics.TestScenarioCO
import com.mp.analytics.AnalyticsService
import com.mp.domain.LoginCredential
import com.mp.domain.ShoppingList
import static com.mp.MenuConstants.*;

class ApplicationFilters {

    def subscriptionService
    def permissionFilterService
    AnalyticsService analyticsService
    def filters = {
        cacheImage(controller: 'image', action: 'image') {
            before = {
                response.setHeader('Connection', 'keep-alive')
                response.setHeader('CacheControl', "public")
                response.setDateHeader('Expires', (new Date() + (10 * 365)).time)
            }
        }

        if (GrailsUtil.environment != GrailsApplication.ENV_PRODUCTION) {
            debug(controller: '*', action: '*') {
                before = {
                    long now = System.currentTimeMillis()
                    HttpServletRequest req = request
                    println("GrailsAccessLog-in:${now}:${params}");
                    TestScenarioCO testScenario = session.'active-scenario'
                    AppRequestCO r = analyticsService.recordRequestIn(now, params.toString(), testScenario)
                    session.'active-scenario' = r.within
                    analyticsService.recordIntervalIn(now, r, "controller", params.toString())
                    req.setAttribute('appRequestCO', r)
                }
                after = {
                    HttpServletRequest req = request
                    if (request.'reset-scenario') return
                    def now = System.currentTimeMillis()
                    AppRequestCO r = analyticsService.recordIntervalOut(now, req.getAttribute('appRequestCO'), "controller")
                    analyticsService.recordIntervalIn(now, r, "view", params.toString())
                }
                afterView = {
                    if (request.'reset-scenario') return
                    HttpServletRequest req = request
                    def now = System.currentTimeMillis()
                    AppRequestCO r = analyticsService.recordIntervalOut(now, req.getAttribute('appRequestCO'), "view")
                    analyticsService.recordIntervalIn(now, r, "after-view", params.toString())
                }
            }

        }

        populateDecorations(controller: '*', action: '*') {
            before = {
                analyticsService.recordIntervalIn(System.currentTimeMillis(), request.'appRequestCO', "shopping-list-find-filter", '')
                LoginCredential user = UserTools.currentUser
                def shoppingLists = user ? ShoppingList.executeQuery('select id, name from ShoppingList as s where s.party = ?', [user?.party]) : []
                request.'shoppingLists' = shoppingLists
                analyticsService.recordIntervalOut(System.currentTimeMillis(), request.'appRequestCO', "shopping-list-find-filter")

            }
        }
        verifyAccess(controller: '*', action: '*') {
            before = {
//                println("Verifying Access:${new Date()}:${params}");
                def start = System.currentTimeMillis()
                analyticsService.recordIntervalIn(start, request.'appRequestCO', "verify-access", params.toString())
                if(!request.requestURI ==~ '.*js|.*css|.*image.*' )
                {
                    List<Cookie> cookies = request.cookies as List
                    Cookie guestVisitor = cookies.find {it.name == 'guestVisitor'}
                    if (params.action != 'search') {
                        if ((params.controller != 'paypal') && !(params.action in ['clickBankPromotion', 'newClickBankSubscription'])) {
                            if (params.controller in ['recipe', 'menuPlan', 'shoppingList'] && params.action in ['show'] && params.guestVisitor) {
                                if (!guestVisitor && !UserTools.currentUser) {
                                    guestVisitor = new Cookie('guestVisitor', 'true');
                                    guestVisitor.path = "/"
                                    guestVisitor.maxAge = 365 * 24 * 60 * 60 //for 1 year/365 days
                                    response.addCookie(guestVisitor)
                                }
                            } else if (!AccessTools.isUnrestrictedAccess(params.controller, params.action, params.uri, response, request, guestVisitor)) {
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
                    }
                }
                if (log.isDebugEnabled()) log.debug("Unrestricted Access");
                if (log.isDebugEnabled()) log.debug("Time in verifyUserHasPermission Filter:${(System.currentTimeMillis() - start)}");
                AppRequestCO r = analyticsService.recordIntervalOut(System.currentTimeMillis(), request.'appRequestCO', "verify-access")

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
        checkPreview(controller: '*', action: '*') {
            before = {
                if (!UserTools.currentUser && params.controller in ['recipe', 'menuPlan', 'shoppingList'] && params.action in ['show', 'list']) {
                    List<Cookie> cookies = request.cookies as List
                    Cookie previewTracker = cookies.find {it.name == PREVIEW_TRACKER}
                    if (!previewTracker) {
                        previewTracker = new Cookie(PREVIEW_TRACKER, '1');
                        previewTracker.maxAge = 60 * 60 * 24 * 30
                        previewTracker.path = "/"
                        response.addCookie(previewTracker)
                    } else {
                        long visitNum = Long.parseLong(previewTracker.getValue())
                        visitNum++
                        previewTracker.setValue("" + visitNum);
                        previewTracker.maxAge = (60 * 60 * 24 * 30)
                        previewTracker.path = "/"
                        response.addCookie(previewTracker)
                        if (visitNum >= 6) {
                            previewTracker.setValue("0");
                            previewTracker.maxAge = (60 * 60 * 24 * 30)
                            previewTracker.path = "/"
                            response.addCookie(previewTracker)
                            flash.message = "Your Preview has ended - want to see more?  Why not join now?  It's free."
                            redirect(controller: 'user', action: 'chooseSubscription', params: params)
                        }
                    }
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
        def start = System.currentTimeMillis()
        def now = new Date()
        def isVerified = true
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
        if (requiresPermission && !UserTools.hasRole(PartyRoleType.SuperAdmin)) {
            isVerified = permissionFilterService.verifyPermission(UserTools.currentUser, params.controller, params.action, request.requestURI, now)
        }
        def end = System.currentTimeMillis()
        if (log.isDebugEnabled()) log.debug("Time in verifyUserHasPermission Filter:${(end - start)}");

        return isVerified
    }


    private boolean verifyUserHasSubscription(params, request, response) {
        def now = new Date()
        def start = System.currentTimeMillis()
        def isVerified = true

        if (!UserTools.hasRole(PartyRoleType.SuperAdmin)) {
            isVerified = subscriptionService.verifySubscription(UserTools.currentUser, params.controller, params.action, request.requestURI, now)
        }
        if (log.isDebugEnabled()) log.debug("Time in verifyUserHasSubscription Filter:${(System.currentTimeMillis() - start)}");

        return isVerified
    }
}