package org.nastya.dao;

import org.hibernate.Session;
import org.nastya.util.DataListenerUtil;

import java.util.Optional;

public class SessionDAO {

    public void save(org.nastya.model.Session userSession){
        try(Session session = DataListenerUtil.getSession()) {
            session.beginTransaction();

            session.persist(userSession);

            session.getTransaction().commit();
        }
    }

    public Optional<org.nastya.model.Session> find(String id){
        try(Session session = DataListenerUtil.getSession()) {
            session.beginTransaction();

            org.nastya.model.Session userSession = session.createSelectionQuery(
                    "FROM Session WHERE id = :id", org.nastya.model.Session.class)
                    .setParameter("id", id)
                    .uniqueResult();

            session.getTransaction().commit();
            return Optional.ofNullable(userSession);
        }
    }

}
