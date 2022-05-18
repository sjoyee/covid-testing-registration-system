package com.fit3077.covidtestingregistration.controller;

import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fit3077.covidtestingregistration.model.MainFacade;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/{userId}/booking")
public class BookingController {

    private MainFacade mainFacade;

    public BookingController() {
        mainFacade = new MainFacade();
    }

    @PostMapping("")
    public ResponseEntity<Void> addBooking(@PathVariable("userId") String userId,
            @RequestBody ObjectNode userObject) {
        boolean successful = this.mainFacade.addBooking(userId, userObject);
        if (successful) {
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/check-status")
    public ResponseEntity<String> checkStatus(@PathVariable("userId") String userId, @RequestParam("pin") String pin) {
        String status = this.mainFacade.getBookingStatus(userId, pin);
        return new ResponseEntity<>(status, HttpStatus.OK);
    }

    @PostMapping("/update-test-kit-issued")
    public ResponseEntity<Void> updateTestKitIssued(@PathVariable("userId") String userId,
            @RequestParam("qrCode") String qrCode) {
        boolean successful = this.mainFacade.updateTestKitIssued(userId, qrCode);
        if (successful) {
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

}
