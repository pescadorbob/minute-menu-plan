import com.mp.domain.User

// locations to search for config files that get merged into the main config
// config files can either be Java properties files or ConfigSlurper scripts

// grails.config.locations = [ "classpath:${appName}-config.properties",
//                             "classpath:${appName}-config.groovy",
//                             "file:${userHome}/.grails/${appName}-config.properties",
//                             "file:${userHome}/.grails/${appName}-config.groovy"]

// if(System.properties["${appName}.config.location"]) {
//    grails.config.locations << "file:" + System.properties["${appName}.config.location"]
// }
grails.rateable.rater.evaluator = { User.currentUser }
grails.views.javascript.library = "jquery"
grails.mime.file.extensions = true // enables the parsing of file extensions from URLs into the request format
grails.mime.use.accept.header = false
grails.mime.types = [html: ['text/html', 'application/xhtml+xml'],
        xml: ['text/xml', 'application/xml'],
        text: 'text/plain',
        js: 'text/javascript',
        rss: 'application/rss+xml',
        atom: 'application/atom+xml',
        css: 'text/css',
        csv: 'text/csv',
        all: '*/*',
        json: ['application/json', 'text/json'],
        form: 'application/x-www-form-urlencoded',
        multipartForm: 'multipart/form-data'
]
// The default codec used to encode data with ${}
grails.views.default.codec = "none" // none, html, base64
grails.views.gsp.encoding = "UTF-8"
grails.converters.encoding = "UTF-8"
// enable Sitemesh preprocessing of GSP pages
grails.views.gsp.sitemesh.preprocess = true
// scaffolding templates configuration
grails.scaffolding.templates.domainSuffix = ''

// Set to false to use the new Grails 1.2 JSONBuilder in the render method
grails.json.legacy.builder = false
// enabled native2ascii conversion of i18n properties files
grails.enable.native2ascii = true
// whether to install the java.util.logging bridge for sl4j. Disable fo AppEngine!
grails.logging.jul.usebridge = true
// packages to include in Spring bean scanning
grails.spring.bean.packages = []
imagesRootDir = System.getProperty("java.io.tmpdir") + "/mpImages"
// set per-environment serverURL stem for creating absolute links
grails{
    mail {
        host = "smtp.gmail.com"
        port = 465
        username = "qa.menuplanner@gmail.com"
        password = "minutemenu"
        props = ["mail.smtp.auth": "true",
                "mail.smtp.socketFactory.port": "465",
                "mail.smtp.socketFactory.class": "javax.net.ssl.SSLSocketFactory",
                "mail.smtp.socketFactory.fallback": "false"]
    }
}

environments {
    production {
//        mail {
//            host = "www.menuplanner.com"
//            port = 25
//        }
//        grails.mail.default.from = '"MinuteMenu" <minutemenu@menuplanner.com>'
        grails.serverURL = "http://www.changeme.com"
        facebookConnect {
			//These two values need to be grabbed from facebook when you create your application there.
			// http://www.facebook.com/developers/
			apiKey = "c7cb03d1076112dfdee19d3e4972c3eb"
			secretKey = "2f069ab14fb4c6ab88cfe463aa9d4344"
		}
    }
    development {
//        mail {
//            host = "dev.menuplanner.intelligrape.net"
//            port = 25
//        }
//        grails.mail.default.from = '"MinuteMenu" <dev@dev.menuplanner.intelligrape.net>'
        grails.serverURL = "http://localhost:8080/${appName}"
        facebookConnect {
			//These two values need to be grabbed from facebook when you create your application there.
			// http://www.facebook.com/developers/
			apiKey = "8fc5bc0ed2fe785413bb1c028503a04c"
			secretKey = "73f7aba0121cc3527e04b02d15da407a"
		}
    }
    test {
//        mail {
//            host = "qa.menuplanner.intelligrape.net"
//            port = 25
//        }
//        grails.mail.default.from = '"MinuteMenu" <qa@qa.menuplanner.intelligrape.net>'
        grails.serverURL = "http://qa.menuplanner.intelligrape.net"
        facebookConnect {
			//These two values need to be grabbed from facebook when you create your application there.
			// http://www.facebook.com/developers/
            apiKey = "c7cb03d1076112dfdee19d3e4972c3eb"
            secretKey = "2f069ab14fb4c6ab88cfe463aa9d4344"
		}
    }
    qa {
//        mail {
//            host = "qa.menuplanner.intelligrape.net"
//            port = 25
//        }
//        grails.mail.default.from = '"MinuteMenu" <qa@qa.menuplanner.intelligrape.net>'
        grails.serverURL = "http://qa.menuplanner.intelligrape.net"
        facebookConnect {
			//These two values need to be grabbed from facebook when you create your application there.
			// http://www.facebook.com/developers/
            apiKey = "c7cb03d1076112dfdee19d3e4972c3eb"
            secretKey = "2f069ab14fb4c6ab88cfe463aa9d4344"
		}
    }

}

// log4j configuration
log4j = {
    // Example of changing the log pattern for the default console
    // appender:
    //
    //appenders {
    //    console name:'stdout', layout:pattern(conversionPattern: '%c{2} %m%n')
    //}


    error 'org.codehaus.groovy.grails.web.servlet',  //  controllers
            'org.codehaus.groovy.grails.web.pages', //  GSP
            'org.codehaus.groovy.grails.web.sitemesh', //  layouts
            'org.codehaus.groovy.grails.web.mapping.filter', // URL mapping
            'org.codehaus.groovy.grails.web.mapping', // URL mapping
            'org.codehaus.groovy.grails.commons', // core / classloading
            'org.codehaus.groovy.grails.plugins', // plugins
            'org.codehaus.groovy.grails.orm.hibernate', // hibernate integration
            'org.springframework',
            'org.hibernate',
            'net.sf.ehcache.hibernate'

    warn 'org.mortbay.log'
}


     