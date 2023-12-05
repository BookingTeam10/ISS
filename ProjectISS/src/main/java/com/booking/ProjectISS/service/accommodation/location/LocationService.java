package com.booking.ProjectISS.service.accommodation.location;
import com.booking.ProjectISS.dto.accomodations.LocationDTO;
import com.booking.ProjectISS.model.accomodations.Location;
import com.booking.ProjectISS.repository.accomodations.location.ILocationRepository;
import jakarta.transaction.Transactional;
import jakarta.validation.ConstraintViolation;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.*;

@Service
public class LocationService implements ILocationService{

    @Autowired
    private ILocationRepository locationRepository;
    //ResourceBundle bundle = ResourceBundle.getBundle("ValidationMessages", LocaleContextHolder.getLocale());

    @Override
    public LocationDTO findOneDTO(Long id) {
        Optional<Location> found = locationRepository.findById(id);

        if(found.isEmpty()){
            return null;
            //ako bude trebalo promeniti u ovo
            //String value = bundle.getString("location.notFound");
            //throw new ResponseStatusException(HttpStatus.NOT_FOUND, value);
        }
        return new LocationDTO(found.get());
    }
    @Override
    public Location findOne(Long id) {
        Optional<Location> found = locationRepository.findById(id);

        if(found.isEmpty()){
            return null;
            //ako bude trebalo promeniti u ovo
            //String value = bundle.getString("location.notFound");
            //throw new ResponseStatusException(HttpStatus.NOT_FOUND, value);
        }
        return found.get();
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
        Location found = findOne(id);
        locationRepository.delete(found);
        locationRepository.flush();
    }
    @Override
    public LocationDTO create(Location location) throws Exception{
        return new LocationDTO(locationRepository.save(new Location(location)));
    }
    @Override
    public LocationDTO update(Location location) throws Exception {
        return null;
    }
}
