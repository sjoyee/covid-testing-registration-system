package com.fit3077.covidtestingregistration.user;

import com.fasterxml.jackson.databind.node.ObjectNode;

public class HealthcareWorker extends User {

    public HealthcareWorker(String id, String givenName, String familyName, String phoneNumber) {
        super(id, givenName, familyName, phoneNumber);
        setUserType(UserType.HEALTHCARE_WORKER);
    }

    @Override
    public boolean handleBooking(ObjectNode userObject) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public String checkStatus(String smsPin) {
        // TODO Auto-generated method stub
        return null;
    }

}
