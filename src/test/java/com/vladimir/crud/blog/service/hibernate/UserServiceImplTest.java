package com.vladimir.crud.blog.service.hibernate;

import com.vladimir.crud.blog.model.Post;
import com.vladimir.crud.blog.model.Region;
import com.vladimir.crud.blog.model.Role;
import com.vladimir.crud.blog.model.User;
import com.vladimir.crud.blog.service.UserService;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.StaleStateException;
import org.hibernate.Transaction;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import java.util.ArrayList;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

public class UserServiceImplTest {
    private static final User BASIC_USER =
            new User(1L,
            "Vladimir",
            "Hrynevych",
            new ArrayList<Post>(),
            new Region(1L, "UA"),
            Role.ADMIN);

    @Mock private HibernateConnection hibernateConnection;
    @Mock private Session session;
    @Mock private Transaction transaction;
    @Mock private Query query;

    private UserService userService;

    @BeforeEach
    public void setup(){
        hibernateConnection = mock(HibernateConnection.class);
        session = mock(Session.class);
        transaction = mock(Transaction.class);

        userService = new UserServiceImpl(hibernateConnection);

        when(hibernateConnection.getSession()).thenReturn(session);
        when(session.beginTransaction()).thenReturn(transaction);
    }

    @Test
    public void methodSaveShouldReturnUserWithId(){
        User setUp = cloneUser(BASIC_USER);
        setUp.setId(null);

        when(session.save(setUp)).thenReturn(BASIC_USER.getId());

        User actual = userService.save(setUp);

        assertEquals(BASIC_USER, actual);
    }

    @Test
    public void methodSaveShouldCommitTransactionAndCloseSession(){
        when(session.save(BASIC_USER)).thenReturn(1L);

        userService.save(BASIC_USER);

        verify(transaction).commit();
        verify(session).close();
    }

    @Test
    public void methodUpdateShouldThrowServiceExceptionIfPostDoesNotExist(){
        doThrow(StaleStateException.class).when(transaction).commit();

        assertThrows(ServiceException.class, () -> userService.update(BASIC_USER));
    }

    @Test
    public void methodUpdateShouldCommitTransactionAndCloseSession() throws ServiceException {
        userService.update(BASIC_USER);

        verify(transaction).commit();
        verify(session).close();
    }

    @Test
    public void methodUpdateShouldCommitTransactionAndCloseSessionIfThrowsServiceException(){
        doThrow(StaleStateException.class).when(transaction).commit();

        try {
            userService.update(BASIC_USER);
        } catch (ServiceException e) {
            e.printStackTrace();
        }

        verify(transaction).commit();
        verify(session).close();
    }

    @Test
    public void shouldReturnUserIfRegionIsNull() throws ServiceException {
        User userWithNullRegion = cloneUser(BASIC_USER);
        userWithNullRegion.setRegion(null);

        when(session.get(User.class, BASIC_USER.getId())).thenReturn(userWithNullRegion);

        User actual = userService.getById(BASIC_USER.getId());

        assertNull(actual.getRegion());
    }

    @Test
    public void methodGetByIdShouldThrowServiceExceptionIfPostIsNotFound(){
        when(session.get(User.class, BASIC_USER.getId())).thenReturn(null);

        assertThrows(ServiceException.class, () -> userService.getById(BASIC_USER.getId()));
    }

    @Test
    public void methodGetByIdShouldCommitTransactionAndCloseSession() throws ServiceException {
        when(session.get(User.class, BASIC_USER.getId())).thenReturn(cloneUser(BASIC_USER));

        userService.getById(BASIC_USER.getId());

        verify(transaction).commit();
        verify(session).close();
    }

    @Test
    public void methodGetByIdShouldCommitTransactionAndCloseSessionIfThrowsServiceException(){
        when(session.get(User.class, BASIC_USER.getId())).thenReturn(null);

        try {
            userService.getById(BASIC_USER.getId());
        } catch (ServiceException ignored) {}

        verify(transaction).commit();
        verify(session).close();
    }

    @Test
    public void methodDeleteByIdShouldCommitTransactionAndCloseSession() throws ServiceException {
        userService.deleteById(BASIC_USER.getId());

        verify(transaction).commit();
        verify(session).close();
    }

    @Test
    public void methodDeleteByIdShouldCommitTransactionAndCloseSessionIfThrowsServiceException(){
        doThrow(StaleStateException.class).when(transaction).commit();

        try {
            userService.deleteById(BASIC_USER.getId());
        } catch (ServiceException ignored) {}

        verify(transaction).commit();
        verify(session).close();
    }

    @Test
    public void methodGetAllShouldCommitTransactionAndCloseSession(){
        query = mock(Query.class);
        when(session.createQuery(anyString())).thenReturn(query);
        when(query.list()).thenReturn(new ArrayList<User>());

        userService.getAll();

        verify(transaction).commit();
        verify(session).close();
    }

    public User cloneUser(User user){
        if(user == null) {
            return null;
        }
        return new User(user.getId(),
                user.getFirstName(),
                user.getLastName(),
                user.getPosts(),
                user.getRegion(),
                user.getRole());
    }
}
