package com.myorg.service;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.myorg.domain.ContentWatched;
import com.myorg.repository.ContentWatchedRepository;

@Service
public class ContentWatchedServiceImpl implements ContentWatchedService {

    private static final Logger logger = LoggerFactory.getLogger(ContentWatchedServiceImpl.class);

    @Autowired
    ContentWatchedRepository repository;

    @Override
    public List<ContentWatched> retrieveContentWatchedRecords() {
        logger.debug("retrieveContentWatchedRecords called");
        return repository.findAll();
    }

    //TODO: can do few improvements here to improve this method returns list instead of single record
    @Override
    public ContentWatched retrieveContentWatchedRecordsByUserId(String userId) {
        logger.debug("retrieveContentWatchedRecordsByUserId called");
        return repository.findById(userId).orElse(new ContentWatched());
    }

}
