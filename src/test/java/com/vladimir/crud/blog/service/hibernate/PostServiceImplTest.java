package com.vladimir.crud.blog.service.hibernate;

import com.vladimir.crud.blog.model.Post;
import com.vladimir.crud.blog.service.PostService;
import org.hibernate.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import java.util.ArrayList;
import java.util.Date;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

public class PostServiceImplTest {
    private static final long SOME_TIMESTAMP = 1616957853258L;
    private static final Post BASIC_POST = new Post(1L, "Content", new Date(SOME_TIMESTAMP), new Date(SOME_TIMESTAMP));

    @Mock private HibernateConnection hibernateConnection;
    @Mock private Session session;
    @Mock private Transaction transaction;
    @Mock private SQLQuery sqlQuery;

    private PostService postService;

    @BeforeEach
    public void setup(){
        hibernateConnection = mock(HibernateConnection.class);
        session = mock(Session.class);
        transaction = mock(Transaction.class);

        postService = new PostServiceImpl(hibernateConnection);

        when(hibernateConnection.getSession()).thenReturn(session);
        when(session.beginTransaction()).thenReturn(transaction);
    }

    @Test
    public void shouldReturnPostWithId(){
        Post param = new Post(null,
                                BASIC_POST.getContent(),
                                BASIC_POST.getCreated(),
                                BASIC_POST.getUpdated());

        when(session.save(param)).thenReturn(1L);

        Post actualPost = postService.save(param);

        assertEquals(BASIC_POST, actualPost);
    }

    @Test
    public void methodSaveShouldCommitTransactionAndCloseSession(){
        when(session.save(BASIC_POST)).thenReturn(1L);

        postService.save(BASIC_POST);

        verify(transaction).commit();
        verify(session).close();
    }

    @Test
    public void methodUpdateShouldThrowServiceExceptionIfPostDoesNotExist(){
        when(session.get(Post.class, BASIC_POST.getId())).thenReturn(null);

        assertThrows(ServiceException.class, () -> postService.update(BASIC_POST));
    }

    @Test
    public void methodUpdateShouldCommitTransactionAndCloseSession() throws ServiceException {
        when(session.get(Post.class, BASIC_POST.getId())).thenReturn(BASIC_POST);

        postService.update(BASIC_POST);

        verify(transaction).commit();
        verify(session).close();
    }

    @Test
    public void methodUpdateShouldCloseSessionIfThrowsServiceException(){
        when(session.get(Post.class, BASIC_POST.getId())).thenReturn(BASIC_POST);

        try {
            postService.update(BASIC_POST);
        } catch (ServiceException e) {
            e.printStackTrace();
        }

        verify(session).close();
    }

    @Test
    public void shouldUpdateIfPostExists() throws ServiceException {
        when(session.get(Post.class, BASIC_POST.getId())).thenReturn(BASIC_POST);

        Post actual = postService.update(BASIC_POST);

        assertTrue(actual.getUpdated().after(new Date(SOME_TIMESTAMP)));
    }

    @Test
    public void methodGetByIdShouldReturnPostIfFound() throws ServiceException {
        when(session.get(Post.class, BASIC_POST.getId())).thenReturn(BASIC_POST);

        Post actual = postService.getById(BASIC_POST.getId());

        assertEquals(BASIC_POST, actual);
    }

    @Test
    public void shouldThrowServiceExceptionIfPostIsNotFound(){
        when(session.get(Post.class, BASIC_POST.getId())).thenReturn(null);

        assertThrows(ServiceException.class, () -> postService.getById(BASIC_POST.getId()));
    }

    @Test
    public void methodGetByIdShouldCommitTransactionAndCloseSession() throws ServiceException {
        when(session.get(Post.class, BASIC_POST.getId())).thenReturn(BASIC_POST);

        postService.getById(BASIC_POST.getId());

        verify(transaction).commit();
        verify(session).close();
    }

    @Test
    public void methodGetByIdShouldCommitTransactionAndCloseSessionIfThrowsServiceException(){
        when(session.get(Post.class, BASIC_POST.getId())).thenReturn(null);

        assertThrows(ServiceException.class, () -> postService.getById(BASIC_POST.getId()));

        verify(transaction).commit();
        verify(session).close();
    }

    @Test
    public void methodDeleteByIdShouldCommitTransactionAndCloseSession() throws ServiceException {
        sqlQuery = mock(SQLQuery.class);
        when(session.createSQLQuery(anyString())).thenReturn(sqlQuery);

//        when(sqlQuery.setParameter(anyString(), anyLong())).thenReturn(sqlQuery);
//        when(sqlQuery.executeUpdate()).thenReturn(1);

        postService.deleteById(BASIC_POST.getId());

        verify(transaction).commit();
        verify(session).close();
    }

    @Test
    public void methodDeleteByIdShouldCommitTransactionAndCloseSessionIfThrowsServiceException(){
        sqlQuery = mock(SQLQuery.class);

        doThrow(StaleStateException.class).when(transaction).commit();
        when(session.createSQLQuery(anyString())).thenReturn(sqlQuery);

        try {
            postService.deleteById(BASIC_POST.getId());
        } catch (ServiceException e) {
            e.printStackTrace();
        }

        verify(transaction).commit();
        verify(session).close();
    }

    @Test
    public void methodGetAllShouldCommitTransactionAndCloseSession(){
        Query query = mock(Query.class);
        when(session.createQuery(anyString())).thenReturn(query);
        when(query.list()).thenReturn(new ArrayList<Post>());

        postService.getAll();

        verify(transaction).commit();
        verify(session).close();
    }

}
