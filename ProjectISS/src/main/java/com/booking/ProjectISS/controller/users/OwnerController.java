package com.booking.ProjectISS.controller.users;

import com.booking.ProjectISS.dto.accomodations.AccommodationDTO;
import com.booking.ProjectISS.dto.users.GuestDTO;
import com.booking.ProjectISS.dto.users.OwnerDTO;
import com.booking.ProjectISS.dto.users.UserDTO;
import com.booking.ProjectISS.model.accomodations.Accommodation;
import com.booking.ProjectISS.model.accomodations.Location;
import com.booking.ProjectISS.model.users.Owner;
import com.booking.ProjectISS.service.accommodation.IAccommodationService;
import com.booking.ProjectISS.service.users.owner.IOwnerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.Collection;
import java.util.Date;

@RestController
@RequestMapping("/api/owners")
public class OwnerController {

    @Autowired
    private IOwnerService ownerService;

    @Autowired
    private IAccommodationService accommodationService;

    //getAll
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Collection<OwnerDTO>> getOwnerDTO() {
        Collection<OwnerDTO> Owners = ownerService.findAllDTO();
        return new ResponseEntity<Collection<OwnerDTO>>(Owners, HttpStatus.OK);
    }
    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<OwnerDTO> getOwner(@PathVariable("id") Long id) {
        OwnerDTO Owner = ownerService.findOneDTO(id);
        if (Owner != null) {
            return new ResponseEntity<OwnerDTO>(Owner, HttpStatus.OK);
        }

        return new ResponseEntity<OwnerDTO>(HttpStatus.NOT_FOUND);
    }

    //deleteOne, 3.4 for owner
    @DeleteMapping(value = "/{id}")
    public ResponseEntity<OwnerDTO> deleteOwner(@PathVariable("id") Long id) {
        ownerService.delete(id);
        return new ResponseEntity<OwnerDTO>(HttpStatus.NO_CONTENT);
    }
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<OwnerDTO> createOwner(@RequestBody Owner Owner) throws Exception {
        OwnerDTO OwnerDTO = ownerService.create(Owner);
        return new ResponseEntity<OwnerDTO>(OwnerDTO, HttpStatus.CREATED);
    }

    //put
    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<OwnerDTO> updateOwner(@RequestBody Owner Owner, @PathVariable Long id)
            throws Exception {
        Owner OwnerForUpdate = ownerService.findOne(id);
        if (OwnerForUpdate == null) {
            return new ResponseEntity<OwnerDTO>(HttpStatus.NOT_FOUND);
        }
        OwnerForUpdate.copyValues(Owner);
        OwnerDTO updatedOwner = ownerService.update(OwnerForUpdate);
        return new ResponseEntity<OwnerDTO>(updatedOwner, HttpStatus.OK);
    }

    //post with id owner,3.5
    @PostMapping(value = "/{id}/accommodations",consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<AccommodationDTO> createAccommodationByOwner(@PathVariable Long id, @RequestBody Accommodation accommodation) throws Exception {
        AccommodationDTO accommodationDTO = accommodationService.createByOwner(id, accommodation);
        return new ResponseEntity<AccommodationDTO>(accommodationDTO, HttpStatus.CREATED);
    }

    //post without id owner, 3.5
    @PostMapping(value = "/accommodations",consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<AccommodationDTO> createAccommodation(@RequestBody Accommodation accommodation) throws Exception {
        AccommodationDTO accommodationDTO = accommodationService.create(accommodation);
        return new ResponseEntity<AccommodationDTO>(accommodationDTO, HttpStatus.CREATED);
    }

    //3.8 with owners id, view accommodations
    @GetMapping(value = "/{id}/accommodations", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getOwnerAccomodation(@PathVariable("id") Long id) {
        OwnerDTO Owner = ownerService.findOneDTO(id);
        if (Owner != null) {
            Collection<AccommodationDTO> accommodationDTOs=accommodationService.findAllByOwnerDTO(id);
            if(accommodationDTOs!=null){

                return new ResponseEntity<Collection<AccommodationDTO>>(accommodationDTOs, HttpStatus.OK);
            }
            return new ResponseEntity<OwnerDTO>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<OwnerDTO>(HttpStatus.NOT_FOUND);
    }

    //3.8 without owners id, get, pregled smestaja
    @GetMapping(value = "/accommodations", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getOwnerAccommodation() {
        Collection<Accommodation> accommodations = accommodationService.findAll();
        if (accommodations != null){
            return new ResponseEntity<Collection<Accommodation>>(accommodations, HttpStatus.OK);
        }

        return new ResponseEntity<OwnerDTO>(HttpStatus.NOT_FOUND);
    }

    //3.8, update Accommodation
    @PutMapping(value = "/accommodations/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<AccommodationDTO> updateAccommodation(@RequestBody Accommodation accommodation, @PathVariable Long id)
            throws Exception {
        Accommodation accommodationUpdate = accommodationService.findOne(id);
        if (accommodationUpdate == null)
            return new ResponseEntity<AccommodationDTO>(HttpStatus.NOT_FOUND);

        accommodationUpdate.copyValues(accommodation);
        AccommodationDTO updatedAccommodation = accommodationService.update(accommodationUpdate);
        return new ResponseEntity<AccommodationDTO>(updatedAccommodation, HttpStatus.OK);
    }

    //3.9, search and filter accommodations
    @GetMapping(value = "/accommodationsSearch")
    public ResponseEntity<Collection<AccommodationDTO>> getSearchedAccommodations(
            @RequestParam(required = false) String location,
            @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") Date start,
            @RequestParam(required = false)  @DateTimeFormat(pattern = "yyyy-MM-dd")  Date end,
            @RequestParam(required = false) int numPeople){
        Collection<AccommodationDTO> accommodationDTOS = accommodationService.getAccommodationsSearched(start,end,numPeople,location);
        if(accommodationDTOS == null)
            return new ResponseEntity<Collection<AccommodationDTO>>(HttpStatus.NOT_FOUND);
        return ResponseEntity.ok(accommodationDTOS);
    }
    //3.10
    @GetMapping(value="/accommodations")
    public ResponseEntity<Collection<AccommodationDTO>> getAccommodationsDTO() {
        Collection<AccommodationDTO> accommodationDTOS = accommodationService.findAllDTO();
        return new ResponseEntity<Collection<AccommodationDTO>>(accommodationDTOS, HttpStatus.OK);
    }
}
