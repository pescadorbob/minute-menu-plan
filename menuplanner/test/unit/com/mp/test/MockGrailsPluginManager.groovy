package com.mp.test;

import groovy.lang.GroovyClassLoader;
import junit.framework.Assert;
import org.codehaus.groovy.grails.commons.DefaultGrailsApplication;
import org.codehaus.groovy.grails.commons.GrailsApplication;
import org.codehaus.groovy.grails.plugins.exceptions.PluginException;
import org.springframework.core.io.Resource;

import javax.servlet.ServletContext;
import java.io.File;
import java.io.Writer;
import java.math.BigDecimal;
import java.util.Collection;
import java.util.Map
import org.codehaus.groovy.grails.plugins.AbstractGrailsPluginManager
import org.codehaus.groovy.grails.plugins.GrailsPlugin;

/**
 * @author Graeme Rocher
 * @since 0.4
 *
 */

public class MockGrailsPluginManager extends AbstractGrailsPluginManager {
    private ServletContext servletContext;
    private boolean checkForChangesExpected = false;

    public ServletContext getServletContext() {
        return servletContext;
    }

    public MockGrailsPluginManager(GrailsApplication application) {
		super(application);
		loadPlugins();
	}

	public MockGrailsPluginManager() {
		this(new DefaultGrailsApplication(new Class[0], new GroovyClassLoader()));
	}

	public GrailsPlugin getGrailsPlugin(String name) {
		return this.plugins.get(name);
	}

	public GrailsPlugin getGrailsPlugin(String name, BigDecimal version) {
		return this.plugins.get(name);
	}

	public boolean hasGrailsPlugin(String name) {
		return this.plugins.containsKey(name);
	}

	public void registerMockPlugin(GrailsPlugin plugin) {
		this.plugins.put(plugin.getName(), plugin);
		this.pluginList.add(plugin);
	}

	public void loadPlugins() throws PluginException {
		this.initialised = true;
	}

	public void checkForChanges() {
        Assert.assertTrue(this.checkForChangesExpected);
        this.checkForChangesExpected = false;
    }

	public void doWebDescriptor(Resource descriptor, Writer target) {
		// do nothing
	}

	public void doWebDescriptor(File descriptor, Writer target) {
		// do nothing
	}

	public boolean isInitialised() {
		return true;
	}

    public void refreshPlugin(String name) {
        GrailsPlugin plugin = plugins.get(name);
        if(plugin != null) {
            plugin.refresh();
        }
    }

    public Collection getPluginObservers(GrailsPlugin plugin) {
        throw new UnsupportedOperationException("The class [MockGrailsPluginManager] doesn't support the method getPluginObservers");
    }

    public void informObservers(String pluginName, Map event) {
        // do nothing
    }

    public void setServletContext(ServletContext servletContext) {
        this.servletContext = servletContext;
    }

    public void expectCheckForChanges() {
        Assert.assertFalse(this.checkForChangesExpected);
        this.checkForChangesExpected = true;
    }

    public void verify() {
        Assert.assertFalse(this.checkForChangesExpected);
    }

  public GrailsPlugin[] getUserPlugins() {
    return new GrailsPlugin[0];  //To change body of implemented methods use File | Settings | File Templates.
  }
}

