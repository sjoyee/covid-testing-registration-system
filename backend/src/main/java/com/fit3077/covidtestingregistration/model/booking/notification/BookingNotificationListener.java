package com.fit3077.covidtestingregistration.model.booking.notification;

public class BookingNotificationListener implements BookingEventListener{
    private String notification;

    public BookingNotificationListener(String notification) {
        this.notification = notification;
    }

    @Override
    public void update(String eventType) {
        System.out.println("Email to " + notification + ": Someone has performed " + eventType + " operation with the following file: " );
    }
}
