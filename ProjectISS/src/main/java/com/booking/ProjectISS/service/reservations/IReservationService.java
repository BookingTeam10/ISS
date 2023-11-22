package com.booking.ProjectISS.service.reservations;


import com.booking.ProjectISS.dto.reservations.ReservationDTO;
import com.booking.ProjectISS.model.reservations.Reservation;

import java.util.Collection;

public interface IReservationService {
    ReservationDTO findOneDTO(Long id);
    Reservation findOne(Long id);
    Collection<ReservationDTO> findAllDTO();
    Collection<Reservation> findAll();
    void delete(Long id);
    ReservationDTO create(Reservation reservation) throws Exception;
    ReservationDTO update(Reservation reservation) throws Exception;
}
