package com.myorg.service;

import com.myorg.domain.PlayoutEvent;

public interface PlayoutEventsService {
    void processPlayoutEvent(PlayoutEvent playoutEvent);
}
