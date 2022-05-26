package com.fit3077.covidtestingregistration.model.observer;

public class Notification {
    private String notificationString;
    private String event;

    public Notification(){
        
    }

    private void setNotification(String event){
        if (event=="modify"){
            setModificationNotification(newTestSite, newDateTime, receptionistId, bookingId);
        }
        else if(event =="cancel"){
            setCancelledNotification(receptionistId, bookingId);
        }
        else if (event == "add"){
            setAddNotification(receptionistId, bookingId);
        }
        else if(event == "delete"){
            setDeleteNotification(receptionistId, bookingId);
        }
            }
 
    public void setModificationNotification(String newTestSite, String newDateTime, String receptionistId,String bookingId){
        this.notificationString = "Booking No. " + bookingId + " has been modified by Admin No. "+ receptionistId 
        + "\n Updated: Testing Site No. " + newTestSite + ", Start Time : " + newDateTime; 
    }
    public void setCancelledNotification( String receptionistId,String bookingId){
        this.notificationString = "Booking No. " + bookingId + " has been cancelled by Admin No. "+ receptionistId ; 
    }
    public void setAddNotification(String receptionistId,String bookingId){
        this.notificationString = "Booking No. " + bookingId + " has been created by Admin No. "+ receptionistId ; 
    }
    public void setDeleteNotification(String receptionistId,String bookingId){
        this.notificationString = "Booking No. " + bookingId + " has been deleted by Admin No. "+ receptionistId ; 
    }

    public String getNotificationString(){
        return this.notificationString;
    }
}
