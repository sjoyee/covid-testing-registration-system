package com.fit3077.covidtestingregistration.model.booking.notification;

import java.util.ArrayList;
import java.util.List;
import com.fit3077.covidtestingregistration.model.booking.ActiveBooking;

public class NotifyReceptionistListener implements BookingEventListener {
    private String notificationString = "test";
    private List<String> subscriberList = new ArrayList<>();
    private String currentUserId = "";

    private void setModificationNotification(String newTestSite, String newDateTime, String userId, String bookingId) {
        this.notificationString = "Booking No. " + bookingId + " has been modified by User No. " + userId
                + "\nUpdated Info: Testing Site No. " + newTestSite + ", Start Time : " + newDateTime;
    }

    private void setCancelledNotification(String userId, String bookingId) {
        this.notificationString = "Booking No. " + bookingId + " has been cancelled by User No. " + userId;
    }

    private void setRevertNotification(String newTestSite, String newDateTime, String userId, String bookingId) {
        this.notificationString = "Booking No. " + bookingId + " has been reverted by User No. " + userId
                + "\nUpdated Info: Testing Site No. " + newTestSite + ", Start Time : " + newDateTime;
    }

    private void setDeleteNotification(String userId, String bookingId) {
        this.notificationString = "Booking No. " + bookingId + " has been deleted by Admin No. " + userId;
    }

    private void setCreateNotification(String userId) {
        this.notificationString = "A booking has been created for Customer No. " + userId;
    }

    public String getNotificationString() {
        return this.notificationString;
    }

    public List<String> getSubscribers() {
        return this.subscriberList;
    }

    @Override
    public void update(String event, List<String> subscribers, ActiveBooking activeBooking, String currentUserId) {
        String bookingId = activeBooking.getId();
        String newTestSite = activeBooking.getTestingSite().getId();
        String newDateTime = activeBooking.getStartTime();
        if (event == "modify") {
            setModificationNotification(newTestSite, newDateTime, currentUserId, bookingId);
        } else if (event == "cancel") {
            setCancelledNotification(currentUserId, bookingId);
        } else if (event == "restore") {
            setRevertNotification(newTestSite, newDateTime, currentUserId, bookingId);
        } else if (event == "delete") {
            setDeleteNotification(currentUserId, bookingId);
        }
        
        this.subscriberList = subscribers;
        this.currentUserId = currentUserId;

    }
    @Override
    public void update(String event, List<String> subscribers, String customerId) {
        
        if (event == "create") {
            setCreateNotification(currentUserId);
        }
        this.subscriberList = subscribers;
        this.currentUserId= customerId;
    }


    @Override
    public String notifyUser(String inputUserId) {
        // only send to the other not yourself
        if (this.getSubscribers().contains(inputUserId) && !inputUserId.equals(currentUserId)) {
            return this.notificationString;
        }
        return null;
    }

}
