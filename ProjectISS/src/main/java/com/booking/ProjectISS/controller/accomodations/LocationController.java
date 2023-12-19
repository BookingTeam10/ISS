package com.booking.ProjectISS.controller.accomodations;

import com.booking.ProjectISS.dto.accomodations.AccommodationDTO;
import com.booking.ProjectISS.dto.accomodations.LocationDTO;
import com.booking.ProjectISS.model.accomodations.Accommodation;
import com.booking.ProjectISS.model.accomodations.Location;
import com.booking.ProjectISS.service.accommodation.IAccommodationService;
import com.booking.ProjectISS.service.accommodation.location.ILocationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@RestController
@RequestMapping("/api/locations")
@CrossOrigin(origins = "http://localhost:4200")
public class LocationController {
    @Autowired
    private ILocationService locationService;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Collection<LocationDTO>> getLocationDTOS() {
        Collection<LocationDTO> locationDTOS = locationService.findAllDTO();
        return new ResponseEntity<Collection<LocationDTO>>(locationDTOS, HttpStatus.OK);
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<LocationDTO> getLocation(@PathVariable("id") Long id) {
        LocationDTO locationDTO = locationService.findOneDTO(id);
        if (locationDTO!= null)
            return new ResponseEntity<LocationDTO>(locationDTO, HttpStatus.OK);
        return new ResponseEntity<LocationDTO>(HttpStatus.NOT_FOUND);
    }
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasRole('Owner')") //zato sto dodaje smestaj
    public ResponseEntity<LocationDTO> createLocation(@RequestBody Location location) throws Exception {
        LocationDTO locationDTO =locationService.create(location);
        return new ResponseEntity<LocationDTO>(locationDTO, HttpStatus.CREATED);
    }

    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasRole('Owner')")
    public ResponseEntity<LocationDTO> updateLocation(@RequestBody Location location, @PathVariable Long id)
            throws Exception {
        Location updateLocation =locationService.findOne(id);
        if (updateLocation == null)
            return new ResponseEntity<LocationDTO>(HttpStatus.NOT_FOUND);
        updateLocation.copyValues(location);
        LocationDTO updatedLocation = locationService.update(updateLocation);
        return new ResponseEntity<LocationDTO>(updatedLocation, HttpStatus.OK);
    }
    @DeleteMapping(value = "/{id}")
    @PreAuthorize("hasAnyRole('Admin', 'Owner')")
    public ResponseEntity<LocationDTO> deleteLocation(@PathVariable("id") Long id) {
        locationService.delete(id);
        return new ResponseEntity<LocationDTO>(HttpStatus.NO_CONTENT);
    }
}
