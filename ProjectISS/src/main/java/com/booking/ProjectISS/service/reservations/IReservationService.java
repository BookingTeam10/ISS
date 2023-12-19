package com.booking.ProjectISS.service.reservations;


import com.booking.ProjectISS.dto.reservations.ReservationDTO;
import com.booking.ProjectISS.enums.ReservationStatus;
import com.booking.ProjectISS.model.reservations.Reservation;
import com.booking.ProjectISS.model.users.Guest;
import com.booking.ProjectISS.model.users.User;

import java.util.Collection;
import java.util.List;

public interface IReservationService {
    ReservationDTO findOneDTO(Long id);

    Reservation findOne(Long id);

    Collection<ReservationDTO> findAllDTO();

    Collection<Reservation> findAll();

    boolean delete(Long id);

    ReservationDTO create(Reservation reservation) throws Exception;

    ReservationDTO update(Reservation reservation) throws Exception;

    boolean hasOverlappingRequests(Reservation reservation);

    List<Reservation> getGuestReservations(long guestId);

    List<Reservation> searchReservations(List<Reservation> reservation, String location, String date);

    List<Reservation> filterReservations(List<Reservation> reservations, ReservationStatus status);

    Collection<ReservationDTO> getReservationsDTO(List<Reservation> reservations);

    ReservationDTO convertToDTO(Reservation reservation);

    List<Reservation> getOwnerReservations(Long ownerId);

    void cancelledAllReservation(Guest u);
    Collection<ReservationDTO> findAllNotAcceptedDTO();
    Collection<ReservationDTO> findByAccommodation(Long id);
}
