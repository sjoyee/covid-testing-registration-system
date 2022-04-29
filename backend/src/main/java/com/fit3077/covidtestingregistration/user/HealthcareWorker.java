package com.fit3077.covidtestingregistration.user;

import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fit3077.covidtestingregistration.api.BookingApi;
import com.fit3077.covidtestingregistration.booking.BookingStatus;
import com.fit3077.covidtestingregistration.covidtest.CovidTest;
import com.fit3077.covidtestingregistration.covidtest.CovidTestType;

public class HealthcareWorker extends User {

    protected HealthcareWorker(String id, String givenName, String familyName, String userName, String phoneNumber) {
        super(id, givenName, familyName, userName, phoneNumber);
        setIsHealthcareWorker(true);
    }

    @Override
    public boolean handleBooking(ObjectNode testObject) {
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
