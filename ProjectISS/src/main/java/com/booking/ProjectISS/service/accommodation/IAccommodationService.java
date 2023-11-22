package com.booking.ProjectISS.service.accommodation;

import com.booking.ProjectISS.dto.accomodations.AccommodationDTO;
import com.booking.ProjectISS.model.accomodations.Accommodation;
import com.booking.ProjectISS.model.users.Owner;

import java.util.Collection;

public interface IAccommodationService {
    AccommodationDTO findOneDTO(Long id);
    Accommodation findOne(Long id);
    Collection<AccommodationDTO> findAllDTO();
    Collection<Accommodation> findAll();
    void delete(Long id);
    AccommodationDTO create(Accommodation accommodation) throws Exception;
    AccommodationDTO update(Accommodation accommodation) throws Exception;

    Collection<AccommodationDTO> findAllByOwnerDTO(Long id);

    AccommodationDTO createByOwner(Long id, Accommodation accommodation);

}
