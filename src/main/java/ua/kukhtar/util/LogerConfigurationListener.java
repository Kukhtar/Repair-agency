package ua.kukhtar.util;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.core.Logger;
import org.apache.logging.log4j.core.config.Configurator;
import ua.kukhtar.controller.ControllerServlet;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class LogerConfigurationListener implements ServletContextListener {
    private static final Logger logger = (Logger) LogManager.getLogger(ControllerServlet.class);

    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {
        Properties properties = new Properties();
        String confLocation;
        try {
            properties.load(new FileInputStream("../webapps/Repair_agency_war/WEB-INF/classes/configuration.properties"));
            confLocation = properties.getProperty("log4jConfigLocation");
        } catch (IOException e) {
            logger.error(e);
            throw new IllegalStateException(e);
        }

        Configurator.initialize("log4jConfiguration", null, confLocation);
        logger.info("Logger file configuration has been set from file: {}", confLocation);
    }

    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {

    }
}
