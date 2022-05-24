package com.fit3077.covidtestingregistration.model.booking.notification;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.fit3077.covidtestingregistration.model.booking.ActiveBooking;

public class BookingEventManager {
    Map<String, List<BookingEventListener>> listeners = new HashMap<>();

    public BookingEventManager(String... operations) {
        for (String operation : operations) {
            this.listeners.put(operation, new ArrayList<>());
        }
    }

    public void subscribe(String eventType, BookingEventListener listener) {
        List<BookingEventListener> users = listeners.get(eventType);
        users.add(listener);
    }

    public void unsubscribe(String eventType, BookingEventListener listener) {
        List<BookingEventListener> users = listeners.get(eventType);
        users.remove(listener);
    }

    public void notify(String eventType, ActiveBooking booking) {
        List<BookingEventListener> users = listeners.get(eventType);
        for (BookingEventListener listener : users) {
            listener.update(eventType, booking);
        }
    }
}
