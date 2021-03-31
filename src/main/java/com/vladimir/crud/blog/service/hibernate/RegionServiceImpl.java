package com.vladimir.crud.blog.service.hibernate;

import com.vladimir.crud.blog.model.Region;
import com.vladimir.crud.blog.service.RegionService;
import org.hibernate.*;
import java.util.List;

public class RegionServiceImpl implements RegionService {
    private final HibernateConnection hibernateConnection;

    public RegionServiceImpl(HibernateConnection hibernateConnection) {
        this.hibernateConnection = hibernateConnection;
    }

    @Override
    public Region save(Region region) {
        Session session = hibernateConnection.getSession();
        Transaction transaction = session.beginTransaction();

        Long id = (Long)session.save(region);
        transaction.commit();
        session.close();
        region.setId(id);
        return region;
    }

    @Override
    public Region update(Region region) throws ServiceException {
        try (Session session = hibernateConnection.getSession();) {
            Transaction transaction = session.beginTransaction();
            session.update(region);
            transaction.commit();
        } catch (StaleStateException e) {
            throw new ServiceException("There is no region with id " + region.getId());
        }
        return region;
    }

    @Override
    public Region getById(Long id) throws ServiceException {
        Session session = hibernateConnection.getSession();
        Transaction transaction = session.beginTransaction();

        Region region = session.get(Region.class, id);
        transaction.commit();

        session.close();

        if(region == null)
            throw new ServiceException("There is no region with id " + id);
        return region;
    }

    @Override
    public void deleteById(Long id) throws ServiceException {
        Session session = hibernateConnection.getSession();
        Transaction transaction = session.beginTransaction();

        Query query = session.createQuery("UPDATE User user SET user.region = null WHERE user.region.id = :id");
        query.setParameter("id", id);
        query.executeUpdate();

        Region region = new Region();
        region.setId(id);

        session.delete(region);

        try {
            transaction.commit();
        }catch (StaleStateException e){
            throw new ServiceException("There is no region with id " + id);
        } finally {
            session.close();
        }
    }

    @Override
    public List<Region> getAll() {
        Session session = hibernateConnection.getSession();
        Transaction transaction = session.beginTransaction();

        Query query = session.createQuery("FROM Region");
        List<Region> list = query.list();

        transaction.commit();
        session.close();

        return list;
    }
}
