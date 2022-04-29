package com.fit3077.covidtestingregistration.controller;
import com.fit3077.covidtestingregistration.TestingSiteFacade;
import com.fit3077.covidtestingregistration.testingsite.TestingSite;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/testing-site")
public class TestingSiteController {

    private TestingSiteFacade testingSiteFacade;

    public TestingSiteController() {
        testingSiteFacade = new TestingSiteFacade();
    }

    @GetMapping("/selected")
    public ResponseEntity<List<TestingSite>> filteredTestingSite(@RequestParam("type") String type,
            @RequestParam("suburb") String suburb) {
        List<TestingSite> sites = this.testingSiteFacade.filterSite(type, suburb);
        return new ResponseEntity<>(sites, HttpStatus.OK);
    }

    ;

}
