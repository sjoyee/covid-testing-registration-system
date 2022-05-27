package com.fit3077.covidtestingregistration.model.booking;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fit3077.covidtestingregistration.api.BookingApi;
import com.fit3077.covidtestingregistration.model.booking.notification.BookingEventManager;

public class OnsiteBookingStrategy implements BookingStrategy {

    private String testingSiteId;

    public OnsiteBookingStrategy(String testingSiteId) {
        this.testingSiteId = testingSiteId;

    }

    @Override
    public boolean executeBooking(String customerId, String startTime,BookingEventManager bookingEvents) {
        BookingApi bookingApi = new BookingApi();
        ObjectMapper mapper = new ObjectMapper();

        ObjectNode bookingNode = mapper.createObjectNode();
        bookingNode.put("customerId", customerId);
        bookingNode.put("testingSiteId", this.testingSiteId);
        bookingNode.put("startTime", startTime);
        bookingNode.with("additionalInfo").put("isHomeBooking", false);
        bookingNode.with("additionalInfo").putArray("snapshots");
        bookingEvents.notify("create", customerId,this.testingSiteId);
        return bookingApi.createNewBooking(bookingNode) != null;
    }

}
