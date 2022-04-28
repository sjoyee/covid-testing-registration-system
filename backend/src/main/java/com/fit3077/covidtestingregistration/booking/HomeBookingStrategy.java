package com.fit3077.covidtestingregistration.booking;

import java.util.Random;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fit3077.covidtestingregistration.api.BookingApi;

public class HomeBookingStrategy implements BookingStrategy {
    private Random rand = new Random();

    private boolean hasRatKit;

    public HomeBookingStrategy(boolean hasRatKit) {
        this.hasRatKit = hasRatKit;
    }

    @Override
    public boolean executeBooking(String customerId, String startTime) {
        BookingApi bookingApi = new BookingApi();
        ObjectMapper mapper = new ObjectMapper();

        ObjectNode bookingNode = mapper.createObjectNode();
        bookingNode.put("customerId", customerId);
        bookingNode.put("startTime", startTime);
        bookingNode.with("additionalInfo").put("qrCode", generateRandStr(8));
        bookingNode.with("additionalInfo").put("url", generateUrl());
        bookingNode.with("additionalInfo").put("hasRatKit", this.hasRatKit);

        return bookingApi.createNewBooking(bookingNode);
    }

    private String generateRandStr(int length) {

        String possibleChar = "ABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890";

        StringBuilder qrCode = new StringBuilder();

        for (int i = 0; i < length; i++) {
            int randIndex = rand.nextInt(possibleChar.length());
            qrCode.append(possibleChar.charAt(randIndex));
        }
        return qrCode.toString();
    }

    private String generateUrl() {
        String url = "https://covid19-home-testing/";
        url += generateRandStr(10);
        return url;
    }

}
