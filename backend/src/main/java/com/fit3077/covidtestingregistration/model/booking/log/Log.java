package com.fit3077.covidtestingregistration.model.booking.log;


import com.fit3077.covidtestingregistration.model.booking.ActiveBooking;

public class Log {
    private ActiveBooking selectedBooking;
    private String testingSiteId;
    private String timeOfUpdate;
    private LogEventType eventType; 

    public Log(ActiveBooking selectedBooking, String testingSiteId, String timeOfUpdate, LogEventType eventType){
        this.selectedBooking = selectedBooking;
        this.testingSiteId = testingSiteId;
        this.timeOfUpdate = timeOfUpdate;
        this.eventType = eventType;

    }







}
