package com.vladimir.crud.blog.repository.hibernate;

import com.vladimir.crud.blog.model.Region;
import com.vladimir.crud.blog.repository.RegionRepository;
import com.vladimir.crud.blog.service.hibernate.HibernateConnection;
import com.vladimir.crud.blog.service.hibernate.RegionServiceImpl;
import com.vladimir.crud.blog.service.hibernate.ServiceException;

import java.util.List;

public class HibernateRegionRepositoryImpl implements RegionRepository {
    private final RegionServiceImpl regionService;

    public HibernateRegionRepositoryImpl(HibernateConnection hibernateConnection) {
        this.regionService = new RegionServiceImpl(hibernateConnection);
    }

    @Override
    public Region save(Region region) {
        return regionService.save(region);
    }

    @Override
    public Region update(Region region) throws ServiceException {
        return regionService.update(region);
    }

    @Override
    public Region getById(Long id) throws ServiceException {
        return regionService.getById(id);
    }

    @Override
    public void deleteById(Long id) throws ServiceException {
        regionService.deleteById(id);
    }

    @Override
    public List<Region> getAll() {
        return regionService.getAll();
    }
}
