package com.vladimir.crud.blog.service.hibernate;

import com.vladimir.crud.blog.model.Post;
import com.vladimir.crud.blog.model.Region;
import com.vladimir.crud.blog.model.User;
import com.vladimir.crud.blog.service.UserService;
import org.hibernate.*;

import java.util.ArrayList;
import java.util.List;

public class UserServiceImpl implements UserService {
    private final HibernateConnection hibernateConnection;

    public UserServiceImpl(HibernateConnection hibernateConnection) {
        this.hibernateConnection = hibernateConnection;
    }

    @Override
    public User save(User user) {
        Session session = hibernateConnection.getSession();
        Transaction transaction = session.beginTransaction();

        Long id = (Long) session.save(user);
        transaction.commit();
        session.close();
        user.setId(id);

        return user;
    }

    @Override
    public User update(User user) throws ServiceException {
        Session session = hibernateConnection.getSession();
        Transaction transaction = session.beginTransaction();

        try {
            session.update(user);
            transaction.commit();
        } catch (StaleStateException e) {
            throw new ServiceException("There is no user with id " + user.getId());
        }

        session.close();

        return user;
    }

    @Override
    public User getById(Long id) throws ServiceException {
        Session session = hibernateConnection.getSession();
        Transaction transaction = session.beginTransaction();

        User user = session.get(User.class, id);

        if (user == null) {
            transaction.commit();
            session.close();
            throw new ServiceException("There is no user with id " + id);
        }

        user.setPosts(new ArrayList<>(user.getPosts()));
        user.setRegion(new Region(user.getRegion().getId(), user.getRegion().getName()));

        transaction.commit();
        session.close();

        return user;
    }

    @Override
    public void deleteById(Long id) throws ServiceException {
        Session session = hibernateConnection.getSession();
        Transaction transaction = session.beginTransaction();

        User user = new User();
        user.setId(id);

        session.delete(user);

        try {
            transaction.commit();
        } catch (StaleStateException e) {
            throw new ServiceException("There is no user wth id " + id);
        } finally {
            session.close();
        }
    }

    @Override
    public List<User> getAll() {
        Session session = hibernateConnection.getSession();
        Transaction transaction = session.beginTransaction();

        Query query = session.createQuery("From User");
        List<User> list = query.list();

        list.forEach(user -> {
            user.setPosts(new ArrayList<>(user.getPosts()));
            user.setRegion(new Region(user.getRegion().getId(), user.getRegion().getName()));
        });

        transaction.commit();
        session.close();

        return list;
    }
}
