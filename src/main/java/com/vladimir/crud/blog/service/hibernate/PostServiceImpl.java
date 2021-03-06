package com.vladimir.crud.blog.service.hibernate;

import com.vladimir.crud.blog.model.Post;
import com.vladimir.crud.blog.service.PostService;
import org.hibernate.*;

import java.util.Date;
import java.util.List;

public class PostServiceImpl implements PostService {
    private final HibernateConnection hibernateConnection;

    public PostServiceImpl(HibernateConnection hibernateConnection) {
        this.hibernateConnection = hibernateConnection;
    }

    @Override
    public Post save(Post post) {
        Session session = hibernateConnection.getSession();
        Transaction transaction = session.beginTransaction();

        Long id = (Long) session.save(post);
        transaction.commit();
        session.close();
        post.setId(id);
        return post;
    }

    @Override
    public Post update(Post post) throws ServiceException {
        Session session = hibernateConnection.getSession();
        Transaction transaction = session.beginTransaction();

        Post newPost = session.get(Post.class, post.getId());
        if (newPost == null) {
            session.close();
            throw new ServiceException("There is no post with id " + post.getId());
        }

        newPost.setCreated(newPost.getCreated());
        newPost.setUpdated(new Date());

        session.update(newPost);

        transaction.commit();
        session.close();
        return newPost;
    }

    @Override
    public Post getById(Long id) throws ServiceException {
        Session session = hibernateConnection.getSession();
        Transaction transaction = session.beginTransaction();

        Post post = session.get(Post.class, id);

        transaction.commit();
        session.close();

        if (post == null)
            throw new ServiceException("There is no post with id " + id);
        return post;
    }

    @Override
    public void deleteById(Long id) throws ServiceException {
        Session session = hibernateConnection.getSession();
        Transaction transaction = session.beginTransaction();

        SQLQuery query = session.createSQLQuery("DELETE FROM users_posts WHERE post_id = :post_id");
        query.setParameter("post_id", id);
        query.executeUpdate();

        Post post = new Post();
        post.setId(id);
        session.delete(post);

        try {
            transaction.commit();
        } catch (StaleStateException e) {
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
        List<Post> list = query.list();

        transaction.commit();
        session.close();

        return list;
    }
}
