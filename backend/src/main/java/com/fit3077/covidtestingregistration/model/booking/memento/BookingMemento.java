package com.fit3077.covidtestingregistration.model.booking.memento;

import java.time.Instant;
import java.util.List;
import java.util.ListIterator;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fit3077.covidtestingregistration.api.BookingApi;

// Memento class for Memento pattern design
public class BookingMemento {

    private List<BookingHistory> historyList;
    private String bookingId;
    private String testingSiteId;
    private String dateTime;

    public BookingMemento(List<BookingHistory> historyList, String bookingId, String testingSiteId,
            String dateTime) {
        this.historyList = historyList;
        this.bookingId = bookingId;
        this.testingSiteId = testingSiteId;
        this.dateTime = dateTime;
    }

    public void restore(String restoreTestingSiteId, String restoreDateTime) {

        ObjectMapper mapper = new ObjectMapper();
        ObjectNode updatedNode = mapper.createObjectNode();

        ArrayNode arrayNode = createArrayNode(updatedNode);

        // add the current testing site id and datetime to the history list since it
        // will be considered as past changes
        addLatestRecord(this.testingSiteId, this.dateTime);

        ListIterator<BookingHistory> itr = this.historyList.listIterator();
        while (itr.hasNext()) {
            BookingHistory history = itr.next();
            if (history.getTestingSiteId().equals(restoreTestingSiteId)
                    && history.getDateTime().equals(restoreDateTime)) {
                // remove the to-restore version
                itr.remove();

            } else {
                arrayNode.add(mapper.valueToTree(history));
            }
        }

        BookingApi bookingApi = new BookingApi();
        bookingApi.updateActiveBooking(this.bookingId, updatedNode);

    }

    public void update() {
        if (this.historyList.size() == 3) {
            // the first item is always the oldest / least recent past record
            this.historyList.remove(0);
        }

        addLatestRecord(this.testingSiteId, this.dateTime);

        // update api
        ObjectMapper mapper = new ObjectMapper();
        ObjectNode updatedNode = mapper.createObjectNode();

        ArrayNode arrayNode = createArrayNode(updatedNode);

        ListIterator<BookingHistory> itr = this.historyList.listIterator();
        while (itr.hasNext()) {
            BookingHistory history = itr.next();
            arrayNode.add(mapper.valueToTree(history));
        }

        BookingApi bookingApi = new BookingApi();
        bookingApi.updateActiveBooking(this.bookingId, updatedNode);

    }

    private void addLatestRecord(String testingSiteId, String dateTime) {
        String updatedAt = Instant.now().toString();
        BookingHistory history = new BookingHistory(updatedAt, testingSiteId, dateTime);
        this.historyList.add(history);
    }

    private ArrayNode createArrayNode(ObjectNode updatedNode) {
        String additionalInfo = "additionalInfo";
        updatedNode.with(additionalInfo).put("isHomeBooking", false);
        return updatedNode.with(additionalInfo).putArray("snapshots");
    }

}
