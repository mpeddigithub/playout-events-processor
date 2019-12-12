package com.myorg.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.myorg.domain.ContentWatched;
import com.myorg.domain.PlayoutEvent;
import com.myorg.service.ContentWatchedService;
import com.myorg.service.PlayoutEventsService;

@RestController
@RequestMapping("api")
public class PlayoutEventsController {

    private static final Logger logger = LoggerFactory.getLogger(PlayoutEventsController.class);

    @Autowired
    ContentWatchedService contentWatchedService;

    @Autowired
    private PlayoutEventsService service;

    @PostMapping("/event")
    public ResponseEntity<Void> processPlayoutEvent(@RequestBody PlayoutEvent event) {
        logger.debug("processPlayoutEvent called");
        service.processPlayoutEvent(event);
        return new ResponseEntity<Void>(HttpStatus.CREATED);
    }

    @GetMapping("/contentwatched")
    public ResponseEntity<List<ContentWatched>> getContentWatchedRecords() {
        logger.debug("getContentWatchedRecords called");
        return new ResponseEntity<List<ContentWatched>>(contentWatchedService.retrieveContentWatchedRecords(), HttpStatus.OK);
    }

    //TODO: can do few improvements here to improve this method returns list instead of single record
    @GetMapping("/contentwatched/{userId}")
    public ResponseEntity<ContentWatched> getContentWatchedRecordsByUserId(@PathVariable String userId) {
        logger.debug("getContentWatchedRecords called");
        return new ResponseEntity<ContentWatched>(contentWatchedService.retrieveContentWatchedRecordsByUserId(userId), HttpStatus.OK);
    }

}
