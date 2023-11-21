package com.booking.ProjectISS.controller.users;

import com.booking.ProjectISS.dto.users.AdministratorDTO;
import com.booking.ProjectISS.model.users.Administrator;
import com.booking.ProjectISS.service.users.administrator.IAdministratorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@RestController
@RequestMapping("/api/admin")
public class AdministratorController {

    @Autowired
    private IAdministratorService administratorService;

    //getAll
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Collection<AdministratorDTO>> getAdministratorDTO() {
        Collection<AdministratorDTO> administrators = administratorService.findAllDTO();
        return new ResponseEntity<Collection<AdministratorDTO>>(administrators, HttpStatus.OK);
    }

//without DTO

//    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
//    public ResponseEntity<Collection<Administrator>> getAdministrator() {
//        Collection<Administrator> administrators = AdministratorService.findAll();
//        return new ResponseEntity<Collection<Administrator>>(administrators, HttpStatus.OK);
//    }

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
    public ResponseEntity<AdministratorDTO> updateGreeting(@RequestBody Administrator administrator, @PathVariable Long id)
            throws Exception {
        Administrator administratorForUpdate = administratorService.findOne(id);
        if (administratorForUpdate == null) {
            return new ResponseEntity<AdministratorDTO>(HttpStatus.NOT_FOUND);
        }
        administratorForUpdate.copyValues(administrator);
        AdministratorDTO updatedAdministrator = administratorService.update(administratorForUpdate);
        return new ResponseEntity<AdministratorDTO>(updatedAdministrator, HttpStatus.OK);
    }
}
