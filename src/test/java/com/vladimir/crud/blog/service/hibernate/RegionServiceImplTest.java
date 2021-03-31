package com.vladimir.crud.blog.service.hibernate;

import com.vladimir.crud.blog.model.Region;
import com.vladimir.crud.blog.service.RegionService;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.StaleStateException;
import org.hibernate.Transaction;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.Mock;

import java.util.ArrayList;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;



public class RegionServiceImplTest {
    private static final Region BASIC_REGION = new Region(1L, "UA");

    @Mock private HibernateConnection hibernateConnection;
    @Mock private Session session;
    @Mock private Transaction transaction;
    @Mock private Query query;

    private RegionService regionService;

    @BeforeEach
    public void setup(){
        hibernateConnection = mock(HibernateConnection.class);
        session = mock(Session.class);
        transaction = mock(Transaction.class);

        regionService = new RegionServiceImpl(hibernateConnection);

        when(hibernateConnection.getSession()).thenReturn(session);
        when(session.beginTransaction()).thenReturn(transaction);
    }

    @Test
    public void shouldReturnRegionWithId(){
        Region region = new Region(null, "UA");

        when(session.save(region)).thenReturn(1L);

        Region region1 = regionService.save(region);

        assertEquals(new Region(1L, "UA"), region1);
    }

    @Test
    public void methodSaveShouldCommitTransactionAndCloseSession(){
        when(session.save(new Region(1L, "UA"))).thenReturn(1L);

        regionService.save(new Region(1L, "UA"));

        verify(transaction).commit();
        verify(session).close();
    }

    @Test
    public void methodUpdateShouldThrowServiceExceptionIfRegionDoesNotExist(){
        doThrow(StaleStateException.class).when(transaction).commit();

        assertThrows(ServiceException.class, () -> regionService.update(BASIC_REGION));
    }

    @Test
    public void methodUpdateShouldCommitTransactionAndCloseSession() throws ServiceException{
        regionService.update(new Region(1L, "UA"));

        verify(transaction).commit();
        verify(session).close();
    }

    @Test
    public void methodUpdateShouldCloseSessionIfThrowsServiceException(){
        doThrow(StaleStateException.class).when(transaction).commit();

        try {
            regionService.update(BASIC_REGION);
        } catch (ServiceException e) {
            e.printStackTrace();
        }

        verify(transaction).commit();
        verify(session).close();
    }

    @Test
    public void shouldReturnRegionIfFound() throws ServiceException {
        when(session.get(Region.class, 1L)).thenReturn(BASIC_REGION);

        Region actual = regionService.getById(1L);
        assertEquals(BASIC_REGION, actual);
    }

    @Test
    public void methodGetByIdShouldThrowServiceExceptionIfRegionIsNotFound(){
        when(session.get(Region.class, 1L)).thenReturn(null);

        assertThrows(ServiceException.class, () -> regionService.getById(1L));
    }

    @Test
    public void methodGetByIdShouldCommitTransactionAndCloseSession() throws ServiceException {
        when(session.get(Region.class, BASIC_REGION.getId())).thenReturn(BASIC_REGION);

        regionService.getById(BASIC_REGION.getId());

        verify(transaction).commit();
        verify(session).close();
    }

    @Test
    public void methodGetByIdShouldCommitTransactionAndCloseSessionIfThrowsServiceException(){
        when(session.get(Region.class, BASIC_REGION.getId())).thenReturn(null);

        try {
            regionService.getById(BASIC_REGION.getId());
        } catch (ServiceException ignored) {}

        verify(transaction).commit();
        verify(session).close();
    }

    @Test
    public void methodDeleteByIdShouldCommitTransactionAndCloseSession() throws ServiceException{
        query = mock(Query.class);
        when(session.createQuery(anyString())).thenReturn(query);
        when(query.setParameter(anyString(), anyLong())).thenReturn(query);
        when(query.executeUpdate()).thenReturn(1);

        regionService.deleteById(BASIC_REGION.getId());

        verify(transaction).commit();
        verify(session).close();
    }

    @Test
    public void methodDeleteByIdShouldCloseSessionIfThrowsServiceException(){
        query = mock(Query.class);
        when(session.createQuery(anyString())).thenReturn(query);
        when(query.setParameter(anyString(), anyLong())).thenReturn(query);
        when(query.executeUpdate()).thenReturn(1);

        doThrow(StaleStateException.class).when(transaction).commit();

        try {
            regionService.deleteById(BASIC_REGION.getId());
        } catch (ServiceException ignored) {}

        verify(transaction).commit();
        verify(session).close();
    }

    @Test
    public void methodGetAllShouldCommitTransactionAndCloseSession(){
        query = mock(Query.class);
        when(session.createQuery(anyString())).thenReturn(query);
        when(query.list()).thenReturn(new ArrayList<Region>());

        regionService.getAll();

        verify(transaction).commit();
        verify(session).close();
    }


}
