package com.booking.ProjectISS.controller.users;

import com.booking.ProjectISS.dto.accomodations.AccommodationDTO;
import com.booking.ProjectISS.dto.users.AdministratorDTO;
import com.booking.ProjectISS.dto.users.OwnerDTO;
import com.booking.ProjectISS.dto.users.UserDTO;
import com.booking.ProjectISS.model.accomodations.Accommodation;
import com.booking.ProjectISS.model.users.Administrator;
import com.booking.ProjectISS.model.users.Owner;
import com.booking.ProjectISS.model.users.User;
import com.booking.ProjectISS.service.accommodation.AccommodationService;
import com.booking.ProjectISS.service.accommodation.IAccommodationService;
import com.booking.ProjectISS.service.users.administrator.IAdministratorService;
import com.booking.ProjectISS.service.users.user.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@RestController
@RequestMapping("/api/admin")
public class AdministratorController {

    @Autowired
    private IAdministratorService administratorService;
    @Autowired
    private IAccommodationService accommodationService;
    @Autowired
    private IUserService userService;

    //getAll
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Collection<AdministratorDTO>> getAdministratorDTO() {
        Collection<AdministratorDTO> administrators = administratorService.findAllDTO();
        return new ResponseEntity<Collection<AdministratorDTO>>(administrators, HttpStatus.OK);
    }

    //getOne
    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<AdministratorDTO> getAdministrator(@PathVariable("id") Long id) {
        AdministratorDTO administrator = administratorService.findOneDTO(id);
        if (administrator != null) {
            return new ResponseEntity<AdministratorDTO>(administrator, HttpStatus.OK);
        }

        return new ResponseEntity<AdministratorDTO>(HttpStatus.NOT_FOUND);
    }

    //deleteOne
    @DeleteMapping(value = "/{id}")
    public ResponseEntity<AdministratorDTO> deleteAdministrator(@PathVariable("id") Long id) {
        administratorService.delete(id);
        return new ResponseEntity<AdministratorDTO>(HttpStatus.NO_CONTENT);
    }

    //post
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<AdministratorDTO> createAdministrator(@RequestBody Administrator Administrator) throws Exception {
        AdministratorDTO administratorDTO = administratorService.create(Administrator);
        return new ResponseEntity<AdministratorDTO>(administratorDTO, HttpStatus.CREATED);
    }

    //put
    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<AdministratorDTO> updateAdmin(@RequestBody Administrator administrator, @PathVariable Long id)
            throws Exception {
        Administrator administratorForUpdate = administratorService.findOne(id);
        if (administratorForUpdate == null) {
            return new ResponseEntity<AdministratorDTO>(HttpStatus.NOT_FOUND);
        }
        administratorForUpdate.copyValues(administrator);
        AdministratorDTO updatedAdministrator = administratorService.update(administratorForUpdate);
        return new ResponseEntity<AdministratorDTO>(updatedAdministrator, HttpStatus.OK);
    }

    @GetMapping(value = "/accomodations", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Collection<AccommodationDTO>> getAccommodationsDTO() {
        Collection<AccommodationDTO> accommodationDTOS = accommodationService.findAllDTO();
        return new ResponseEntity<Collection<AccommodationDTO>>(accommodationDTOS, HttpStatus.OK);
    }

    @GetMapping(value = "/accomodations/pending", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Collection<AccommodationDTO>> getAccommodationsPendingDTO() {
        List<AccommodationDTO> accommodationPending=new ArrayList<AccommodationDTO>();
        Collection<AccommodationDTO> accommodationDTOS = accommodationService.findAllDTO();

        //Seljacki nacin za trazenje pendinga, uzeti sve i samo naci ako nije odobren

        for(AccommodationDTO a:accommodationDTOS){
            if(!a.isAccepted()){
                accommodationPending.add(a);
            }
        }

        return new ResponseEntity<Collection<AccommodationDTO>>(accommodationPending, HttpStatus.OK);
    }

    @PutMapping(value = "/accomodations/pending/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<AccommodationDTO> updateAccomodationPeding(@RequestBody Accommodation accommodation, @PathVariable Long id)
            throws Exception {
        Accommodation accommodationUp=accommodationService.findOne(id);
        if (accommodationUp == null) {
            return new ResponseEntity<AccommodationDTO>(HttpStatus.NOT_FOUND);
        }
        accommodationUp.copyValues(accommodation);
        return new ResponseEntity<AccommodationDTO>(new AccommodationDTO(accommodationUp), HttpStatus.OK);
    }

    @GetMapping(value = "/reportsUsers", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Collection<UserDTO>> getReportedUsers() {
        List<UserDTO> userReport=new ArrayList<UserDTO>();
        Collection<UserDTO> users = userService.findAllDTO();

        for(UserDTO a:users){
            if(a.isReported() && !a.isBlocked()){
                userReport.add(a);
            }
        }
        return new ResponseEntity<Collection<UserDTO>>(userReport, HttpStatus.OK);
    }


    //srediti i napraviti novi PUT
    @PutMapping(value = "/reportsOwner/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<UserDTO> blockUsers(@RequestBody Owner o, @PathVariable Long id)
            throws Exception {
        UserDTO userDTO=userService.findOneDTO(id);
        if (userDTO == null) {
            return new ResponseEntity<UserDTO>(HttpStatus.NOT_FOUND);
        }
        userDTO.copyValues(o);
        return new ResponseEntity<UserDTO>(userDTO, HttpStatus.OK);
    }
}
