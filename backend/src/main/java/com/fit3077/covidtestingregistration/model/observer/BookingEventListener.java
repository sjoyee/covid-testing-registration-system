package com.fit3077.covidtestingregistration.model.observer;

import java.util.List;

import com.fit3077.covidtestingregistration.model.booking.ActiveBooking;

public interface BookingEventListener {
    //
    void update(String event,List<String> subscribers, ActiveBooking activeBooking, String currentUserId);
}
