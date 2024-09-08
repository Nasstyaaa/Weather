package org.nastya.util;

import org.nastya.dao.SessionDAO;
import org.nastya.model.Session;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class SessionManagerUtil {
    private final ScheduledExecutorService scheduler = Executors.newSingleThreadScheduledExecutor();
    private final SessionDAO sessionDAO = new SessionDAO();

    public SessionManagerUtil() {
        scheduler.scheduleAtFixedRate(this::checkSessionsState, 0, 1, TimeUnit.MINUTES);
    }

    private void checkSessionsState(){
        for (Session session: sessionDAO.findAllExpired()){
            sessionDAO.delete(session);
        }
    }
}
