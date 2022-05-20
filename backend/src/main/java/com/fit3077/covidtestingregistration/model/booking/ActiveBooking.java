package com.fit3077.covidtestingregistration.model.booking;

import java.time.Instant;

public class ActiveBooking {
    private String id;
    private String testingSiteName;
    private String status;
    private Instant dateTime;

    public ActiveBooking(String id, String testingSiteName, String status, Instant dateTime) {
        this.id = id;
        this.testingSiteName = testingSiteName;
        this.status = status;
        this.dateTime = dateTime;
    }

    public String getId() {
        return id;
    }

    public String getTestingSiteName() {
        return testingSiteName;
    }

    public String getStatus() {
        return status;
    }

    public Instant getDateTime() {
        return dateTime;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setTestingSiteName(String testingSiteName) {
        this.testingSiteName = testingSiteName;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setDateTime(Instant dateTime) {
        this.dateTime = dateTime;
    }
}
