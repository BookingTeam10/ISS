package com.booking.ProjectISS.repository.accomodations.location;

import com.booking.ProjectISS.model.accomodations.Accommodation;
import com.booking.ProjectISS.model.accomodations.Location;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;

public interface ILocationRepository{
    Location findOne(Long id);
    Collection<Location> findAll();
    void delete(Long id);
    Location create(Location location);
}
