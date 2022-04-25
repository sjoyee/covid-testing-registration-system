package com.fit3077.covidtestingregistration.user;

public class HealthcareWorker extends User {

    public HealthcareWorker(String id, String givenName, String familyName, String phoneNumber) {
        super(id, givenName, familyName, phoneNumber);
        setUserType(UserType.HEALTHCARE_WORKER);
    }
}
