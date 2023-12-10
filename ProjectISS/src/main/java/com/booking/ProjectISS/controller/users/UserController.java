package com.booking.ProjectISS.controller.users;

import com.booking.ProjectISS.dto.reviews.ReviewDTO;
import com.booking.ProjectISS.dto.users.*;
import com.booking.ProjectISS.model.reviews.Review;
import com.booking.ProjectISS.model.users.Administrator;
import com.booking.ProjectISS.model.users.Token;
import com.booking.ProjectISS.model.users.User;
import com.booking.ProjectISS.security.jwt.JwtTokenUtil;
import com.booking.ProjectISS.service.users.administrator.IAdministratorService;
import com.booking.ProjectISS.service.users.guest.IGuestService;
import com.booking.ProjectISS.service.users.owner.IOwnerService;
import com.booking.ProjectISS.service.users.user.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
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

    //3.22 i promeniti status u owneru da je REPORT=TRUE
    @PostMapping(value = "/{idR}/report/{id}",consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ReviewDTO> createReport(@PathVariable("idR") Long idR, @PathVariable("id") Long id, @RequestBody Review review) throws Exception {
        ReviewDTO reviewDTO=new ReviewDTO();
        //ReviewDTO reviewDTO = reviewService.createByUser(idR,id, review);
        return new ResponseEntity<ReviewDTO>(reviewDTO, HttpStatus.CREATED);
    }

    @PostMapping("/login")
    @CrossOrigin(origins = "http://localhost:4200")
    public Token logIn(@RequestBody LoginDTO login) {
        Token tokenJWT=new Token();
        UsernamePasswordAuthenticationToken authReq = new UsernamePasswordAuthenticationToken(login.getEmail(),login.getPassword());
        Authentication auth = authenticationManager.authenticate(authReq);

        SecurityContext sc = SecurityContextHolder.getContext();
        sc.setAuthentication(auth);

        String token = jwtTokenUtil.generateToken(login.getEmail());
        login.setJwt(token);
        tokenJWT.setJwt(token);
        return tokenJWT;
    }
}
