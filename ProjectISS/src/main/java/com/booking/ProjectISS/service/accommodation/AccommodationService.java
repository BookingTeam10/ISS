package com.booking.ProjectISS.service.accommodation;

import com.booking.ProjectISS.dto.accomodations.AccommodationDTO;
import com.booking.ProjectISS.dto.users.GuestDTO;
import com.booking.ProjectISS.dto.users.UserDTO;
import com.booking.ProjectISS.model.accomodations.Accommodation;
import com.booking.ProjectISS.model.users.User;
import com.booking.ProjectISS.repository.accomodations.IAccommodationRepository;
import com.booking.ProjectISS.repository.users.guests.IGuestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;

@Service
public class AccommodationService implements IAccommodationService {

    @Autowired
    private IAccommodationRepository accommodationRepository;
    @Override
    public AccommodationDTO findOneDTO(Long id) {
        Accommodation accommodation = accommodationRepository.findOne(id);

        if(accommodation ==null){
            return null;
        }
        return new AccommodationDTO(accommodation);
    }
    @Override
    public Accommodation findOne(Long id) {
        Accommodation accommodation= accommodationRepository.findOne(id);
        if(accommodation==null){
            return null;
        }
        return accommodation;
    }

    @Override
    public Collection<AccommodationDTO> findAllDTO() {
        Collection<Accommodation> accommodations = accommodationRepository.findAll();
        Collection<AccommodationDTO> accommodationDTOS= new ArrayList<AccommodationDTO>();
        for(Accommodation a : accommodations) {
            accommodationDTOS.add(new AccommodationDTO(a));
        }
        return accommodationDTOS;
    }

    @Override
    public Collection<Accommodation> findAll() {
        return accommodationRepository.findAll();
    }
    @Override
    public void delete(Long id) {
        accommodationRepository.delete(id);
    }

    @Override
    public AccommodationDTO create(Accommodation accommodation) throws Exception {
        if (accommodation.getId() != null) {
            throw new Exception("Id mora biti null prilikom perzistencije novog entiteta.");
        }
        Accommodation savedAccommodation= accommodationRepository.create(accommodation);
        return new AccommodationDTO(savedAccommodation);
    }
    @Override
    public AccommodationDTO update(Accommodation guest) throws Exception {
        return null;
    }
}
