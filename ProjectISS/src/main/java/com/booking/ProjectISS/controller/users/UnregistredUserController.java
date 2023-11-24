package com.booking.ProjectISS.controller.users;

import com.booking.ProjectISS.dto.users.RegistrationRequestDTO;
import com.booking.ProjectISS.enums.TypeUser;
import com.booking.ProjectISS.model.users.Guest;
import com.booking.ProjectISS.model.users.Owner;
import com.booking.ProjectISS.service.users.user.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/unregistredUsers")
public class UnregistredUserController {

    @Autowired
    private IUserService userService;

    @PostMapping(value = "/register")
    public ResponseEntity<?> registered(@RequestBody RegistrationRequestDTO registrationRequest){
        RegistrationRequestDTO registrationResponse = userService.register(registrationRequest);
        if(registrationResponse == null){
            return new ResponseEntity<>("User have account", HttpStatus.OK);
        }
        return new ResponseEntity<Owner>(HttpStatus.CREATED);
    }
}
