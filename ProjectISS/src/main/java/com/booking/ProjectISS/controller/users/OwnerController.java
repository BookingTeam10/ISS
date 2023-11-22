package com.booking.ProjectISS.controller.users;

import com.booking.ProjectISS.dto.accomodations.AccommodationDTO;
import com.booking.ProjectISS.dto.users.GuestDTO;
import com.booking.ProjectISS.dto.users.OwnerDTO;
import com.booking.ProjectISS.dto.users.UserDTO;
import com.booking.ProjectISS.model.accomodations.Accommodation;
import com.booking.ProjectISS.model.users.Owner;
import com.booking.ProjectISS.service.accommodation.IAccommodationService;
import com.booking.ProjectISS.service.users.owner.IOwnerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

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

    //getOne

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<OwnerDTO> getOwner(@PathVariable("id") Long id) {
        OwnerDTO Owner = ownerService.findOneDTO(id);
        if (Owner != null) {
            return new ResponseEntity<OwnerDTO>(Owner, HttpStatus.OK);
        }

        return new ResponseEntity<OwnerDTO>(HttpStatus.NOT_FOUND);
    }

    //deleteOne
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
        if (OwnerForUpdate == null)
            return new ResponseEntity<OwnerDTO>(HttpStatus.NOT_FOUND);

        OwnerForUpdate.copyValues(Owner);
        OwnerDTO updatedOwner = ownerService.update(OwnerForUpdate);
        return new ResponseEntity<OwnerDTO>(updatedOwner, HttpStatus.OK);
    }

    //part 3.8, get owners accomodation
    @GetMapping(value = "/{id}/accommodations", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getOwnerAccomodation(@PathVariable("id") Long id) {
        OwnerDTO Owner = ownerService.findOneDTO(id);
        if (Owner != null) {
            Collection<AccommodationDTO> accommodationDTOs=accommodationService.findAllByOwnerDTO(id);
            if(accommodationDTOs!=null)
                return new ResponseEntity<Collection<AccommodationDTO>>(accommodationDTOs, HttpStatus.OK);
            return new ResponseEntity<OwnerDTO>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<OwnerDTO>(HttpStatus.NOT_FOUND);
    }

    //3.5, create accommodation with id
    @PostMapping(value = "/{id}/accommodations",consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<AccommodationDTO> createAccommodationByOwner(@PathVariable Long id, @RequestBody Accommodation accommodation) throws Exception {
        AccommodationDTO accommodationDTO = accommodationService.createByOwner(id, accommodation);
        return new ResponseEntity<AccommodationDTO>(accommodationDTO, HttpStatus.CREATED);
    }

//////    //3.8, update accommodation
//    @PutMapping(value = "/{id1}/accommodations/{id2}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
//    public ResponseEntity<AccommodationDTO> updateAccommodationOwner(@RequestBody Accommodation accommodation, @PathVariable Long id1,@PathVariable Long id2)
//            throws Exception {
//        OwnerDTO owner = ownerService.findOneDTO(id1);
//        if (owner != null) {
//            AccommodationDTO accommodationDTO=accommodationService.findOneOwnerDTO(id1,id2);
//            if(accommodationDTO!=null)
//                return new ResponseEntity<Collection<AccommodationDTO>>(accommodationDTOs, HttpStatus.OK);
//            return new ResponseEntity<AccommodationDTO>(HttpStatus.NOT_FOUND);
//        }
//
//        return new ResponseEntity<AccommodationDTO>(HttpStatus.NOT_FOUND);
//    }


}
