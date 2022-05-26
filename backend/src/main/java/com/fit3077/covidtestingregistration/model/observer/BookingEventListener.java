package com.fit3077.covidtestingregistration.model.observer;

import java.util.List;

import com.fit3077.covidtestingregistration.model.booking.ActiveBooking;

public interface BookingEventListener {
    //
    void update(List<String> subscribers, ActiveBooking activeBooking, String currentUserId);
}
