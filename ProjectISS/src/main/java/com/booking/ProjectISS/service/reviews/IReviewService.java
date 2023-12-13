package com.booking.ProjectISS.service.reviews;

import com.booking.ProjectISS.dto.reviews.ReviewDTO;
import com.booking.ProjectISS.dto.reviews.ReviewDTOComment;
import com.booking.ProjectISS.model.reviews.Review;

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
}
