package com.vladimir.crud.blog.service.hibernate;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class HibernateConnection {
    private static final HibernateConnection hibernateConnection = new HibernateConnection();
    private final SessionFactory sessionFactory;

    private HibernateConnection(){
        sessionFactory = new Configuration().configure().buildSessionFactory();
    }

    public static HibernateConnection getInstance(){
        return hibernateConnection;
    }

    public Session getSession(){
        return sessionFactory.openSession();
    }

    public void close() {
        sessionFactory.close();
    }
}
