package com.fit3077.covidtestingregistration.controller;

import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fit3077.covidtestingregistration.model.MainFacade;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/{userId}/covid-test")
public class CovidTestController {

    private MainFacade mainFacade;

    public CovidTestController() {
        mainFacade = new MainFacade();
    }

    @PostMapping("")
    public ResponseEntity<String> createCovidTest(@PathVariable("userId") String userId,
            @RequestBody ObjectNode testObject) {
        String id = this.mainFacade.addCovidTest(userId, testObject);
        if (id != null) {
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
}
