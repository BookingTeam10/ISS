package com.booking.ProjectISS.controller.reservations;

import com.booking.ProjectISS.dto.reservations.ReservationDTO;
import com.booking.ProjectISS.enums.ReservationStatus;
import com.booking.ProjectISS.model.reservations.Reservation;
import com.booking.ProjectISS.model.users.Guest;
import com.booking.ProjectISS.service.notifications.INotificationService;
import com.booking.ProjectISS.service.reservations.IReservationService;
import com.booking.ProjectISS.service.users.guest.IGuestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
@CrossOrigin
@RestController
@RequestMapping("/api/reservations")
public class ReservationController {
    @Autowired
    private IReservationService reservationService;
    @Autowired
    private INotificationService notificationService;
    @Autowired
    private IGuestService guestService;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    //@PreAuthorize("hasAnyRole( 'Administrator','Owner', 'Guest')")
    public ResponseEntity<Collection<ReservationDTO>> getReservationDTO(){
        System.out.println(reservationService.findAll());
        return new ResponseEntity<Collection<ReservationDTO>>(reservationService.findAllDTO(), HttpStatus.OK);
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    //@PreAuthorize("hasAnyRole( 'Administrator','Owner', 'Guest')")
    public ResponseEntity<ReservationDTO> getReservation(@PathVariable("id") Long id) {
        ReservationDTO reservationDTO = reservationService.findOneDTO(id);
        if (reservationDTO != null) {
            return new ResponseEntity<>(reservationDTO, HttpStatus.OK);
        }

        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping(value = "/owner/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    //@PreAuthorize("hasRole('Owner')")
    public ResponseEntity<Collection<Reservation>> getAllOwnerReservations(@PathVariable("id") Long id){
        Collection<Reservation> allReservations = reservationService.getOwnerReservations(id);
        Collection<Reservation> reservations = allReservations.stream().filter( r -> r.getStatus().equals(ReservationStatus.WAITING)).toList();

        return new ResponseEntity<Collection<Reservation>>(reservations, HttpStatus.OK);
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    //@PreAuthorize("hasRole('Guest')")
    public ResponseEntity<ReservationDTO> createReservation(@RequestBody Reservation reservation) throws Exception {
        if (reservationService.hasOverlappingRequests(reservation)) {
            return new ResponseEntity<>(null, HttpStatus.CONFLICT);
        }

        ReservationDTO reservationDTO = reservationService.create(reservation);
        return new ResponseEntity<>(reservationDTO, HttpStatus.CREATED);
    }



    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    //@PreAuthorize("hasRole( 'Guest')")
    public ResponseEntity<ReservationDTO> updateReservation(@RequestBody Reservation reservation, @PathVariable Long id)
            throws Exception {
        Reservation updateReservation = reservationService.findOne(id);
        if (updateReservation == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        updateReservation.copyValues(reservation);
        ReservationDTO updatedReservation = reservationService.update(updateReservation);
        return new ResponseEntity<>(updatedReservation, HttpStatus.OK);
    }

    @PutMapping(value = "/accept/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    //@PreAuthorize("hasRole('Owner')")
    public ResponseEntity<ReservationDTO> acceptReservation(@PathVariable Long id) throws Exception {

        Reservation reservation = reservationService.findOne(id);
        reservation.setStatus(ReservationStatus.ACCEPTED);
        ReservationDTO reservationDTO = reservationService.update(reservation);
        reservationService.cancelAllWaiting(reservation);
        return new ResponseEntity<>(reservationDTO, HttpStatus.OK);
    }
    @PutMapping(value = "/cancel/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
   // @PreAuthorize("hasAnyRole('Owner', 'Guest')")
    public ResponseEntity<ReservationDTO> cancelReservation(@PathVariable Long id,
                                                            @RequestParam(value= "canceledByHost", required=false, defaultValue = "false") boolean canceledByHost)
            throws Exception {

        Reservation reservation = reservationService.findOne(id);
        if(canceledByHost){
            reservation.setStatus(ReservationStatus.REJECTED);
        }else {
            reservation.setStatus(ReservationStatus.CANCELLED);
            Guest guest = reservation.getGuest();
            int numberCanceled = guest.getNumberCanceledReservation();
            guest.setNumberCanceledReservation(numberCanceled+1);
            guestService.update(guest);
        }
        return new ResponseEntity<>(reservationService.update(reservation), HttpStatus.OK);
    }


    @DeleteMapping(value = "/{id}")
   // @PreAuthorize("hasAnyRole('Owner', 'Guest')")
    public ResponseEntity<ReservationDTO> deleteReservation(@PathVariable("id") Long id) {
        reservationService.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping(value = "/{idAccommodation}/reservations")
    //@PreAuthorize("hasAnyRole('Owner', 'Guest','Administrator')")
    public ResponseEntity<Collection<ReservationDTO>> getByAccommodations(@PathVariable("idAccommodation") Long id) {
        Collection<ReservationDTO> reservationDTOS = reservationService.findByAccommodation(id);
        if (reservationDTOS != null) {
            return new ResponseEntity<Collection<ReservationDTO>>(reservationDTOS, HttpStatus.OK);
        }

        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
    @GetMapping(value = "/guests/{id}",produces = MediaType.APPLICATION_JSON_VALUE)
    //@PreAuthorize("hasAnyRole( 'Administrator','Owner', 'Guest')
    public ResponseEntity<Collection<ReservationDTO>> getReservationDTOByUser(){
        System.out.println("POGODI");
        return new ResponseEntity<Collection<ReservationDTO>>(reservationService.findAllDTO(), HttpStatus.OK);
    }
    @GetMapping(value = "/byGuest/{id}",produces = MediaType.APPLICATION_JSON_VALUE)
    //@PreAuthorize("hasAnyRole( 'Administrator','Owner', 'Guest')
    public ResponseEntity<Collection<ReservationDTO>> getReservationDTOByUserGuest(@PathVariable("id") Long id){
        Collection<ReservationDTO> reservationDTOS=reservationService.findByGuest(id);
        System.out.println("BY GOST");
        return new ResponseEntity<Collection<ReservationDTO>>(reservationDTOS, HttpStatus.OK);
    }

    @GetMapping(value = "/byOwner/{id}",produces = MediaType.APPLICATION_JSON_VALUE)
    //@PreAuthorize("hasAnyRole( 'Administrator','Owner', 'Guest')
    public ResponseEntity<Collection<ReservationDTO>> getReservationDTOByUserOwner(@PathVariable("id") Long id){
        Collection<ReservationDTO> reservationDTOS=reservationService.findByOwner(id);
        System.out.println("BY GOST");
        return new ResponseEntity<Collection<ReservationDTO>>(reservationDTOS, HttpStatus.OK);
    }

}
