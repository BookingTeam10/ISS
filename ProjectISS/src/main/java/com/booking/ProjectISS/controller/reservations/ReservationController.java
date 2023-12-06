package com.booking.ProjectISS.controller.reservations;

import com.booking.ProjectISS.dto.reservations.ReservationDTO;
import com.booking.ProjectISS.enums.ReservationStatus;
import com.booking.ProjectISS.model.accomodations.Accommodation;
import com.booking.ProjectISS.model.reservations.Reservation;
import com.booking.ProjectISS.service.reservations.IReservationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.print.attribute.standard.Media;
import java.awt.*;
import java.util.Collection;

@RestController
@RequestMapping("/api/reservations")
public class ReservationController {
    @Autowired
    private IReservationService reservationService;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @CrossOrigin(origins = "http://localhost:4200")
    public ResponseEntity<Collection<ReservationDTO>> getReservationDTO(){
        return new ResponseEntity<Collection<ReservationDTO>>(reservationService.findAllDTO(), HttpStatus.OK);
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @CrossOrigin(origins = "http://localhost:4200")
    public ResponseEntity<ReservationDTO> getReservation(@PathVariable("id") Long id) {
        ReservationDTO reservationDTO = reservationService.findOneDTO(id);
        if (reservationDTO != null) {
            return new ResponseEntity<>(reservationDTO, HttpStatus.OK);
        }

        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @CrossOrigin(origins = "http://localhost:4200")
    public ResponseEntity<ReservationDTO> createReservation(@RequestBody Reservation reservation) throws Exception {

        if (reservationService.hasOverlappingRequests(reservation)) {
            return new ResponseEntity<>(null, HttpStatus.CONFLICT);
        }

        ReservationDTO reservationDTO = reservationService.create(reservation);
        return new ResponseEntity<>(reservationDTO, HttpStatus.CREATED);
    }

    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @CrossOrigin(origins = "http://localhost:4200")
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
    @CrossOrigin(origins = "http://localhost:4200")
    public ResponseEntity<ReservationDTO> acceptReservation(@PathVariable Long id) throws Exception {

        Reservation reservation = reservationService.findOne(id);
        reservation.setStatus(ReservationStatus.ACCEPTED);
        return new ResponseEntity<>(reservationService.update(reservation), HttpStatus.OK);
    }
    @PutMapping(value = "/cancel/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @CrossOrigin(origins = "http://localhost:4200")
    public ResponseEntity<ReservationDTO> cancelReservation(@PathVariable Long id,
    @RequestParam(value= "canceledByHost", required=false, defaultValue = "false") boolean canceledByHost)
            throws Exception {

        Reservation reservation = reservationService.findOne(id);
        if(canceledByHost){
            reservation.setStatus(ReservationStatus.REJECTED);
        }else {
            reservation.setStatus(ReservationStatus.CANCELLED);
        }
        return new ResponseEntity<>(reservationService.update(reservation), HttpStatus.OK);
    }


    @DeleteMapping(value = "/{id}")
    @CrossOrigin(origins = "http://localhost:4200")
    public ResponseEntity<ReservationDTO> deleteReservation(@PathVariable("id") Long id) {
        reservationService.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
