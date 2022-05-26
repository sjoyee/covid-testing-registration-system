package com.fit3077.covidtestingregistration.model.observer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.fit3077.covidtestingregistration.model.booking.ActiveBooking;

public class BookingEventManager {
    private Map<String, Set<String>> testingSiteAdmin = new HashMap<>();
    private BookingEventListener listener = new BookingNotificationListener(); 

 
    public void addTestingSite(String... testingSites){
        for (String testingSite : testingSites) {
            this.testingSiteAdmin.put(testingSite, new HashSet<>());
        }
    }

    public void subscribe(String testingSiteId, String adminId) {
        if(!testingSiteAdmin.containsKey(testingSiteId)){
            this.addTestingSite(testingSiteId);
        }
        Set<String> users = testingSiteAdmin.get(testingSiteId);
        users.add(adminId);
    }

    public void unsubscribe(String testingSiteId, String listenerId) {
        Set<String> users = testingSiteAdmin.get(testingSiteId);
        users.remove(listenerId);
    }

    //notify when there is changes made to Booking
    public void notify(String eventType, ActiveBooking booking,String currentUserId,String... testingSitesInvolved) {
        List<String> subscriberList = new ArrayList<>();
        //get all involved receptionists
        if(testingSitesInvolved.length>0){
            for (String testingSite : testingSitesInvolved){
            Set<String> users = testingSiteAdmin.get(testingSite);
            subscriberList.addAll(users);
        }
        }
        else{
            Set<String> users = testingSiteAdmin.get(booking.getTestingSiteId());
            subscriberList.addAll(users);
        }
        
        
        
        listener.update(eventType,subscriberList,booking,currentUserId);
        
    }
    
}
