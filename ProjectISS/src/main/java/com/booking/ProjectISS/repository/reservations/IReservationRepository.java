package com.booking.ProjectISS.repository.reservations;

import com.booking.ProjectISS.enums.ReservationStatus;
import com.booking.ProjectISS.model.reservations.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Collection;
import java.util.Optional;

public interface IReservationRepository  extends JpaRepository<Reservation, Long> {

    @Query("select r from Reservation r where r.guest.id = ?1")
    Reservation findByGuest(String guestId);
    @Query("select r from Reservation r where r.accommodation.id = ?1")
    Collection<Reservation> findByAccommodation(Long id);

    @Query("select r from Reservation r where r.guest.id = ?1 and r.status!=?2")
    Collection<Reservation> findByGuestId(Long guestId, ReservationStatus status);

    @Query("select r from Reservation r where r.guest.id = ?2 and r.id!=?1")
    Collection<Reservation> findByAccommodationGuest(Long idAccommodation, Long idGuest);
}
