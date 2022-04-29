package com.fit3077.covidtestingregistration.testingsite;

import java.util.List;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fit3077.covidtestingregistration.api.TestingSiteApi;

public class TestingSiteCollection {
    private List<TestingSite> allSites = new ArrayList<>();
    private List<TestingSite> displaySiteList = new ArrayList<>();

    public TestingSiteCollection() {
        createTestingSite();
    }

    private void createTestingSite() {
        TestingSiteApi testingSiteApi = new TestingSiteApi();

        for (ObjectNode testingNode : testingSiteApi.getTestingSites()) {

            String id = testingNode.get("id").textValue();
            String name = testingNode.get("name").textValue();
            String description = testingNode.get("description").textValue();
            String websiteUrl = testingNode.get("websiteUrl").textValue();
            String phoneNumber = testingNode.get("phoneNumber").textValue();
            Address address = jsonToAddress(testingNode);
            String siteType = testingNode.get("additionalInfo").get("siteType").textValue();
            TestingSite createdSite = new TestingSite(id, name, description, websiteUrl, phoneNumber, address,
                    siteType);
            this.allSites.add(createdSite);
        }
    }

    private List<TestingSite> filteredType(String inputType, String inputSuburb) {
        Set<TestingSite> filteredSitesSet = new HashSet<>();

        if (inputType.isEmpty() && inputSuburb.isEmpty()) {
            return this.allSites;
        }
        for (TestingSite testingSite : this.allSites) {
            if (!inputType.isEmpty() && testingSite.getTestingSiteType().name().equals(inputType)) {
                filteredSitesSet.add(testingSite);
            }
            if (!inputSuburb.isEmpty() && testingSite.getAddress().getSuburb().equals(inputSuburb)) {
                filteredSitesSet.add(testingSite);
            }

        }
        return new ArrayList<>(filteredSitesSet);
    }

    private Address jsonToAddress(ObjectNode node) {
        String street = node.get("address").get("street").textValue();
        double longitude = node.get("address").get("longitude").asDouble();
        double latitude = node.get("address").get("latitude").asDouble();
        String postcode = node.get("address").get("postcode").textValue();
        String unitNumber = node.get("address").get("unitNumber").textValue();
        String suburb = node.get("address").get("suburb").textValue();
        String state = node.get("address").get("state").textValue();
        String street2 = node.get("address").get("street2").textValue();

        return new Address(latitude, longitude, unitNumber, street, street2, suburb, state, postcode);
    }

    public void updateTestingSiteList(String inputType, String inputSuburb) {
        this.displaySiteList = this.filteredType(inputType, inputSuburb);
    }

    public List<TestingSite> getTestingSiteList() {
        return this.displaySiteList;
    }

}
