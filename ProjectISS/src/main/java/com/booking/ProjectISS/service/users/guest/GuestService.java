package com.booking.ProjectISS.service.users.guest;

import com.booking.ProjectISS.dto.users.GuestDTO;
import com.booking.ProjectISS.dto.users.OwnerDTO;
import com.booking.ProjectISS.dto.users.UserDTO;
import com.booking.ProjectISS.enums.ReservationStatus;
import com.booking.ProjectISS.model.reservations.Reservation;
import com.booking.ProjectISS.model.users.Guest;
import com.booking.ProjectISS.model.users.User;
import com.booking.ProjectISS.repository.users.guests.IGuestRepository;
import com.booking.ProjectISS.service.reservations.IReservationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class GuestService implements IGuestService {

    @Autowired
    private IGuestRepository guestRepository;
    @Autowired
    private IReservationService reservationService;

    @Override
    public GuestDTO findOneDTO(Long id) {
        Optional<Guest> Guest=guestRepository.findById(id);
        if(Guest.isEmpty()){
            return null;
        }
        return new GuestDTO(Guest.get());
    }

    @Override
    public Guest findOne(Long id) {
        Optional<Guest> Guest=guestRepository.findById(id);
        if(Guest.isEmpty()){
            return null;
        }
        return Guest.get();
    }

    @Override
    public Collection<GuestDTO> findAllDTO() {
        Collection<Guest> guests = guestRepository.findAll();
        Collection<GuestDTO> ret = new ArrayList<GuestDTO>();       //primitive way, delete other this line after
        for(Guest g : guests) {
            ret.add(new GuestDTO(g));
        }
        return ret;
    }

    @Override
    public Collection<Guest> findAll() {
        Collection<Guest> guests = guestRepository.findAll();
        return guests;
    }

    @Override
    public void delete(Long id) {
        Guest found = findOne(id);
        guestRepository.delete(found);
        guestRepository.flush();
    }

    @Override
    public GuestDTO create(Guest guest) throws Exception {
        return new GuestDTO(guestRepository.save(new Guest(guest)));
    }

    @Override
    public GuestDTO update(Guest guestForUpdate) throws Exception {

        Optional<Guest> optionalGuest = this.guestRepository.findById(guestForUpdate.getId());

        optionalGuest.ifPresent(oldGuest -> {
            oldGuest.setEmail(guestForUpdate.getEmail());
            oldGuest.setPassword(guestForUpdate.getPassword());
            oldGuest.setName(guestForUpdate.getName());
            oldGuest.setSurname(guestForUpdate.getSurname());
            oldGuest.setPhone(guestForUpdate.getPhone());
            oldGuest.setAddress(guestForUpdate.getAddress());
            oldGuest.setNumberCanceledReservation(guestForUpdate.getNumberCanceledReservation());
            oldGuest.setTurnOnNotification(guestForUpdate.isTurnOnNotification());
            oldGuest.setFavouriteAccommodations(guestForUpdate.getFavouriteAccommodations());
            this.guestRepository.save(oldGuest);
        });

        return new GuestDTO(guestForUpdate);
    }

    @Override
    public boolean reportOwner(Long idGuest,Long idOwner) {
        return true;
    }

    @Override
    public Guest findUsername(String username) {
        return this.guestRepository.findByEmail(username);
    }

    @Override
    public void cancelAllReservations(Long id) throws Exception {
        List<Reservation> reservationList = this.reservationService.getGuestReservations(id);

        reservationList = reservationList.stream()
                .filter(reservation ->
                        reservation.getStatus() == ReservationStatus.WAITING ||
                                reservation.getStatus() == ReservationStatus.ACCEPTED)
                .collect(Collectors.toList());


        for(Reservation r: reservationList){
            r.setStatus(ReservationStatus.CANCELLED);
            this.reservationService.update(r);
        }
    }


}
