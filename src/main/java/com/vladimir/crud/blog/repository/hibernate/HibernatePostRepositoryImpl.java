package com.vladimir.crud.blog.repository.hibernate;

import com.vladimir.crud.blog.model.Post;
import com.vladimir.crud.blog.repository.PostRepository;
import com.vladimir.crud.blog.service.hibernate.HibernateConnection;
import com.vladimir.crud.blog.service.PostService;
import com.vladimir.crud.blog.service.hibernate.PostServiceImpl;
import com.vladimir.crud.blog.service.hibernate.ServiceException;

import java.util.List;

public class HibernatePostRepositoryImpl implements PostRepository {
    private final PostService postService;

    public HibernatePostRepositoryImpl(HibernateConnection hibernateConnection) {
        this.postService = new PostServiceImpl(hibernateConnection);
    }

    @Override
    public Post save(Post post) {
        return postService.save(post);
    }

    @Override
    public Post update(Post post) throws ServiceException {
        return postService.update(post);
    }

    @Override
    public Post getById(Long id) throws ServiceException {
        return postService.getById(id);
    }

    @Override
    public void deleteById(Long id) throws ServiceException {
        postService.deleteById(id);
    }

    @Override
    public List<Post> getAll() {
        return postService.getAll();
    }
}
