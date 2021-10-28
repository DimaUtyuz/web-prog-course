package ru.itmo.wp.model.domain;

import java.io.Serializable;
import java.util.Date;

public class Event implements Serializable {
    private long id;
    private long userId;
    private TYPES type;
    private Date creationTime;

    public enum TYPES {
        ENTER,
        LOGOUT
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public TYPES getType() {
        return type;
    }

    public void setType(TYPES type) {
        this.type = type;
    }

    public Date getCreationTime() {
        return creationTime;
    }

    public void setCreationTime(Date creationTime) {
        this.creationTime = creationTime;
    }
}
