package com.fit3077.covidtestingregistration.model.booking.observer;

import com.fit3077.covidtestingregistration.model.booking.ActiveBooking;
import com.fit3077.covidtestingregistration.model.booking.Booking;

public class BookingLogOpenListener implements BookingEventListener{
    
    private Booking booking;
    public BookingLogOpenListener(Booking booking) {
        this.booking = booking ;
    }

    @Override
    public void update(String eventType, ActiveBooking activeBooking) {
        System.out.println("Save to log " + booking + ": Someone has performed " + eventType + " operation with the following booking: " + activeBooking.getId());
    }
    
}
