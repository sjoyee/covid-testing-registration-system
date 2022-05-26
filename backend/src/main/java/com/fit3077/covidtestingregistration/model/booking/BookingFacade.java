package com.fit3077.covidtestingregistration.model.booking;

import java.util.List;

import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fit3077.covidtestingregistration.model.booking.observer.BookingEventManager;
import com.fit3077.covidtestingregistration.model.user.User;
import com.fit3077.covidtestingregistration.model.user.UserGenerator;

public class BookingFacade {

    public boolean createBooking(String userId, ObjectNode userObject,BookingEventManager bookingEvents) {
     
     
        return new UserGenerator().generateUser(userId,bookingEvents).handleBooking(userObject,bookingEvents);
    }

  

    public String checkStatus(String userId, String verifier, boolean isId,BookingEventManager bookingEvents) {
        ObjectNode bookingNode;
        if (isId) {
            bookingNode = new UserGenerator().generateUser(userId,bookingEvents).checkBookingId(verifier);
        } else {
            bookingNode = new UserGenerator().generateUser(userId,bookingEvents).checkPinCode(verifier);
        }

        if (bookingNode == null) {
            return BookingStatus.INVALID.toString();
        }
        return bookingNode.get("status").textValue();
    }

    public boolean updateHomeTestKit(String userId, String qrCode,BookingEventManager bookingEvents) {
        return new UserGenerator().generateUser(userId,bookingEvents).updateData(qrCode,bookingEvents);
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

    public ActiveBooking updateActiveBooking(String userId, String bookingId, String testingSiteId, String dateTime,BookingEventManager bookingEvents) {
        ActiveBooking activeBooking = getActiveBookingByBookingId(bookingId);
        if (activeBooking == null) {
            return null;
        }
        return new UserGenerator().generateBookingUser(userId,bookingEvents).modifyActiveBooking(activeBooking, testingSiteId,
                dateTime,bookingEvents);
    }

    public ActiveBooking restorePastChange(String userId, String bookingId, String testingSiteId, String dateTime,BookingEventManager bookingEvents) {
        ActiveBooking activeBooking = getActiveBookingByBookingId(bookingId);
        if (activeBooking == null) {
            return null;
        }
        return new UserGenerator().generateBookingUser(userId,bookingEvents).restorePastChange(activeBooking, testingSiteId,
                dateTime,bookingEvents);
    }

    public void cancelActiveBooking(String userId, String bookingId, BookingEventManager bookingEvents) {
        ActiveBooking activeBooking = getActiveBookingByBookingId(bookingId);
        new UserGenerator().generateBookingUser(userId,bookingEvents).cancelActiveBooking(activeBooking,bookingEvents);
    }

   

}
