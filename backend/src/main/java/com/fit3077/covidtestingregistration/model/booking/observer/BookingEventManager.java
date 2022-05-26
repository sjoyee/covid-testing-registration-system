package com.fit3077.covidtestingregistration.model.booking.observer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.fit3077.covidtestingregistration.model.booking.ActiveBooking;

public class BookingEventManager {
    Map<String, List<String>> listeners = new HashMap<>();

    // public BookingEventManager() {
        
    // }

    public void addTestingSite(String... operations){
        for (String operation : operations) {
            this.listeners.put(operation, new ArrayList<>());
        }
    }

    public void subscribe(String testingSiteId, String listenerId) {
        List<String> users = listeners.get(testingSiteId);
        users.add(listenerId);
    }

    public void unsubscribe(String testingSiteId, String listenerId) {
        List<String> users = listeners.get(testingSiteId);
        users.remove(listenerId);
    }

    // public void notify(String testingSiteId, ActiveBooking booking) {
    //     List<String> users = listeners.get(testingSiteId);
    //     for (String listener : users) {
    //         listener.update(testingSiteId, booking);
    //     }
    // }
    // public void notify(String eventType, String userId, String testingSiteId) {
    //     List<BookingEventListener> users = listeners.get(eventType);
    //     for (BookingEventListener listener : users) {
    //         listener.update(eventType, userId, testingSiteId);
    //     }
    // }
}
