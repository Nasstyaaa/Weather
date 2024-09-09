package org.nastya.listener;

import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;
import org.nastya.util.DBConfigurationUtil;

@WebListener
public class DataListener implements ServletContextListener {
    @Override
    public void contextInitialized(ServletContextEvent sce) {
        DBConfigurationUtil.configure();
    }
}

