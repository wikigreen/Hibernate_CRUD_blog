package com.vladimir.crud.blog.service;

import com.vladimir.crud.blog.service.hibernate.ServiceException;

import java.util.List;

public interface GenericService<T>  {
    T save(T t);

    T update(T t) throws ServiceException;

    T getById(Long id) throws ServiceException;

    void deleteById(Long id) throws ServiceException;

    List<T> getAll();
}
