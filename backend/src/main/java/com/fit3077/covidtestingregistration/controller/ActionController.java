package com.fit3077.covidtestingregistration.controller;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fit3077.covidtestingregistration.ActionFacade;

import com.fit3077.covidtestingregistration.testingsite.TestingSite;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/user")
public class ActionController {

    private ActionFacade actionFacade;

    public ActionController() {
        actionFacade = new ActionFacade();
    }

    @PostMapping("/login")
    public ResponseEntity<JsonNode> login(@RequestParam("userName") String userName,
            @RequestParam("password") String password) {
        this.actionFacade.createLogin(userName, password);
        JsonNode userNode = new ObjectMapper().convertValue(this.actionFacade.getUser(),
                JsonNode.class);
        if (userNode == null) {
            return new ResponseEntity<>(userNode, HttpStatus.FORBIDDEN);
        } else {
            return new ResponseEntity<>(userNode, HttpStatus.OK);
        }
    }

    @PostMapping("/booking")
    public ResponseEntity<Void> onsiteBooking(@RequestBody ObjectNode userObject) {
        boolean successful = this.actionFacade.createBooking(userObject);
        if (successful) {
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/check-status")
    public ResponseEntity<String> checkStatus(@RequestParam("pin") String pin) {
        String status = this.actionFacade.checkBookingStatus(pin);
        return new ResponseEntity<>(status, HttpStatus.OK);
    }

    @PostMapping("/covid-test")
    public ResponseEntity<Void> covidTest(@RequestBody ObjectNode testObject) {
        boolean successful = this.actionFacade.createCovidTest(testObject);
        if (successful) {
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/update-test-kit-issued")
    public ResponseEntity<Void> updateTestKitIssued(@RequestParam("qrCode") String qrCode) {
        boolean successful = this.actionFacade.updateTestKitIssued(qrCode);
        if (successful) {
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

}
