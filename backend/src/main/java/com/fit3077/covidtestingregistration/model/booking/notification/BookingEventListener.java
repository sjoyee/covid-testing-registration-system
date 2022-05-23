package com.fit3077.covidtestingregistration.model.booking.notification;

import com.fit3077.covidtestingregistration.model.booking.Booking;

public interface BookingEventListener {
    void update(String eventType, Booking booking);
}
