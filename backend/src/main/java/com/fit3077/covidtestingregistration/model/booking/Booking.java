package com.fit3077.covidtestingregistration.model.booking;

import java.util.List;

import com.fit3077.covidtestingregistration.model.booking.memento.BookingHistory;
import com.fit3077.covidtestingregistration.model.testingsite.TestingSite;

public class Booking {

    private String id;
    private TestingSite testingSite;
    private String startTime;
    private BookingStatus status;
    private List<BookingHistory> histories;
    private boolean isActive;

    protected Booking() {
    }

    protected Booking(String id, TestingSite testingSite, String startTime, BookingStatus status,
            List<BookingHistory> histories, boolean isActive) {
        this.id = id;
        this.testingSite = testingSite;
        this.startTime = startTime;
        this.status = status;
        this.histories = histories;
        this.isActive = isActive;
    }

    public String getId() {
        return this.id;
    }

    public TestingSite getTestingSite() {
        return testingSite;
    }

    public String getStartTime() {
        return this.startTime;
    }

    public BookingStatus getStatus() {
        return this.status;
    }

    public List<BookingHistory> getHistories() {
        return this.histories;
    }

    public boolean getIsActive() {
        return this.isActive;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setTestingSite(TestingSite testingSite) {
        this.testingSite = testingSite;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public void setStatus(BookingStatus status) {
        this.status = status;
    }

    public void setHistories(List<BookingHistory> histories) {
        this.histories = histories;
    }

    public void setIsActive(boolean isActive) {
        this.isActive = isActive;
    }
}
