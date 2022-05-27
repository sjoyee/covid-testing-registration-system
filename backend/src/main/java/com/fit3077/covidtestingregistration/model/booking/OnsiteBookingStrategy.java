package com.fit3077.covidtestingregistration.model.booking;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fit3077.covidtestingregistration.api.BookingApi;
import com.fit3077.covidtestingregistration.model.booking.notification.BookingEventManager;

public class OnsiteBookingStrategy implements BookingStrategy {

    private String testingSiteId;
    BookingEventManager bookingEvents;

    public OnsiteBookingStrategy(String testingSiteId, BookingEventManager bookingEvents) {
        this.testingSiteId = testingSiteId;
        this.bookingEvents = bookingEvents;
    }

    @Override
    public String executeBooking(String customerId, String startTime) {
        BookingApi bookingApi = new BookingApi();
        ObjectMapper mapper = new ObjectMapper();

        ObjectNode bookingNode = mapper.createObjectNode();
        bookingNode.put("customerId", customerId);
        bookingNode.put("testingSiteId", this.testingSiteId);
        bookingNode.put("startTime", startTime);
        bookingNode.with("additionalInfo").put("isHomeBooking", false);
        bookingNode.with("additionalInfo").putArray("snapshots");

        // notify receptionist
        this.bookingEvents.notify("create", customerId, this.testingSiteId);

        ObjectNode node = bookingApi.createNewBooking(bookingNode);
        if (node != null) {
            return node.get("id").textValue();
        }
        return null;
    }

}
