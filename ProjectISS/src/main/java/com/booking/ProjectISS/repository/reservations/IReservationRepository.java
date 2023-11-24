package com.booking.ProjectISS.repository.reservations;

import com.booking.ProjectISS.model.reservations.Reservation;

import java.util.Collection;

public interface IReservationRepository {

    Reservation findOne(long id);
    Collection<Reservation> findAll();
    void delete(long id);
    Reservation create(Reservation reservation);

    Collection<Reservation> findAllByGuest(Long id);
}
