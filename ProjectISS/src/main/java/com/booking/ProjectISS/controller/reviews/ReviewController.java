package com.booking.ProjectISS.controller.reviews;

import com.booking.ProjectISS.dto.reservations.ReservationDTO;
import com.booking.ProjectISS.dto.reviews.ReviewDTO;
import com.booking.ProjectISS.model.reviews.Review;
import com.booking.ProjectISS.service.reviews.IReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.awt.*;
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
}
