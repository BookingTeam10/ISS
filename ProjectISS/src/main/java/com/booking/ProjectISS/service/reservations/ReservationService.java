package com.booking.ProjectISS.service.reservations;

import com.booking.ProjectISS.dto.accomodations.AccommodationDTO;
import com.booking.ProjectISS.dto.reservations.ReservationDTO;
import com.booking.ProjectISS.enums.ReservationStatus;
import com.booking.ProjectISS.model.accomodations.Accommodation;
import com.booking.ProjectISS.model.reservations.Reservation;
import com.booking.ProjectISS.model.accomodations.Location;
import com.booking.ProjectISS.model.users.Guest;
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
        Optional<Reservation> found = reservationRepository.findById(id);
        return found.map(ReservationDTO::new).orElse(null);

    }

    @Override
    public Reservation findOne(Long id) {
        Optional<Reservation> found = reservationRepository.findById(id);
        return found.orElse(null);
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
        Optional<Reservation> found = reservationRepository.findById(id);
        if(found.isEmpty()){ return;}
        reservationRepository.delete(found.get());
        reservationRepository.flush();
    }

    @Override
    public ReservationDTO create(Reservation reservation) throws Exception {

        return new ReservationDTO(reservationRepository.save(reservation));
    }

    @Override
    public ReservationDTO update(Reservation reservation) throws Exception {
        reservationRepository.delete(reservation);
        reservationRepository.save(reservation);

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
    public List<Reservation> searchReservations(List<Reservation> reservations, String location, String date) {
        return reservations.stream()
                .filter(reservation -> matchesLocation(reservation, location) && matchesDates(reservation, date ))
                .toList();
    }
    private boolean matchesDates(Reservation reservation, String date) {
        if(date == null || date.isEmpty()){return true;}

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        try {
            Date searchedDate = truncateTime(dateFormat.parse(date));
            Date startOfDay = truncateTime(reservation.getStartDate());
            Date endOfDay = truncateTime(reservation.getEndDate());

            return searchedDate.equals(startOfDay) || searchedDate.equals(endOfDay)
                    || (searchedDate.after(startOfDay) && searchedDate.before(endOfDay));
        } catch (ParseException e) {
            return true;
        }
    }

    private Date truncateTime(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        return calendar.getTime();
    }

    private boolean matchesLocation(Reservation reservation, String location) {
        if(location == null || location.isEmpty()){return true;}

        //Location accomodationLocation = reservation.getAccommodation().getLocation();

        //String country = accomodationLocation.getCountry();
        //String city = accomodationLocation.getCity();
        //String street = accomodationLocation.getStreet();

        //return containsIgnoreCase(country, location) || containsIgnoreCase(city, location) || containsIgnoreCase(street, location);
        return true;
    }

    private boolean containsIgnoreCase(String str, String searchTerm) {
        return str != null && str.toLowerCase().contains(searchTerm.toLowerCase());
    }

    private boolean doDatesOverlap(Date start1, Date end1, Date start2, Date end2) {
        return start1.before(end2) && end1.after(start2);
    }

    @Override
    public List<Reservation> filterReservations(List<Reservation> reservations, ReservationStatus status) {
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

    @Override
    public List<Reservation> getOwnerReservations(Long ownerId) {
        return reservationRepository.findAll().stream()
                .filter(reservation -> reservation.getAccommodation().getOwner().getId().equals(ownerId))
                .toList();

    }

    @Override
    public void cancelledAllReservation(Guest u) {
//        Collection<Reservation> reservations=reservationRepository.findAllByGuest(u.getId());
//        for(Reservation r:reservations){
//            r.setStatus(ReservationStatus.DELETED);
//        }
    }
}