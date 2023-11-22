package com.booking.ProjectISS.service.reservations;

import com.booking.ProjectISS.dto.accomodations.AccommodationDTO;
import com.booking.ProjectISS.dto.reservations.ReservationDTO;
import com.booking.ProjectISS.model.accomodations.Accommodation;
import com.booking.ProjectISS.model.reservations.Reservation;
import com.booking.ProjectISS.repository.reservations.IReservationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;

@Service
public class ReservationService implements IReservationService{

    @Autowired
    private IReservationRepository reservationRepository;
    @Override
    public ReservationDTO findOneDTO(Long id) {
        return new ReservationDTO(reservationRepository.findOne(id));
    }

    @Override
    public Reservation findOne(Long id) {
        return reservationRepository.findOne(id);
    }

    @Override
    public Collection<ReservationDTO> findAllDTO() {
        Collection<Reservation> reservations = reservationRepository.findAll();
        Collection<ReservationDTO> reservationDTOS = new ArrayList<>();

        for(Reservation r: reservations){
            reservationDTOS.add(new ReservationDTO(r));
        }

        return reservationDTOS;
    }

    @Override
    public Collection<Reservation> findAll() {
        return reservationRepository.findAll();
    }

    @Override
    public void delete(Long id) {
        reservationRepository.delete(id);
    }

    @Override
    public ReservationDTO create(Reservation reservation) throws Exception {
        Reservation savedReservations = reservationRepository.create(reservation);
        return new ReservationDTO(savedReservations);
    }

    @Override
    public ReservationDTO update(Reservation accommodation) throws Exception {
        return null;
    }
}
