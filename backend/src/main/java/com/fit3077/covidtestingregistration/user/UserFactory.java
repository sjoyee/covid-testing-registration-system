package com.fit3077.covidtestingregistration.user;

import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fit3077.covidtestingregistration.api.UserApi;

public class UserFactory {
    private UserApi userApi;
    private User user;

    public UserFactory() {
        this.userApi = new UserApi();
    }

    // Assume each user has only one role
    // if each user has multiple roles, can be refactored to be returning a list of
    // user roles
    public User getUser() {
        return this.user;
    }

    public void createUser(LoginUser loginUser) {
        boolean loggedIn = login(loginUser.getUserName(), loginUser.getPassword());

        if (loggedIn) {

            for (ObjectNode userNode : this.userApi.getUsers()) {

                if (userNode.get("userName").textValue().equals(loginUser.getUserName())) {

                    String id = userNode.get("id").textValue();
                    String givenName = userNode.get("givenName").textValue();
                    String familyName = userNode.get("familyName").textValue();
                    String phoneNumber = userNode.get("phoneNumber").textValue();

                    // Assume each user only has one role
                    if (userNode.get("isHealthcareWorker").asBoolean()) {
                        this.user = new HealthcareWorker(id, givenName, familyName, phoneNumber);

                    } else if (userNode.get("isCustomer").asBoolean()) {
                        this.user = new Customer(id, givenName, familyName, phoneNumber);

                    } else if (userNode.get("isReceptionist").asBoolean()) {
                        String testingSiteId = userNode.get("additionalInfo").get("testingSiteId").textValue();
                        this.user = new Receptionist(id, givenName, familyName, phoneNumber, testingSiteId);
                    }
                    break;
                }
            }
        }
    }

    private boolean login(String userName, String password) {
        String jwtString = userApi.authenticateUser(userName, password);
        return userApi.verifyToken(jwtString);

    }

}
