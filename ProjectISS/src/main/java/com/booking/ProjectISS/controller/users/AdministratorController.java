package com.booking.ProjectISS.controller.users;

import com.booking.ProjectISS.dto.accomodations.AccommodationDTO;
import com.booking.ProjectISS.dto.reviews.ReviewDTO;
import com.booking.ProjectISS.dto.reviews.ReviewDTOComment;
import com.booking.ProjectISS.dto.users.AdministratorDTO;
import com.booking.ProjectISS.dto.users.OwnerDTO;
import com.booking.ProjectISS.dto.users.UserDTO;
import com.booking.ProjectISS.enums.ReviewStatus;
import com.booking.ProjectISS.model.accomodations.Accommodation;
import com.booking.ProjectISS.model.users.Administrator;
import com.booking.ProjectISS.model.users.Guest;
import com.booking.ProjectISS.model.users.Owner;
import com.booking.ProjectISS.model.users.User;
import com.booking.ProjectISS.service.accommodation.AccommodationService;
import com.booking.ProjectISS.service.accommodation.IAccommodationService;
import com.booking.ProjectISS.service.reservations.IReservationService;
import com.booking.ProjectISS.service.reviews.IReviewService;
import com.booking.ProjectISS.service.users.administrator.IAdministratorService;
import com.booking.ProjectISS.service.users.user.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@RestController
@RequestMapping("/api/admin")
@CrossOrigin(origins = "http://localhost:4200")
public class AdministratorController {

    @Autowired
    private IAdministratorService administratorService;
    @Autowired
    private IAccommodationService accommodationService;
    @Autowired
    private IUserService userService;
    @Autowired
    private IReviewService reviewService;
    @Autowired
    private IReservationService reservationService;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasRole('Admin')")
    public ResponseEntity<Collection<AdministratorDTO>> getAdministratorDTO() {
        Collection<AdministratorDTO> administrators = administratorService.findAllDTO();
        return new ResponseEntity<Collection<AdministratorDTO>>(administrators, HttpStatus.OK);
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasRole('Admin')")
    public ResponseEntity<AdministratorDTO> getAdministrator(@PathVariable("id") Long id) {
        AdministratorDTO administrator = administratorService.findOneDTO(id);
        if (administrator != null) {
            return new ResponseEntity<AdministratorDTO>(administrator, HttpStatus.OK);
        }

        return new ResponseEntity<AdministratorDTO>(HttpStatus.NOT_FOUND);
    }

    @GetMapping(value = "/username/{username}",produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasRole('Admin')")
    public ResponseEntity<Administrator> getAdministratorUsername(@PathVariable("username") String username){
        Administrator administrator = administratorService.findUsername(username);
        if(administrator == null){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<Administrator>(administrator, HttpStatus.OK);
    }
    //deleteOne
    @DeleteMapping(value = "/{id}")
    @PreAuthorize("hasRole('Admin')")
    public ResponseEntity<AdministratorDTO> deleteAdministrator(@PathVariable("id") Long id) {
        administratorService.delete(id);
        return new ResponseEntity<AdministratorDTO>(HttpStatus.NO_CONTENT);
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasRole('Admin')")
    public ResponseEntity<AdministratorDTO> createAdministrator(@RequestBody Administrator Administrator) throws Exception {
        AdministratorDTO administratorDTO = administratorService.create(Administrator);
        return new ResponseEntity<AdministratorDTO>(administratorDTO, HttpStatus.CREATED);
    }
    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasRole('Admin')")
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
    @PreAuthorize("hasRole('Admin')")
    public ResponseEntity<Collection<AccommodationDTO>> getAccommodationsDTO() {
        Collection<AccommodationDTO> accommodationDTOS = accommodationService.findAllDTO();
        return new ResponseEntity<Collection<AccommodationDTO>>(accommodationDTOS, HttpStatus.OK);
    }

    @GetMapping(value = "/accomodations/pending", produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasRole('Admin')")
    public ResponseEntity<Collection<AccommodationDTO>> getAccommodationsPendingDTO() {
        List<AccommodationDTO> accommodationPending=new ArrayList<AccommodationDTO>();
        Collection<AccommodationDTO> accommodationDTOS = accommodationService.findAllDTO();

        for(AccommodationDTO a:accommodationDTOS){
            if(!a.isAccepted()){
                accommodationPending.add(a);
            }
        }

        return new ResponseEntity<Collection<AccommodationDTO>>(accommodationPending, HttpStatus.OK);
    }

    @PutMapping(value = "/accomodations/pending/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasRole('Admin')")
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
    @PreAuthorize("hasRole('Admin')")
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

    @PutMapping(value = "/reportsUsers/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasRole('Admin')")
    public ResponseEntity<?> blockUsers(@RequestBody User u, @PathVariable Long id)
            throws Exception {
        User user=userService.findOne(id);
        if (user == null)
            return new ResponseEntity<UserDTO>(HttpStatus.NOT_FOUND);
        if(!user.isReported()){
            return new ResponseEntity<>("User does not have report", HttpStatus.OK);
        }
        user.copyValues(u);
        if(u instanceof Guest){
            reservationService.cancelledAllReservation((Guest) u);
        }
        return new ResponseEntity<UserDTO>(new UserDTO(user), HttpStatus.OK);
    }

    //ova 3
    @GetMapping(value = "/allComments", produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasRole('Admin')")
    public ResponseEntity<Collection<ReviewDTOComment>> allComments() {
        Collection<ReviewDTOComment> reviews = reviewService.findAllDTOComments();
        return new ResponseEntity<Collection<ReviewDTOComment>>(reviews, HttpStatus.OK);
    }

    @GetMapping(value = "/allComments/report", produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasRole('Admin')")
    public ResponseEntity<Collection<ReviewDTOComment>> allCommentsReport() {
        Collection<ReviewDTOComment> newReviews=new ArrayList<>();
        Collection<ReviewDTOComment> reviews = reviewService.findAllDTOComments();
        for(ReviewDTOComment r:reviews){
            if(r.getStatus()== ReviewStatus.REPORTED){
                newReviews.add(r);
            }
        }
        return new ResponseEntity<Collection<ReviewDTOComment>>(newReviews, HttpStatus.OK);
    }

    @DeleteMapping(value = "/allComments/report/{id}")
    @PreAuthorize("hasRole('Admin')")
    public ResponseEntity<ReviewDTO> deleteReview(@PathVariable("id") Long id) {
        reviewService.deleteReport(id);
        return new ResponseEntity<ReviewDTO>(HttpStatus.NO_CONTENT);
    }
}
