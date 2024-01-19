package com.booking.ProjectISS.controller.reviews;

import com.booking.ProjectISS.dto.accomodations.AccommodationDTO;
import com.booking.ProjectISS.dto.reservations.ReservationDTO;
import com.booking.ProjectISS.dto.reviews.ReviewDTO;
import com.booking.ProjectISS.dto.reviews.ReviewOwnerDTO;
import com.booking.ProjectISS.dto.users.GuestDTO;
import com.booking.ProjectISS.dto.users.OwnerDTO;
import com.booking.ProjectISS.enums.ReviewStatus;
import com.booking.ProjectISS.model.accomodations.Accommodation;
import com.booking.ProjectISS.model.reservations.Reservation;
import com.booking.ProjectISS.model.reviews.Review;
import com.booking.ProjectISS.model.reviews.ReviewOwner;
import com.booking.ProjectISS.model.users.Guest;
import com.booking.ProjectISS.model.users.Owner;
import com.booking.ProjectISS.model.users.ReportUser;
import com.booking.ProjectISS.repository.reservations.IReservationRepository;
import com.booking.ProjectISS.repository.users.guests.IGuestRepository;
import com.booking.ProjectISS.repository.users.owner.IOwnerRepository;
import com.booking.ProjectISS.service.reservations.IReservationService;
import com.booking.ProjectISS.service.reviews.IReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.awt.*;
import java.util.*;

@RestController
@CrossOrigin
@RequestMapping("/api/reviews")
public class ReviewController {
    @Autowired
    private IReviewService reviewService;

    @Autowired
    private IOwnerRepository ownerRepository;

    @Autowired
    private IGuestRepository guestRepository;

    @Autowired
    private SimpMessagingTemplate simpMessagingTemplate;

