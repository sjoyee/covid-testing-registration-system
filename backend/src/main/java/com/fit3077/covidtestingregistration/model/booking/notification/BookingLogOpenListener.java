package com.fit3077.covidtestingregistration.model.booking.notification;

import com.fit3077.covidtestingregistration.model.booking.Booking;

public class BookingLogOpenListener implements BookingEventListener{
    
    private Booking booking;
    public BookingLogOpenListener(Booking booking) {
        this.booking = booking ;
    }

    @Override
    public void update(String eventType, Booking booking) {
        System.out.println("Save to log " + booking + ": Someone has performed " + eventType + " operation with the following booking: " + booking.get());
    }
    
}
