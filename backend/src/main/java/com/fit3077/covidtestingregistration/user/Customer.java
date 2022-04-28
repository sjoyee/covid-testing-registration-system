package com.fit3077.covidtestingregistration.user;

import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fit3077.covidtestingregistration.booking.Booking;

public class Customer extends User {

    protected Customer(String id, String givenName, String familyName, String userName, String phoneNumber) {
        super(id, givenName, familyName, userName, phoneNumber);
        setIsCustomer(true);
    }

    @Override
    public boolean handleBooking(ObjectNode userObject) {
        boolean isHomeBooking = userObject.get("isHomeBooking").asBoolean();
        boolean hasRatKit = userObject.get("hasRatKit").asBoolean();
        Booking booking = new Booking(getId(), isHomeBooking);
        booking.setHasRatKit(hasRatKit);
        return booking.assignBookingDetails();
    }

}
