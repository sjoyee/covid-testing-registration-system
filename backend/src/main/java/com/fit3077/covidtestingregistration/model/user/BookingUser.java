package com.fit3077.covidtestingregistration.model.user;

import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fit3077.covidtestingregistration.model.booking.active.ActiveBooking;

public abstract class BookingUser extends User {
    protected BookingUser(String id, String givenName, String familyName, String userName, String phoneNumber) {
        super(id, givenName, familyName, userName, phoneNumber);
    }

    public abstract boolean handleBooking(ObjectNode objectNode);

    public abstract boolean updateData(String code);

    /**
     * 
     * @param activeBooking
     * @param newTestingSiteId
     * @param newDateTime
     */
    public ActiveBooking modifyActiveBooking(ActiveBooking activeBooking, String newTestingSiteId, String newDateTime) {
        activeBooking.createMemento().update(activeBooking.getTestingSiteId(), activeBooking.getDateTime());

        activeBooking.setTestingSiteId(newTestingSiteId);
        activeBooking.setDateTime(newDateTime);
        activeBooking.updateChanges();

        return activeBooking;
    }

    /**
     * 
     * @param activeBooking
     * @param updatedAt
     */
    public ActiveBooking restorePastChange(ActiveBooking activeBooking, String updatedAt) {
        activeBooking.createMemento().restore(updatedAt, activeBooking.getTestingSiteId(), activeBooking.getDateTime());
        return activeBooking;
    }
}
