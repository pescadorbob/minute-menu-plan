package com.mp.test

import grails.test.GrailsUnitTestCase
import groovy.lang.ExpandoMetaClass
import groovy.lang.ExpandoMetaClassCreationHandle
import groovy.lang.GroovyClassLoader
import groovy.lang.GroovySystem
import org.codehaus.groovy.grails.commons.ApplicationHolder
import org.codehaus.groovy.grails.commons.DefaultGrailsApplication
import org.codehaus.groovy.grails.commons.GrailsApplication
import org.codehaus.groovy.grails.commons.spring.DefaultRuntimeSpringConfiguration
import org.codehaus.groovy.grails.commons.spring.GrailsRuntimeConfigurator
import org.codehaus.groovy.grails.plugins.datasource.DataSourceGrailsPlugin
import org.codehaus.groovy.grails.plugins.i18n.I18nGrailsPlugin
import org.codehaus.groovy.grails.support.MockApplicationContext
import org.hibernate.Session
import org.hibernate.SessionFactory
import org.springframework.context.ApplicationContext
import org.springframework.context.support.StaticMessageSource
import org.springframework.orm.hibernate3.SessionFactoryUtils
import org.springframework.orm.hibernate3.SessionHolder
import org.springframework.transaction.support.TransactionSynchronizationManager
import org.codehaus.groovy.grails.plugins.*

abstract class AbstractPersistenceTestCase extends GrailsUnitTestCase {
    GroovyClassLoader gcl = new GroovyClassLoader(this.getClass().classLoader)
    MockApplicationContext ctx;
    ApplicationContext appCtx
    SessionFactory sessionFactory
    Session session

    protected void setUp() {
        super.setUp();

        ExpandoMetaClass.enableGlobally()

        GroovySystem.metaClassRegistry.metaClassCreationHandle = new ExpandoMetaClassCreationHandle();

        gcl.parseClass('''
dataSource {
	pooled = true
	driverClassName = "org.hsqldb.jdbcDriver"
	username = "sa"
	password = ""
    dbCreate = "create-drop" // one of 'create', 'create-drop','update'
    url = "jdbc:hsqldb:mem:testDB"
}
hibernate {
    cache.use_second_level_cache=true
    cache.use_query_cache=true
    cache.provider_class='com.opensymphony.oscache.hibernate.OSCacheProvider'
}
''', "DataSource")

        ctx = new MockApplicationContext();

        def classes = getDomainClasses()

        gcl.getLoadedClasses().each {
            classes << it
        }

        def ga = new DefaultGrailsApplication(classes as Class[], gcl)
        def mockManager = new MockGrailsPluginManager(ga)
        ctx.registerMockBean("manager", mockManager)
        PluginManagerHolder.setPluginManager(mockManager)

        def dependentPlugins = [
                DataSourceGrailsPlugin,
                DomainClassGrailsPlugin,
                I18nGrailsPlugin,
                MockHibernateGrailsPlugin
        ].collect { new DefaultGrailsPlugin(it, ga)}

        dependentPlugins.each { mockManager.registerMockPlugin(it); it.manager = mockManager }
        mockManager.doArtefactConfiguration();
        ctx.registerMockBean(PluginMetaManager.BEAN_ID, new DefaultPluginMetaManager());

        ga.initialise()
        ga.setApplicationContext(ctx);
        ApplicationHolder.setApplication(ga)
        ctx.registerMockBean(GrailsApplication.APPLICATION_ID, ga);
        ctx.registerMockBean("messageSource", new StaticMessageSource())

        def springConfig = new DefaultRuntimeSpringConfiguration(ctx, gcl)
        dependentPlugins*.doWithRuntimeConfiguration(springConfig)

        appCtx = springConfig.getApplicationContext()
        dependentPlugins*.doWithApplicationContext(appCtx)

        mockManager.applicationContext = appCtx
        mockManager.doDynamicMethods()

        sessionFactory = appCtx.getBean(GrailsRuntimeConfigurator.SESSION_FACTORY_BEAN);

        if (!TransactionSynchronizationManager.hasResource(sessionFactory)) {
            session = sessionFactory.openSession();
            TransactionSynchronizationManager.bindResource(sessionFactory, new SessionHolder(session));
        }
    }

    abstract List getDomainClasses()

    protected void tearDown() {
        if (TransactionSynchronizationManager.hasResource(this.sessionFactory)) {
            SessionHolder holder = (SessionHolder) TransactionSynchronizationManager.getResource(this.sessionFactory);
            org.hibernate.Session s = holder.getSession();
            //s.flush();
            TransactionSynchronizationManager.unbindResource(this.sessionFactory);
            SessionFactoryUtils.releaseSession(s, this.sessionFactory);
        }

        ApplicationHolder.setApplication(null)
        ExpandoMetaClass.disableGlobally()
        PluginManagerHolder.setPluginManager(null)

        super.tearDown();
    }
}

