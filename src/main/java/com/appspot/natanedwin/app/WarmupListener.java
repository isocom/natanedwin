package com.appspot.natanedwin.app;

import com.appspot.natanedwin.service.spring.SpringContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

public class WarmupListener implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        SpringContext.INSTANCE.toString();
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
    }

}
