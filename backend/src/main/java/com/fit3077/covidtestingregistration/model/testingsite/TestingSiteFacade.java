package com.fit3077.covidtestingregistration.model.testingsite;

import java.util.List;

/**
 * A class for uphelding the Facade design pattern,
 * A Facade class for TestingSite related functionality
 */
public class TestingSiteFacade {
    /**
     * The method make create new {@link TestingSiteCollection} and update the list
     * with the parameters
     * 
     * @param inputType   Selected input for Testing Site Type
     * @param inputSuburb Selected input for Suburb Name
     */
    public List<TestingSite> filterSite(String inputType, String inputSuburb) {

        TestingSiteCollection testingSiteList = new TestingSiteCollection();
        testingSiteList.updateTestingSiteList(inputType, inputSuburb);
        return testingSiteList.getTestingSiteList();
    }

}
