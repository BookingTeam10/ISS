package com.booking.ProjectISS.service.reviews;

import com.booking.ProjectISS.dto.reservations.ReservationDTO;
import com.booking.ProjectISS.dto.reviews.ReviewDTO;
import com.booking.ProjectISS.dto.reviews.ReviewDTOComment;
import com.booking.ProjectISS.dto.reviews.ReviewOwnerDTO;
import com.booking.ProjectISS.enums.ReservationStatus;
import com.booking.ProjectISS.enums.ReviewStatus;
import com.booking.ProjectISS.model.accomodations.Accommodation;
import com.booking.ProjectISS.model.reservations.Reservation;
import com.booking.ProjectISS.model.reviews.Review;
import com.booking.ProjectISS.model.reviews.ReviewOwner;
import com.booking.ProjectISS.model.users.Guest;
import com.booking.ProjectISS.model.users.Owner;
import com.booking.ProjectISS.repository.accomodations.IAccommodationRepository;
import com.booking.ProjectISS.repository.reservations.IReservationRepository;
import com.booking.ProjectISS.repository.reviews.IReviewOwnerRepository;
import com.booking.ProjectISS.repository.reviews.IReviewRepository;
import com.booking.ProjectISS.repository.users.guests.IGuestRepository;
import com.booking.ProjectISS.repository.users.owner.IOwnerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class ReviewService implements IReviewService {

    @Autowired
    private IReviewRepository reviewRepository;

    @Autowired
    private IReviewOwnerRepository reviewOwnerRepository;

    @Autowired
    private IOwnerRepository ownerRepository;

    @Autowired
    private IGuestRepository guestRepository;

    @Autowired
    private IAccommodationRepository accommodationRepository;

    @Autowired
    private IReservationRepository reservationRepository;

    @Override
    public ReviewDTO findOneDTO(Long id) {
        Optional<Review> found = reviewRepository.findById(id);
        return found.map(ReviewDTO::new).orElse(null);

    }

    @Override
    public Review findOne(Long id) {
        Optional<Review> found = reviewRepository.findById(id);
        return found.orElse(null);
    }

    @Override
    public Collection<ReviewDTO> findAllDTO() {
        Collection<Review> reviews = reviewRepository.findAll();
        Collection<ReviewDTO> reviewDTOs = new ArrayList<>();

        for (Review r : reviews) {
            reviewDTOs.add(new ReviewDTO(r));
        }

        return reviewDTOs;
    }

    @Override
    public Collection<Review> findAll() {
        return reviewRepository.findAll();
    }

    @Override
    public void delete(Long id) {
        Optional<Review> found = reviewRepository.findById(id);
        if(found.isEmpty()){ return;}
        reviewRepository.delete(found.get());
        reviewRepository.flush();
    }

    @Override
    public ReviewDTO create(Review review) throws Exception {
        Review savedReview = reviewRepository.save(review);
        return new ReviewDTO(savedReview);
    }

    @Override
    public ReviewDTO update(Review review) throws Exception {
        // Implement your update logic if needed
        return null;
    }

    @Override
    public ReviewDTO update(ReviewDTO review) throws Exception {
        // Implement your update logic if needed
        return null;
    }
    @Override
    public ReviewDTO createByReservation(Long idReservation, Review review) {

        return null;
    }

    @Override
    public Collection<ReviewDTOComment> findAllDTOComments() {
        Collection<Review> reviews = reviewRepository.findAll();
        Collection<ReviewDTOComment> reviewDTOs = new ArrayList<>();
        for (Review r : reviews) {
            reviewDTOs.add(new ReviewDTOComment(r));
        }
        return reviewDTOs;
    }

    @Override
    public void deleteReport(Long id) {
//        Review review=reviewRepository.findOne(id);
//        if(review.getStatus()== ReviewStatus.REPORTED){
//            reviewRepository.delete(id);
//        }
    }
    @Override
    public ReviewDTO findByReservation(Long reservationId) {
        if (reviewRepository.findByReservation(reservationId)!=null)
            return new ReviewDTO(reviewRepository.findByReservation(reservationId));
        return null;
    }

    @Override
    public ReviewOwner findReviewByOwnerGuest(Long idOwner, Long idGuest) {
        ReviewOwner reviewOwner=reviewOwnerRepository.findByOwnerGuest(idOwner,idGuest);
        System.out.println(reviewOwner);
        return reviewOwner;
    }

    @Override
    public ReviewOwner deleteByOwnerGuest(Long idOwner, Long idGuest) {
        //reviewRepository.deleteByOwnerGuest(idOwner,idGuest);
        ReviewOwner reviewOwner=reviewRepository.findByOwnerGuest(idOwner,idGuest);
        System.out.println(reviewOwner.getId());
        reviewRepository.deleteById(reviewOwner.getId());
        return reviewOwner;
    }

    @Override
    public ReviewOwnerDTO createOwnerRewiew(ReviewOwner review, Long idOwner,Long idGuest) {
        Optional<Owner> o=ownerRepository.findById(idOwner);
        Optional<Guest> g=guestRepository.findById(idGuest);
        review.setOwner(o.get());
        review.setGuest(g.get());
        review.setStatus(ReviewStatus.ACTIVE);
        ReviewOwner savedReview = reviewOwnerRepository.save(review);
        return new ReviewOwnerDTO(savedReview);
    }

    @Override
    public Collection<Owner> findReviewByGuestOwner(Long idGuest) {
        Collection<Owner> owners=new HashSet<Owner>();
        Collection<Reservation> reservations=reviewOwnerRepository.findReservationByGuest(idGuest,ReservationStatus.CANCELLED);
        for(Reservation r:reservations){
            owners.add(r.getAccommodation().getOwner());
        }
        System.out.println(owners);
        return owners;
    }

    @Override
    public ReviewOwner find(Long id) {
        Optional<ReviewOwner> found = reviewOwnerRepository.findById(id);
        return found.orElse(null);
    }

    @Override
    public Collection<Accommodation> findReviewAccommodation(Long id) {
        Collection<Accommodation> accommodations=new HashSet<Accommodation>();
        Collection<Reservation> reservations=reviewRepository.findByGuest(id);
        for(Reservation r:reservations){
            accommodations.add(r.getAccommodation());   //dodati 7dana
        }
        return accommodations;
    }

    @Override
    public Collection<Guest> findGuestByOwner(Long id) {
        Collection<Guest> guests=new HashSet<Guest>();
        Collection<Accommodation> accommodations=accommodationRepository.findAllByOwner(id);
        System.out.println(accommodations);
        System.out.println(accommodations.size());
        for(Accommodation a:accommodations){
            Collection<Reservation> res=reservationRepository.findByAccommodation(a.getId());
            for(Reservation r:res){
                guests.add(r.getGuest());
            }
        }
        System.out.println(guests);
        return guests;
    }

    @Override
    public Collection<ReviewOwner> findNoReported(Long id) {
        Collection<ReviewOwner> ro=new ArrayList<ReviewOwner>();
        Collection<ReviewOwner> reviewNoReported=reviewOwnerRepository.findNoRep(id);
        for(ReviewOwner r:reviewNoReported){
            if(!r.getStatus().equals(ReviewStatus.REPORTED)){   //dodati mzd i ono iz tabele true/false
                ro.add(r);
            }
        }
        System.out.println(ro);
        System.out.println(ro.size());
        return ro;
    }

    @Override
    public Collection<Review> findNoReportedAcc(Long id) {
        Collection<Review> reviews=new ArrayList<Review>();
        Collection<Reservation> reservations=new ArrayList<Reservation>();
        Collection<Accommodation> accommodations=accommodationRepository.findAllByOwner(id);
        System.out.println(accommodations);
        for(Accommodation a:accommodations){
            Collection<Reservation> res=reservationRepository.findByAccommodation(a.getId());
            reservations.addAll(res);
        }
        System.out.println(reservations);
        for(Reservation r:reservations){
            Review rw=reviewRepository.findByReservation(r.getId());
            System.out.println(rw);
            if(rw==null){
                break;
            }
            if(!rw.getStatus().equals(ReviewStatus.REPORTED)){
                reviews.add(rw);
            }
        }
        System.out.println(reviews);
        System.out.println(reviews.size());
        return reviews;
    }
}
