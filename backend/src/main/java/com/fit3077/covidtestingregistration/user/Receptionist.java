package com.fit3077.covidtestingregistration.user;

public class Receptionist extends User {

    public Receptionist(String id, String givenName, String familyName, String phoneNumber) {
        super(id, givenName, familyName, phoneNumber);
        setUserType(UserType.RECEPTIONIST);
    }
}
