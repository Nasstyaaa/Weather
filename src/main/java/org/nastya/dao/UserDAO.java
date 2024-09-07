package org.nastya.dao;

import org.hibernate.Session;
import org.nastya.model.User;
import org.nastya.util.DataListenerUtil;

import java.util.Optional;

public class UserDAO {

    public void save(User user) {
        try (Session session = DataListenerUtil.getSession()) {
            session.beginTransaction();

            session.persist(user);

            session.getTransaction().commit();
        }
    }

    public Optional<User> find(User user){
        try(Session session = DataListenerUtil.getSession()) {
            session.beginTransaction();

            User foundUser = session.createSelectionQuery("FROM User WHERE login = :login", User.class)
                    .setParameter("login", user.getLogin())
                    .getSingleResult();

            session.getTransaction().commit();
            return Optional.ofNullable(foundUser);
        }
    }
}
