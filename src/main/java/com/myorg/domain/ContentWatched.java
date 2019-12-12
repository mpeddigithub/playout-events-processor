package com.myorg.domain;

import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table
public class ContentWatched {

    @Column
    private long startTimestamp;
    @Id
    @Column
    private String userId;
    @Column
    private String contentId;
    @Column
    private long timeWatched;

    public ContentWatched() {
    }

    public ContentWatched(long startTimestamp, String userId, String contentId, long timeWatched) {
        this.startTimestamp = startTimestamp;
        this.userId = userId;
        this.contentId = contentId;
        this.timeWatched = timeWatched;
    }

    public long getStartTimestamp() {
        return startTimestamp;
    }

    public void setStartTimestamp(long startTimestamp) {
        this.startTimestamp = startTimestamp;
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

    public long getTimeWatched() {
        return timeWatched;
    }

    public void setTimeWatched(long timeWatched) {
        this.timeWatched = timeWatched;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ContentWatched)) return false;
        ContentWatched that = (ContentWatched) o;
        return getStartTimestamp() == that.getStartTimestamp() &&
                getTimeWatched() == that.getTimeWatched() &&
                Objects.equals(getUserId(), that.getUserId()) &&
                Objects.equals(getContentId(), that.getContentId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getStartTimestamp(), getUserId(), getContentId(), getTimeWatched());
    }

    @Override
    public String toString() {
        return "ContentWatched{" +
                "startTimestamp=" + startTimestamp +
                ", userId='" + userId + '\'' +
                ", contentId='" + contentId + '\'' +
                ", timeWatched=" + timeWatched +
                '}';
    }
}
