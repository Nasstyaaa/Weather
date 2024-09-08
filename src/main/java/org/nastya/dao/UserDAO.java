package org.nastya.dao;

import org.hibernate.Session;
import org.nastya.model.User;
import listener.DataListener;

import java.util.Optional;

public class UserDAO {

    public void save(User user) {
        try (Session session = DataListener.getSession()) {
            session.beginTransaction();

            session.persist(user);

            session.getTransaction().commit();
        }
    }

    public Optional<User> findByLogin(String login){
        try(Session session = DataListener.getSession()) {
            session.beginTransaction();

            User foundUser = session.createSelectionQuery("FROM User WHERE login = :login", User.class)
                    .setParameter("login", login)
                    .uniqueResult();

            session.getTransaction().commit();
            return Optional.ofNullable(foundUser);
        }
    }
}
