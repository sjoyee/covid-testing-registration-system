package com.fit3077.covidtestingregistration.user;

import com.fasterxml.jackson.databind.node.ObjectNode;

public class HealthcareWorker extends User {

    protected HealthcareWorker(String id, String givenName, String familyName, String userName, String phoneNumber) {
        super(id, givenName, familyName, userName, phoneNumber);
        setIsHealthcareWorker(true);
    }

    @Override
    public boolean handleBooking(ObjectNode userObject) {
        // TODO Auto-generated method stub
        return false;
    }

}
