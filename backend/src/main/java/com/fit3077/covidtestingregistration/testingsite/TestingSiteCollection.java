package com.fit3077.covidtestingregistration.testingsite;

import java.util.List;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fit3077.covidtestingregistration.api.TestingSiteApi;

public class TestingSiteCollection {
    /** A Class for a collection of TestingSites
     */

    

    private List<TestingSite> allSites = new ArrayList<>();
    private List<TestingSite> displaySiteList = new ArrayList<>();

     /** Constructor method for TestingSiteCollection
     */
    public TestingSiteCollection() {
        createTestingSite();
    }
     /** A method to create TestingSite instances and retrieve data from the API
      * for the creation of TestingSite instances, it aids in updating the private list as wel
     */
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
            String openTime = testingNode.get("additionalInfo").get("openTime").textValue();
            String closeTime = testingNode.get("additionalInfo").get("closeTime").textValue();
            TestingSite createdSite = new TestingSite(id, name, description, websiteUrl, phoneNumber, address,
                    siteType, openTime, closeTime);
            this.allSites.add(createdSite);
        }
    }

     /** filter method for the TestingSite based on the input from API 
      * 
     */
    private List<TestingSite> filteredType(String inputType, String inputSuburb) {
        Set<TestingSite> filteredSitesSet = new HashSet<>();

        if (inputType.isEmpty() && inputSuburb.isEmpty()) {
            return this.allSites;
        }

        // if both are not empty
        if (!inputType.isEmpty() && !inputSuburb.isEmpty()) {
            for (TestingSite testingSite : this.allSites) {
                if (testingSite.getTestingSiteType().name().equals(inputType)
                        && testingSite.getAddress().getSuburb().equals(inputSuburb)) {
                    filteredSitesSet.add(testingSite);
                }
            }
        }
        // either empty
        else {
            for (TestingSite testingSite : this.allSites) {
                if (!inputType.isEmpty() && testingSite.getTestingSiteType().name().equals(inputType)) {
                    filteredSitesSet.add(testingSite);
                }
                if (!inputSuburb.isEmpty() && testingSite.getAddress().getSuburb().equals(inputSuburb)) {
                    filteredSitesSet.add(testingSite);
                }
            }

        }
        return new ArrayList<>(filteredSitesSet);

    }

     /** method for creating Address object
     */
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
