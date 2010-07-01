package com.mp.domain

import org.codehaus.groovy.grails.commons.ConfigurationHolder as CH
import org.codehaus.groovy.grails.commons.ConfigurationHolder

class FacebookTagLib {

    static namespace = 'facebook'
    def connect = {
        if (!User.currentUser.fbOauthToken) {
            String apiKey = ConfigurationHolder.config.facebookConnect.apiKey
            String popupUrl = "http://www.facebook.com/login.php?api_key=${apiKey}&connect_display=popup&v=1.0"
            String allowUrl = "http://localhost:8080/menuplanner/user/facebookConnect/"
            String cancelUrl = 'http://localhost:8080/menuplanner/util/index'
            String applicationUrl = "${popupUrl}&next=${allowUrl}&cancel_url=${cancelUrl}&fbconnect=true&return_session=true&session_key_only=true&req_perms=read_stream, offline_access"
            println "******************* ApplicationUrl: " + applicationUrl
            out << g.render(template: '/facebook/connectToFacebook', model: [applicationUrl: applicationUrl, apiKey: apiKey])
        }
    }

    def facebookConnectService

    //Example: <g:facebookConnectJavascript base="http://example.com" secure="true"/>
    //Base attribute is optional.
    //Secure attribute is optional.  You should set it to true if your site uses ssl
    def facebookConnectJavascript = {attrs, body ->
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
        out << "FB.init(\"${CH.config.facebookConnect.APIKey}\", \"${attrs['base'] ? attrs['base'] : ""}"

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
