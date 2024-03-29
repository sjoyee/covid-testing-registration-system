package com.fit3077.covidtestingregistration.controller;

import java.util.List;

import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fit3077.covidtestingregistration.model.MainFacade;
import com.fit3077.covidtestingregistration.model.booking.ActiveBooking;
import com.fit3077.covidtestingregistration.model.booking.Booking;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/{userId}/booking")
public class BookingController {

    private MainFacade mainFacade;

    public BookingController() {
        mainFacade = new MainFacade();
    }

    @PostMapping("")
    public ResponseEntity<String> addBooking(@PathVariable("userId") String userId,
            @RequestBody ObjectNode userObject) {
        String id = this.mainFacade.addBooking(userId, userObject);
        if (id != null) {
            return new ResponseEntity<>(id, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(id, HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/check-status")
    public ResponseEntity<String> checkStatusByPin(@PathVariable("userId") String userId,
            @RequestParam("pin") String pin) {
        String status = this.mainFacade.getBookingStatus(userId, pin, false);
        return new ResponseEntity<>(status, HttpStatus.OK);
    }

    @GetMapping("/check-status-by-id")
    public ResponseEntity<String> checkStatusById(@PathVariable("userId") String userId,
            @RequestParam("id") String bookingId) {
        String status = this.mainFacade.getBookingStatus(userId, bookingId, true);
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

    @GetMapping("/active-bookings")
    public ResponseEntity<List<ActiveBooking>> getActiveBookings(@PathVariable("userId") String userId) {
        return new ResponseEntity<>(this.mainFacade.displayActiveBookings(userId), HttpStatus.OK);
    }

    @GetMapping("/active-booking")
    public ResponseEntity<ActiveBooking> getActiveBookingById(@RequestParam("id") String bookingId) {
        ActiveBooking activeBooking = this.mainFacade.displayActiveBookingById(bookingId);
        if (activeBooking == null) {
            return new ResponseEntity<>(activeBooking, HttpStatus.BAD_REQUEST);
        } else {
            return new ResponseEntity<>(activeBooking, HttpStatus.OK);
        }
    }

    @GetMapping("/active-booking-by-pin")
    public ResponseEntity<ActiveBooking> getActiveBookingByPin(@RequestParam("smsPin") String pin) {
        ActiveBooking activeBooking = this.mainFacade.displayActiveBookingByPin(pin);
        if (activeBooking == null) {
            return new ResponseEntity<>(activeBooking, HttpStatus.BAD_REQUEST);
        } else {
            return new ResponseEntity<>(activeBooking, HttpStatus.OK);
        }
    }

    @PostMapping("/active-booking/modify")
    public ResponseEntity<ActiveBooking> modifyActiveBooking(@PathVariable("userId") String userId,
            @RequestParam("bookingId") String bookingId, @RequestParam("testingSiteId") String testingSiteId,
            @RequestParam("dateTime") String dateTime) {

        ActiveBooking activeBooking = this.mainFacade.updateActiveBooking(userId, bookingId, testingSiteId, dateTime);
        if (activeBooking == null) {
            return new ResponseEntity<>(activeBooking, HttpStatus.BAD_REQUEST);
        } else {
            return new ResponseEntity<>(activeBooking, HttpStatus.OK);
        }
    }

    @PostMapping("/active-booking/restore")
    public ResponseEntity<ActiveBooking> restoreActiveBooking(@PathVariable("userId") String userId,
            @RequestParam("bookingId") String bookingId, @RequestParam("testingSiteId") String testingSiteId,
            @RequestParam("dateTime") String dateTime) {
        ActiveBooking activeBooking = this.mainFacade.restorePastBookingChanges(userId, bookingId, testingSiteId,
                dateTime);
        if (activeBooking == null) {
            return new ResponseEntity<>(activeBooking, HttpStatus.BAD_REQUEST);
        } else {
            return new ResponseEntity<>(activeBooking, HttpStatus.OK);
        }
    }

    @PostMapping("/active-booking/cancel")
    public ResponseEntity<Void> cancelActiveBooking(@PathVariable("userId") String userId,
            @RequestParam("id") String bookingId) {
        this.mainFacade.cancelActiveBooking(userId, bookingId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/active-booking/delete")
    public ResponseEntity<Void> deleteActiveBooking(@PathVariable("userId") String userId,
            @RequestParam("id") String bookingId) {
        this.mainFacade.deleteActiveBooking(userId, bookingId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/all")
    public ResponseEntity<List<Booking>> getBookingsByTestingSite(@RequestParam("id") String testingSiteId) {
        return new ResponseEntity<>(this.mainFacade.displayBookingsByTestingSiteId(testingSiteId), HttpStatus.OK);
    }

    @GetMapping("/admin-notif")
    public ResponseEntity<String> notifyAdmin(@PathVariable("userId") String adminId) {

        String notification = this.mainFacade.notifyBookingUpdate(adminId);
        return new ResponseEntity<>(notification, HttpStatus.OK);

    }

}
