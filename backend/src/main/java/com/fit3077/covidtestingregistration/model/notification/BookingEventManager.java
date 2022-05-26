package com.fit3077.covidtestingregistration.model.notification;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fit3077.covidtestingregistration.api.UserApi;
import com.fit3077.covidtestingregistration.model.booking.ActiveBooking;

public class BookingEventManager {
    private Map<String, List<List<String>>> testingSiteAdmin = new HashMap<>();
    private BookingEventListener listener = new NotifyReceptionistListener();

    public void addTestingSite(String... testingSites) {
        for (String testingSite : testingSites) {
            this.testingSiteAdmin.put(testingSite, new ArrayList<>());
        }
    }

    public void subscribeAll() {
        UserApi userApi = new UserApi();

        for (ObjectNode userNode : userApi.getUsers()) {
            if (userNode.get("isReceptionist").asBoolean()) {
                String id = userNode.get("id").textValue();
                String givenName = userNode.get("givenName").textValue();
                String testingSiteId = userNode.get("additionalInfo").get("testingSiteId").textValue();

                List<String> pair = new ArrayList<>();
                pair.add(id);
                pair.add(givenName);

                // if the map has not add a specific testing site, add them
                if (!testingSiteAdmin.containsKey(testingSiteId)) {
                    this.addTestingSite(testingSiteId);
                }
                // retrieve the value from the map which is a list of (id,name) pair
                List<List<String>> users = testingSiteAdmin.get(testingSiteId);
                users.add(pair);

            }
        }

    }

    public void unsubscribe(String testingSiteId, String listenerId) {
        List<List<String>> users = testingSiteAdmin.get(testingSiteId);
        users.remove(listenerId);
    }

    // notify when there is changes made to Booking
    public void notify(String eventType, ActiveBooking booking, String currentUserId, String... testingSitesInvolved) {
        List<String> subscriberList = new ArrayList<>();

        // check duplication

        // get all involved receptionists
        if (testingSitesInvolved.length > 0 && !testingSitesInvolved[0].equals(testingSitesInvolved[1])) {

            for (String testingSite : testingSitesInvolved) {
                Set<String> users = testingSiteAdmin.get(testingSite);
                subscriberList.addAll(users);
            }

        }

        else {
            Set<String> users = testingSiteAdmin.get(booking.getTestingSiteId());
            subscriberList.addAll(users);
        }

        listener.update(eventType, subscriberList, booking, currentUserId);

    }

    // extensible with a List<BookingEventListener>>
    public BookingEventListener getNotifyListener() {
        return this.listener;
    }

}
