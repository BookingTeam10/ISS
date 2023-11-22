package com.booking.ProjectISS.controller.users;


import com.booking.ProjectISS.dto.reservations.ReservationDTO;
import com.booking.ProjectISS.enums.ReservationStatus;
import com.booking.ProjectISS.model.reservations.Reservation;
import com.booking.ProjectISS.dto.accomodations.AccommodationDTO;
import com.booking.ProjectISS.dto.reviews.ReviewDTO;
import com.booking.ProjectISS.dto.users.AdministratorDTO;
import com.booking.ProjectISS.dto.users.GuestDTO;
import com.booking.ProjectISS.dto.users.OwnerDTO;
import com.booking.ProjectISS.model.accomodations.Accommodation;
import com.booking.ProjectISS.model.reviews.Review;
import com.booking.ProjectISS.model.users.Guest;
import com.booking.ProjectISS.service.accommodation.IAccommodationService;
import com.booking.ProjectISS.service.reservations.IReservationService;
import com.booking.ProjectISS.service.reviews.IReviewService;
import com.booking.ProjectISS.service.users.guest.IGuestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/api/guests")
public class GuestController {

    @Autowired
    private IGuestService guestService;
    @Autowired
    private IAccommodationService accommodationService;
    @Autowired
    private IReservationService reservationService;
    @Autowired
    private IReviewService reviewService;


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

    //delete one, 3.4 for guest
    @GetMapping(value = "/{id}/favouriteAccommodations", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Collection<Accommodation>> getFavouriteAccommodation(@PathVariable("id") Long id){

        return new ResponseEntity<Collection<Accommodation>>(guestService.findOne(id).getFavouriteAccommodations(),
                HttpStatus.OK);
    }
    @GetMapping(value = "/{id}/requests", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Collection<ReservationDTO>> getGuestReservations(
        @PathVariable("id") Long id,
        @RequestParam(name = "location", required = false) String location,
        @RequestParam(name = "date", required = false) String date,
        @RequestParam(name = "status", required = false) ReservationStatus status){

        List<Reservation> reservations = reservationService.getGuestReservations(guestService.findOne(id).getId());
        if(location != null || date != null){
            reservations = reservationService.searchReservations(reservations, location, date);
        }
        if(status != null){
            reservations = reservationService.filterReservations(reservations, status);
        }


        return new ResponseEntity<Collection<ReservationDTO>>(reservationService.getReservationsDTO(reservations),
        HttpStatus.OK);
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

    @DeleteMapping(value = "/{id}/request/{reqId}")
    public ResponseEntity<ReservationDTO> deleteGuestReservation(@PathVariable("id") Long id,
                                                                 @PathVariable("reqId") Long reqId){
        if(reservationService.findOne(reqId).getStatus() == ReservationStatus.ACCEPTED){
            return new ResponseEntity<ReservationDTO>(HttpStatus.NOT_FOUND);
        }else{
            reservationService.delete(reqId);
        }
        return new ResponseEntity<ReservationDTO>(HttpStatus.NO_CONTENT);
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
//        ReviewDTO reviewDTO=new ReviewDTO();
        ReviewDTO reviewDTO = reviewService.createByReservation(idReservation, review);
        return new ResponseEntity<ReviewDTO>(reviewDTO, HttpStatus.CREATED);
    }

    @DeleteMapping(value = "/comment/{id}")
    public ResponseEntity<ReviewDTO> deleteComm(@PathVariable("id") Long id) {
        reviewService.delete(id);
        return new ResponseEntity<ReviewDTO>(HttpStatus.NO_CONTENT);
    }




//    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
//    public ResponseEntity<GuestDTO> createGuest(@RequestBody Guest guest) throws Exception {
//        GuestDTO guestDTO = guestService.create(guest);
//        return new ResponseEntity<GuestDTO>(guestDTO, HttpStatus.CREATED);
//    }
    @GetMapping(value = "/accommodationsSearch")
    public ResponseEntity<Collection<AccommodationDTO>> getSearchedAccommodations(
            @RequestParam(required = false) String location,
            @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") Date start,
            @RequestParam(required = false)  @DateTimeFormat(pattern = "yyyy-MM-dd")  Date end,
            @RequestParam(required = false) int numPeople){
        Collection<AccommodationDTO> accommodationDTOS = accommodationService.getAccommodationsSearched(null,null,numPeople,location);
        if(accommodationDTOS == null)
            return new ResponseEntity<Collection<AccommodationDTO>>(HttpStatus.NOT_FOUND);
    return ResponseEntity.ok(accommodationDTOS);
    }

    //3.10 for guests
    @GetMapping(value="/accommodations")
    public ResponseEntity<Collection<AccommodationDTO>> getAccommodationsDTO() {
        Collection<AccommodationDTO> accommodationDTOS = accommodationService.findAllDTO();
        return new ResponseEntity<Collection<AccommodationDTO>>(accommodationDTOS, HttpStatus.OK);
    }
}
