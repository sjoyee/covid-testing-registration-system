package com.fit3077.covidtestingregistration.model.booking;

import com.fit3077.covidtestingregistration.model.booking.memento.BookingMemento;
import com.fit3077.covidtestingregistration.model.notification.BookingEventManager;

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

    public ActiveBooking restore(String testingSiteId, String dateTime,BookingEventManager bookingEvents,String userId) {
        String currentTestingSiteId = this.activeBooking.getTestingSiteId();
        this.activeBookingMemento.restore(testingSiteId, dateTime);
        this.activeBooking.updateChanges(testingSiteId, dateTime);
        bookingEvents.notify("restore",this.activeBooking, userId,currentTestingSiteId,testingSiteId);
        return this.activeBooking;
    }

    public ActiveBooking modify(String testingSiteId, String dateTime,BookingEventManager bookingEvents,String userId) {
        String currentTestingSiteId = this.activeBooking.getTestingSiteId();
        this.activeBookingMemento.update();
        this.activeBooking.updateChanges(testingSiteId, dateTime);
        
        bookingEvents.notify("modify",this.activeBooking,userId,currentTestingSiteId,testingSiteId);
        return this.activeBooking;
    }

    public void cancel(BookingEventManager bookingEvents,String userId) {
        this.activeBooking.updateChanges(BookingStatus.CANCELLED);
        //notify function may need to review
        bookingEvents.notify("cancel",this.activeBooking,userId);
    }
    public void delete(BookingEventManager bookingEvents, String userId) {
        bookingEvents.notify("delete",this.activeBooking, userId);
        //do api delete
        this.activeBooking = null;
    }

    // public void update(){
        
    // }
}
