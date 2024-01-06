package com.booking.ProjectISS.repository.reviews;

import com.booking.ProjectISS.model.reservations.Reservation;
import com.booking.ProjectISS.model.reviews.Review;
import com.booking.ProjectISS.model.reviews.ReviewOwner;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.Collection;

public interface IReviewRepository extends JpaRepository<Review, Long> {

    @Query("select r from Review  r where r.reservation.id = ?1")
    Review findByReservation(Long reservationId);

    @Query("select r from ReviewOwner r where r.owner.id=?1 and r.guest.id=?2")
    ReviewOwner findByOwnerGuest(Long idOwner, Long idGuest);

    @Modifying
    @Query("delete from ReviewOwner r where r.owner.id=?1 and r.guest.id=?2")
    void deleteByOwnerGuest(Long idOwner, Long idGuest);

    @Query("select r from Reservation r where r.guest.id=?1")
    Collection<Reservation> findByGuest(Long id);

    @Query("select r from Review r where r.reservation.id=?1")
    Review findByOwnerGuestAccommodation(Long idReservation, Long idGuest);
}
