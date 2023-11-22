package com.booking.ProjectISS.controller.users;

import com.booking.ProjectISS.dto.accomodations.AccommodationDTO;
import com.booking.ProjectISS.dto.reviews.ReviewDTO;
import com.booking.ProjectISS.dto.users.GuestDTO;
import com.booking.ProjectISS.dto.users.OwnerDTO;
import com.booking.ProjectISS.model.accomodations.Accommodation;
import com.booking.ProjectISS.model.reviews.Review;
import com.booking.ProjectISS.model.users.Guest;
import com.booking.ProjectISS.service.users.guest.IGuestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
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
    //getOne

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<GuestDTO> getGuest(@PathVariable("id") Long id) {
        GuestDTO guest = guestService.findOneDTO(id);
        if (guest != null) {
            return new ResponseEntity<GuestDTO>(guest, HttpStatus.OK);
        }

        return new ResponseEntity<GuestDTO>(HttpStatus.NOT_FOUND);
    }

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
    public ResponseEntity<GuestDTO> updateGuest(@RequestBody Guest guest, @PathVariable Long id)
            throws Exception {
        Guest guestForUpdate = guestService.findOne(id);
        if (guestForUpdate == null) {
            return new ResponseEntity<GuestDTO>(HttpStatus.NOT_FOUND);
        }
        guestForUpdate.copyValues(guest);
        GuestDTO updatedGuest = guestService.update(guestForUpdate);
        return new ResponseEntity<GuestDTO>(updatedGuest, HttpStatus.OK);
    }

    //radi cekamo aleksu samo
    @GetMapping(value = "/{id}/allComments", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Collection<ReviewDTO>> allComments(@PathVariable("id") Long id) {
        Collection<ReviewDTO> reviews = new ArrayList<ReviewDTO>();
        //Collection<ReviewDTO> reviews = reviewService.findAllCommentsByGuestsDTO(id);
        //return new ResponseEntity<Collection<ReviewDTO>>(reviews, HttpStatus.OK);
        return new ResponseEntity<Collection<ReviewDTO>>(reviews, HttpStatus.OK);
    }

    //3.17
    @PostMapping(value = "/{id}/comment",consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ReviewDTO> createComment(@PathVariable("id") Long idReservation,@RequestBody Review review) throws Exception {
        ReviewDTO reviewDTO=new ReviewDTO();
        //ReviewDTO reviewDTO = reviewService.createByReservation(idReservation, review);
        return new ResponseEntity<ReviewDTO>(reviewDTO, HttpStatus.CREATED);
    }

    @DeleteMapping(value = "/comment/{id}")
    public ResponseEntity<ReviewDTO> deleteComm(@PathVariable("id") Long id) {
        //reviewService.delete(id);
        return new ResponseEntity<ReviewDTO>(HttpStatus.NO_CONTENT);
    }




//    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
//    public ResponseEntity<GuestDTO> createGuest(@RequestBody Guest guest) throws Exception {
//        GuestDTO guestDTO = guestService.create(guest);
//        return new ResponseEntity<GuestDTO>(guestDTO, HttpStatus.CREATED);
//    }
}
