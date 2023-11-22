package com.booking.ProjectISS.controller.reservations;

import com.booking.ProjectISS.dto.reservations.ReservationDTO;
import com.booking.ProjectISS.model.reservations.Reservation;
import com.booking.ProjectISS.service.reservations.IReservationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.awt.*;
import java.util.Collection;

@RestController
@RequestMapping("/api/reservations")
public class ReservationController {
    @Autowired
    private IReservationService reservationService;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Collection<ReservationDTO>> getReservationDTO(){
        return new ResponseEntity<Collection<ReservationDTO>>(reservationService.findAllDTO(), HttpStatus.OK);
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ReservationDTO> getReservation(@PathVariable("id") Long id) {
        ReservationDTO reservationDTO = reservationService.findOneDTO(id);
        if (reservationDTO != null) {
            return new ResponseEntity<>(reservationDTO, HttpStatus.OK);
        }

        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ReservationDTO> createReservation(@RequestBody Reservation reservation) throws Exception {
        ReservationDTO reservationDTO = reservationService.create(reservation);
        return new ResponseEntity<>(reservationDTO, HttpStatus.CREATED);
    }

    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
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

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<ReservationDTO> deleteReservation(@PathVariable("id") Long id) {
        reservationService.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
