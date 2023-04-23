package ru.voting.storage;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DatabaseService {
    @Autowired
    private SessionFactory entityManagerFactory;

    public <T> void add(T elem) {
        Session session = entityManagerFactory.openSession();
        session.beginTransaction();
        session.persist(elem);
        session.getTransaction().commit();
        session.close();
    }

    public <T> T getById(Class<T> tClass, String id) {
        Session session = entityManagerFactory.openSession();
        session.beginTransaction();
        T elem = session.get(tClass, id);
        session.getTransaction().commit();
        session.close();
        return elem;
    }

    public <T> T remove(T elem) {
        Session session = entityManagerFactory.openSession();
        session.beginTransaction();
        session.remove(elem);
        session.getTransaction().commit();
        session.close();
        return elem;
    }

    public <T> void update(T elem) {
        Session session = entityManagerFactory.openSession();
        session.beginTransaction();
        session.update(elem);
        session.getTransaction().commit();
        session.close();
    }
}
