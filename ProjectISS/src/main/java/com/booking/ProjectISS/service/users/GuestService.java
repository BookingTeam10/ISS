package com.booking.ProjectISS.service.users;

import com.booking.ProjectISS.dto.users.GuestDTO;
import com.booking.ProjectISS.model.users.Guest;
import com.booking.ProjectISS.repository.users.IGuestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;

@Service
public class GuestService implements IGuestService {

    @Autowired
    private IGuestRepository guestRepository;

    @Override
    public GuestDTO findOneDTO(Long id) {
        Guest guest=guestRepository.findOne(id);
        if(guest==null){
            return null;
        }
        return new GuestDTO(guest);
    }

    @Override
    public Guest findOne(Long id) {
        Guest guest=guestRepository.findOne(id);
        if(guest==null){
            return null;
        }
        return guest;
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
        guestRepository.delete(id);
    }

    @Override
    public GuestDTO create(Guest guest) throws Exception {
        if (guest.getId() != null) {
            throw new Exception("Id mora biti null prilikom perzistencije novog entiteta.");
        }
        Guest savedGuest = guestRepository.create(guest);
        return new GuestDTO(savedGuest);
    }

    @Override
    public GuestDTO update(Guest guest) throws Exception {
        return null;
    }
}
