package com.fit3077.covidtestingregistration.model.observer;

import com.fit3077.covidtestingregistration.model.booking.ActiveBooking;

public class BookingNotificationListener implements BookingEventListener{
    private String notification;

    public BookingNotificationListener() {
        
    }

    


    @Override
    public void update(List<String> subscribers, ActiveBooking activeBooking, String currentUserId) {
        
        
    }
}
