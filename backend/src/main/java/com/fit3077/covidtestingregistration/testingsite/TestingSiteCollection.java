package com.fit3077.covidtestingregistration.testingsite;
import java.util.List;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fit3077.covidtestingregistration.api.TestingSiteApi;

public class TestingSiteCollection {
    private List<TestingSite> displaySiteList = new ArrayList<>();

    public TestingSiteCollection(){
        this.displaySiteList = createTestingSite();
    }
    private List<TestingSite> createTestingSite() {
        TestingSiteApi testingSiteApi = new TestingSiteApi();
        List<TestingSite> displaySiteList = new ArrayList<>();

        for (ObjectNode testingNode : testingSiteApi.getTestingSites()) {

            String id = testingNode.get("id").textValue();
            System.out.println("here" +id);
            String name = testingNode.get("name").textValue();
            String description = testingNode.get("description").textValue();
            String websiteUrl = testingNode.get("websiteUrl").textValue();
            String phoneNumber = testingNode.get("phoneNumber").textValue();
            Address address = jsonToAddress(testingNode);
            String siteType = testingNode.get("additionalInfo").get("siteType").textValue();
            TestingSite createdSite = new TestingSite(id, name, description, websiteUrl, phoneNumber, address,
                    siteType);
            displaySiteList.add(createdSite);
        }
        return displaySiteList;
    }

    private List<TestingSite> filteredType(String inputType, String inputSuburb) {
        
        List<TestingSite> allSites = createTestingSite();
        
        Set<TestingSite> filteredSitesSet = new HashSet<TestingSite>();

        if (inputType == null && inputSuburb == null){
            return allSites;
        }
        for (TestingSite testingSite: allSites){
            if (inputType != null){
                if (testingSite.getTestingSiteType().name() == inputType){
                    filteredSitesSet.add(testingSite);
                }
            }
            if(inputSuburb != null){
                if (testingSite.getTestingSiteType().name()== inputType){
                    filteredSitesSet.add(testingSite);
                }
            }
            
        }
        List<TestingSite> filteredSites = new ArrayList<>(filteredSitesSet);
        return filteredSites;
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
        System.out.println(suburb);
        System.out.println(longitude);
        return new Address(latitude, longitude, unitNumber, street, street2, suburb, state, postcode);
    }

    public void updateTestingSiteList(String inputType, String inputSuburb){
        this.displaySiteList = filteredType(inputType, inputSuburb);
    }

    public List<TestingSite> getTestingSiteList(){
        return this.displaySiteList;
    }


}
