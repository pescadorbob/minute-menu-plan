import com.mp.domain.Subscriber
import com.mp.domain.Subscriber
import com.mp.domain.LoginCredential

// locations to search for config files that get merged into the main config
// config files can either be Java properties files or ConfigSlurper scripts

// grails.config.locations = [ "classpath:${appName}-config.properties",
//                             "classpath:${appName}-config.groovy",
//                             "file:${userHome}/.grails/${appName}-config.properties",
//                             "file:${userHome}/.grails/${appName}-config.groovy"]

// if(System.properties["${appName}.config.location"]) {
//    grails.config.locations << "file:" + System.properties["${appName}.config.location"]
// }
grails.rateable.rater.evaluator = { LoginCredential.currentUser }
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
tempDir = System.getProperty("java.io.tmpdir") + "/mpImages/tempImages"
recipesRootDir = "/recipes/"
usersRootDir = "/users/"
sessions = []
// set per-environment serverURL stem for creating absolute links
grails {
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

uiperformance {
    html.compress = true
    keepOriginals = true
    processImages = false
    html.includeContentTypes = ['text/html', 'text/xml', 'text/plain', 'application/json']
}

uiperformance.bundles = [
        [type: 'js',
                name: 'allMenuJquery',
                files: ['jquery/jquery-1.4.1.min',
                        'tiny_mce/tiny_mce',
                        'createRecipe',
                        'jquery.autocomplete.min',
                        'jquery.tools.min',
                        'shoppingList',
                        'jquery.uploadify-v2.1.0/swfobject',
                        'jquery.uploadify-v2.1.0/jquery.uploadify.v2.1.0.min',
                        'jquery.lightbox-0.5']],

        [type: 'css',
                name: 'allMenuCss',
                files: ['common',
                        'layout',
                        'menuPlan',
                        'user',
                        'securityRole',
                        'jquery.autocomplete',
                        'jquery.lightbox-0.5']],

        [type: 'js',
                name: 'allPrintRecipeJquery',
                files: ['createRecipe',
                        'jquery.autocomplete.min',
                        'jquery.tools.min',
                        'menuplanner']],

        [type: 'css',
                name: 'allPrintRecipeCss',
                files: ['common',
                        'layout',
                        'user',
                        'jquery.autocomplete',
                        'securityRole']]

]

environments {
    production {
        grails.serverURL = "http://www.changeme.com"
        facebookConnect {
            apiKey = "c7cb03d1076112dfdee19d3e4972c3eb"
            secretKey = "2f069ab14fb4c6ab88cfe463aa9d4344"
        }
        googleCheckout {
//            merchantId = "827529108383447"
//            merchantKey = "ScAQ9VNMRniNUR3T5sW2kQ"
//            requestFormUrl = "https://checkout.google.com/api/checkout/v2/requestForm/Merchant/827529108383447"
//            action="https://checkout.google.com/api/checkout/v2/checkoutForm/Merchant/827529108383447"
//            imageSource="http://checkout.google.com/buttons/checkout.gif?merchant_id=827529108383447&w=180&h=46&style=white&variant=text&loc=en_US"
        }
        externalKeys {
            shareThisKey = "d50f28c4-1205-472e-93eb-ca6165bb6cf9"
        }
    }
    development {
        grails.serverURL = "http://localhost:8080/menuplanner"
        facebookConnect {
            apiKey = "8fc5bc0ed2fe785413bb1c028503a04c"
            secretKey = "73f7aba0121cc3527e04b02d15da407a"
        }
        googleCheckout {
            merchantId = "827529108383447"
            merchantKey = "ScAQ9VNMRniNUR3T5sW2kQ"
            requestFormUrl = "https://sandbox.google.com/checkout/api/checkout/v2/requestForm/Merchant/827529108383447"
            action = "https://sandbox.google.com/checkout/api/checkout/v2/checkoutForm/Merchant/827529108383447"
            imageSource = "http://sandbox.google.com/checkout/buttons/checkout.gif?merchant_id=827529108383447&w=180&h=46&style=white&variant=text&loc=en_US"
        }
        externalKeys {
            shareThisKey = "d50f28c4-1205-472e-93eb-ca6165bb6cf9"
        }
        uiperformance.enabled = false
    }
    test {
        grails.serverURL = "http://qa.menuplanner.intelligrape.net"
        facebookConnect {
            apiKey = "c7cb03d1076112dfdee19d3e4972c3eb"
            secretKey = "2f069ab14fb4c6ab88cfe463aa9d4344"
        }
        googleCheckout {
            merchantId = "827529108383447"
            merchantKey = "ScAQ9VNMRniNUR3T5sW2kQ"
            requestFormUrl = "https://sandbox.google.com/checkout/api/checkout/v2/requestForm/Merchant/827529108383447"
            action = "https://sandbox.google.com/checkout/api/checkout/v2/checkoutForm/Merchant/827529108383447"
            imageSource = "http://sandbox.google.com/checkout/buttons/checkout.gif?merchant_id=827529108383447&w=180&h=46&style=white&variant=text&loc=en_US"
        }
        externalKeys {
            shareThisKey = "d50f28c4-1205-472e-93eb-ca6165bb6cf9"
        }
        uiperformance.enabled = false
    }
    qa {
        grails.serverURL = "http://qa.menuplanner.intelligrape.net"
        facebookConnect {
            apiKey = "3698f2a4398c83d0ddccc887777e5b36"
            secretKey = "bd031859be42a47634407b881b9db474"
        }
        googleCheckout {
            merchantId = "827529108383447"
            merchantKey = "ScAQ9VNMRniNUR3T5sW2kQ"
            requestFormUrl = "https://sandbox.google.com/checkout/api/checkout/v2/requestForm/Merchant/827529108383447"
            action = "https://sandbox.google.com/checkout/api/checkout/v2/checkoutForm/Merchant/827529108383447"
            imageSource = "http://sandbox.google.com/checkout/buttons/checkout.gif?merchant_id=827529108383447&w=180&h=46&style=white&variant=text&loc=en_US"
        }
        externalKeys {
            shareThisKey = "d50f28c4-1205-472e-93eb-ca6165bb6cf9"
        }
    }
    beta {
        grails.serverURL = "http://beta.menuplanner.intelligrape.net"
        facebookConnect {
            apiKey = "c7cb03d1076112dfdee19d3e4972c3eb"
            secretKey = "2f069ab14fb4c6ab88cfe463aa9d4344"
        }
        googleCheckout {
            merchantId = "827529108383447"
            merchantKey = "ScAQ9VNMRniNUR3T5sW2kQ"
            requestFormUrl = "https://sandbox.google.com/checkout/api/checkout/v2/requestForm/Merchant/827529108383447"
            action = "https://sandbox.google.com/checkout/api/checkout/v2/checkoutForm/Merchant/827529108383447"
            imageSource = "http://sandbox.google.com/checkout/buttons/checkout.gif?merchant_id=827529108383447&w=180&h=46&style=white&variant=text&loc=en_US"
        }
        externalKeys {
            shareThisKey = "d50f28c4-1205-472e-93eb-ca6165bb6cf9"
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


     