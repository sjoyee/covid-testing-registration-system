package com.fit3077.covidtestingregistration.model.booking;

import com.fit3077.covidtestingregistration.model.booking.memento.BookingMemento;
import com.fit3077.covidtestingregistration.model.notification.BookingEventManager;

// Caretaker class for Memento design pattern
public class BookingModifier {

    private ActiveBooking activeBooking;
    private BookingMemento activeBookingMemento;
    private BookingEventManager bookingEvents;

    public BookingModifier(ActiveBooking activeBooking) {
        this.activeBooking = activeBooking;
        this.bookingEvents = new BookingEventManager();
        
    }

    public void saveHistories() {
        this.activeBookingMemento = this.activeBooking.createMemento();
    }

    public ActiveBooking restore(String testingSiteId, String dateTime,String userId) {
        this.bookingEvents.subscribeAll();
        String currentTestingSiteId = this.activeBooking.getTestingSiteId();
        this.activeBookingMemento.restore(testingSiteId, dateTime);
        this.activeBooking.updateChanges(testingSiteId, dateTime);
        this.bookingEvents.notify("restore",this.activeBooking, userId,currentTestingSiteId,testingSiteId);
        return this.activeBooking;
    }

    public ActiveBooking modify(String testingSiteId, String dateTime,String userId) {
        this.bookingEvents.subscribeAll();
        String currentTestingSiteId = this.activeBooking.getTestingSiteId();
        this.activeBookingMemento.update();
        this.activeBooking.updateChanges(testingSiteId, dateTime);
        
        this.bookingEvents.notify("modify",this.activeBooking,userId,currentTestingSiteId,testingSiteId);
        return this.activeBooking;
    }

    public void cancel(String userId) {
        this.bookingEvents.subscribeAll();
        this.activeBooking.updateChanges(BookingStatus.CANCELLED);
        //notify function may need to review
        this.bookingEvents.notify("cancel",this.activeBooking,userId);
    }
    public void delete( String userId) {
        this.bookingEvents.subscribeAll();
        
        //do api delete
        this.activeBooking.deleteBooking();
        this.bookingEvents.notify("delete",this.activeBooking, userId);
        this.activeBooking = null;
    }

    // public void update(){
        
    // }
}
