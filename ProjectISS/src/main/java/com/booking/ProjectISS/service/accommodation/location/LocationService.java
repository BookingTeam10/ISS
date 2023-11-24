package com.booking.ProjectISS.service.accommodation.location;
import com.booking.ProjectISS.dto.accomodations.LocationDTO;
import com.booking.ProjectISS.model.accomodations.Location;
import com.booking.ProjectISS.repository.accomodations.location.ILocationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;

@Service
public class LocationService implements ILocationService{

    @Autowired
    private ILocationRepository locationRepository;
    @Override
    public LocationDTO findOneDTO(Long id) {
        Location location = locationRepository.findOne(id);

        if(location ==null){
            return null;
        }
        return new LocationDTO(location);
    }

    @Override
    public Location findOne(Long id) {
        Location location= locationRepository.findOne(id);
        if (location==null){
            return null;
        }
        return location;
    }
    @Override
    public Collection<LocationDTO> findAllDTO() {
        Collection<Location> locations= locationRepository.findAll();
        Collection<LocationDTO> locationDTOS= new ArrayList<LocationDTO>();
        for(Location l : locations) {
            locationDTOS.add(new LocationDTO(l));
        }
        return locationDTOS;
    }

    @Override
    public Collection<Location> findAll() {
        return locationRepository.findAll();
    }
    @Override
    public void delete(Long id) {
        locationRepository.delete(id);
    }
    @Override
    public LocationDTO create(Location location) throws Exception {
        if (location.getId() != null) {
            throw new Exception("Id mora biti null prilikom perzistencije novog entiteta.");
        }
        Location savedLocation= locationRepository.create(location);
        return new LocationDTO(savedLocation);
    }
    @Override
    public LocationDTO update(Location location) throws Exception {
        return null;
    }
}
