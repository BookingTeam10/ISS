package com.booking.ProjectISS.repository.reservations;

import com.booking.ProjectISS.dto.reservations.ReservationDTO;
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
    @Query("select r from Reservation r where r.status = ?1")
    Collection<ReservationDTO> findByStatus(ReservationStatus status);
    @Query("select r from Reservation r where r.accommodation.name like  %?1%")
    Collection<ReservationDTO> findByAccommodationName(String accommodationName);
    @Query("select r from Reservation r where r.guest.id = ?1")
    Collection<ReservationDTO> findByGuest(Long guestId);

    @Query("select r from Reservation r where r.guest.id = ?2 and r.accommodation.id=?1")
    Collection<Reservation> findByAccommodationGuest(Long idAccommodation, Long idGuest);

    @Query("select r from Reservation r where r.accommodation.id = ?1 and r.status!=?2 and r.status!=?3")
    Collection<Reservation> findAllByAccommodationId(Long id,ReservationStatus statusCancelled, ReservationStatus statusRejected);
}
