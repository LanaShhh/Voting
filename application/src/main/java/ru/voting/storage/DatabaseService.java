package ru.voting.storage;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DatabaseService {
    @Autowired
    private SessionFactory entityManagerFactory;

    public <T> boolean tryAddById(Class<T> tClass, String id, T elem) {
        Session session = entityManagerFactory.openSession();
        session.beginTransaction();
        boolean ans = true;
        T dbElem = session.get(tClass, id);
        if (dbElem != null) {
            session.persist(elem);
            ans = false;
        }
        if (ans == true) {
            session.persist(elem);
        }
        session.getTransaction().commit();
        session.close();
        return ans;
    }

    public <T> T getById(Class<T> tClass, String id) {
        Session session = entityManagerFactory.openSession();
        session.beginTransaction();
        T elem = session.get(tClass, id);
        session.getTransaction().commit();
        session.close();
        return elem;
    }
}
