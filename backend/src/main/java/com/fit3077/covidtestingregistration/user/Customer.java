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
        String patientId = userObject.get("patientId").textValue();

        Booking booking = new Booking(getId(), isHomeBooking);
        booking.setHasRatKit(hasRatKit);
        booking.setPatientId(patientId);

        return booking.assignBookingDetails();
    }

    @Override
    public boolean updateData(String code) {
        return false;
    }

}
