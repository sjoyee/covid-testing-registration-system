package com.fit3077.covidtestingregistration;

import java.util.List;

import com.fit3077.covidtestingregistration.testingsite.TestingSite;
import com.fit3077.covidtestingregistration.testingsite.TestingSiteCollection;

public class TestingSiteFacade {

    public List<TestingSite> filterSite(String inputType, String inputSuburb) {

        TestingSiteCollection testingSiteList = new TestingSiteCollection();
        testingSiteList.updateTestingSiteList(inputType, inputSuburb);
        return testingSiteList.getTestingSiteList();
    }

}
