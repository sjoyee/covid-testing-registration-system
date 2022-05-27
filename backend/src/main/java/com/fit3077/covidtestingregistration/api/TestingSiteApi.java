package com.fit3077.covidtestingregistration.api;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.security.InvalidKeyException;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

public class TestingSiteApi {

    private static final String API_KEY = System.getenv("FIT3077_API_KEY");

    private static final String KEY_NAME = "Authorization";

    private String rootUrl = "https://fit3077.com/api/v2/testing-site";

    public ObjectNode[] getTestingSites() {

        HttpClient client = HttpClient.newHttpClient();

        HttpRequest request = HttpRequest
                .newBuilder(URI.create(rootUrl))
                .setHeader(KEY_NAME, API_KEY)
                .GET()
                .build();

        try {
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() != 200) {
                throw new InvalidKeyException("Please specify your API key");
            }

            return new ObjectMapper().readValue(response.body(), ObjectNode[].class);

        } catch (InterruptedException e0) {
            e0.printStackTrace();
            Thread.currentThread().interrupt();

        } catch (IOException e1) {
            e1.printStackTrace();

        } catch (InvalidKeyException e2) {
            e2.printStackTrace();
        }

        return new ObjectNode[0];

    }

    public String getTestingSiteNameById(String testingSiteId) {
        String testingSiteUrl = rootUrl + '/' + testingSiteId;

        HttpClient client = HttpClient.newHttpClient();

        HttpRequest request = HttpRequest
                .newBuilder(URI.create(testingSiteUrl))
                .setHeader(KEY_NAME, API_KEY)
                .GET()
                .build();

        try {
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() != 200) {
                throw new InvalidKeyException("Please specify your API key");
            }

            return new ObjectMapper().readValue(response.body(), ObjectNode.class).get("name").textValue();

        } catch (InterruptedException e0) {
            e0.printStackTrace();
            Thread.currentThread().interrupt();

        } catch (IOException e1) {
            e1.printStackTrace();

        } catch (InvalidKeyException e2) {
            e2.printStackTrace();
        }

        return null;
    }

    public JsonNode getBookingsByTestingSiteId(String testingSiteId) {

        String testingSiteUrl = rootUrl + '/' + testingSiteId + "?fields=bookings";

        HttpClient client = HttpClient.newHttpClient();

        HttpRequest request = HttpRequest
                .newBuilder(URI.create(testingSiteUrl))
                .setHeader(KEY_NAME, API_KEY)
                .GET()
                .build();

        try {
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() != 200) {
                throw new InvalidKeyException("Please specify your API key");
            }
            return new ObjectMapper().readTree(response.body()).get("bookings");

        } catch (InterruptedException e0) {
            e0.printStackTrace();
            Thread.currentThread().interrupt();

        } catch (IOException e1) {
            e1.printStackTrace();

        } catch (InvalidKeyException e2) {
            e2.printStackTrace();
        }

        return null;

    }

}
