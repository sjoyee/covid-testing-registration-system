package com.fit3077.covidtestingregistration.user;

import com.fasterxml.jackson.databind.node.ObjectNode;

public class Customer extends User {

    protected Customer(String id, String givenName, String familyName, String userName, String phoneNumber) {
        super(id, givenName, familyName, userName, phoneNumber);
        setIsCustomer(true);
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
