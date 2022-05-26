package com.fit3077.covidtestingregistration.model.user;

import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fit3077.covidtestingregistration.model.booking.ActiveBooking;
import com.fit3077.covidtestingregistration.model.booking.BookingModifier;
import com.fit3077.covidtestingregistration.model.booking.observer.BookingEventManager;

public abstract class BookingUser extends User {
    protected BookingUser(String id, String givenName, String familyName, String userName, String phoneNumber) {
        super(id, givenName, familyName, userName, phoneNumber);
    }

    public abstract boolean handleBooking(ObjectNode objectNode, BookingEventManager bookingEvents);

    public abstract boolean updateData(String code, BookingEventManager bookingEvents);

    public ActiveBooking modifyActiveBooking(ActiveBooking activeBooking, String newTestingSiteId, String newDateTime,BookingEventManager bookingEvents) {
        BookingModifier modifier = new BookingModifier(activeBooking);
        modifier.saveHistories();
        modifier.modify(newTestingSiteId, newDateTime,bookingEvents);
        return activeBooking;
    }

    public ActiveBooking restorePastChange(ActiveBooking activeBooking, String testingSiteId, String dateTime,BookingEventManager bookingEvents) {
        BookingModifier modifier = new BookingModifier(activeBooking);
        modifier.saveHistories();
        modifier.restore(testingSiteId, dateTime,bookingEvents);
        return activeBooking;
    }

    public void cancelActiveBooking(ActiveBooking activeBooking,BookingEventManager bookingEvents) {
        BookingModifier modifier = new BookingModifier(activeBooking);
        modifier.cancel(bookingEvents);
    }



    
}
