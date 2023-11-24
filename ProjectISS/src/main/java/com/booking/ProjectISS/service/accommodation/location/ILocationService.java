package com.booking.ProjectISS.service.accommodation.location;

import com.booking.ProjectISS.dto.accomodations.AccommodationDTO;
import com.booking.ProjectISS.dto.accomodations.LocationDTO;
import com.booking.ProjectISS.model.accomodations.Accommodation;
import com.booking.ProjectISS.model.accomodations.Location;

import java.util.Collection;

public interface ILocationService {
    LocationDTO findOneDTO(Long id);
    Location findOne(Long id);
    Collection<LocationDTO> findAllDTO();
    Collection<Location> findAll();
    void delete(Long id);
    LocationDTO create(Location location) throws Exception;
    LocationDTO update(Location location) throws Exception;
}
