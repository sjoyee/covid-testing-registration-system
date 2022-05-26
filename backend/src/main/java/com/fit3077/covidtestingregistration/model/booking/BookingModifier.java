package com.fit3077.covidtestingregistration.model.booking;

import com.fit3077.covidtestingregistration.model.booking.memento.BookingMemento;
import com.fit3077.covidtestingregistration.model.booking.observer.BookingEventManager;

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

    public ActiveBooking restore(String testingSiteId, String dateTime,BookingEventManager bookingEvents) {
        this.activeBookingMemento.restore(testingSiteId, dateTime);
        this.activeBooking.updateChanges(testingSiteId, dateTime);
        bookingEvents.notify(this.activeBooking.getTestingSiteId(),this.activeBooking);
        return this.activeBooking;
    }

    public ActiveBooking modify(String testingSiteId, String dateTime,BookingEventManager bookingEvents) {
        this.activeBookingMemento.update();
        this.activeBooking.updateChanges(testingSiteId, dateTime);
        //might need old changes as well
        //might need user info
        //first argument it will notify admin in that testingsite
        bookingEvents.notify(this.activeBooking.getTestingSiteId(),this.activeBooking);
        return this.activeBooking;
    }

    public void cancel(BookingEventManager bookingEvents) {
        this.activeBooking.updateChanges(BookingStatus.CANCELLED);
        //notify function may need to review
        bookingEvents.notify(this.activeBooking.getTestingSiteId(),this.activeBooking);
    }
    // public void delete(BookingEventManager bookingEvents) {
    //     bookingEvents.notify(this.activeBooking.getTestingSiteId(),this.activeBooking);
    //     this.activeBooking = null;
    // }

    // public void update(){
        
    // }
}
