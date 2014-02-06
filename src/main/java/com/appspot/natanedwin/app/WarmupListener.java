package com.appspot.natanedwin.app;

import com.appspot.natanedwin.service.spring.SpringContext;
import com.codahale.metrics.MetricRegistry;
import com.codahale.metrics.health.HealthCheckRegistry;
import static com.codahale.metrics.servlets.HealthCheckServlet.HEALTH_CHECK_REGISTRY;
import static com.codahale.metrics.servlets.MetricsServlet.METRICS_REGISTRY;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

public class WarmupListener implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        SpringContext.INSTANCE.toString();

        // wire the Metrics framework to servlet context
        sce.getServletContext().setAttribute(HEALTH_CHECK_REGISTRY, SpringContext.INSTANCE.getBean(HealthCheckRegistry.class));
        sce.getServletContext().setAttribute(METRICS_REGISTRY, SpringContext.INSTANCE.getBean(MetricRegistry.class));
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
    }

}
