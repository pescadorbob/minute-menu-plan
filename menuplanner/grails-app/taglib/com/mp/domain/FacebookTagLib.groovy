package com.mp.domain

import org.codehaus.groovy.grails.commons.ConfigurationHolder as CH
import org.codehaus.groovy.grails.commons.ConfigurationHolder
import grails.util.GrailsUtil
import com.mp.domain.party.Party

class FacebookTagLib {

    static namespace = 'facebook'
    def connect = {attrs ->
        if (!(GrailsUtil.environment in ['test'])) {
            Long userId = attrs['userId'] ? attrs['userId'].toLong() : 0L
            Party party = userId ? Party.get(userId) : null
            if(party) println "User.name: " + party?.name
            if (!party?.facebookAccount) {
                String apiKey = ConfigurationHolder.config.facebookConnect.apiKey
                String allowUrl = g.createLink(controller: 'user', action: 'facebookConnect', absolute: true, params: [userId: userId]).encodeAsURL()
                String applicationUrl = "https://graph.facebook.com/oauth/authorize?client_id=${apiKey}&redirect_uri=${allowUrl}&scope=read_stream,offline_access,user_location,email"
                out << g.render(template: '/facebook/connectToFacebook', model: [applicationUrl: applicationUrl, apiKey: apiKey, party: party])
            } else {
                out << g.render(template: '/facebook/disconnectFromFacebook', model: [party: party])

            }
        }
    }

    def freeUserConnect = {attrs ->
        String coachId = attrs['coachId']
        coachId = coachId ? coachId : ''
        SessionUtils.session.coachUniqueId = coachId
        if (!(GrailsUtil.environment in ['test'])) {
            String apiKey = ConfigurationHolder.config.facebookConnect.apiKey
            String allowUrl = g.createLink(controller: 'user', action: 'facebookConnect', absolute: true,).encodeAsURL()
            String applicationUrl = "https://graph.facebook.com/oauth/authorize?client_id=${apiKey}&redirect_uri=${allowUrl}&scope=read_stream,offline_access,user_location,email"
            out << g.render(template: '/facebook/connectToFacebook', model: [applicationUrl: applicationUrl, apiKey: apiKey])
        }
    }

    def facebookConnectService

    //Example: <g:facebookConnectJavascript base="http://example.com" secure="true"/>
    //Base attribute is optional.
    //Secure attribute is optional.  You should set it to true if your site uses ssl
    def facebookConnectJavascript = {attrs, body ->
        if (!(GrailsUtil.environment in ['test'])) {
            out << '''\n'''
            out << '''\n'''
            out << '''\n'''
            out << '''<!-- BEGIN: Javascript for facebook connect -->'''
            out << '''\n'''

            if (attrs['secure'])
                out << '''<script type="text/javascript" src="https://static.ak.connect.facebook.com/js/api_lib/v0.4/FeatureLoader.js.php"></script>'''
            else out << '''<script type="text/javascript" src="http://static.ak.connect.facebook.com/js/api_lib/v0.4/FeatureLoader.js.php"></script>'''

            out << '''\n'''
            out << "<script type=\"text/javascript\">"
            out << "FB.init(\"${CH.config.facebookConnect.apiKey}\", \"${attrs['base'] ? attrs['base'] : ""}"

            if (attrs['secure'])
                out << "${g.resource(dir: "/facebook", file: "xd_receiver_ssl.htm")}\");"
            else out << "${g.resource(dir: "/facebook", file: "xd_receiver.htm")}\");"

            out << "</script>"
            out << '''\n'''
            out << '''<!-- END: Javascript for facebook connect -->'''
            out << '''\n'''
            out << '''\n'''
            out << '''\n'''
        }
    }
}
