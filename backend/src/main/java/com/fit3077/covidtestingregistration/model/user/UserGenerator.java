package com.fit3077.covidtestingregistration.model.user;

import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fit3077.covidtestingregistration.api.UserApi;

public class UserGenerator {

    private UserApi userApi;

    public UserGenerator() {
        this.userApi = new UserApi();
    }

    /**
     *
     * @param userName
     * @param password
     * @return
     */
    public User generateUser(String userName, String password) {
        for (ObjectNode userNode : this.userApi.getUsers()) {
            String currUserName = userNode.get("userName").textValue();
            if (currUserName.equals(userName) && currUserName.equals(password)) {
                return generateUserByType(userNode);
            }
        }
        return null;
    }

    /**
     *
     * @param id
     * @return
     */
    public User generateUser(String id) {
        ObjectNode userNode = this.userApi.getUserById(id);
        return generateUserByType(userNode);
    }

    /**
     *
     * @param id
     * @return
     */
    public BookingUser generateBookingUser(String id) {
        ObjectNode userNode = this.userApi.getUserById(id);
        return generateBookingUserByType(userNode);
    }

    /**
     *
     * @param userNode
     * @return
     */
    private User generateUserByType(ObjectNode userNode) {
        String id = userNode.get("id").textValue();
        String givenName = userNode.get("givenName").textValue();
        String familyName = userNode.get("familyName").textValue();
        String phoneNumber = userNode.get("phoneNumber").textValue();
        String userName = userNode.get("userName").textValue();

        if (userNode.get("isHealthcareWorker").asBoolean()) {
            return new HealthcareWorker(id, givenName, familyName, userName,
                    phoneNumber);

        } else if (userNode.get("isCustomer").asBoolean()) {
            return new Customer(id, givenName, familyName, userName, phoneNumber);

        } else if (userNode.get("isReceptionist").asBoolean()) {
            String testingSiteId = userNode.get("additionalInfo").get("testingSiteId").textValue();

            return new Receptionist(id, givenName, familyName, userName, phoneNumber,
                    testingSiteId);
        }
        return null;
    }

    /**
     *
     * @param userNode
     * @return
     */
    private BookingUser generateBookingUserByType(ObjectNode userNode) {
        String id = userNode.get("id").textValue();
        String givenName = userNode.get("givenName").textValue();
        String familyName = userNode.get("familyName").textValue();
        String phoneNumber = userNode.get("phoneNumber").textValue();
        String userName = userNode.get("userName").textValue();

        if (userNode.get("isCustomer").asBoolean()) {
            return new Customer(id, givenName, familyName, userName, phoneNumber);

        } else if (userNode.get("isReceptionist").asBoolean()) {
            String testingSiteId = userNode.get("additionalInfo").get("testingSiteId").textValue();
            
            return new Receptionist(id, givenName, familyName, userName, phoneNumber,
                    testingSiteId);
        }
        return null;
    }
}
