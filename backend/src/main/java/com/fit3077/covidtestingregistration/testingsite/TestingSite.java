
package com.fit3077.covidtestingregistration.testingsite;

public class TestingSite {

    private String id;
    private String name;
    private String description;
    private String websiteUrl;
    private String phoneNumber;
    private Address address;
    private TestingSiteType siteType;
    private int waitingTime;

    /**
     * No args constructor for use in serialization
     * 
     */
    public TestingSite() {
    }

    public TestingSite(String id, String name, String description, String websiteUrl, String phoneNumber,
            Address address, String testingSiteType) {

        this.id = id;
        this.name = name;
        this.description = description;
        this.websiteUrl = websiteUrl;
        this.phoneNumber = phoneNumber;
        this.address = address;
        this.waitingTime = 120;
        checkTestingSiteType(testingSiteType);
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getWebsiteUrl() {
        return websiteUrl;
    }

    public void setWebsiteUrl(String websiteUrl) {
        this.websiteUrl = websiteUrl;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public int getWaitingTime() {
        return waitingTime;
    }

    public void setWaitingTime(int waitingTime) {
        this.waitingTime = waitingTime;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public TestingSiteType getTestingSiteType() {
        return siteType;
    }

    private void checkTestingSiteType(String apiString) {

        if (apiString.equals(TestingSiteType.DRIVETHROUGH.toString())) {
            this.siteType = TestingSiteType.DRIVETHROUGH;

        } else if (apiString.equals(TestingSiteType.WALKIN.toString())) {
            this.siteType = TestingSiteType.WALKIN;

        } else if (apiString.equals(TestingSiteType.CLINIC.toString())) {
            this.siteType = TestingSiteType.CLINIC;

        } else if (apiString.equals(TestingSiteType.GP.toString())) {
            this.siteType = TestingSiteType.GP;

        } else if (apiString.equals(TestingSiteType.HOSPITAL.toString())) {
            this.siteType = TestingSiteType.HOSPITAL;
        }

    }

}
