package com.fit3077.covidtestingregistration.model.booking.active;

import java.time.Instant;
import java.util.List;
import java.util.ListIterator;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fit3077.covidtestingregistration.api.BookingApi;
import com.fit3077.covidtestingregistration.model.booking.BookingStatus;

public class ActiveBooking {
    private String id;
    private String testingSiteId;
    private BookingStatus status;
    private String dateTime;
    private List<ActiveBookingHistory> histories;

    public ActiveBooking(String id, String testingSiteId, BookingStatus status, String dateTime,
            List<ActiveBookingHistory> histories) {
        this.id = id;
        this.testingSiteId = testingSiteId;
        this.status = status;
        this.dateTime = dateTime;
        this.histories = histories;
        // always check validity of all datetimes of the booking histories
        checkValidity();
    }

    public String getId() {
        return id;
    }

    public String getTestingSiteId() {
        return testingSiteId;
    }

    public BookingStatus getStatus() {
        return status;
    }

    public String getDateTime() {
        return dateTime;
    }

    public List<ActiveBookingHistory> getHistories() {
        return histories;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setTestingSiteId(String testingSiteId) {
        this.testingSiteId = testingSiteId;
    }

    public void setStatus(BookingStatus status) {
        this.status = status;
    }

    public void setDateTime(String dateTime) {
        this.dateTime = dateTime;
    }

    public void setHistories(List<ActiveBookingHistory> histories) {
        this.histories = histories;
    }

    public ActiveBookingMemento createMemento() {
        return new ActiveBookingMemento(this.histories, this.id, this.testingSiteId, this.dateTime);
    }

    public void updateChanges(String testingSiteId, String dateTime) {
        setTestingSiteId(testingSiteId);
        setDateTime(dateTime);

        ObjectMapper mapper = new ObjectMapper();
        ObjectNode updatedNode = mapper.createObjectNode();
        updatedNode.put("testingSiteId", this.testingSiteId);
        updatedNode.put("startTime", this.dateTime);

        BookingApi bookingApi = new BookingApi();
        bookingApi.updateActiveBooking(this.id, updatedNode);
    }

    public void updateChanges(BookingStatus status) {
        setStatus(status);
        ObjectMapper mapper = new ObjectMapper();
        ObjectNode updatedNode = mapper.createObjectNode();
        updatedNode.put("status", this.status.name());

        BookingApi bookingApi = new BookingApi();
        bookingApi.updateActiveBooking(this.id, updatedNode);
    }

    private void checkValidity() {
        ListIterator<ActiveBookingHistory> itr = this.histories.listIterator();
        while (itr.hasNext()) {
            ActiveBookingHistory history = itr.next();
            // remove booking which is lapsed / expired
            if (Instant.parse(history.getDateTime()).isBefore(Instant.now())) {
                itr.remove();
            }
        }
    }
}
