package com.fit3077.covidtestingregistration.model.booking;

public interface BookingStrategy {
    String executeBooking(String customerId, String startTime);
}
