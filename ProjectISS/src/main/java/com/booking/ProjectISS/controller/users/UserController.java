package com.booking.ProjectISS.controller.users;

import com.booking.ProjectISS.dto.users.GuestDTO;
import com.booking.ProjectISS.dto.users.OwnerDTO;
import com.booking.ProjectISS.dto.users.UserDTO;
import com.booking.ProjectISS.service.users.guest.IGuestService;
import com.booking.ProjectISS.service.users.owner.IOwnerService;
import com.booking.ProjectISS.service.users.user.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private IUserService userService;

    @Autowired
    private IGuestService guestService;

    @Autowired
    private IOwnerService ownerService;

    //getAll
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Collection<UserDTO>> getUserDTO() {
        Collection<UserDTO> users = userService.findAllDTO();
        Collection<GuestDTO> guests = guestService.findAllDTO();
        Collection<OwnerDTO> owners=ownerService.findAllDTO();
        for (GuestDTO guest : guests) {
            boolean alreadyExists = false;
            for (UserDTO user : users) {
                if (user.getId().equals(guest.getId())) {
                    alreadyExists = true;
                    break;
                }
            }
            if (!alreadyExists) {
                UserDTO userFromGuest = new UserDTO(guest);
                users.add(userFromGuest);
            }
        }
        for (OwnerDTO owner : owners) {
            boolean alreadyExists = false;
            for (UserDTO user : users) {
                if (user.getId().equals(owner.getId())) {
                    alreadyExists = true;
                    break;
                }
            }
            if (!alreadyExists) {
                UserDTO userFromOwner = new UserDTO(owner);
                users.add(userFromOwner);
            }
        }
        return new ResponseEntity<Collection<UserDTO>>(users, HttpStatus.OK);
    }
    //getOne

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<UserDTO> getUser(@PathVariable("id") Long id) {
        UserDTO user = userService.findOneDTO(id);
        GuestDTO guest=guestService.findOneDTO(id);
        OwnerDTO owner=ownerService.findOneDTO(id);
        if (user != null) {
            return new ResponseEntity<UserDTO>(user, HttpStatus.OK);
        }
        if (guest != null) {
            return new ResponseEntity<UserDTO>(new UserDTO(guest), HttpStatus.OK);
        }
        if (owner != null) {
            return new ResponseEntity<UserDTO>(new UserDTO(owner), HttpStatus.OK);
        }
        return new ResponseEntity<UserDTO>(HttpStatus.NOT_FOUND);
    }

    //deleteOne
    @DeleteMapping(value = "/{id}")
    public ResponseEntity<UserDTO> deleteUser(@PathVariable("id") Long id) {
        userService.delete(id);
        guestService.delete(id);
        ownerService.delete(id);
        return new ResponseEntity<UserDTO>(HttpStatus.NO_CONTENT);
    }
}
