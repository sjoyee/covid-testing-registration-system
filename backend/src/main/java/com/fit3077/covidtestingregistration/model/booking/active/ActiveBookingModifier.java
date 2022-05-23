package com.fit3077.covidtestingregistration.model.booking.active;

// Caretaker class for Memento design pattern
public class ActiveBookingModifier {

    private ActiveBooking activeBooking;
    private ActiveBookingMemento activeBookingMemento;

    public ActiveBookingModifier(ActiveBooking activeBooking) {
        this.activeBooking = activeBooking;
    }

    public void saveHistories() {
        this.activeBookingMemento = this.activeBooking.createMemento();
    }

    public ActiveBooking restore(String testingSiteId, String dateTime) {
        this.activeBookingMemento.restore(testingSiteId, dateTime);
        updateBookingChanges(testingSiteId, dateTime);
        return this.activeBooking;
    }

    public ActiveBooking modify(String testingSiteId, String dateTime) {
        this.activeBookingMemento.update();
        updateBookingChanges(testingSiteId, dateTime);
        return this.activeBooking;
    }

    private void updateBookingChanges(String testingSiteId, String dateTime) {
        this.activeBooking.setTestingSiteId(testingSiteId);
        this.activeBooking.setDateTime(dateTime);
        this.activeBooking.updateChanges();
    }

}
