package com.fit3077.covidtestingregistration.user;

public class Customer extends User {

    public Customer(String id, String givenName, String familyName, String phoneNumber) {
        super(id, givenName, familyName, phoneNumber);
        setUserType(UserType.CUSTOMER);
    }
}
