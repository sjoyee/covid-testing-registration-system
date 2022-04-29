package com.fit3077.covidtestingregistration.user;

import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fit3077.covidtestingregistration.api.BookingApi;
import com.fit3077.covidtestingregistration.api.UserApi;
import com.fit3077.covidtestingregistration.booking.Booking;

public class Receptionist extends User {

    private String testingSiteId;

    protected Receptionist(String id, String givenName, String familyName, String userName, String phoneNumber,
            String testingSiteId) {
        super(id, givenName, familyName, userName, phoneNumber);
        this.testingSiteId = testingSiteId;
        setIsReceptionist(true);
    }

    @Override
    public boolean handleBooking(ObjectNode userObject) {
        String customerId = "";
        String userName = userObject.get("userName").textValue();

        UserApi userApi = new UserApi();

        for (ObjectNode userNode : userApi.getUsers()) {
            if (userNode.get("userName").textValue().equals(userName)) {
                customerId = userNode.get("id").textValue();
                break;
            }
        }
        if (customerId.isEmpty()) {
            userObject.put("isCustomer", true);
            userObject.put("isReceptionist", false);
            userObject.put("isHealthcareWorker", false);

            customerId = userApi.createNewUser(userObject).get("id").textValue();
        }
        boolean isHomeBooking = userObject.get("isHomeBooking").asBoolean();

        Booking booking = new Booking(customerId, isHomeBooking);

        if (!isHomeBooking) {
            booking.setTestingSiteId(this.testingSiteId);
        }
        return booking.assignBookingDetails();
    }

    @Override
    public boolean updateData(String qrCode) {
        BookingApi bookingApi = new BookingApi();
        String additionalInfo = "additionalInfo";

        for (ObjectNode bookingNode : bookingApi.getBookings()) {
            if (!bookingNode.get(additionalInfo).toString().equals("{}")
                    && bookingNode.get(additionalInfo).get("isHomeBooking").asBoolean()
                    && !bookingNode.get(additionalInfo).get("qrCode").textValue().equals(qrCode)) {

                if (!bookingNode.get(additionalInfo).get("hasRatKit").asBoolean()) {

                    bookingNode.with(additionalInfo).put("hasRatKit", true);
                    return bookingApi.updateTestKitData(bookingNode.get("id").textValue(), bookingNode);
                }
            }
        }
        return false;
    }
}
