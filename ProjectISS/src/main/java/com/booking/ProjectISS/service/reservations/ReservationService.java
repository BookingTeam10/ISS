package com.booking.ProjectISS.service.reservations;

import com.booking.ProjectISS.dto.accomodations.AccommodationDTO;
import com.booking.ProjectISS.dto.reservations.ReservationDTO;
import com.booking.ProjectISS.enums.ReservationStatus;
import com.booking.ProjectISS.model.accomodations.Accommodation;
import com.booking.ProjectISS.model.reservations.Reservation;
import com.booking.ProjectISS.model.accomodations.Location;
import com.booking.ProjectISS.repository.reservations.IReservationRepository;
import org.apache.coyote.Request;
import org.hibernate.dialect.SimpleDatabaseVersion;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

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
    public ReservationDTO update(Reservation reservation) throws Exception {
        reservationRepository.delete(reservation.getId());
        reservationRepository.create(reservation);

        return new ReservationDTO(reservation);
    }

    @Override
    public boolean hasOverlappingRequests(Reservation newReservation) {
        for (Reservation existingReservation : reservationRepository.findAll()) {
            if(!Objects.equals(existingReservation.getAccommodation().getId(), newReservation.getAccommodation().getId()))continue;
            if (doDatesOverlap(newReservation.getStartDate(), newReservation.getEndDate(),
                    existingReservation.getStartDate(), existingReservation.getEndDate())) {
                return true;
            }
        }
        return false;
    }

    @Override
    public List<Reservation> getGuestReservations(long guestId) {

        return reservationRepository.findAll().stream()
                .filter(reservation -> reservation.getGuest().getId().equals(guestId))
                .toList();


    }

    @Override
    public List<Reservation> searchGuestReservations(List<Reservation> reservations, String location, String date) {
        return reservations.stream()
                .filter(reservation -> matchesLocation(reservation, location) && matchesDates(reservation, date ))
                .toList();
    }
    private boolean matchesDates(Reservation reservation, String date) {
        if(date == null || date.isEmpty()){return true;}

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        try {
            Date searchedDate = dateFormat.parse(date);

            return reservation.getStartDate().equals(searchedDate) || reservation.getEndDate().equals(searchedDate)
                    || (searchedDate.after(reservation.getStartDate()) && searchedDate.before(reservation.getEndDate()));
        } catch (ParseException e) {
            return true;
        }


    }

    private boolean matchesLocation(Reservation reservation, String location) {
        if(location == null || location.isEmpty()){return true;}

        Location accomodationLocation = reservation.getAccommodation().getLocation();

        String country = accomodationLocation.getCountry();
        String city = accomodationLocation.getCity();
        String street = accomodationLocation.getStreet();

        return containsIgnoreCase(country, location) || containsIgnoreCase(city, location) || containsIgnoreCase(street, location);
    }

    private boolean containsIgnoreCase(String str, String searchTerm) {
        return str != null && str.toLowerCase().contains(searchTerm.toLowerCase());
    }

    private boolean doDatesOverlap(Date start1, Date end1, Date start2, Date end2) {
        return start1.before(end2) && end1.after(start2);
    }

    @Override
    public List<Reservation> filterGuestReservations(List<Reservation> reservations, ReservationStatus status) {
        return reservations.stream()
                .filter(reservation -> reservation.getStatus().equals(status))
                .toList();
    }

    @Override
    public Collection<ReservationDTO> getReservationsDTO(List<Reservation> reservations) {
        return  reservations.stream()
                .map(this::convertToDTO)
                .toList();


    }

    @Override
    public ReservationDTO convertToDTO(Reservation reservation) {
        return new ReservationDTO(reservation);
    }

}
