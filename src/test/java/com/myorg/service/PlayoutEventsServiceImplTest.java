package com.myorg.service;

import static org.mockito.Mockito.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

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
import com.myorg.domain.PlayoutEvent;
import com.myorg.domain.PlayoutEventType;
import com.myorg.repository.ContentWatchedRepository;
import com.myorg.repository.PlayoutEventRepository;

@RunWith(SpringRunner.class)
public class PlayoutEventsServiceImplTest {

    @TestConfiguration
    static class PlayoutEventsServiceTestContextConfiguration {
        @Bean
        public PlayoutEventsService playoutEventsService() {
            return new PlayoutEventsServiceImpl();
        }
    }

    @MockBean
    private ContentWatchedRepository contentRepository;

    @MockBean
    private PlayoutEventRepository eventRepository;

    @Autowired
    private PlayoutEventsServiceImpl service;

    private PlayoutEvent startEvent, stopEvent;
    private PlayoutEventType startEventType, stopEventType;

    @Before
    public void setUp() {
        startEventType = new PlayoutEventType("START", "u1", "c1");
        startEvent = new PlayoutEvent(1576063768, "s1", startEventType);

        stopEventType = new PlayoutEventType("STOP");
        stopEvent = new PlayoutEvent(1576064468, "s1", stopEventType);
    }

    @Test
    public void testProcessPlayoutEvent() { // playout events arrived in sync - successful scenario
        when(eventRepository.findById("s1")).thenReturn(java.util.Optional.of(startEvent));
        service.processPlayoutEvent(stopEvent);
        verify(contentRepository, times(1)).save(any(ContentWatched.class)); // ContentWatched created - success
    }

    @Test
    public void testProcessPlayoutEventsArrivedNotInSync() { // first stop event and then start event - arrived out of order
        startEvent.setSessionId("s2");
        stopEvent.setSessionId("s2");
        when(eventRepository.findById("s2")).thenReturn(java.util.Optional.of(stopEvent));
        service.processPlayoutEvent(startEvent);
        verify(contentRepository, times(1)).save(any(ContentWatched.class)); // ContentWatched created - success
    }

    @Test
    public void testProcessPlayoutEventsDurationLessthan10Min() { // duration is less than 10 Min - ignore playout event
        startEvent.setSessionId("s3");
        startEvent.setEventTimestamp(1576065568);
        stopEvent.setSessionId("s3");
        stopEvent.setEventTimestamp(1576065570); //duration is less than 10 Mins
        when(eventRepository.findById("s3")).thenReturn(java.util.Optional.of(startEvent));
        service.processPlayoutEvent(stopEvent);
        verify(contentRepository, times(0)).save(any(ContentWatched.class)); // ContentWatched not created - success
    }

    @Test
    public void testProcessPlayoutEventsDurationMoreThan8Hrs() { // duration is more than 8 Hrs - ignore playout event
        startEvent.setSessionId("s4");
        startEvent.setEventTimestamp(1576077768);
        stopEvent.setSessionId("s4");
        stopEvent.setEventTimestamp(1576108768); // duration is more than 8 hours
        when(eventRepository.findById("s4")).thenReturn(java.util.Optional.of(startEvent));
        service.processPlayoutEvent(stopEvent);
        verify(contentRepository, times(0)).save(any(ContentWatched.class)); // ContentWatched not created - success
    }

    @After
    public void tearDown() {
        startEvent = null;
        stopEvent = null;
        startEventType = null;
        stopEventType = null;
    }

}
