package com.fit3077.covidtestingregistration.model.booking.observer;

import com.fit3077.covidtestingregistration.model.booking.ActiveBooking;

public class BookingNotificationListener implements BookingEventListener{
    private String notification;

    public BookingNotificationListener() {
        
    }

    public void setModificationNotification(String newTestSite, String newDateTime, String receptionistId,String bookingId){
        this.notification = "Booking No. " + bookingId + " has been modified by Admin No. "+ receptionistId 
        + "\n Updated: Testing Site No. " + newTestSite + ", Start Time : " + newDateTime; 
    }
    public void setCancelledNotification( String receptionistId,String bookingId){
        this.notification = "Booking No. " + bookingId + " has been cancelled by Admin No. "+ receptionistId ; 
    }
    public void setAddNotification(String receptionistId,String bookingId){
        this.notification = "Booking No. " + bookingId + " has been created by Admin No. "+ receptionistId ; 
    }
    public void setDeleteNotification(String receptionistId,String bookingId){
        this.notification = "Booking No. " + bookingId + " has been deleted by Admin No. "+ receptionistId ; 
    }


    @Override
    public void update(String testingSiteId, ActiveBooking activeBooking) {
        // System.out.println("Email to " + notification + ": Someone has performed " + eventType + " operation with the following booking: " + activeBooking.getId());
        
    }
}
