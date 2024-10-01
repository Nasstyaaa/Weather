package org.nastya.util;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.nastya.model.Location;
import org.nastya.model.Session;
import org.nastya.model.User;

import java.util.Properties;

public class DBConfigurationUtil {
    private static SessionFactory sessionFactory;

    public static void configure() {
        Configuration configuration = new Configuration()
                .addAnnotatedClass(User.class)
                .addAnnotatedClass(Session.class)
                .addAnnotatedClass(Location.class);

        Properties properties = new Properties();
//        properties.setProperty("hibernate.connection.url", System.getenv("DATASOURCE_URL"));
//        properties.setProperty("hibernate.connection.username", System.getenv("DATASOURCE_USERNAME"));
//        properties.setProperty("hibernate.connection.password", System.getenv("DATASOURCE_PASSWORD"));

        properties.setProperty("hibernate.connection.url", "jdbc:postgresql://localhost:5432/weather");
        properties.setProperty("hibernate.connection.username", "nastya");
        properties.setProperty("hibernate.connection.password", "1234");
        configuration.addProperties(properties);

        initSessionFactory(configuration);
    }

    public synchronized static org.hibernate.Session getSession() {
        return sessionFactory.openSession();
    }

    public static void initSessionFactory(Configuration configuration) {
        sessionFactory = configuration.buildSessionFactory();
    }
}
