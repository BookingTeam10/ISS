package com.booking.ProjectISS.service.reservations;

import com.booking.ProjectISS.dto.reservations.ReservationDTO;
import com.booking.ProjectISS.dto.users.OwnerDTO;
import com.booking.ProjectISS.enums.ReservationStatus;
import com.booking.ProjectISS.model.accomodations.Accommodation;
import com.booking.ProjectISS.model.notifications.NotificationVisible;
import com.booking.ProjectISS.model.reservations.Reservation;
import com.booking.ProjectISS.model.accomodations.Location;
import com.booking.ProjectISS.model.users.Guest;
import com.booking.ProjectISS.repository.accomodations.IAccommodationRepository;
import com.booking.ProjectISS.repository.reservations.IReservationRepository;
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
    @Autowired
    private IAccommodationRepository accommodationService;
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
    public Collection<ReservationDTO> findAllNotAcceptedDTO() {
        Collection<Reservation> reservations = reservationRepository.findAll();
        Collection<ReservationDTO> reservationDTOS = new ArrayList<>();

        for(Reservation r: reservations){
            if (r.getStatus() != ReservationStatus.ACCEPTED)
                reservationDTOS.add(new ReservationDTO(r));
        }

        return reservationDTOS;
    }

    @Override
    public Collection<ReservationDTO> findByAccommodation(Long id) {
        Collection<Reservation> reservations = reservationRepository.findByAccommodation(id);
        Collection<ReservationDTO> reservationDTOS = new ArrayList<>();
        for(Reservation r: reservations){
            reservationDTOS.add(new ReservationDTO(r));
        }
        return reservationDTOS;
    }

    @Override
    public void cancelAllWaiting(Reservation accepptedReservation) throws Exception {
        List<Reservation> reservations = this.reservationRepository.findAll();
        for (Reservation r : reservations) {
            if (r.getStatus() == ReservationStatus.WAITING && r.getAccommodation().getId().equals(accepptedReservation.getAccommodation().getId())) {
                if (doDatesOverlap(accepptedReservation.getStartDate(), accepptedReservation.getEndDate(), r.getStartDate(), r.getEndDate())) {
                    r.setStatus(ReservationStatus.REJECTED);
                    this.update(r);
                }
            }
        }
    }
    public Collection<OwnerDTO> findOwnerByReservationGuest(Long id) {
        List<String> lista=new ArrayList<String>();
        Collection<OwnerDTO> ownerDTOS = new HashSet<OwnerDTO>();
        Collection<Reservation> reservations=reservationRepository.findByGuestId(id,ReservationStatus.CANCELLED);
        if(reservations.isEmpty()){
            return ownerDTOS;
        }
        System.out.println(reservations.size());
        for(Reservation r:reservations){
            if(!lista.contains(r.getAccommodation().getOwner().getEmail())){
                ownerDTOS.add(new OwnerDTO(r.getAccommodation().getOwner()));
                lista.add(r.getAccommodation().getOwner().getEmail());
            }
            //ownerDTOS.add(new OwnerDTO(r.getAccommodation().getOwner()));
        }
        return ownerDTOS;
    }
    @Override
    public Collection<Reservation> findAll() {
        return reservationRepository.findAll();
    }

    @Override
    public boolean  delete(Long id) {
        //staviti ako ima review sa id od rezervacije da ne moze da se obrise
        Optional<Reservation> found = reservationRepository.findById(id);
        if(found.isEmpty()){ return false;}
        reservationRepository.delete(found.get());
        reservationRepository.flush();
        return true;
    }
    @Override
    public ReservationDTO create(Reservation reservation) throws Exception {
        if(reservation.getAccommodation().isAutomaticConfirmation()){
            reservation.setStatus(ReservationStatus.ACCEPTED);
            this.cancelAllWaiting(reservation);
        }
        System.out.println(reservation);
        return new ReservationDTO(reservationRepository.save(reservation));
    }
    @Override
    public ReservationDTO update(Reservation reservationForUpdate) throws Exception {
        Optional<Reservation> optionalReservation = this.reservationRepository.findById(reservationForUpdate.getId());
        optionalReservation.ifPresent(oldReservation -> {
            oldReservation.setStatus(reservationForUpdate.getStatus());
            oldReservation.setAccommodation(reservationForUpdate.getAccommodation());
            oldReservation.setGuest(reservationForUpdate.getGuest());
            oldReservation.setReviews(reservationForUpdate.getReviews());
            oldReservation.setEndDate(reservationForUpdate.getEndDate());
            oldReservation.setStartDate(reservationForUpdate.getStartDate());
            oldReservation.setNumberOfNights(reservationForUpdate.getNumberOfNights());
            oldReservation.setTotalPrice(reservationForUpdate.getTotalPrice());

            this.reservationRepository.save(oldReservation);
        });

        return new ReservationDTO(reservationForUpdate);
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
    public Collection<ReservationDTO> getOwnersRequests(Long idOwner) {
        Collection<Accommodation> accommodations = accommodationService.findAll();
        Collection<Accommodation> ownerAccommodations = new ArrayList<>();
        Collection<Reservation> reservations = findAll();
        Collection<ReservationDTO> ownerReservations = new ArrayList<>();

        for(Accommodation accommodation:accommodations){
            if (accommodation.getOwner().getId()==idOwner)
                ownerAccommodations.add(accommodation);
        }

        for(Reservation reservation:reservations){
            for(Accommodation accommodation:ownerAccommodations){
                if (reservation.getAccommodation().getId()==accommodation.getId())
                    ownerReservations.add(new ReservationDTO(reservation));
            }
        }
        return ownerReservations;
    }

    //dodati posle za datume
    @Override
    public Collection<ReservationDTO> searchedRequests(String type, Date start, Date end, String nameAccommodation,Long idOwner) {

        Collection<ReservationDTO> ownerReservations = getOwnersRequests(idOwner);
        Collection<ReservationDTO> ownerReservationsType;
        Collection<ReservationDTO> ownerReservationsName;
        if(type!=null){
            ReservationStatus reservationStatus = ReservationStatus.valueOf(type);
            ownerReservationsType = reservationRepository.findByStatus(reservationStatus);
        }else{
            //posto radimo presek ako ne bude definisano vracace uvek praznu listu zato moramo ovako da stavimo
            ownerReservationsType = ownerReservations;
        }
        System.out.println(ownerReservationsType);
        if(nameAccommodation!=null){

            ownerReservationsName = reservationRepository.findByAccommodationName(nameAccommodation);;
        }else{
            //posto radimo presek ako ne bude definisano vracace uvek praznu listu zato moramo ovako da stavimo
            ownerReservationsName = ownerReservations;
        }
        System.out.println(ownerReservationsName);

        Collection<ReservationDTO> presek = ownerReservations.stream()
                .filter(reservation -> ownerReservationsType.contains(reservation) &&
                        ownerReservationsName.contains(reservation))
                .collect(Collectors.toList());

        System.out.println(presek);
        return presek;
    }

    @Override
    public void cancelledAllReservation(Guest u) {
//        Collection<Reservation> reservations=reservationRepository.findAllByGuest(u.getId());
//        for(Reservation r:reservations){
//            r.setStatus(ReservationStatus.DELETED);
//        }
    }
    @Override
    public Collection<ReservationDTO> findByGuest(Long id) {
        Collection<ReservationDTO> reservations=reservationRepository.findByGuest(id);
        return reservations;
    }

    @Override
    public int totalPrice(Collection<ReservationDTO> reservationDTOS) {
        int total = 0;
        for(ReservationDTO reservation:reservationDTOS)
            total+=reservation.getTotalPrice();

        return total;
    }
    @Override
    public Collection<ReservationDTO> findAllNotAcceptedGuestDTO(Long idGuest) {
        Collection<ReservationDTO> reservations = reservationRepository.findByGuest(idGuest);
        Collection<ReservationDTO> reservationDTOS = new ArrayList<>();

        for(ReservationDTO r: reservations){
            if (r.getStatus() != ReservationStatus.ACCEPTED)
                reservationDTOS.add(r);
        }

        return reservationDTOS;
    }


}
