package com.booking.ProjectISS.repository.reviews;

import com.booking.ProjectISS.model.reviews.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Collection;

public interface IReviewRepository extends JpaRepository<Review, Long> {

    @Query("select r from Review  r where r.reservation.id = ?1")
    Review findByReservation(Long reservationId);
}
