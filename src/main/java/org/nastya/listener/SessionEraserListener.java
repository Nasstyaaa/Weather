package org.nastya.listener;

import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;
import org.nastya.dao.SessionDAO;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

@WebListener
public class SessionEraserListener implements ServletContextListener {
    private final ScheduledExecutorService scheduler = Executors.newSingleThreadScheduledExecutor();
    private final SessionDAO sessionDAO = new SessionDAO();

    @Override
    public void contextInitialized(ServletContextEvent sce){
        scheduler.scheduleAtFixedRate(this::cleanupExpiredSessions,0, 1, TimeUnit.MINUTES);
    }

    private void cleanupExpiredSessions() {
        sessionDAO.findAllExpired()
                .forEach(sessionDAO::delete);
    }
}
