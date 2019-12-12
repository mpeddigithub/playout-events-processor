package com.myorg.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit4.SpringRunner;

import com.myorg.domain.ContentWatched;
import com.myorg.repository.ContentWatchedRepository;

@RunWith(SpringRunner.class)
public class ContentWatchedServiceImplTest {

    @TestConfiguration
    static class ContentWatchedServiceTestContextConfiguration {
        @Bean
        public ContentWatchedService contentWatchedService() {
            return new ContentWatchedServiceImpl();
        }
    }

    @MockBean
    private ContentWatchedRepository repository;

    @Autowired
    private ContentWatchedServiceImpl service;

    private List<ContentWatched> contentWatchedRecords;

    @Before
    public void setUp() {
        ContentWatched contentWatched = new ContentWatched();
        contentWatched.setStartTimestamp(1576064468);
        contentWatched.setUserId("u1");
        contentWatched.setContentId("c1");
        contentWatched.setTimeWatched(9000);
        contentWatchedRecords = new ArrayList<ContentWatched>();
        contentWatchedRecords.add(contentWatched);
        when(repository.findAll()).thenReturn(contentWatchedRecords);
    }

    @Test
    public void testRetrieveContentWatchedRecords() {
        List<ContentWatched> records = service.retrieveContentWatchedRecords();
        assertThat(records.size()).isEqualTo(1);
    }

    @After
    public void tearDown() {
        contentWatchedRecords = null;
    }

}
