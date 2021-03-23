package com.vladimir.crud.blog.controller;

import com.vladimir.crud.blog.model.Post;
import com.vladimir.crud.blog.repository.PostRepository;
import com.vladimir.crud.blog.repository.hibernate.HibernatePostRepositoryImpl;
import com.vladimir.crud.blog.service.hibernate.HibernateConnection;
import com.vladimir.crud.blog.service.hibernate.ServiceException;

import java.util.List;

public class PostController {
    private final PostRepository repository = new HibernatePostRepositoryImpl(HibernateConnection.getInstance());

    public PostController(){
    }

    public List<Post> getAll() {
        return repository.getAll();
    }

    public Post addPost(String content){
        Post post = new Post(null, content.trim());
        repository.save(post);
        return post;
    }

    public Post getByID(Long id) throws ServiceException {
        return repository.getById(id);
    }

    public void deleteByID(Long id) throws ServiceException {
        repository.deleteById(id);
    }

    public Post update(Post post) throws ServiceException {
        repository.update(post);
        return post;
    }
}
