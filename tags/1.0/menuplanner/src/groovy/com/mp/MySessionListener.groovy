package com.mp

import javax.servlet.http.HttpSessionListener;
import javax.servlet.http.HttpSessionEvent
import org.codehaus.groovy.grails.commons.ConfigurationHolder;

public class MySessionListener implements HttpSessionListener {

    def config = ConfigurationHolder.config

    public void sessionCreated(HttpSessionEvent event) {
        ConfigurationHolder.config.sessions << event.getSession();
    }

    public void sessionDestroyed(HttpSessionEvent event) {
    }
}