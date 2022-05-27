package com.fit3077.covidtestingregistration.model.booking.memento;

public class BookingHistory {
    private String updatedAt;
    private String testingSiteId;
    private String dateTime;

    // constructor with no parameters
    public BookingHistory() {
    }

    public BookingHistory(String updatedAt, String testingSiteId, String dateTime) {
        this.updatedAt = updatedAt;
        this.testingSiteId = testingSiteId;
        this.dateTime = dateTime;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public String getTestingSiteId() {
        return testingSiteId;
    }

    public String getDateTime() {
        return dateTime;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

    public void setTestingSiteId(String testingSiteId) {
        this.testingSiteId = testingSiteId;
    }

    public void setDateTime(String dateTime) {
        this.dateTime = dateTime;
    }

    @Override
    public String toString() {
        return "{" +
                "\"updatedAt\":\"" + this.updatedAt + "\"," +
                "\"testingSiteId\":\"" + this.testingSiteId + "\"," +
                "\"dateTime\":" + this.dateTime +
                "}";
    }
}
