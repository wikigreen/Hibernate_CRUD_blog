package com.vladimir.crud.blog.controller;

import com.vladimir.crud.blog.model.Region;
import com.vladimir.crud.blog.repository.RegionRepository;
import com.vladimir.crud.blog.repository.hibernate.HibernateRegionRepositoryImpl;
import com.vladimir.crud.blog.service.hibernate.HibernateConnection;
import com.vladimir.crud.blog.service.hibernate.ServiceException;

import java.util.List;

public class RegionController {
    private final RegionRepository repository;

    public RegionController() {
        this.repository = new HibernateRegionRepositoryImpl(HibernateConnection.getInstance());
    }
    public List<Region> getAll(){
        return repository.getAll();
    }

    public Region addRegion(String name){
        Region region = new Region(null, name);
        repository.save(region);
        return region;
    }

    public Region getByID(Long id) throws ServiceException {
        return repository.getById(id);
    }


    public void deleteByID(Long id) throws ServiceException {
        repository.deleteById(id);
    }

    public Region update(Region region) throws ServiceException {
        repository.update(region);
        return region;
    }

}
