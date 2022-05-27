package com.fit3077.covidtestingregistration.model.booking;

import java.util.List;

import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fit3077.covidtestingregistration.model.notification.BookingEventManager;
import com.fit3077.covidtestingregistration.model.user.UserGenerator;

public class BookingFacade {

    public boolean createBooking(String userId, ObjectNode userObject) {

        return new UserGenerator().generateUser(userId).handleBooking(userObject);
    }

    public String checkStatus(String userId, String verifier, boolean isId) {
        ObjectNode bookingNode;
        if (isId) {
            bookingNode = new UserGenerator().generateUser(userId).checkBookingId(verifier);
        } else {
            bookingNode = new UserGenerator().generateUser(userId).checkPinCode(verifier);
        }

        if (bookingNode == null) {
            return BookingStatus.INVALID.toString();
        }
        return bookingNode.get("status").textValue();
    }

    public boolean updateHomeTestKit(String userId, String qrCode) {
        return new UserGenerator().generateUser(userId).updateData(qrCode);
    }

    public List<ActiveBooking> getActiveBookingsByUserId(String userId) {
        ActiveBookingCollection activeBookings = new ActiveBookingCollection();
        activeBookings.setActiveBookingsByUserId(userId);
        return activeBookings.getActiveBookings();
    }

    public ActiveBooking getActiveBookingByBookingId(String bookingId) {
        ActiveBookingCollection activeBookings = new ActiveBookingCollection();
        activeBookings.setActiveBookingByBookingId(bookingId);
        return activeBookings.getActiveBookings().isEmpty() ? null : activeBookings.getActiveBookings().get(0);
    }

    public ActiveBooking getActiveBookingByPinCode(String pin) {
        ActiveBookingCollection activeBookings = new ActiveBookingCollection();
        activeBookings.setActiveBookingByPin(pin);
        return activeBookings.getActiveBookings().isEmpty() ? null : activeBookings.getActiveBookings().get(0);
    }

    public ActiveBooking updateActiveBooking(String userId, String bookingId, String testingSiteId, String dateTime) {
        ActiveBooking activeBooking = getActiveBookingByBookingId(bookingId);
        if (activeBooking == null) {
            return null;
        }
        return new UserGenerator().generateBookingUser(userId).modifyActiveBooking(activeBooking, testingSiteId,
                dateTime);
    }

    public ActiveBooking restorePastChange(String userId, String bookingId, String testingSiteId, String dateTime) {
        ActiveBooking activeBooking = getActiveBookingByBookingId(bookingId);
        if (activeBooking == null) {
            return null;
        }
        return new UserGenerator().generateBookingUser(userId).restorePastChange(activeBooking, testingSiteId,
                dateTime);
    }

    public void cancelActiveBooking(String userId, String bookingId) {
        ActiveBooking activeBooking = getActiveBookingByBookingId(bookingId);
        new UserGenerator().generateBookingUser(userId).cancelActiveBooking(activeBooking);
    }

    public String notifyBookingUpdate(String userId) {
        return new UserGenerator().generateBookingUser(userId).retrieveBookingUpdate();
    }

    // check here

    public List<Booking> getBookingByTestingSiteId(String userId) {
        return new UserGenerator().generateBookingUser(userId).retrieveBookingByTestingSiteId();

    }

}
