package com.fit3077.covidtestingregistration.model.booking.observer;

import com.fit3077.covidtestingregistration.model.booking.ActiveBooking;

public class BookingNotificationListener implements BookingEventListener{
    private String notification;

    public BookingNotificationListener(String notification) {
        this.notification = notification;
    }


    @Override
    public void update(String testingSiteId, ActiveBooking activeBooking) {
        System.out.println("Email to " + notification + ": Someone has performed " + eventType + " operation with the following booking: " + activeBooking.getId());
        
    }
}
