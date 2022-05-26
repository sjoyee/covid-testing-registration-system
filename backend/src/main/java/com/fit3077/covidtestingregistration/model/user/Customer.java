package com.fit3077.covidtestingregistration.model.user;

import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fit3077.covidtestingregistration.model.booking.BookingExecutor;
import com.fit3077.covidtestingregistration.model.notification.BookingEventManager;

public class Customer extends BookingUser {

    protected Customer(String id, String givenName, String familyName, String userName, String phoneNumber) {
        super(id, givenName, familyName, userName, phoneNumber);
        setIsCustomer(true);
    }

    @Override
    public String toString() {
        return "CUSTOMER";
    }

    @Override
    public boolean handleBooking(ObjectNode userObject,BookingEventManager bookingEvents) {
        boolean isHomeBooking = userObject.get("isHomeBooking").asBoolean();
        boolean hasRatKit = userObject.get("hasRatKit").asBoolean();
        String patientId = userObject.get("patientId").textValue();

        BookingExecutor booking = new BookingExecutor(getId(), isHomeBooking);
        booking.setHasRatKit(hasRatKit);
        booking.setPatientId(patientId);

        return booking.assignBookingDetails();
    }

    @Override
    public boolean updateData(String code, BookingEventManager bookingEvents) {
        return false;
    }

}
