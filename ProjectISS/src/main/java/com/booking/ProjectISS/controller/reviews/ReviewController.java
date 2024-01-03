package com.booking.ProjectISS.controller.reviews;

import com.booking.ProjectISS.dto.reservations.ReservationDTO;
import com.booking.ProjectISS.dto.reviews.ReviewDTO;
import com.booking.ProjectISS.dto.reviews.ReviewOwnerDTO;
import com.booking.ProjectISS.dto.users.OwnerDTO;
import com.booking.ProjectISS.model.accomodations.Accommodation;
import com.booking.ProjectISS.model.reviews.Review;
import com.booking.ProjectISS.model.reviews.ReviewOwner;
import com.booking.ProjectISS.model.users.Owner;
import com.booking.ProjectISS.service.reviews.IReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.awt.*;
import java.util.ArrayList;
import java.util.Collection;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/api/reviews")
public class ReviewController {
    @Autowired
    private IReviewService reviewService;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasAnyRole( 'Administrator','Owner', 'Guest')")
    public ResponseEntity<Collection<ReviewDTO>> getReviewDTO(){
        return new ResponseEntity<Collection<ReviewDTO>>(reviewService.findAllDTO(), HttpStatus.OK);
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasAnyRole( 'Administrator','Owner', 'Guest')")
    public ResponseEntity<ReviewDTO> getReview(@PathVariable("id") Long id) {
        ReviewDTO reviewDTO = reviewService.findOneDTO(id);
        if (reviewDTO != null) {
            return new ResponseEntity<>(reviewDTO, HttpStatus.OK);
        }

        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasAnyRole( 'Administrator','Owner', 'Guest')")
    public ResponseEntity<ReviewDTO> createReview(@RequestBody Review review) throws Exception {
        ReviewDTO reviewDTO = reviewService.create(review);
        return new ResponseEntity<>(reviewDTO, HttpStatus.CREATED);
    }

    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasAnyRole( 'Administrator','Owner', 'Guest')")
    public ResponseEntity<ReviewDTO> updateReview(@RequestBody Review review, @PathVariable Long id)
            throws Exception {
        Review updateReview = reviewService.findOne(id);
        if (updateReview == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        updateReview.copyValues(review);
        ReviewDTO updatedReview = reviewService.update(updateReview);
        return new ResponseEntity<>(updatedReview, HttpStatus.OK);
    }

    @DeleteMapping(value = "/{id}")
    @PreAuthorize("hasRole('Administrator')")
    public ResponseEntity<ReviewDTO> deleteReview(@PathVariable("id") Long id) {
        reviewService.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping(value = "/{idReservation}/reviews", produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasAnyRole( 'Administrator','Owner', 'Guest')")
    public ResponseEntity<ReviewDTO> getByReservations(@PathVariable("idReservation") Long id) {
        ReviewDTO reviewDTO = reviewService.findByReservation(id);
        if (reviewDTO != null) {
            return new ResponseEntity<>(reviewDTO, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping(value = "/rate/{idOwner}/{idGuest}", produces = MediaType.APPLICATION_JSON_VALUE)
    //@PreAuthorize("hasRole('Guest')")
    public ResponseEntity<ReviewOwnerDTO> rateOwner(@PathVariable("idOwner") Long idOwner, @PathVariable("idGuest") Long idGuest) {
        ReviewOwner  reviewOwner = reviewService.findReviewByOwnerGuest(idOwner,idGuest);
        if (reviewOwner == null) {
            return null;
        }
        return new ResponseEntity<ReviewOwnerDTO>(new ReviewOwnerDTO(reviewOwner),HttpStatus.OK);
    }

    @GetMapping(value = "/rate/reviewAccommodation/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    //@PreAuthorize("hasRole('Guest')")
    public ResponseEntity<Collection<Accommodation>> getReviewAccommodation(@PathVariable("id") Long id) {
        Collection<Accommodation> accommodations = reviewService.findReviewAccommodation(id);
        return new ResponseEntity<Collection<Accommodation>>(accommodations,HttpStatus.OK);
    }

    @GetMapping(value = "/rate/reviewOwner/{idReview}", produces = MediaType.APPLICATION_JSON_VALUE)
    //@PreAuthorize("hasRole('Guest')")
    public ResponseEntity<ReviewOwner> getReviewOwner(@PathVariable("idReview") Long id) {
        ReviewOwner reviewOwner = reviewService.find(id);
        if(reviewOwner==null){
            return null;
        }
        return new ResponseEntity<ReviewOwner>(reviewOwner,HttpStatus.OK);
    }

    @GetMapping(value = "/rate/{idGuest}", produces = MediaType.APPLICATION_JSON_VALUE)
    //@PreAuthorize("hasRole('Guest')")
    public ResponseEntity<Collection<Owner>> rateOwner(@PathVariable("idGuest") Long idGuest) {
        Collection<Owner> owners= reviewService.findReviewByGuestOwner(idGuest);
        return new ResponseEntity<Collection<Owner>>(owners,HttpStatus.OK);
    }


    @DeleteMapping(value = "/rate/{idOwner}/{idGuest}", produces = MediaType.APPLICATION_JSON_VALUE)
    //@PreAuthorize("hasRole('Guest')")
    public ResponseEntity<ReviewOwner> deleteReviewOwner(@PathVariable("idOwner") Long idOwner, @PathVariable("idGuest") Long idGuest) {
        System.out.println("USLO JE DELETE");
        ReviewOwner reviewOwner=reviewService.deleteByOwnerGuest(idOwner,idGuest);
        return new ResponseEntity<ReviewOwner>(reviewOwner,HttpStatus.NO_CONTENT);
    }

    @PostMapping(value = "/rate/{idOwner}/{idGuest}",consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    //@PreAuthorize("hasRole('Guest')")
    public ResponseEntity<ReviewOwnerDTO> createReview(@RequestBody ReviewOwner review) throws Exception {
        System.out.println("UDJE");
        System.out.println(review);
        ReviewOwnerDTO reviewDTO = reviewService.createOwnerRewiew(review);
        return new ResponseEntity<>(reviewDTO, HttpStatus.CREATED);
    }

}
