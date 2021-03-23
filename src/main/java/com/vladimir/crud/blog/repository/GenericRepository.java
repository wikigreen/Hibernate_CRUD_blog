package com.vladimir.crud.blog.repository;

import com.vladimir.crud.blog.service.hibernate.ServiceException;

import java.util.List;

public interface GenericRepository<T, ID> {

    T save(T t);

    T update(T t) throws ServiceException;

    T getById(ID id) throws ServiceException;

    void deleteById(ID id) throws ServiceException;

    List<T> getAll();
}
