package com.myorg.service;

import java.util.List;

import com.myorg.domain.ContentWatched;

public interface ContentWatchedService {
    List<ContentWatched> retrieveContentWatchedRecords();
    ContentWatched retrieveContentWatchedRecordsByUserId(String userId);
}
