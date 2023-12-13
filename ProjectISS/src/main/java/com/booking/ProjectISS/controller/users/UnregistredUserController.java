package com.booking.ProjectISS.controller.users;

import com.booking.ProjectISS.dto.users.RegistrationRequestDTO;
import com.booking.ProjectISS.enums.TypeUser;
import com.booking.ProjectISS.model.users.Guest;
import com.booking.ProjectISS.model.users.Owner;
import com.booking.ProjectISS.model.users.User;
import com.booking.ProjectISS.security.jwt.JwtTokenUtil;
import com.booking.ProjectISS.service.users.EmailService;
import com.booking.ProjectISS.service.users.user.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.UUID;

@RestController
@RequestMapping("/api")
public class UnregistredUserController {

    @Autowired
    private IUserService userService;

    @Autowired
    private EmailService emailService;

    @PostMapping(value = "/register")
    @CrossOrigin(origins = "http://localhost:4200")
    public ResponseEntity<User> registered(@RequestBody RegistrationRequestDTO registrationRequest){
        User user = userService.register(registrationRequest);
        if (user != null) {
            return ResponseEntity.status(HttpStatus.CREATED).body(user);
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(user);
        }
    }
    @PostMapping("/activate/{code}")
    @CrossOrigin(origins = "http://localhost:4200")
    public ResponseEntity<String> activateUser(@PathVariable String code) {
        System.out.println("USLO ACTIVATE");
        User isActivated = userService.activateUser(code);
        if (isActivated!=null) {
            System.out.println("AKT");
            return ResponseEntity.ok("Aktivacija uspešna");
        }
        System.out.println("NEAKT");
        return ResponseEntity.badRequest().body("Aktivacioni kod nije validan");
    }
}
