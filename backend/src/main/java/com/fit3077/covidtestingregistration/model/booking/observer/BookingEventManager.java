package com.fit3077.covidtestingregistration.model.booking.observer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.fit3077.covidtestingregistration.model.booking.ActiveBooking;

public class BookingEventManager {
    private Map<String, Set<String>> listeners = new HashMap<>();
    

    // public BookingEventManager() {
        
    // }

    public void addTestingSite(String... operations){
        for (String operation : operations) {
            this.listeners.put(operation, new HashSet<>());
        }
    }

    public void subscribe(String testingSiteId, String listenerId) {
        Set<String> users = listeners.get(testingSiteId);
        users.add(listenerId);
    }

    public void unsubscribe(String testingSiteId, String listenerId) {
        Set<String> users = listeners.get(testingSiteId);
        users.remove(listenerId);
    }

    public void notify(String testingSiteId, ActiveBooking booking) {
        Set<String> users = listeners.get(testingSiteId);
        List<String> userList = new ArrayList<>();
        userList.addAll(users);
        for (String listener : userList) {
            //need to think
            listener.update(testingSiteId, booking);
        }
    }
    // public void notify(String eventType, String userId, String testingSiteId) {
    //     List<BookingEventListener> users = listeners.get(eventType);
    //     for (BookingEventListener listener : users) {
    //         listener.update(eventType, userId, testingSiteId);
    //     }
    // }
}
