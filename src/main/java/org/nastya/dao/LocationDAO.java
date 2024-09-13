package org.nastya.dao;

import org.hibernate.Session;
import org.nastya.model.Location;
import org.nastya.model.User;
import org.nastya.util.DBConfigurationUtil;

import java.util.Optional;

public class LocationDAO {

    public void save(Location location) {
        try (Session session = DBConfigurationUtil.getSession()) {
            session.beginTransaction();

            session.persist(location);

            session.getTransaction().commit();
        }
    }

    public void delete(Location location) {
        try (Session session = DBConfigurationUtil.getSession()) {
            session.beginTransaction();

            session.remove(location);

            session.getTransaction().commit();
        }
    }

    public Optional<Location> findByNameAndUser(String name, User user) {
        try (Session session = DBConfigurationUtil.getSession()) {
            session.beginTransaction();

            Location location = session.createSelectionQuery("FROM Location WHERE name = :name and user.id = :user", Location.class)
                    .setParameter("name", name)
                    .setParameter("user", user.getId())
                    .uniqueResult();

            session.getTransaction().commit();
            return Optional.ofNullable(location);
        }
    }

}
