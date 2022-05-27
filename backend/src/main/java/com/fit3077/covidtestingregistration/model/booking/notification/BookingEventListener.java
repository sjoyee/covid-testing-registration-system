package com.fit3077.covidtestingregistration.model.booking.notification;

import java.util.List;

import com.fit3077.covidtestingregistration.model.booking.ActiveBooking;

public interface BookingEventListener {

    String notifyUser(String userIdInput);

    void update(String event, List<String> subscribers, ActiveBooking activeBooking, String currentUserId);
    void update(String event, List<String> subscribers,  String currentUserId);
}
