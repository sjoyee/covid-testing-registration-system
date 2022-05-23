package com.fit3077.covidtestingregistration.model.user;

import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fit3077.covidtestingregistration.model.booking.active.ActiveBooking;
import com.fit3077.covidtestingregistration.model.booking.active.ActiveBookingModifier;

public abstract class BookingUser extends User {
    protected BookingUser(String id, String givenName, String familyName, String userName, String phoneNumber) {
        super(id, givenName, familyName, userName, phoneNumber);
    }

    public abstract boolean handleBooking(ObjectNode objectNode);

    public abstract boolean updateData(String code);

    public ActiveBooking modifyActiveBooking(ActiveBooking activeBooking, String newTestingSiteId, String newDateTime) {
        ActiveBookingModifier modifier = new ActiveBookingModifier(activeBooking);
        modifier.saveHistories();
        modifier.modify(newTestingSiteId, newDateTime);
        return activeBooking;
    }

    public ActiveBooking restorePastChange(ActiveBooking activeBooking, String testingSiteId, String dateTime) {
        ActiveBookingModifier modifier = new ActiveBookingModifier(activeBooking);
        modifier.saveHistories();
        modifier.restore(testingSiteId, dateTime);
        return activeBooking;
    }

    public void cancelActiveBooking(ActiveBooking activeBooking) {
        ActiveBookingModifier modifier = new ActiveBookingModifier(activeBooking);
        modifier.cancel();
    }
}