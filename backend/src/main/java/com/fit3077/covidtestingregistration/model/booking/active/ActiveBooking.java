package com.fit3077.covidtestingregistration.model.booking.active;

import java.util.List;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fit3077.covidtestingregistration.api.BookingApi;

public class ActiveBooking {
    private String id;
    private String testingSiteId;
    private String status;
    private String dateTime;
    private List<ActiveBookingHistory> histories;

    public ActiveBooking(String id, String testingSiteId, String status, String dateTime,
            List<ActiveBookingHistory> histories) {
        this.id = id;
        this.testingSiteId = testingSiteId;
        this.status = status;
        this.dateTime = dateTime;
        this.histories = histories;
    }

    public String getId() {
        return id;
    }

    public String getTestingSiteId() {
        return testingSiteId;
    }

    public String getStatus() {
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

    public void setStatus(String status) {
        this.status = status;
    }

    public void setDateTime(String dateTime) {
        this.dateTime = dateTime;
    }

    public void setHistories(List<ActiveBookingHistory> histories) {
        this.histories = histories;
    }

    public ActiveBookingMemento createMemento() {
        return new ActiveBookingMemento(this.id, this.histories);
    }

    public void updateChanges() {
        ObjectMapper mapper = new ObjectMapper();
        ObjectNode updatedNode = mapper.createObjectNode();
        updatedNode.put("testingSiteId", this.testingSiteId);
        updatedNode.put("startTime", this.dateTime);

        BookingApi bookingApi = new BookingApi();
        bookingApi.updateActiveBooking(this.id, updatedNode);
    }
}
