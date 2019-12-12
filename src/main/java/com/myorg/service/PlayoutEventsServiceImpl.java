package com.myorg.service;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.myorg.domain.ContentWatched;
import com.myorg.domain.EventType;
import com.myorg.domain.PlayoutEvent;
import com.myorg.repository.ContentWatchedRepository;
import com.myorg.repository.PlayoutEventRepository;

@Service
public class PlayoutEventsServiceImpl implements PlayoutEventsService {

    private static final Logger logger = LoggerFactory.getLogger(PlayoutEventsServiceImpl.class);

    private static final long MIN_DURATION = 10 * 60; // 10 MINs
    private static final long MAX_DURATION = 8 * 60 * 60; // 8 Hrs

    @Autowired
    ContentWatchedRepository contentRepository;

    @Autowired
    PlayoutEventRepository eventsRepository;

    @Override
    public void processPlayoutEvent(PlayoutEvent playoutEvent) {
        logger.debug("processPlayoutEvent called");
        process(playoutEvent);
    }

    private void process(PlayoutEvent playoutEvent) {
        if (playoutEvent.getEventType().getEventType().equals(EventType.START.name())) {
            logger.debug("START event");
            Optional<PlayoutEvent> existingEvent = eventsRepository.findById(playoutEvent.getSessionId());
            if (existingEvent.isPresent()) { // STOP event already existing for this session, mostly it arrived to system prior to START event
                logger.debug("STOP event already present in the system for the same session");
                validateAndGenerateContentWatched(playoutEvent, existingEvent.get());
            } else { // STOP event not exist and just save the event
                logger.debug("Saving START event for future processing");
                eventsRepository.save(playoutEvent);
            }
        } else {
            logger.debug("STOP event");
            Optional<PlayoutEvent> existingEvent = eventsRepository.findById(playoutEvent.getSessionId());
            if (existingEvent.isPresent()) { // START event already existing
                logger.debug("START event already present in the system for the same session - processing events");
                validateAndGenerateContentWatched(existingEvent.get(), playoutEvent);
            } else {
                logger.debug("Saving STOP event for future processing");
                eventsRepository.save(playoutEvent);
            }
        }
    }

    private void validateAndGenerateContentWatched(PlayoutEvent startEvent, PlayoutEvent stopEvent) {
        long timeWatched = stopEvent.getEventTimestamp() - startEvent.getEventTimestamp();
        if (timeWatched >= MIN_DURATION && timeWatched < MAX_DURATION) { // validate min & max duration otherwise ignore the event
            ContentWatched contentWatched = new ContentWatched(startEvent.getEventTimestamp(), startEvent.getEventType().getUserId(),
                    startEvent.getEventType().getContentId(), timeWatched);
            contentRepository.save(contentWatched);
        } else {
            logger.debug("Event duration is either less than 10 minutes or more than 8 hours - just ignoring this event processing");
        }
    }

    //TODO : Error handling can be implemented

}
