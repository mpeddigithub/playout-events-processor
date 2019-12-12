package com.myorg.service;

import java.util.List;
import java.util.Optional;

import com.myorg.domain.ContentWatched;

public interface ContentWatchedService {
    List<ContentWatched> retrieveContentWatchedRecords();
    ContentWatched retrieveContentWatchedRecordsByUserId(String userId);
}
