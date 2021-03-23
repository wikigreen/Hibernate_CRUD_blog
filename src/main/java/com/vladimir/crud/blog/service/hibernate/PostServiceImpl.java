package com.vladimir.crud.blog.service.hibernate;

import com.vladimir.crud.blog.model.Post;
import com.vladimir.crud.blog.service.PostService;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.StaleStateException;
import org.hibernate.Transaction;

import java.util.Date;
import java.util.List;

public class PostServiceImpl implements PostService {
    private final HibernateConnection hibernateConnection;

    public PostServiceImpl(HibernateConnection hibernateConnection){
        this.hibernateConnection = hibernateConnection;
    }

    @Override
    public Post save(Post post) {
        Session session = hibernateConnection.getSession();
        Transaction transaction = session.beginTransaction();

        Long id = (Long)session.save(post);
        transaction.commit();
        session.close();
        post.setId(id);
        return post;
    }

    @Override
    public Post update(Post post) throws ServiceException {
        Session session = hibernateConnection.getSession();
        Transaction transaction = session.beginTransaction();

        Post oldPost = session.get(Post.class, post.getId());
        if(oldPost == null)
            throw new ServiceException("There is no post with id " + post.getId());
        transaction.commit();
        session.close();

        session = hibernateConnection.getSession();
        transaction = session.beginTransaction();

        post.setCreated(oldPost.getCreated());
        post.setUpdated(new Date());

        session.update(post);

        transaction.commit();
        session.close();
        return post;
    }

    @Override
    public Post getById(Long id) throws ServiceException {
        Session session = hibernateConnection.getSession();
        Transaction transaction = session.beginTransaction();

        Post post = session.get(Post.class, id);

        transaction.commit();
        session.close();

        if(post == null)
            throw new ServiceException("There is no post with id " + id);
        return post;
    }

    @Override
    public void deleteById(Long id) throws ServiceException {
        Session session = hibernateConnection.getSession();
        Transaction transaction = session.beginTransaction();

        Post post = new Post();
        post.setId(id);

        session.delete(post);

        try{
            transaction.commit();
        } catch (StaleStateException e){
            throw new ServiceException("There is no post with id " + id);
        } finally {
            session.close();
        }
    }

    @Override
    public List<Post> getAll() {
        Session session = hibernateConnection.getSession();
        Transaction transaction = session.beginTransaction();

        Query query = session.createQuery("FROM Post");
        List list = query.list();

        transaction.commit();
        session.close();

        return (List<Post>) list;
    }
}
