import com.mp.tools.UserTools
import grails.plugin.databasemigration.MigrationUtils

// locations to search for config files that get merged into the main config
// config files can either be Java properties files or ConfigSlurper scripts

grails.config.locations = ["classpath:local.properties"]
// grails.config.locations = [ "classpath:${appName}-config.properties",
//                             "classpath:${appName}-config.groovy",
//                             "file:${userHome}/.grails/${appName}-config.properties",
//                             "file:${userHome}/.grails/${appName}-config.groovy"]

// if(System.properties["${appName}.config.location"]) {
//    grails.config.locations << "file:" + System.properties["${appName}.config.location"]
// }
migrations.enabled = true
migrations.changelogs = ['migrations/changelog.xml']
grails.validateable.classes = [com.mp.domain.UserCO]
grails.rateable.rater.evaluator = { UserTools.currentUser }
grails.views.javascript.library = "jquery"
grails.mime.file.extensions = false // enables the parsing of file extensions from URLs into the request format
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
imagesRootDir = System.getProperty("java.io.tmpdir") + "/mpImages/"
tempDir = System.getProperty("java.io.tmpdir") + "/mpImages/tempImages"
recipesRootDir = "recipes/"
usersRootDir = "users/"
homepageRootDir = "homepage/"
shareCookieMaxAge = 2592000
sessions = []
// set per-environment serverURL stem for creating absolute links
grails {
    mail {
        host = "aws-smtp.critsend.com"
        port = 587
        username = "bc.fisher@yahoo.com"
        password = "y8dUV6SXWT1nTR"
        props = ["mail.smtp.starttls.enable": "true",
                "mail.smtp.port": "587"]
    }
}
grails.mail.default.from = "MinuteMenuPlan Admin <bc.fisher@yahoo.com>"

uiperformance {
    html.compress = true
    keepOriginals = true
    processImages = true
    html.includeContentTypes = ['text/html', 'text/xml', 'text/plain', 'application/json']
}

uiperformance.exclusions = ['**/plugins/**']

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
        [type: 'js',
                name: 'menuPlanJquery',
                files: ['ui.core',
                        'ui.sortable',
                        'menuPlan']],

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
weceem.content.prefix = 'mpcontent'


fckeditor {
    upload {
        basedir = "/homepage"
        overwrite = false
        image {
            browser = true
            upload = true
            allowed = ['jpg', 'gif', 'jpeg', 'png']
            denied = []
        }
    }
}

