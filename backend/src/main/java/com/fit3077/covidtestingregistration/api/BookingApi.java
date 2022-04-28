package com.fit3077.covidtestingregistration.api;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

public class BookingApi {

    private static final String API_KEY = System.getenv("FIT3077_API_KEY");

    private static final String KEY_NAME = "Authorization";

    private String rootUrl = "https://fit3077.com/api/v1/booking";

    public boolean createNewBooking(ObjectNode bookingNode) {

        String jsonString = bookingNode.toString();

        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder(URI.create(rootUrl))
                .setHeader(KEY_NAME, API_KEY)
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(jsonString))
                .build();

        try {
            client.send(request, HttpResponse.BodyHandlers.ofString());
            // return true if booking is successful
            return true;

        } catch (InterruptedException e) {
            e.printStackTrace();
            Thread.currentThread().interrupt();

        } catch (IOException e) {
            e.printStackTrace();

        }
        // if booking is unsuccessful, return false
        return false;

    }

    public ObjectNode[] getBookings() {

        HttpClient client = HttpClient.newHttpClient();

        HttpRequest request = HttpRequest
                .newBuilder(URI.create(rootUrl))
                .setHeader(KEY_NAME, API_KEY)
                .GET()
                .build();

        try {
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            return new ObjectMapper().readValue(response.body(), ObjectNode[].class);

        } catch (InterruptedException e0) {
            e0.printStackTrace();
            Thread.currentThread().interrupt();

        } catch (IOException e1) {
            e1.printStackTrace();

        }

        return new ObjectNode[0];

    }

}