package com.booking.ProjectISS.controller;

import com.booking.ProjectISS.model.Guest;
import com.booking.ProjectISS.service.IGuestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@RestController
@RequestMapping("/api/guests")
public class GuestController {

    @Autowired
    private IGuestService guestService;

    //getAll
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Collection<Guest>> getGuest() {
        Collection<Guest> guests = guestService.findAll();
        return new ResponseEntity<Collection<Guest>>(guests, HttpStatus.OK);
    }

    //getOne

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Guest> getGuest(@PathVariable("id") Long id) {
        Guest guest = guestService.findOne(id);
        if (guest == null) {
            return new ResponseEntity<Guest>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<Guest>(guest, HttpStatus.OK);
    }

    //moze proci i ovo
//    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
//    public ResponseEntity<Guest> getGuest(@RequestParam("id") String email) {
//        Collection<Guest> guests=new ArrayList<>();
//        Guest guest=new Guest(5,"a","b","c","d","065555555","fwae");
//        guests.add(guest);
//        //Greeting greeting = greetingService.findOne(id);
//
//        if(email.equals("ab")){
//            return new ResponseEntity<Guest>(guest, HttpStatus.OK);
//        }
//        return new ResponseEntity<Guest>(HttpStatus.NOT_FOUND);
//    }

    //deleteOne
    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Guest> deleteGuest(@PathVariable("id") Long id) {
        guestService.delete(id);
        return new ResponseEntity<Guest>(HttpStatus.NO_CONTENT);
    }

    //post

    //put
}