environments {
    production {
        grails.plugin.databasemigration.updateOnStart = true
        grails.plugin.databasemigration.updateOnStartFileNames = ['changelog.xml']
      grails.paypal.server = "https://www.paypal.com/cgi-bin/webscr"
      grails.paypal.email = "bc.fisher@yahoo.com"

        google.analytics.webPropertyID = 'UA-4197138-5'
        google.analytics.enabled = true
        grails.serverURL = "http://www.minutemenuplan.com"
        facebookConnect {
            apiKey = "120e11133b438517eea8c2bae826af1b"
            secretKey = "602b4d22ab0e5ff2c230362085c60235"
        }
//        imagesRootDir = "c:/work/trunk/mpImages/"
        imagesRootDir = "/home/ubuntu/mpImages/"
        weceem.upload.dir = "file:///home/ubuntu/weceem"
        tempDir = "/home/ubuntu/mpImages/tempImages"
        googleCheckout {
            merchantId = "826975927018241"
            merchantKey = "zNvRegBtHvk4NZm03MDXYg"
            requestFormUrl = "https://checkout.google.com/api/checkout/v2/requestForm/Merchant/826975927018241"
            action = "https://checkout.google.com/api/checkout/v2/checkoutForm/Merchant/826975927018241"
            imageSource = "http://checkout.google.com/buttons/checkout.gif?merchant_id=826975927018241&w=180&h=46&style=white&variant=text&loc=en_US"
        }
        externalKeys {
            shareThisKey = "d50f28c4-1205-472e-93eb-ca6165bb6cf9"
        }
    }
    development {
        imagesRootDir = "c:/work/trunk/mpImages/"
      weceem.upload.dir = "file:///c:/work/weceem"

        // re-enable to test migrations
        grails.plugin.databasemigration.updateOnStart = true
        grails.plugin.databasemigration.updateOnStartFileNames = ['changelog.xml']

        grails.paypal.server = "https://www.sandbox.paypal.com/cgi-bin/webscr"
        grails.paypal.email = "qa.men_1291870296_biz@gmail.com"
        grails.paypal.returnUrl = "http://mmp.no-ip.org:8080/menuplanner/subscription/paymentConfirm"
        grails.paypal.pdttoken = "NuPSG08guvqaIgOTMz2yjqnI-zAKEIrv82WcyF4o6sd7oFe2mQCVj66lebq"

        google.analytics.enabled = false
        grails.serverURL = "http://localhost:8080/menuplanner"
        facebookConnect {
            apiKey = "8fc5bc0ed2fe785413bb1c028503a04c"
            secretKey = "73f7aba0121cc3527e04b02d15da407a"
        }
        googleCheckout {
            merchantId = "826975927018241"
            merchantKey = "zNvRegBtHvk4NZm03MDXYg"
            requestFormUrl = "https://sandbox.google.com/checkout/api/checkout/v2/requestForm/Merchant/826975927018241"
            action = "https://sandbox.google.com/checkout/api/checkout/v2/checkoutForm/Merchant/826975927018241"
            imageSource = "http://sandbox.google.com/checkout/buttons/checkout.gif?merchant_id=826975927018241&w=180&h=46&style=white&variant=text&loc=en_US"
        }
        externalKeys {
            shareThisKey = "d50f28c4-1205-472e-93eb-ca6165bb6cf9"
        }
        uiperformance.enabled = false
        grails.clickBank.featuredPlanId = 1
        grails.clickBank.vendorKey = "mmpdev"
        grails.clickBank.secretKey = "MMPDEVSECRETKEY"
        grails.clickBank.paymentLink = "http://1.mmpdev.pay.clickbank.net"
    }
    test {
        migrations.enabled = false
        google.analytics.enabled = false
        grails.serverURL = "http://localhost:8080/menuplanner"
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
        grails.paypal.server = "https://www.sandbox.paypal.com/cgi-bin/webscr"
        grails.paypal.email = "qa.men_1291870296_biz@gmail.com"
        google.analytics.webPropertyID = 'UA-18954005-1'
        google.analytics.enabled = true
        grails.serverURL = "http://qa.menuplanner.intelligrape.net"
        facebookConnect {
            apiKey = "3698f2a4398c83d0ddccc887777e5b36"
            secretKey = "bd031859be42a47634407b881b9db474"
        }
        googleCheckout {
            merchantId = "827529108383447"
            merchantKey = "zNvRegBtHvk4NZm03MDXYg"
            requestFormUrl = "https://sandbox.google.com/checkout/api/checkout/v2/requestForm/Merchant/827529108383447"
            action = "https://sandbox.google.com/checkout/api/checkout/v2/checkoutForm/Merchant/827529108383447"
            imageSource = "http://sandbox.google.com/checkout/buttons/checkout.gif?merchant_id=827529108383447&w=180&h=46&style=white&variant=text&loc=en_US"
        }
        externalKeys {
            shareThisKey = "d50f28c4-1205-472e-93eb-ca6165bb6cf9"
        }
        grails.clickBank.featuredPlanId = 1
        grails.clickBank.vendorKey = "mmpqa"
        grails.clickBank.secretKey = "MMPQASECRETKEY"
        grails.clickBank.paymentLink = "http://3.mmpqa.pay.clickbank.net"
    }
    beta {
        google.analytics.webPropertyID = 'UA-18954005-1'
        google.analytics.enabled = true
        grails.serverURL = "http://beta.menuplanner.intelligrape.net"
        facebookConnect {
            apiKey = "c7cb03d1076112dfdee19d3e4972c3eb"
            secretKey = "2f069ab14fb4c6ab88cfe463aa9d4344"
        }
        googleCheckout {
            merchantId = "827529108383447"
            merchantKey = "zNvRegBtHvk4NZm03MDXYg"
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
    appenders {
        environments {
            production {
                rollingFile name: "myAppender", maxFileSize: 26214400, maxBackupIndex: 10, file: "/var/log/tomcat6/minutemenu.log"
                rollingFile name: "stacktrace", maxFileSize: 26214400, file: "/var/log/tomcat6/stacktrace.log"
            }
            development {
                console name: 'stdout', layout: pattern(conversionPattern: '%c{2} %m%n')
            }
        }
    }
    environments {
        production {
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
            debug "grails.app.tagLib"
        }
        development {
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
            debug "grails.app", "grails.app.tagLib", "com.mp", "grails.app.filter"
            info "com.linkedin.grails"
        }
        test {
            error 'org.codehaus.groovy.grails.web.servlet',  //  controllers
                    'org.codehaus.groovy.grails.web.pages', //  GSP
                    'org.codehaus.groovy.grails.web.sitemesh', //  layouts
                    'org.codehaus.groovy.grails.web.mapping.filter', // URL mapping
                    'org.codehaus.groovy.grails.web.mapping', // URL mapping
                    'org.codehaus.groovy.grails.commons', // core / classloading
                    'org.codehaus.groovy.grails.plugins', // plugins
                    'org.codehaus.groovy.grails.orm.hibernate', // hibernate integration
                    'org.codehaus.groovy.grails.web.binding', 
                    'org.springframework',
                    'org.hibernate',
                    'net.sf.ehcache.hibernate'

            warn 'org.mortbay.log'
            debug "grails.app", "grails.app.tagLib", "com.mp", "grails.app.filter"
            info "com.linkedin.grails"
        }
    }


}


     
