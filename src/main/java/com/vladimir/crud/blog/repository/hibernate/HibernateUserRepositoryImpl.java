package com.vladimir.crud.blog.repository.hibernate;

import com.vladimir.crud.blog.model.User;
import com.vladimir.crud.blog.repository.UserRepository;
import com.vladimir.crud.blog.service.hibernate.ServiceException;
import com.vladimir.crud.blog.service.UserService;

import java.util.List;

public class HibernateUserRepositoryImpl implements UserRepository {
    private final UserService userService;

    public HibernateUserRepositoryImpl(UserService userService) {
        this.userService = userService;
    }

    @Override
    public User save(User user) {
        return userService.save(user);
    }

    @Override
    public User update(User user) throws ServiceException {
        return userService.update(user);
    }

    @Override
    public User getById(Long id) throws ServiceException {
        return userService.getById(id);
    }

    @Override
    public void deleteById(Long id) throws ServiceException {
        userService.deleteById(id);
    }

    @Override
    public List<User> getAll() {
        return userService.getAll();
    }
}
