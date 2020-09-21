package ua.kukhtar.util;

import org.apache.logging.log4j.core.config.Configurator;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import java.util.logging.Logger;

public class LogerConfigurationListener implements ServletContextListener {
    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {
        Configurator.initialize("log4jConfiguration", null, "/home/kukhtar/Java/Epam/HomeWorks/FinalProject/Repair-agency/src/main/webapp/WEB-INF/log4j2.xml");
    }

    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {

    }
}
