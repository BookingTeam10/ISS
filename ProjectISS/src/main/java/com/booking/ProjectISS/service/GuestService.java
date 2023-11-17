package com.booking.ProjectISS.service;

import com.booking.ProjectISS.model.Guest;
import com.booking.ProjectISS.repository.GuestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
public class GuestService implements IGuestService {

    @Autowired
    private GuestRepository guestRepository;

    @Override
    public Guest findOne(Long id) {
        Guest guest=guestRepository.findOne(id);
        return guest;
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
}
