package com.booking.ProjectISS.service.reviews;

import com.booking.ProjectISS.dto.reviews.ReviewDTO;
import com.booking.ProjectISS.dto.reviews.ReviewDTOComment;
import com.booking.ProjectISS.dto.reviews.ReviewOwnerDTO;
import com.booking.ProjectISS.model.accomodations.Accommodation;
import com.booking.ProjectISS.model.reviews.Review;
import com.booking.ProjectISS.model.reviews.ReviewOwner;
import com.booking.ProjectISS.model.users.Guest;
import com.booking.ProjectISS.model.users.Owner;

import java.util.ArrayList;
import java.util.Collection;

public interface IReviewService {
    ReviewDTO findOneDTO(Long id);
    Review findOne(Long id);
    Collection<ReviewDTO> findAllDTO();
    Collection<Review> findAll();
    void delete(Long id);
    ReviewDTO create(Review review) throws Exception;
    ReviewDTO update(Review review) throws Exception;
    ReviewDTO update(ReviewDTO review) throws Exception;
    ReviewDTO createByReservation(Long idReservation, Review review);
    Collection<ReviewDTOComment> findAllDTOComments();
    void deleteReport(Long id);
    ReviewDTO findByReservation(Long reservationId);

    ReviewOwner findReviewByOwnerGuest(Long idOwner, Long idGuest);

    ReviewOwner deleteByOwnerGuest(Long idOwner, Long idGuest);

    ReviewOwnerDTO createOwnerRewiew(ReviewOwner review,Long idOwner,Long idGuest);

    Collection<Owner> findReviewByGuestOwner(Long idGuest);

    ReviewOwner find(Long id);

    Collection<Accommodation> findReviewAccommodation(Long id);

    Collection<Guest> findGuestByOwner(Long id);

    Collection<ReviewOwner> findNoReported(Long id);

    Collection<Review> findNoReportedAcc(Long id);

    ReviewOwner updateReviewOwner(ReviewOwner reviewOwner);

    Review updateReview(Review review);

    Review findReviewByOwnerGuestAccommodation(Long idAccommodation, Long idGuest);

    Review deleteByAccommodationGuest(Long idAccommodation, Long idGuest);

    Collection<ReviewOwner> findAllReviewOwner();
    Review findReviewByAccommodationId(Long idReservation);

    Review findReviewByAccommodationIdSingle(Long idReservation);
}