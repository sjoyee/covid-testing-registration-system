package com.fit3077.covidtestingregistration.model.booking.log;

import java.util.List;


public class LogCollection {
    private List<Log> allLogs;
    private List<Log> logs;

    public void addLogToCollection(Log log){
        this.logs.add(log);
    }

    // private List<Log> filterLogByTestingSite(String testingSiteId){

    // }



    //this collection helps to regulate the list of logs
    //so that if in the future I need a Clear Log feature
    // can easily just add a method here as well
    //extensible :>
}
