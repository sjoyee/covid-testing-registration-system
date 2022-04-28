package com.fit3077.covidtestingregistration.booking;

public interface BookingStrategy {
    boolean executeBooking(String customerId, String startTime);
}
