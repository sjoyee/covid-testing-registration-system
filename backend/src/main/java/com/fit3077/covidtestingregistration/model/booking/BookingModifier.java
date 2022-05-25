package com.fit3077.covidtestingregistration.model.booking;

import com.fit3077.covidtestingregistration.model.booking.memento.BookingMemento;

// Caretaker class for Memento design pattern
public class BookingModifier {

    private ActiveBooking activeBooking;
    private BookingMemento activeBookingMemento;

    public BookingModifier(ActiveBooking activeBooking) {
        this.activeBooking = activeBooking;
    }

    public void saveHistories() {
        this.activeBookingMemento = this.activeBooking.createMemento();
    }

    public ActiveBooking restore(String testingSiteId, String dateTime) {
        this.activeBookingMemento.restore(testingSiteId, dateTime);
        this.activeBooking.updateChanges(testingSiteId, dateTime);
        return this.activeBooking;
    }

    public ActiveBooking modify(String testingSiteId, String dateTime) {
        this.activeBookingMemento.update();
        this.activeBooking.updateChanges(testingSiteId, dateTime);
        return this.activeBooking;
    }

    public void cancel() {
        this.activeBooking.updateChanges(BookingStatus.CANCELLED);
    }

    public void update(){
        
    }
}
