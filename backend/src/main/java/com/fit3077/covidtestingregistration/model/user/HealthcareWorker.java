package com.fit3077.covidtestingregistration.model.user;

import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fit3077.covidtestingregistration.api.BookingApi;
import com.fit3077.covidtestingregistration.model.booking.BookingStatus;
import com.fit3077.covidtestingregistration.model.booking.notification.BookingEventManager;
import com.fit3077.covidtestingregistration.model.covidtest.CovidTest;
import com.fit3077.covidtestingregistration.model.covidtest.CovidTestType;

public class HealthcareWorker extends User {

    protected HealthcareWorker(String id, String givenName, String familyName, String userName, String phoneNumber) {
        super(id, givenName, familyName, userName, phoneNumber);
        setIsHealthcareWorker(true);
    }

    @Override
    public String toString() {
        return "HEALTHCARE_WORKER";
    }

    @Override
    public boolean handleBooking(ObjectNode testObject,BookingEventManager bookingEvents) {
        String patientId = testObject.get("patientId").textValue();
        String bookingId = testObject.get("bookingId").textValue();
        String type = testObject.get("type").textValue();
        CovidTest covidTest;
        if (type.equals(CovidTestType.PCR.toString())) {
            covidTest = new CovidTest(patientId, CovidTestType.PCR, bookingId);
        } else {
            covidTest = new CovidTest(patientId, CovidTestType.RAT, bookingId);
        }
        return covidTest.assignCovidTestDetails();
    }

    @Override
    public boolean updateData(String bookingId) {
        BookingApi bookingApi = new BookingApi();
        bookingApi.updateBookingStatus(bookingId, BookingStatus.PROCESSED.toString());
        return false;
    }
}
