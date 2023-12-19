package com.booking.ProjectISS.controller.accomodations;

import com.booking.ProjectISS.dto.accomodations.AccommodationDTO;
import com.booking.ProjectISS.enums.AccommodationStatus;
import com.booking.ProjectISS.model.accomodations.Accommodation;
import com.booking.ProjectISS.model.accomodations.Amenity;
import com.booking.ProjectISS.service.accommodation.IAccommodationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.Date;
import java.util.List;


@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/api/accommodations")
public class AccommodationController {
    @Autowired
    private IAccommodationService accommodationService;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Collection<AccommodationDTO>> getAccommodationsDTO() {
        System.out.println(accommodationService.findAll());
        Collection<AccommodationDTO> accommodationDTOS = accommodationService.findAllDTO();
        return new ResponseEntity<Collection<AccommodationDTO>>(accommodationDTOS, HttpStatus.OK);
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<AccommodationDTO> getAccommodation(@PathVariable("id") Long id) {
        AccommodationDTO accommodationDTO = accommodationService.findOneDTO(id);
        if (accommodationDTO!= null) {
            return new ResponseEntity<AccommodationDTO>(accommodationDTO, HttpStatus.OK);
        }

        return new ResponseEntity<AccommodationDTO>(HttpStatus.NOT_FOUND);
    }
    @PostMapping(value="/add" ,consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasRole('Owner')")
    public ResponseEntity<AccommodationDTO> createAccommodation(@RequestBody Accommodation accommodation) throws Exception {
        AccommodationDTO accommodationDTO =accommodationService.add(accommodation);
        return new ResponseEntity<AccommodationDTO>(accommodationDTO, HttpStatus.CREATED);
    }

    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasRole('Owner')")
    public ResponseEntity<AccommodationDTO> updateAccommodation(@RequestBody Accommodation accommodation, @PathVariable Long id)
            throws Exception {
        Accommodation updateAccommodation =accommodationService.findOne(id);
        if (updateAccommodation == null) {
            return new ResponseEntity<AccommodationDTO>(HttpStatus.NOT_FOUND);
        }

        AccommodationDTO updatedAccommodation = accommodationService.update(accommodation);
        return new ResponseEntity<AccommodationDTO>(updatedAccommodation, HttpStatus.OK);
    }
    @DeleteMapping(value = "/{id}")
    @PreAuthorize("hasAnyRole('Admin', 'Owner')")
    public ResponseEntity<AccommodationDTO> deleteAccommodation(@PathVariable("id") Long id) {
        accommodationService.delete(id);
        return new ResponseEntity<AccommodationDTO>(HttpStatus.NO_CONTENT);
    }

    @PutMapping(value = "/pending/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<AccommodationDTO> updateAccomodation(@RequestBody Accommodation accommodation, @PathVariable Long id)
            throws Exception {
        Accommodation accomodationForUpdate = accommodationService.findOne(id);
        if (accomodationForUpdate == null) {
            return new ResponseEntity<AccommodationDTO>(HttpStatus.NOT_FOUND);
        }
        accomodationForUpdate.copyValues(accommodation);
        AccommodationDTO updatedAcc = accommodationService.update(accomodationForUpdate);
        return new ResponseEntity<AccommodationDTO>(updatedAcc, HttpStatus.OK);
    }

    //3.9 for unregisted user
//    @GetMapping(value = "/accommodationsSearch")
//    @CrossOrigin(origins = "http://localhost:4200")
//    public ResponseEntity<Collection<AccommodationDTO>> getSearchedAccommodations(
//            @RequestParam(required = false) String location,
//            @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") Date start,
//            @RequestParam(required = false)  @DateTimeFormat(pattern = "yyyy-MM-dd")  Date end,
//            @RequestParam(required = false) int numPeople){
//        Collection<AccommodationDTO> accommodationDTOS = accommodationService.getAccommodationsSearched(start,end,numPeople,location);
//        if(accommodationDTOS == null)
//            return new ResponseEntity<Collection<AccommodationDTO>>(HttpStatus.NOT_FOUND);
//        return ResponseEntity.ok(accommodationDTOS);
//    }

    @GetMapping(value = "/accommodationsSearch")
    @PreAuthorize("hasAnyRole('Guest', 'Owner', 'User')")
    public ResponseEntity<Collection<AccommodationDTO>> getSearchedAccommodations(
            @RequestParam(required = false) String location,
            @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") Date start,
            @RequestParam(required = false)  @DateTimeFormat(pattern = "yyyy-MM-dd")  Date end,
            @RequestParam(required = false) int numPeople,
            @RequestParam(required = false) String minPrice,
            @RequestParam(required = false) String maxPrice,
            @RequestParam(required = false) List<Amenity> amenities){
        Collection<AccommodationDTO> accommodationDTOS = accommodationService.getAccommodationsSearched(start,end,numPeople,location,minPrice,maxPrice,amenities);

        if(accommodationDTOS == null) {
            return new ResponseEntity<Collection<AccommodationDTO>>(HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(accommodationDTOS);
    }



    @GetMapping(value = "/{id}/amenity", produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasAnyRole('Admin', 'Owner', 'Guest')")
    public ResponseEntity<Collection<Amenity>> getAmenityByAccommodation(@PathVariable("id") Long id) {
        Accommodation accommodation = accommodationService.findOne(id);
        if (accommodation!= null) {
            return new ResponseEntity<Collection<Amenity>>(accommodation.getAmenities(), HttpStatus.OK);
        }
        return new ResponseEntity<Collection<Amenity>>(HttpStatus.NOT_FOUND);
    }

    @PostMapping(value = "/approve/{id}")
    @PreAuthorize("hasRole('Admin')")
    public ResponseEntity<AccommodationDTO> setApproveAccommodation(@PathVariable("id") Long id) throws Exception {
        Accommodation accommodation = accommodationService.findOne(id);
        if (accommodation == null) {
            return new ResponseEntity<AccommodationDTO>(HttpStatus.NOT_FOUND);
        }
        accommodation.setAccommodationStatus(AccommodationStatus.APPROVED);
        AccommodationDTO accommodationDTO = accommodationService.update(accommodation);
        return new ResponseEntity<AccommodationDTO>(accommodationDTO,HttpStatus.OK);
    }

    @PostMapping(value = "/reject/{id}")
    @PreAuthorize("hasRole('Admin')")
    public ResponseEntity<AccommodationDTO> setRejectAccommodation(@PathVariable("id") Long id) throws Exception {
        Accommodation accommodation = accommodationService.findOne(id);
        if (accommodation == null) {
            return new ResponseEntity<AccommodationDTO>(HttpStatus.NOT_FOUND);
        }
        accommodation.setAccommodationStatus(AccommodationStatus.REJECTED);
        AccommodationDTO accommodationDTO = accommodationService.update(accommodation);
        return new ResponseEntity<AccommodationDTO>(accommodationDTO,HttpStatus.OK);
    }

}
