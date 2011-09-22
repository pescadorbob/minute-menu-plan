package com.mp.test

import org.codehaus.groovy.grails.plugins.orm.hibernate.HibernatePluginSupport

class MockHibernateGrailsPlugin {
    def version = grails.util.GrailsUtil.getGrailsVersion()
    def dependsOn = [dataSource: version,
            i18n: version,
            core: version,
            domainClass: version]

    def loadAfter = ['controllers']
    def doWithSpring = HibernatePluginSupport.doWithSpring
    def doWithDynamicMethods = HibernatePluginSupport.doWithDynamicMethods
}

