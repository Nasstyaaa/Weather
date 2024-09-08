package org.nastya.dao;

import org.hibernate.Session;
import org.nastya.listener.DataListener;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class SessionDAO {

    public void save(org.nastya.model.Session userSession) {
        try (Session session = DataListener.getSession()) {
            session.beginTransaction();

            session.persist(userSession);

            session.getTransaction().commit();
        }
    }

    public void delete(org.nastya.model.Session userSession) {
        try (Session session = DataListener.getSession()) {
            session.beginTransaction();

            session.remove(userSession);

            session.getTransaction().commit();
        }
    }

    public List<org.nastya.model.Session> findAllExpired() {
        try (Session session = DataListener.getSession()) {
            session.beginTransaction();

            List<org.nastya.model.Session> sessionList = session.createSelectionQuery(
                    "FROM Session WHERE expiresAt <= :time", org.nastya.model.Session.class)
                    .setParameter("time", LocalDateTime.now())
                    .getResultList();

            session.getTransaction().commit();
            return sessionList;
        }
    }


    public Optional<org.nastya.model.Session> findById(UUID id) {
        try (Session session = DataListener.getSession()) {
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
