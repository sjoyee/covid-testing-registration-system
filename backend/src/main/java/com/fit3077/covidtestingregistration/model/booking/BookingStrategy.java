package com.fit3077.covidtestingregistration.model.booking;

import com.fit3077.covidtestingregistration.model.booking.notification.BookingEventManager;

public interface BookingStrategy {
    boolean executeBooking(String customerId, String startTime,BookingEventManager bookingEvents);
}
