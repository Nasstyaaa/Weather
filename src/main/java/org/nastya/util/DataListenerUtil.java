package org.nastya.util;

import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.nastya.model.Location;
import org.nastya.model.Session;
import org.nastya.model.User;

@WebListener
public class DataListenerUtil implements ServletContextListener{
    public static SessionFactory sessionFactory;

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        Configuration configuration = new Configuration()
                .addAnnotatedClass(User.class)
                .addAnnotatedClass(Session.class)
                .addAnnotatedClass(Location.class);
        sessionFactory = configuration.buildSessionFactory();
    }

    public synchronized static org.hibernate.Session getSession(){
        return sessionFactory.openSession();
    }
}
