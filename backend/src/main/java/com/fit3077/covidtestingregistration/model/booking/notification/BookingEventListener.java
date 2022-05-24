package com.fit3077.covidtestingregistration.model.booking.notification;

import com.fit3077.covidtestingregistration.model.booking.ActiveBooking;

public interface BookingEventListener {
    //
    void update(String eventType, ActiveBooking activeBooking);
}
