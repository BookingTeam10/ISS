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
import com.booking.ProjectISS.model.users.Administrator;
import com.booking.ProjectISS.model.users.Guest;
import com.booking.ProjectISS.model.users.Owner;
import com.booking.ProjectISS.service.accommodation.IAccommodationService;
import com.booking.ProjectISS.service.reservations.IReservationService;
import com.booking.ProjectISS.service.reviews.IReviewService;
import com.booking.ProjectISS.service.users.guest.IGuestService;
import com.booking.ProjectISS.service.users.owner.IOwnerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/guests")
@CrossOrigin(origins = "http://localhost:4200")
public class GuestController {

    @Autowired
    private IGuestService guestService;
    @Autowired
    private IAccommodationService accommodationService;
    @Autowired
    private IReservationService reservationService;
    @Autowired
    private IReviewService reviewService;

    @Autowired
    private IOwnerService ownerService;
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Collection<GuestDTO>> getGuestDTO() {
        Collection<GuestDTO> guests = guestService.findAllDTO();
        return new ResponseEntity<Collection<GuestDTO>>(guests, HttpStatus.OK);
    }
    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasRole('Guest')")
    public ResponseEntity<GuestDTO> getGuest(@PathVariable("id") Long id) {
        GuestDTO guest = guestService.findOneDTO(id);
        if (guest != null) {
            return new ResponseEntity<GuestDTO>(guest, HttpStatus.OK);
        }

        return new ResponseEntity<GuestDTO>(HttpStatus.NOT_FOUND);
    }

    @GetMapping(value = "/username/{username}", produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasRole('Guest')")
    public ResponseEntity<Guest> getGuestUsername(@PathVariable("username") String username){
        return new ResponseEntity<Guest>(guestService.findUsername(username), HttpStatus.OK);
    }
    @GetMapping(value = "/{id}/requests", produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasRole('Guest')")
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
    @DeleteMapping(value = "/{id}")
    @PreAuthorize("hasRole('Guest')")
    public ResponseEntity<GuestDTO> deleteGuest(@PathVariable("id") Long id) {
        List<Reservation> acceptedReservations = reservationService.getGuestReservations(id).stream()
                .filter(reservation -> reservation.getStatus() == ReservationStatus.ACCEPTED)
                .toList();

        if(!acceptedReservations.isEmpty()){return  new ResponseEntity<>(HttpStatus.FORBIDDEN);}
        guestService.delete(id);
        return new ResponseEntity<GuestDTO>(HttpStatus.NO_CONTENT);
    }
    @DeleteMapping(value = "/request/{reqId}")
    @PreAuthorize("hasRole('Guest')")
    public ResponseEntity<ReservationDTO> deleteGuestReservation(@PathVariable("reqId") Long reqId){
        if(reservationService.findOne(reqId).getStatus() == ReservationStatus.ACCEPTED){
            return new ResponseEntity<ReservationDTO>(HttpStatus.NOT_FOUND);
        }else{
            if(!reservationService.delete(reqId)){
                return new ResponseEntity<ReservationDTO>(HttpStatus.FORBIDDEN);
            }
        }
        return new ResponseEntity<ReservationDTO>(HttpStatus.NO_CONTENT);
    }
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasRole('Guest')")
    public ResponseEntity<GuestDTO> createGuest(@RequestBody Guest guest) throws Exception {
        GuestDTO guestDTO = guestService.create(guest);
        return new ResponseEntity<GuestDTO>(guestDTO, HttpStatus.CREATED);
    }
    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasRole('Guest')")
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

    @GetMapping(value = "/{id}/allComments", produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasRole('Guest')")
    public ResponseEntity<Collection<ReviewDTO>> allComments(@PathVariable("id") Long id) {
        Collection<ReviewDTO> reviews = new ArrayList<ReviewDTO>();
        //Collection<ReviewDTO> reviews = reviewService.findAllCommentsByGuestsDTO(id);
        //return new ResponseEntity<Collection<ReviewDTO>>(reviews, HttpStatus.OK);
        return new ResponseEntity<Collection<ReviewDTO>>(reviews, HttpStatus.OK);
    }

    @PostMapping(value = "/{id}/comment",consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasRole('Guest')")
    public ResponseEntity<ReviewDTO> createComment(@PathVariable("id") Long idReservation,@RequestBody Review review) throws Exception {
        ReviewDTO reviewDTO = reviewService.createByReservation(idReservation, review);
        return new ResponseEntity<ReviewDTO>(reviewDTO, HttpStatus.CREATED);
    }
    @DeleteMapping(value = "/comment/{id}")
    @PreAuthorize("hasRole('Guest')")
    public ResponseEntity<ReviewDTO> deleteComm(@PathVariable("id") Long id) {
        reviewService.delete(id);
        return new ResponseEntity<ReviewDTO>(HttpStatus.NO_CONTENT);
    }

    @PutMapping(value = "{idGuest}/reportOwner/{idOwner}")
    @PreAuthorize("hasRole('Guest')")
    public ResponseEntity<?> reportOwner(@PathVariable("idGuest") Long idGuest,@PathVariable("idOwner") Long idOwner) {
        boolean canReport=guestService.reportOwner(idGuest,idOwner);
        if(canReport){
            Owner owner=ownerService.findOne(idOwner);
            owner.setReported(true);
            return new ResponseEntity<>("Guest can report", HttpStatus.ACCEPTED);
        }
        return new ResponseEntity<>("Guest cant report", HttpStatus.BAD_REQUEST);
    }
    @GetMapping(value = "/accommodationsSearch")
    @PreAuthorize("hasRole('Guest')")
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
    @PreAuthorize("hasRole('Guest')")
    public ResponseEntity<Collection<AccommodationDTO>> getAccommodationsDTO() {
        Collection<AccommodationDTO> accommodationDTOS = accommodationService.findAllDTO();
        return new ResponseEntity<Collection<AccommodationDTO>>(accommodationDTOS, HttpStatus.OK);
    }
    @PostMapping(value="/reservations")
    @PreAuthorize("hasRole('Guest')")
    public ResponseEntity<ReservationDTO> createReservation(@RequestBody Reservation reservation) throws Exception {
        if (reservationService.hasOverlappingRequests(reservation)) {
            return new ResponseEntity<>(null, HttpStatus.CONFLICT);
        }
        ReservationDTO reservationDTO = reservationService.create(reservation);
        return new ResponseEntity<>(reservationDTO, HttpStatus.CREATED);
    }

    @GetMapping(value = "/request")
    @PreAuthorize("hasRole('Guest')")
    public ResponseEntity<Collection<ReservationDTO>> guestNotAcceptedReservation(){
        Collection<ReservationDTO> reservations = reservationService.findAllNotAcceptedDTO();
        return new ResponseEntity<Collection<ReservationDTO>>(reservations, HttpStatus.OK);
    }

    @GetMapping(value = "/owner-reservation/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    //@PreAuthorize("hasRole('Guest')")
    public ResponseEntity<Collection<OwnerDTO>> getOwnerByReservationGuest(@PathVariable("id") Long id) {
        System.out.println("USLO");
        Collection<OwnerDTO>  owners = reservationService.findOwnerByReservationGuest(id);
        System.out.println("USLO1");
        return new ResponseEntity<Collection<OwnerDTO>>(owners,HttpStatus.OK);
    }


}
