
package com.fit3077.covidtestingregistration.model.testingsite;

/**
 * A class for Address object and store Address details
 */
public class Address {

    private double latitude;

    private double longitude;

    private String unitNumber;

    private String street;

    private String street2;

    private String suburb;

    private String state;

    private String postcode;

    public Address(double latitude, double longitude, String unitNumber, String street, String street2, String suburb,
            String state, String postcode) {
        super();
        this.latitude = latitude;
        this.longitude = longitude;
        this.unitNumber = unitNumber;
        this.street = street;
        this.street2 = street2;
        this.suburb = suburb;
        this.state = state;
        this.postcode = postcode;

    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public String getUnitNumber() {
        return unitNumber;
    }

    public void setUnitNumber(String unitNumber) {
        this.unitNumber = unitNumber;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public Object getStreet2() {
        return street2;
    }

    public void setStreet2(String street2) {
        this.street2 = street2;
    }

    public String getSuburb() {
        return suburb;
    }

    public void setSuburb(String suburb) {
        this.suburb = suburb;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getPostcode() {
        return postcode;
    }

    public void setPostcode(String postcode) {
        this.postcode = postcode;
    }

}
