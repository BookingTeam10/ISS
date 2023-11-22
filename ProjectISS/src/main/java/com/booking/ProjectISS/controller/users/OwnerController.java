package com.booking.ProjectISS.controller.users;

import com.booking.ProjectISS.dto.reservations.ReservationDTO;
import com.booking.ProjectISS.enums.ReservationStatus;
import com.booking.ProjectISS.model.reservations.Reservation;
import com.booking.ProjectISS.model.users.Owner;
import com.booking.ProjectISS.service.reservations.IReservationService;
import com.booking.ProjectISS.service.users.guest.IGuestService;
import com.booking.ProjectISS.dto.accomodations.AccommodationDTO;
import com.booking.ProjectISS.dto.reviews.ReviewDTO;
import com.booking.ProjectISS.dto.users.GuestDTO;
import com.booking.ProjectISS.dto.users.OwnerDTO;
import com.booking.ProjectISS.dto.users.UserDTO;
import com.booking.ProjectISS.model.accomodations.Accommodation;
import com.booking.ProjectISS.model.reviews.Review;
import com.booking.ProjectISS.model.users.Owner;
import com.booking.ProjectISS.service.accommodation.IAccommodationService;
import com.booking.ProjectISS.service.users.owner.IOwnerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/api/owners")
public class OwnerController {

    @Autowired
    private IOwnerService ownerService;
    @Autowired
    private IReservationService reservationService;
    @Autowired
    private IGuestService guestService;

    @Autowired
    private IAccommodationService accommodationService;

    //getAll
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Collection<OwnerDTO>> getOwnerDTO() {
        Collection<OwnerDTO> Owners = ownerService.findAllDTO();
        return new ResponseEntity<Collection<OwnerDTO>>(Owners, HttpStatus.OK);
    }

//without DTO

//    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
//    public ResponseEntity<Collection<Owner>> getOwner() {
//        Collection<Owner> Owners = OwnerService.findAll();
//        return new ResponseEntity<Collection<Owner>>(Owners, HttpStatus.OK);
//    }

    //getOne

    @GetMapping(value = "/{id}/requests", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Collection<ReservationDTO>> getGuestReservations(
            @PathVariable("id") Long id,
            @RequestParam(name = "location", required = false) String location,
            @RequestParam(name = "date", required = false) String date,
            @RequestParam(name = "status", required = false) ReservationStatus status){

        List<Reservation> reservations = reservationService.getOwnerReservations(ownerService.findOne(id).getId());
        if(location != null || date != null){
            reservations = reservationService.searchReservations(reservations, location, date);
        }
        if(status != null){
            reservations = reservationService.filterReservations(reservations, status);
        }

        return new ResponseEntity<Collection<ReservationDTO>>(reservationService.getReservationsDTO(reservations),
                HttpStatus.OK);
    }
    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<OwnerDTO> getOwner(@PathVariable("id") Long id) {
        OwnerDTO Owner = ownerService.findOneDTO(id);
        if (Owner != null) {
            return new ResponseEntity<OwnerDTO>(Owner, HttpStatus.OK);
        }

        return new ResponseEntity<OwnerDTO>(HttpStatus.NOT_FOUND);
    }

    @GetMapping(value = "/{id}/accomodations", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getOwnerAccomodation(@PathVariable("id") Long id) {
        OwnerDTO Owner = ownerService.findOneDTO(id);
        if (Owner != null) {
            Collection<AccommodationDTO> accommodationDTOs=accommodationService.findAllByOwnerDTO(id);
            if(accommodationDTOs!=null){

                return new ResponseEntity<Collection<AccommodationDTO>>(accommodationDTOs, HttpStatus.OK);
            }
            return new ResponseEntity<OwnerDTO>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<OwnerDTO>(HttpStatus.NOT_FOUND);
    }

    //deleteOne
    @DeleteMapping(value = "/{id}")
    public ResponseEntity<OwnerDTO> deleteOwner(@PathVariable("id") Long id) {
        ownerService.delete(id);
        return new ResponseEntity<OwnerDTO>(HttpStatus.NO_CONTENT);
    }

    //post
    @PostMapping(value = "/{id}/accomodations",consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<AccommodationDTO> createAccomodationByOwner(@PathVariable Long id, @RequestBody Accommodation accommodation) throws Exception {
        AccommodationDTO accommodationDTO = accommodationService.createByOwner(id, accommodation);
        return new ResponseEntity<AccommodationDTO>(accommodationDTO, HttpStatus.CREATED);
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<OwnerDTO> createOwner(@RequestBody Owner Owner) throws Exception {
        OwnerDTO OwnerDTO = ownerService.create(Owner);
        return new ResponseEntity<OwnerDTO>(OwnerDTO, HttpStatus.CREATED);
    }

    //put
    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<OwnerDTO> updateOwner(@RequestBody Owner Owner, @PathVariable Long id)
            throws Exception {
        Owner OwnerForUpdate = ownerService.findOne(id);
        if (OwnerForUpdate == null) {
            return new ResponseEntity<OwnerDTO>(HttpStatus.NOT_FOUND);
        }
        OwnerForUpdate.copyValues(Owner);
        OwnerDTO updatedOwner = ownerService.update(OwnerForUpdate);
        return new ResponseEntity<OwnerDTO>(updatedOwner, HttpStatus.OK);
    }

    //pri proveravanju samo Type i Review menjati
    @PutMapping(value = "/comment/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ReviewDTO> updateComment(@RequestBody Review review, @PathVariable Long id)
            throws Exception {
        //ReviewDTO reviewDTOForUpdate = reviewService.findOne(id);
        ReviewDTO reviewDTOForUpdate=null;
        if (reviewDTOForUpdate == null) {
            return new ResponseEntity<ReviewDTO>(HttpStatus.NOT_FOUND);
        }
        reviewDTOForUpdate.copyValues(review);
        //ReviewDTO reviewDTO = reviewService.update(reviewDTOForUpdate);
        ReviewDTO reviewDTO=null;
        return new ResponseEntity<ReviewDTO>(reviewDTO, HttpStatus.OK);
    }
}
