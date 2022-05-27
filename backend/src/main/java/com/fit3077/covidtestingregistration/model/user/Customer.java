package com.fit3077.covidtestingregistration.model.user;

import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fit3077.covidtestingregistration.model.booking.BookingExecutor;
import com.fit3077.covidtestingregistration.model.booking.notification.BookingEventManager;

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
    public String handleBooking(ObjectNode userObject, BookingEventManager bookingEvents) {
        boolean isHomeBooking = userObject.get("isHomeBooking").asBoolean();
        boolean hasRatKit = userObject.get("hasRatKit").asBoolean();
        String patientId = userObject.get("patientId").textValue();
        String startTime = userObject.get("startTime").textValue();

        BookingExecutor booking = new BookingExecutor(getId(), startTime, isHomeBooking, bookingEvents);
        booking.setHasRatKit(hasRatKit);
        booking.setPatientId(patientId);

        return booking.assignBookingDetails();
    }

    @Override
    public boolean updateData(String code) {
        return false;
    }

}
