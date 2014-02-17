/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.appspot.natanedwin.service.spring;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 *
 * @author prokob01
 */
public enum SpringContext {

    /**
     * Spring context instance
     */
    INSTANCE;
    private final ApplicationContext applicationContext;
    private final String[] PACKAGES = new String[]{
        "com.appspot.natanedwin.app",
        "com.appspot.natanedwin.dao",
        "com.appspot.natanedwin.report",
        "com.appspot.natanedwin.service",
        "com.appspot.natanedwin.servlet",
        "com.appspot.natanedwin.vaadin"
    };

    private SpringContext() {
        applicationContext = new AnnotationConfigApplicationContext(PACKAGES);
    }

    public ApplicationContext getApplicationContext() {
        return applicationContext;
    }

    public <T> T getBean(Class<T> type) {
        return applicationContext.getBean(type);
    }
}
