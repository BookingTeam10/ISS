package com.booking.ProjectISS.repository.reservations;

import com.booking.ProjectISS.model.reservations.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Collection;
import java.util.Optional;

public interface IReservationRepository  extends JpaRepository<Reservation, Long> {

    @Query("select r from Reservation r where r.guest.id = ?1")
    Reservation findByGuest(String guestId);

}
