/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.appspot.natanedwin.service.spring;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

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

    private SpringContext() {
        applicationContext = new ClassPathXmlApplicationContext("classpath*:/META-INF/natanedwin-context.xml");
    }

    public ApplicationContext getApplicationContext() {
        return applicationContext;
    }

    public <T> T getBean(Class<T> type) {
        return applicationContext.getBean(type);
    }

    public static void main(String... args) {
        SpringContext.INSTANCE.getApplicationContext();
    }
}
