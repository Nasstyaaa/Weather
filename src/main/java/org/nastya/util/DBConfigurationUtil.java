package org.nastya.util;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.nastya.model.Location;
import org.nastya.model.Session;
import org.nastya.model.User;

public class DBConfigurationUtil {
    private static SessionFactory sessionFactory;

    public static void configure() {
        Configuration configuration = new Configuration()
                .addAnnotatedClass(User.class)
                .addAnnotatedClass(Session.class)
                .addAnnotatedClass(Location.class);
        initSessionFactory(configuration);
    }

    public synchronized static org.hibernate.Session getSession() {
        return sessionFactory.openSession();
    }

    public static void initSessionFactory(Configuration configuration) {
        sessionFactory = configuration.buildSessionFactory();
    }
}
