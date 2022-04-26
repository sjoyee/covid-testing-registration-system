package com.fit3077.covidtestingregistration.user;

import com.fasterxml.jackson.databind.node.ObjectNode;

public class Customer extends User {

    public Customer(String id, String givenName, String familyName, String phoneNumber) {
        super(id, givenName, familyName, phoneNumber);
        setUserType(UserType.CUSTOMER);
    }

    @Override
    public boolean createBooking(ObjectNode userObject) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public String checkStatus(String smsPin) {
        // TODO Auto-generated method stub
        return null;
    }

}
