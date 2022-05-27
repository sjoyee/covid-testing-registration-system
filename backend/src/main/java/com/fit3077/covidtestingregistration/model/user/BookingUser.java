package com.fit3077.covidtestingregistration.model.user;

import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fit3077.covidtestingregistration.model.booking.ActiveBooking;
import com.fit3077.covidtestingregistration.model.booking.BookingModifier;
import com.fit3077.covidtestingregistration.model.booking.notification.BookingEventManager;

public abstract class BookingUser extends User {

    protected BookingUser(String id, String givenName, String familyName, String userName, String phoneNumber) {
        super(id, givenName, familyName, userName, phoneNumber);
    }

    public abstract String handleBooking(ObjectNode objectNode, BookingEventManager bookingEvents);

    public abstract boolean updateData(String code);

    public ActiveBooking modifyActiveBooking(ActiveBooking activeBooking, String newTestingSiteId, String newDateTime,
            BookingEventManager bookingEvents) {
        BookingModifier modifier = new BookingModifier(activeBooking);
        modifier.saveHistories();
        modifier.modify(newTestingSiteId, newDateTime, this.getId(), bookingEvents);
        return activeBooking;
    }

    public ActiveBooking restorePastChange(ActiveBooking activeBooking, String testingSiteId, String dateTime,
            BookingEventManager bookingEvents) {
        BookingModifier modifier = new BookingModifier(activeBooking);
        modifier.saveHistories();
        modifier.restore(testingSiteId, dateTime, this.getId(), bookingEvents);
        return activeBooking;
    }

    public void cancelActiveBooking(ActiveBooking activeBooking, BookingEventManager bookingEvents) {
        BookingModifier modifier = new BookingModifier(activeBooking);
        modifier.cancel(this.getId(), bookingEvents);
    }

    public void deleteActiveBooking(ActiveBooking activeBooking, BookingEventManager bookingEvents) {
        if (getIsReceptionist()) {
            BookingModifier modifier = new BookingModifier(activeBooking);
            modifier.delete(this.getId(), bookingEvents);
        } else {
            System.out.println("No permission");
        }
    }
}
