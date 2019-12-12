package com.myorg.domain;

import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "PLAYOUT_EVENTS")
public class PlayoutEvent {
    @Column
    private long eventTimestamp;
    @Id
    @Column
    private String sessionId;

    @Override
    public String toString() {
        return "PlayoutEvent{" +
                "eventTimestamp=" + eventTimestamp +
                ", sessionId='" + sessionId + '\'' +
                ", eventType=" + eventType +
                '}';
    }

    @Embedded
    private PlayoutEventType eventType;

    public PlayoutEvent() {
    }

    public PlayoutEvent(long eventTimestamp, String sessionId, PlayoutEventType eventType) {
        this.eventTimestamp = eventTimestamp;
        this.sessionId = sessionId;
        this.eventType = eventType;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof PlayoutEvent)) return false;
        PlayoutEvent that = (PlayoutEvent) o;
        return getEventTimestamp() == that.getEventTimestamp() &&
                Objects.equals(getSessionId(), that.getSessionId()) &&
                Objects.equals(getEventType(), that.getEventType());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getEventTimestamp(), getSessionId(), getEventType());
    }

    public long getEventTimestamp() {
        return eventTimestamp;
    }

    public void setEventTimestamp(long eventTimestamp) {
        this.eventTimestamp = eventTimestamp;
    }

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    public PlayoutEventType getEventType() {
        return eventType;
    }

    public void setEventType(PlayoutEventType eventType) {
        this.eventType = eventType;
    }
}
