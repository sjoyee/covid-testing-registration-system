
package com.fit3077.covidtestingregistration.testingsite;

import java.util.HashMap;
import java.util.Map;
import javax.annotation.Generated;
import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.databind.node.ObjectNode;

@JsonInclude(JsonInclude.Include.NON_NULL)

public class TestingSite {

    @JsonProperty("id")
    private String id;
    @JsonProperty("name")
    private String name;
    @JsonProperty("description")
    private String description;
    @JsonProperty("websiteUrl")
    private String websiteUrl;
    @JsonProperty("phoneNumber")
    private String phoneNumber;
    @JsonProperty("address")
    private Address address;
    // @JsonProperty("createdAt")
    // private String createdAt;
    // @JsonProperty("updatedAt")
    // private String updatedAt;
    @JsonProperty("siteType")
    private TestingSiteType siteType;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    /**
     * No args constructor for use in serialization
     * 
     */
    public TestingSite() {
    }

    /**
     * 
     * @param createdAt
     * @param phoneNumber
     * @param address
     * @param websiteUrl
     * @param name
     * @param siteType
     * @param description
     * @param id
     * @param updatedAt
     */
    public TestingSite(String id, String name, String description, String websiteUrl, String phoneNumber,
        Address address, String testingSiteType) {
        
        this.id = id;
        this.name = name;
        this.description = description;
        this.websiteUrl = websiteUrl;
        this.phoneNumber = phoneNumber;
        this.address = address;
        checkiTestingSiteType(testingSiteType);
            
    }

    @JsonProperty("id")
    public String getId() {
        return id;
    }

    @JsonProperty("id")
    public void setId(String id) {
        this.id = id;
    }

    @JsonProperty("name")
    public String getName() {
        return name;
    }

    @JsonProperty("name")
    public void setName(String name) {
        this.name = name;
    }

    @JsonProperty("description")
    public String getDescription() {
        return description;
    }

    @JsonProperty("description")
    public void setDescription(String description) {
        this.description = description;
    }

    @JsonProperty("websiteUrl")
    public String getWebsiteUrl() {
        return websiteUrl;
    }

    @JsonProperty("websiteUrl")
    public void setWebsiteUrl(String websiteUrl) {
        this.websiteUrl = websiteUrl;
    }

    @JsonProperty("phoneNumber")
    public String getPhoneNumber() {
        return phoneNumber;
    }

    @JsonProperty("phoneNumber")
    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    @JsonProperty("address")
    public Address getAddress() {
        return address;
    }

    @JsonProperty("address")
    public void setAddress(Address address) {
        this.address = address;
    }

    

    @JsonProperty("siteType")
    public TestingSiteType getTestingSiteType() {
        return siteType;
    }

    @JsonProperty("additionalInfo")
    public void setAdditionalInfo(TestingSiteType siteType) {
        this.siteType = siteType;
    }


    private void checkiTestingSiteType(String apiString){
        if (apiString =="DRIVETHROUGH"){
            this.siteType = TestingSiteType.DRIVETHROUGH;
        }
        else if (apiString =="WALKIN"){
            this.siteType = TestingSiteType.WALKIN;
        }
        else if (apiString =="CLINIC"){
            this.siteType = TestingSiteType.CLINIC;
        }
        else if (apiString =="GP"){
            this.siteType = TestingSiteType.GP;
        }
        else if (apiString =="HOSPITAL"){
            this.siteType = TestingSiteType.HOSPITAL;
        }

        

    }
    
    
    
}
