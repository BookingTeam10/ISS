package com.booking.ProjectISS.controller;

import com.booking.ProjectISS.dto.users.GuestDTO;
import com.booking.ProjectISS.model.users.Guest;
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
    public ResponseEntity<Collection<GuestDTO>> getGuestDTO() {
        Collection<GuestDTO> guests = guestService.findAllDTO();
        return new ResponseEntity<Collection<GuestDTO>>(guests, HttpStatus.OK);
    }

//without DTO

//    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
//    public ResponseEntity<Collection<Guest>> getGuest() {
//        Collection<Guest> guests = guestService.findAll();
//        return new ResponseEntity<Collection<Guest>>(guests, HttpStatus.OK);
//    }

    //getOne

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<GuestDTO> getGuest(@PathVariable("id") Long id) {
        GuestDTO guest = guestService.findOneDTO(id);
        if (guest != null) {
            return new ResponseEntity<GuestDTO>(guest, HttpStatus.OK);
        }

        return new ResponseEntity<GuestDTO>(HttpStatus.NOT_FOUND);
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
    public ResponseEntity<GuestDTO> deleteGuest(@PathVariable("id") Long id) {
        guestService.delete(id);
        return new ResponseEntity<GuestDTO>(HttpStatus.NO_CONTENT);
    }

    //post
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<GuestDTO> createGuest(@RequestBody Guest guest) throws Exception {
        GuestDTO guestDTO = guestService.create(guest);
        return new ResponseEntity<GuestDTO>(guestDTO, HttpStatus.CREATED);
    }

    //put
    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<GuestDTO> updateGreeting(@RequestBody Guest guest, @PathVariable Long id)
            throws Exception {
        Guest guestForUpdate = guestService.findOne(id);
        if (guestForUpdate == null) {
            return new ResponseEntity<GuestDTO>(HttpStatus.NOT_FOUND);
        }
        guestForUpdate.copyValues(guest);
        GuestDTO updatedGuest = guestService.update(guestForUpdate);
        return new ResponseEntity<GuestDTO>(updatedGuest, HttpStatus.OK);
    }
}
