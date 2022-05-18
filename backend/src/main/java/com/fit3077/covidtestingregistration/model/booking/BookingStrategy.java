package com.fit3077.covidtestingregistration.model.booking;

public interface BookingStrategy {
    boolean executeBooking(String customerId, String startTime);
}
