package com.booking.ProjectISS.repository.reviews;

import com.booking.ProjectISS.enums.ReservationStatus;
import com.booking.ProjectISS.model.reservations.Reservation;
import com.booking.ProjectISS.model.reviews.Review;
import com.booking.ProjectISS.model.reviews.ReviewOwner;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Collection;

public interface IReviewOwnerRepository extends JpaRepository<ReviewOwner, Long>  {

    @Query("select r from ReviewOwner r where r.owner.id=?1 and r.guest.id=?2")
    ReviewOwner findByOwnerGuest(Long idOwner, Long idGuest);

    @Query("select r from Reservation r where r.guest.id=?1 and r.status <> ?2")
    Collection<Reservation> findReservationByGuest(Long idGuest, ReservationStatus reservationStatus);
}
