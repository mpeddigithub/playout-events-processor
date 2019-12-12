package com.myorg.domain;

import java.util.Objects;

public class PlayoutEventType {

    private String eventType;

    private String userId;
    private String contentId;

    public PlayoutEventType() {
    }

    public PlayoutEventType(String eventType) {
        validateEventType(eventType);
        this.eventType = eventType;
    }

    public PlayoutEventType(String eventType, String userId, String contentId) {
        validateEventType(eventType);
        this.eventType = eventType;
        this.userId = userId;
        this.contentId = contentId;
    }

    public String getEventType() {
        return eventType;
    }

    public void setEventType(String eventType) {
        validateEventType(eventType);
        this.eventType = eventType;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getContentId() {
        return contentId;
    }

    public void setContentId(String contentId) {
        this.contentId = contentId;
    }

    private void validateEventType(String eventType) {
        if (!eventType.equals(EventType.START.name()) && !eventType.equals(EventType.STOP.name())) {
            throw new IllegalArgumentException("Event type must be START or STOP");
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof PlayoutEventType)) return false;
        PlayoutEventType that = (PlayoutEventType) o;
        return getEventType() == that.getEventType() &&
                Objects.equals(getUserId(), that.getUserId()) &&
                Objects.equals(getContentId(), that.getContentId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getEventType(), getUserId(), getContentId());
    }

    @Override
    public String toString() {
        return "PlayoutEventType{" +
                "eventType='" + eventType + '\'' +
                ", userId='" + userId + '\'' +
                ", contentId='" + contentId + '\'' +
                '}';
    }
}
