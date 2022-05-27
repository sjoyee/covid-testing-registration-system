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

    public ActiveBooking restore(String testingSiteId, String dateTime, String userId,
            BookingEventManager bookingEvents) {
        bookingEvents.subscribeAll();
        String currentTestingSiteId = this.activeBooking.getTestingSite().getId();
        this.activeBookingMemento.restore(testingSiteId, dateTime);
        this.activeBooking.updateChanges(testingSiteId, dateTime);
        bookingEvents.notify("restore", this.activeBooking, userId, currentTestingSiteId, testingSiteId);
        return this.activeBooking;
    }

    public ActiveBooking modify(String testingSiteId, String dateTime, String userId,
            BookingEventManager bookingEvents) {
        bookingEvents.subscribeAll();
        String currentTestingSiteId = this.activeBooking.getTestingSite().getId();
        this.activeBookingMemento.update();
        this.activeBooking.updateChanges(testingSiteId, dateTime);

        bookingEvents.notify("modify", this.activeBooking, userId, currentTestingSiteId, testingSiteId);
        return this.activeBooking;
    }

    public void cancel(String userId, BookingEventManager bookingEvents) {
        bookingEvents.subscribeAll();
        this.activeBooking.updateChanges(BookingStatus.CANCELLED);
        // notify function may need to review
        bookingEvents.notify("cancel", this.activeBooking, userId);
    }

    public void delete(String userId, BookingEventManager bookingEvents) {
        bookingEvents.subscribeAll();

        // do api delete
        this.activeBooking.deleteBooking();
        bookingEvents.notify("delete", this.activeBooking, userId);
        this.activeBooking = null;
    }

    // public void update(){

    // }
}
