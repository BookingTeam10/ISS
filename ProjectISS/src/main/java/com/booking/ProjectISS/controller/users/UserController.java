package com.booking.ProjectISS.controller.users;

import com.booking.ProjectISS.dto.reviews.ReviewDTO;
import com.booking.ProjectISS.dto.users.*;
import com.booking.ProjectISS.model.reviews.Review;
import com.booking.ProjectISS.model.users.Administrator;
import com.booking.ProjectISS.model.users.Token;
import com.booking.ProjectISS.model.users.User;
import com.booking.ProjectISS.security.jwt.JwtTokenUtil;
import com.booking.ProjectISS.service.users.administrator.IAdministratorService;
import com.booking.ProjectISS.service.users.guest.GuestService;
import com.booking.ProjectISS.service.users.guest.IGuestService;
import com.booking.ProjectISS.service.users.owner.IOwnerService;
import com.booking.ProjectISS.service.users.user.IUserService;
import com.booking.ProjectISS.service.users.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private IUserService userService;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    public UserDetailsService userDetailsService;

    @Autowired
    private IGuestService guestService;

    @Autowired
    private IOwnerService ownerService;

    @Autowired
    private IAdministratorService administratorService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

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

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<UserDTO> deleteUser(@PathVariable("id") Long id) {
        userService.delete(id);
        guestService.delete(id);
        ownerService.delete(id);
        return new ResponseEntity<UserDTO>(HttpStatus.NO_CONTENT);
    }
    @PostMapping(value = "/{idR}/report/{id}",consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ReviewDTO> createReport(@PathVariable("idR") Long idR, @PathVariable("id") Long id, @RequestBody Review review) throws Exception {
        ReviewDTO reviewDTO=new ReviewDTO();
        //ReviewDTO reviewDTO = reviewService.createByUser(idR,id, review);
        return new ResponseEntity<ReviewDTO>(reviewDTO, HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public Token login(@RequestBody LoginDTO login) {
        System.out.println("LOGIN");
        System.out.println(login);
        System.out.println(guestService.findAll());
        try {
            UsernamePasswordAuthenticationToken authReq =
                    new UsernamePasswordAuthenticationToken(login.getEmail(), login.getPassword());
            Authentication auth = authenticationManager.authenticate(authReq);

            SecurityContext sc = SecurityContextHolder.getContext();
            sc.setAuthentication(auth);

            UserDetails userDetails = userDetailsService.loadUserByUsername(login.getEmail());
            System.out.println("userDetails");
            System.out.println(userDetails);
            boolean activation=userService.findActivation(userDetails.getUsername());
            if(!activation){
                Token tokenJWT = new Token();
                tokenJWT.setJwt("NEUSPESNO");
                return tokenJWT;
            }
            String token = jwtTokenUtil.generateToken(userDetails);
            Token tokenJWT = new Token();
            tokenJWT.setJwt(token);

            return tokenJWT;
        } catch (AuthenticationException e) {
            System.out.println("Authentication failed: " + e.getMessage());
            Token tokenJWT = new Token();
            tokenJWT.setJwt("NEUSPESNO");
            return tokenJWT;
        }
    }

    @PutMapping(value = "/change-password/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> changePassword(@PathVariable("id") Long id, @RequestBody PasswordDTO changePasswordDTO) throws Exception {

        User user = userService.findOne(id);
        System.out.println("PRE ENC PASS    " + changePasswordDTO.getPassword());
//        passwordEncoder.encode(changePasswordDTO.getPassword())
        user.setPassword(passwordEncoder.encode(changePasswordDTO.getPassword()));
         userService.updatePassword(user);

        return new ResponseEntity<String>(HttpStatus.OK);
    }

    @GetMapping("/exists/{username}")
    public boolean doesUsernameExist(@PathVariable String username) {
        return userService.doesUsernameExist(username);
    }
}