    @Autowired
    private IReservationService reservationService;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
   // @PreAuthorize("hasAnyRole( 'Administrator','Owner', 'Guest')")
    public ResponseEntity<Collection<ReviewDTO>> getReviewDTO(){
        return new ResponseEntity<Collection<ReviewDTO>>(reviewService.findAllDTO(), HttpStatus.OK);
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    //@PreAuthorize("hasAnyRole( 'Administrator','Owner', 'Guest')")
    public ResponseEntity<ReviewDTO> getReview(@PathVariable("id") Long id) {
        ReviewDTO reviewDTO = reviewService.findOneDTO(id);
        if (reviewDTO != null) {
            return new ResponseEntity<>(reviewDTO, HttpStatus.OK);
        }

        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    //@PreAuthorize("hasAnyRole( 'Administrator','Owner', 'Guest')")
    public ResponseEntity<ReviewDTO> createReview(@RequestBody Review review) throws Exception {
        ReviewDTO reviewDTO = reviewService.create(review);
        return new ResponseEntity<>(reviewDTO, HttpStatus.CREATED);
    }

    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    //@PreAuthorize("hasAnyRole( 'Administrator','Owner', 'Guest')")
    public ResponseEntity<ReviewDTO> updateReview(@RequestBody Review review, @PathVariable Long id)
            throws Exception {
        Review updateReview = reviewService.findOne(id);
        if (updateReview == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        ReviewDTO updatedReview = reviewService.update(review);
        return new ResponseEntity<>(updatedReview, HttpStatus.OK);
    }

    @DeleteMapping(value = "/{id}")
    //@PreAuthorize("hasRole('Administrator')")
    public ResponseEntity<ReviewDTO> deleteReview(@PathVariable("id") Long id) {
        reviewService.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping(value = "/{idReservation}/reviews", produces = MediaType.APPLICATION_JSON_VALUE)
    //@PreAuthorize("hasAnyRole( 'Administrator','Owner', 'Guest')")
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
        System.out.println(accommodations);
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

    @GetMapping(value = "/reportGuest/{idOwner}", produces = MediaType.APPLICATION_JSON_VALUE)
    //@PreAuthorize("hasRole('Owner')")
    public ResponseEntity<Collection<Guest>> reportGuest(@PathVariable("idOwner") Long id) {
        Collection<Guest> guests= reviewService.findGuestByOwner(id);
        return new ResponseEntity<Collection<Guest>>(guests,HttpStatus.OK);
    }


    @DeleteMapping(value = "/rateDelete/{idOwner}/{idGuest}")
    //@PreAuthorize("hasRole('Guest')")
    public ResponseEntity<ReviewOwner> deleteReviewOwner(@PathVariable("idOwner") Long idOwner, @PathVariable("idGuest") Long idGuest) {
        System.out.println("USLO JE DELETE");
        ReviewOwner reviewOwner=reviewService.deleteByOwnerGuest(idOwner,idGuest);
        return new ResponseEntity<ReviewOwner>(reviewOwner,HttpStatus.NO_CONTENT);
    }

    @DeleteMapping(value = "/rateAccDelete/{idAccommodation}/{idGuest}")
    //@PreAuthorize("hasRole('Guest')")
    public ResponseEntity<Review> deleteReviewAccommodation(@PathVariable("idAccommodation") Long idAccommodation, @PathVariable("idGuest") Long idGuest) {
        System.out.println("USLO JE DELETE123");
        Review review=reviewService.deleteByAccommodationGuest(idAccommodation,idGuest);
        return new ResponseEntity<Review>(review,HttpStatus.NO_CONTENT);
    }

    @PostMapping(value = "/rate/{idOwner}/{idGuest}",consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    //@PreAuthorize("hasRole('Guest')")
    public ResponseEntity<ReviewOwnerDTO> createReview(@PathVariable("idOwner") Long idOwner,
                                                       @PathVariable("idGuest") Long idGuest,@RequestBody ReviewOwner review) throws Exception {
        Optional<Owner> o=ownerRepository.findById(idOwner);
        Optional<Guest> g=guestRepository.findById(idGuest);
        review.setOwner(o.get());
        review.setGuest(g.get());
        ReviewOwnerDTO reviewDTO = reviewService.createOwnerRewiew(review,idOwner,idGuest);
        if(o.get().isRateMeNotification()){
            System.out.println("UPALJENO");
        }
        return new ResponseEntity<>(reviewDTO, HttpStatus.CREATED);
    }

    @GetMapping(value = "/reportGuestComment/{idOwner}", produces = MediaType.APPLICATION_JSON_VALUE)
    //@PreAuthorize("hasRole('Owner')")
    public ResponseEntity<Collection<ReviewOwner>> getCommentsGuest(@PathVariable("idOwner") Long id) {
        Collection<ReviewOwner> reviewOwners= reviewService.findNoReported(id);
        return new ResponseEntity<Collection<ReviewOwner>>(reviewOwners,HttpStatus.OK);
    }

    @GetMapping(value = "/reportAccommodationComment/{idOwner}", produces = MediaType.APPLICATION_JSON_VALUE)
    //@PreAuthorize("hasRole('Owner')")
    public ResponseEntity<Collection<Review>> getCommentsAccommodation(@PathVariable("idOwner") Long id) {
        System.out.println("USLO1234");
        Collection<Review> reviews= reviewService.findNoReportedAcc(id);
        System.out.println("USLO12345");
        return new ResponseEntity<Collection<Review>>(reviews,HttpStatus.OK);
    }

    @GetMapping(value = "/get/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    //@PreAuthorize("hasRole('Owner')")
    public ResponseEntity<ReviewOwner> getReviewOwnerComm(@PathVariable("id") Long id) {
        System.out.println("USLO1234");
        ReviewOwner reviewOwner= reviewService.find(id);
        System.out.println("USLO12345");
        return new ResponseEntity<ReviewOwner>(reviewOwner,HttpStatus.OK);
    }

    @PutMapping(value = "/editReviewOwner/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    //@PreAuthorize("hasRole('Owner')")
    public ResponseEntity<ReviewOwner> putReviewOwnerComm(@PathVariable("id") Long id,@RequestBody ReviewOwner reviewOwner) {
        //ReviewOwner reviewOwner= reviewService.find(id);
        System.out.println(reviewOwner);
        ReviewOwner updatedReviewOwner=reviewService.updateReviewOwner(reviewOwner);
        System.out.println("USLO12345");
        System.out.println(updatedReviewOwner);
        return new ResponseEntity<ReviewOwner>(updatedReviewOwner,HttpStatus.OK);
    }

    @GetMapping(value = "/getAccommodation/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    //@PreAuthorize("hasRole('Owner')")
    public ResponseEntity<Review> getReviewAccommodationComm(@PathVariable("id") Long id) {
        System.out.println("USLO GET ACC");
        Review review= reviewService.findOne(id);
        System.out.println("USLO12345");
        System.out.println(review);
        return new ResponseEntity<Review>(review,HttpStatus.OK);
    }

//    @PutMapping(value = "/editReviewAccommodation", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
//    //@PreAuthorize("hasRole('Owner')")
//    public ResponseEntity<Review> putReviewAccommodationComm(@RequestBody Review review) {
//        System.out.println(review);
//        Review updatedReview=reviewService.updateReview(review);
//        System.out.println("USLO123456");
//        System.out.println(updatedReview);
//        return new ResponseEntity<Review>(updatedReview,HttpStatus.OK);
//    }

    @GetMapping(value = "/editReviewAccommodation/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    //@PreAuthorize("hasRole('Owner')")
    public ResponseEntity<Review> putReviewAccommodationComm(@PathVariable("id") Long id) {
        System.out.println("UDJE EDIT ACC");
        Review review=reviewService.findById(id);
        Review updatedReview=reviewService.updateReview(review);
        System.out.println("USLO123456");
        System.out.println(updatedReview);
        return new ResponseEntity<Review>(updatedReview,HttpStatus.OK);
    }

    @GetMapping(value = "/rateAccommodation/{idAccommodation}/{idGuest}", produces = MediaType.APPLICATION_JSON_VALUE)
    //@PreAuthorize("hasRole('Guest')")
    public ResponseEntity<Review> rateAccommodation(@PathVariable("idAccommodation") Long idAccommodation, @PathVariable("idGuest") Long idGuest) {
        System.out.println("GET ACC RATE");
        System.out.println(idAccommodation);
        System.out.println(idGuest);
        Review  review = reviewService.findReviewByOwnerGuestAccommodation(idAccommodation,idGuest);
        if (review == null) {
            return null;
        }
        return new ResponseEntity<Review>(review,HttpStatus.OK);
    }
    @PostMapping(value = "/addAccRate/{idReservation}",consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    //@PreAuthorize("hasRole('Guest')")
    public ResponseEntity<Review> createReviewAccommodation(@PathVariable("idReservation") Long idRes,@RequestBody Review review) throws Exception {
        System.out.println("USLO ADD RATE ACC");
        //ReviewOwnerDTO reviewDTO = reviewService.createOwnerRewiew(review,1L,3L);
        System.out.println(idRes);
        ReviewDTO reviewDTO = reviewService.createrRewiew(review,idRes,3L);
        return new ResponseEntity<Review>(HttpStatus.CREATED);
    }

    @RequestMapping(value="/sendMessageRest", method = RequestMethod.POST)
    public ResponseEntity<?> sendMessage(@RequestBody Map<String, String> message) {
        System.out.println("POGODI");
        if (message.containsKey("message")) {
            if (message.containsKey("toId") && message.get("toId") != null && !message.get("toId").equals("")) {
                this.simpMessagingTemplate.convertAndSend("/socket-publisher/" + message.get("toId"), message);
                this.simpMessagingTemplate.convertAndSend("/socket-publisher/" + message.get("fromId"), message);
            } else {
                this.simpMessagingTemplate.convertAndSend("/socket-publisher", message);
            }
            return new ResponseEntity<>(message, new HttpHeaders(), HttpStatus.OK);
        }

        return new ResponseEntity<>(new HttpHeaders(), HttpStatus.BAD_REQUEST);
    }

    //dodao matija
    @GetMapping(value = "/rateFull/{idOwner}/{idGuest}", produces = MediaType.APPLICATION_JSON_VALUE)
    //@PreAuthorize("hasRole('Guest')")
    public ResponseEntity<ReviewOwner> rateOwnerFull(@PathVariable("idOwner") Long idOwner, @PathVariable("idGuest") Long idGuest) {
        ReviewOwner  reviewOwner = reviewService.findReviewByOwnerGuest(idOwner,idGuest);
        if (reviewOwner == null) {
            return null;
        }
        return new ResponseEntity<ReviewOwner>(reviewOwner,HttpStatus.OK);
    }
    //dodao matija
    @GetMapping(value = "/byReservationId/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    //@PreAuthorize("hasRole('Owner')")
    public ResponseEntity<Review> getReviewByReservationId(@PathVariable("id") Long idReservation) {
        System.out.println("POGODI");
        Review review= reviewService.findReviewByAccommodationId(idReservation);
        System.out.println(review);
        return new ResponseEntity<Review>(review,HttpStatus.OK);
    }

    @GetMapping(value = "/byReservationIdSingle/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    //@PreAuthorize("hasRole('Owner')")
    public ResponseEntity<Review> getReviewByReservationIdSingle(@PathVariable("id") Long idReservation) {
        System.out.println("POGODI SINGLE");
        Review review= reviewService.findReviewByAccommodationIdSingle(idReservation);
        System.out.println(review);
        return new ResponseEntity<Review>(review,HttpStatus.OK);
    }


    @GetMapping(value = "/reviewOwners" ,produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Collection<ReviewOwner>> getReviewOwner(){

        return new ResponseEntity<Collection<ReviewOwner>>(reviewService.getReviewOwners(), HttpStatus.OK);
    }

    @PutMapping(value = "/reviewOwners/{id}" ,produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ReviewOwner> updateReviewOwner(@PathVariable("id") Long id, @RequestBody ReviewOwner updatedReviewOwner){
        ReviewOwner reviewOwner = reviewService.findReviewOwner(id);
        if(reviewOwner == null){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        ReviewOwner newReviewOwner = reviewService.updateReviewOwnerByAdmin(updatedReviewOwner);
        return new ResponseEntity<ReviewOwner>(newReviewOwner, HttpStatus.OK);
    @PostMapping(value = "/mobile",consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    //@PreAuthorize("hasAnyRole( 'Administrator','Owner', 'Guest')")
    public ResponseEntity<ReviewDTO> createReviewMobile(@RequestParam(required = false) double rate,
                                                        @RequestParam(required = false) String text,
                                                        @RequestParam(required = false) Long reservationId
                                                    ) throws Exception {

        Reservation reservation = reservationService.findOne(reservationId);
        Review review = new Review(100L,rate,text,ReviewStatus.PENDING,reservation);
        ReviewDTO reviewDTO = reviewService.create(review);
        return new ResponseEntity<>(reviewDTO, HttpStatus.CREATED);
    }

    @GetMapping(value = "/reportCommentAccommodationByOwner/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    //@PreAuthorize("hasRole('Owner')")
    public ResponseEntity<Review> getReportComment(@PathVariable("id") Long idReview) {
        System.out.println("POGODI121");
        Review review= reviewService.setReportToReview(idReview);
        System.out.println(review);
        return new ResponseEntity<Review>(review,HttpStatus.OK);
    }

}
