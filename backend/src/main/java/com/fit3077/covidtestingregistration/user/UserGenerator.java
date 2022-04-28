package com.fit3077.covidtestingregistration.user;

import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fit3077.covidtestingregistration.api.UserApi;

public class UserGenerator {

    private User user;

    public User getUser() {
        return this.user;
    }

    public void generateUser(String userName, String password) {
        UserApi userApi = new UserApi();
        for (ObjectNode userNode : userApi.getUsers()) {
            String currUserName = userNode.get("userName").textValue();

            if (currUserName.equals(userName) && currUserName.equals(password)) {

                String id = userNode.get("id").textValue();
                String givenName = userNode.get("givenName").textValue();
                String familyName = userNode.get("familyName").textValue();
                String phoneNumber = userNode.get("phoneNumber").textValue();

                // Assume each user only has one role
                if (userNode.get("isHealthcareWorker").asBoolean()) {
                    this.user = new HealthcareWorker(id, givenName, familyName, userName, phoneNumber);

                } else if (userNode.get("isCustomer").asBoolean()) {
                    this.user = new Customer(id, givenName, familyName, userName, phoneNumber);

                } else if (userNode.get("isReceptionist").asBoolean()) {
                    String testingSiteId = userNode.get("additionalInfo").get("testingSiteId").textValue();
                    this.user = new Receptionist(id, givenName, familyName, userName, phoneNumber, testingSiteId);
                }
                break;
            }
        }
    }

}
