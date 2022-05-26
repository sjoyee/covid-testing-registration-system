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
    // private Map<String, List<BookingEventListener>> listeners = new HashMap<>();
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

    //for cancel
    public void notify( ActiveBooking booking,String currentUserId,String... testingSitesInvolved) {
        List<String> subscriberList = new ArrayList<>();
        //get all involved receptionists
        for (String testingSite : testingSitesInvolved){
            Set<String> users = testingSiteAdmin.get(testingSite);
            subscriberList.addAll(users);
        }
        listener.update(subscriberList,booking,currentUserId);
        
    }
    // public void notify(String eventType, String userId, String testingSiteId) {
    //     List<BookingEventListener> users = listeners.get(eventType);
    //     for (BookingEventListener listener : users) {
    //         listener.update(eventType, userId, testingSiteId);
    //     }
    // }
}
