package com.fit3077.covidtestingregistration.booking;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fit3077.covidtestingregistration.api.BookingApi;

public class OnsiteBookingStrategy implements BookingStrategy {

    private String testingSiteId;

    public OnsiteBookingStrategy(String testingSiteId) {
        this.testingSiteId = testingSiteId;
    }

    @Override
    public boolean executeBooking(String customerId, String startTime) {
        BookingApi bookingApi = new BookingApi();
        ObjectMapper mapper = new ObjectMapper();

        ObjectNode bookingNode = mapper.createObjectNode();
        bookingNode.put("customerId", customerId);
        bookingNode.put("testingSiteId", this.testingSiteId);
        bookingNode.put("startTime", startTime);

        return bookingApi.createNewBooking(bookingNode);
    }

}
