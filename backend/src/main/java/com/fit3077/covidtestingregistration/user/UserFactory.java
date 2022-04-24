package com.fit3077.covidtestingregistration.user;

import com.fasterxml.jackson.databind.node.ObjectNode;

public class UserFactory {
    private UserRepository userRepository;
    private User user;

    public UserFactory() {
        this.userRepository = new UserRepository();
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
            try {

                for (ObjectNode userNode : userRepository.getUsers()) {

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

                        } else {
                            this.user = new Receptionist(id, givenName, familyName, phoneNumber);
                        }
                    }
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private boolean login(String userName, String password) {
        String jsonString = "{" +
                "\"userName\":\"" + userName + "\"," +
                "\"password\":\"" + password + "\"" +
                "}";

        String jwtString = userRepository.authenticateUser(jsonString);

        return userRepository.verifyToken(jwtString);

    }

}
