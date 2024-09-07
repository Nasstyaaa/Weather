package org.nastya.dao;

import org.hibernate.Session;
import org.nastya.model.User;
import org.nastya.util.DataListenerUtil;

public class UserDAO {

    public User save(User user) {
        try (Session session = DataListenerUtil.getSession()) {
            session.beginTransaction();

            session.persist(user);

            session.getTransaction().commit();
            return user;
        }
    }
}
