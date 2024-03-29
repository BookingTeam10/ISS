package com.booking.ProjectISS.controller.users;
import com.booking.ProjectISS.dto.reservations.ReservationDTO;
import com.booking.ProjectISS.dto.reviews.ReviewDTO;
import com.booking.ProjectISS.enums.ReservationStatus;
import com.booking.ProjectISS.model.reservations.Reservation;
import com.booking.ProjectISS.model.reviews.Report;
import com.booking.ProjectISS.model.reviews.ReportAccommodation;
import com.booking.ProjectISS.model.reviews.Review;
import com.booking.ProjectISS.model.users.Guest;
import com.booking.ProjectISS.model.users.Owner;
import com.booking.ProjectISS.service.reservations.IReservationService;
import com.booking.ProjectISS.service.reviews.IReviewService;
import com.booking.ProjectISS.enums.ReservationStatus;
import com.booking.ProjectISS.model.reservations.Reservation;
import com.booking.ProjectISS.model.users.Owner;
import com.booking.ProjectISS.service.reservations.IReservationService;
import com.booking.ProjectISS.service.users.guest.IGuestService;
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
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Collection;
import java.util.Date;
import java.util.*;

@RestController
@RequestMapping("/api/owners")
@CrossOrigin
public class OwnerController {

    @Autowired
    private IOwnerService ownerService;
    @Autowired
    private IReservationService reservationService;
    @Autowired
    private IGuestService guestService;
    @Autowired
    private IAccommodationService accommodationService;
    @Autowired
    private IReviewService reviewService;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Collection<OwnerDTO>> getOwnerDTO() {
        Collection<OwnerDTO> Owners = ownerService.findAllDTO();
        return new ResponseEntity<Collection<OwnerDTO>>(Owners, HttpStatus.OK);
    }
    @GetMapping(value = "/{id}/requests", produces = MediaType.APPLICATION_JSON_VALUE)
   // @PreAuthorize("hasRole('Owner')")
    public ResponseEntity<Collection<ReservationDTO>> getGuestReservations(
            @PathVariable("id") Long id,
            @RequestParam(name = "location", required = false) String location,
            @RequestParam(name = "date", required = false) String date,
            @RequestParam(name = "status", required = false) ReservationStatus status){

        List<Reservation> reservations = reservationService.getOwnerReservations(ownerService.findOne(id).getId());
        if(location != null || date != null){
            reservations = reservationService.searchReservations(reservations, location, date);
        }
        if(status != null){
            reservations = reservationService.filterReservations(reservations, status);
        }

        return new ResponseEntity<Collection<ReservationDTO>>(reservationService.getReservationsDTO(reservations),
                HttpStatus.OK);
    }

    @GetMapping(value = "/username/{username}", produces = MediaType.APPLICATION_JSON_VALUE)
    //@PreAuthorize("hasRole('Owner')")
    public ResponseEntity<Owner> getOwnerUsername(@PathVariable("username") String username){
        return new ResponseEntity<Owner>(ownerService.findUsername(username), HttpStatus.OK);
    }
    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    //@PreAuthorize("hasRole('Owner')")
    public ResponseEntity<OwnerDTO> getOwner(@PathVariable("id") Long id) {
        OwnerDTO Owner = ownerService.findOneDTO(id);
        if (Owner != null) {
            return new ResponseEntity<OwnerDTO>(Owner, HttpStatus.OK);
        }

        return new ResponseEntity<OwnerDTO>(HttpStatus.NOT_FOUND);
    }

    @GetMapping(value = "/full/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Owner> getOwnerFull(@PathVariable("id") Long id) {
        Owner Owner = ownerService.findOne(id);
        System.out.println(Owner);
        if (Owner != null) {
            return new ResponseEntity<Owner>(Owner, HttpStatus.OK);
        }
        return new ResponseEntity<Owner>(HttpStatus.NOT_FOUND);
    }

    @DeleteMapping(value = "/{id}")
    //@PreAuthorize("hasRole('Owner')")
    public ResponseEntity<OwnerDTO> deleteOwner(@PathVariable("id") Long id) {
        List<Reservation> ownerReservations = reservationService.getOwnerReservations(id).stream().filter(reservation ->
            reservation.getStatus() == ReservationStatus.ACCEPTED
        ).toList();

        if(!ownerReservations.isEmpty()){return new ResponseEntity<>(HttpStatus.FORBIDDEN);}
        ownerService.delete(id);
        accommodationService.deleteAllByOwner(id);
        return new ResponseEntity<OwnerDTO>(HttpStatus.NO_CONTENT);
    }
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    //@PreAuthorize("hasRole('Owner')")
    public ResponseEntity<OwnerDTO> createOwner(@RequestBody Owner Owner) throws Exception {
        OwnerDTO OwnerDTO = ownerService.create(Owner);
        return new ResponseEntity<OwnerDTO>(OwnerDTO, HttpStatus.CREATED);
    }

    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    //@PreAuthorize("hasRole('Owner')")
    public ResponseEntity<OwnerDTO> updateOwner(@RequestBody Owner Owner, @PathVariable Long id)
            throws Exception {
        Owner OwnerForUpdate = ownerService.findOne(id);
        if (OwnerForUpdate == null) {
            return new ResponseEntity<OwnerDTO>(HttpStatus.NOT_FOUND);
        }

        OwnerDTO updatedOwner = ownerService.update(Owner);
        return new ResponseEntity<OwnerDTO>(updatedOwner, HttpStatus.OK);
    }

    @PostMapping(value = "/{id}/accommodations",consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    //@PreAuthorize("hasRole('Owner')")
    public ResponseEntity<AccommodationDTO> createAccommodationByOwner(@PathVariable Long id, @RequestBody Accommodation accommodation) throws Exception {
        AccommodationDTO accommodationDTO = accommodationService.createByOwner(id, accommodation);
        return new ResponseEntity<AccommodationDTO>(accommodationDTO, HttpStatus.CREATED);
    }

    @PostMapping(value = "/accommodations",consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    //@PreAuthorize("hasRole('Owner')")
    public ResponseEntity<AccommodationDTO> createAccommodation(@RequestBody Accommodation accommodation) throws Exception {
        AccommodationDTO accommodationDTO = accommodationService.create(accommodation);
        return new ResponseEntity<AccommodationDTO>(accommodationDTO, HttpStatus.CREATED);
    }

    @GetMapping(value = "/{id}/accommodations", produces = MediaType.APPLICATION_JSON_VALUE)
    //@PreAuthorize("hasRole('Owner')")
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

    @PutMapping(value = "/accommodations/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    //@PreAuthorize("hasRole('Owner')")
    public ResponseEntity<AccommodationDTO> updateAccommodation(@RequestBody Accommodation accommodation, @PathVariable Long id)
            throws Exception {
        Accommodation accommodationUpdate = accommodationService.findOne(id);
        if (accommodationUpdate == null)
            return new ResponseEntity<AccommodationDTO>(HttpStatus.NOT_FOUND);

        accommodationUpdate.copyValues(accommodation);
        AccommodationDTO updatedAccommodation = accommodationService.update(accommodationUpdate);
        return new ResponseEntity<AccommodationDTO>(updatedAccommodation, HttpStatus.OK);
    }

    @PutMapping(value = "/comment/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    //@PreAuthorize("hasRole('Owner')")
    public ResponseEntity<ReviewDTO> updateComment(@RequestBody Review review, @PathVariable Long id)
            throws Exception {
        ReviewDTO reviewDTOForUpdate = reviewService.findOneDTO(id);
        if (reviewDTOForUpdate == null) {
            return new ResponseEntity<ReviewDTO>(HttpStatus.NOT_FOUND);
        }
        reviewDTOForUpdate.copyValues(review);
        ReviewDTO reviewDTO = reviewService.update(reviewDTOForUpdate);
        return new ResponseEntity<ReviewDTO>(reviewDTO, HttpStatus.OK);
    }

    @GetMapping(value = "/accommodations", produces = MediaType.APPLICATION_JSON_VALUE)
    //@PreAuthorize("hasRole('Owner')")
    public ResponseEntity<?> getOwnerAccommodation() {
        Collection<Accommodation> accommodations = accommodationService.findAll();
        if (accommodations != null){
            return new ResponseEntity<Collection<Accommodation>>(accommodations, HttpStatus.OK);
        }

        return new ResponseEntity<OwnerDTO>(HttpStatus.NOT_FOUND);
    }

    @GetMapping(value = "/accommodationsSearch")
    //@PreAuthorize("hasRole('Owner')")
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
    @GetMapping(value="/accommodations")
   // @PreAuthorize("hasRole('Owner')")
    public ResponseEntity<Collection<AccommodationDTO>> getAccommodationsDTO() {
        Collection<AccommodationDTO> accommodationDTOS = accommodationService.findAllDTO();
        return new ResponseEntity<Collection<AccommodationDTO>>(accommodationDTOS, HttpStatus.OK);
    }

    @PutMapping(value = "{idOwner}/reportGuest/{idGuest}")
    //@PreAuthorize("hasRole('Owner')")
    public ResponseEntity<?> reportOwner(@PathVariable("idGuest") Long idGuest,@PathVariable("idOwner") Long idOwner) {
        boolean canReport=ownerService.reportGuest(idGuest,idOwner);
        if(canReport){
            Guest guest=guestService.findOne(idGuest);
            guest.setReported(true);
            return new ResponseEntity<>("Guest can report", HttpStatus.ACCEPTED);
        }
        return new ResponseEntity<>("Guest cant report", HttpStatus.BAD_REQUEST);
    }

    @GetMapping(value = "/{idOwner}/requestsReservations", produces = MediaType.APPLICATION_JSON_VALUE)
    //@PreAuthorize("hasRole('Owner')")
    public ResponseEntity<Collection<ReservationDTO>> getOwnersRequests(@PathVariable Long idOwner) {
        Collection<ReservationDTO> ownerReservations = reservationService.getOwnersRequests(idOwner);
        return new ResponseEntity<Collection<ReservationDTO>>(ownerReservations, HttpStatus.OK);
    }

    @GetMapping(value = "/requestsSearch", produces = MediaType.APPLICATION_JSON_VALUE)
    //@PreAuthorize("hasRole('Owner')")
    public ResponseEntity<Collection<ReservationDTO>> searchedRequests(@RequestParam(required = false) String type,
                                                                       @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") Date start,
                                                                       @RequestParam(required = false)  @DateTimeFormat(pattern = "yyyy-MM-dd")  Date end,
                                                                       @RequestParam(required = false) String nameAccommodation,
                                                                       @RequestParam(required = false) Long idOwner
    ) {
        Collection<ReservationDTO> reservationDTOS = new ArrayList<>();
        if(type.equals("") && start == null && end==null && nameAccommodation.equals("")){
             reservationDTOS = reservationService.getOwnersRequests(idOwner);
        }else {
             reservationDTOS = reservationService.searchedRequests(type,start,end,nameAccommodation,idOwner);
        }

        return new ResponseEntity<Collection<ReservationDTO>>(reservationDTOS, HttpStatus.OK);
    }

    @GetMapping(value = "/{id}/report", produces = MediaType.APPLICATION_JSON_VALUE)
    //@PreAuthorize("hasRole('Owner')")
    public ResponseEntity<Collection<Report>> getOwnerReports(@PathVariable("id") Long id,@RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") Date start,
                                                                               @RequestParam(required = false)  @DateTimeFormat(pattern = "yyyy-MM-dd")  Date end){

        Collection<Report> reports = new ArrayList<>();
        Collection<Accommodation> accommodations = accommodationService.findAllByOwner(id);

        for(Accommodation accommodation:accommodations){
            Collection<ReservationDTO> reservationDTOS = reservationService.findByAccommodation(accommodation.getId());
            reports.add(new Report(accommodation,reservationDTOS.size(),reservationService.totalPrice(reservationDTOS)));
        }

        return new ResponseEntity<Collection<Report>>(reports, HttpStatus.OK);
    }
    @GetMapping(value = "/{idAccommodation}/reportYear", produces = MediaType.APPLICATION_JSON_VALUE)
    //@PreAuthorize("hasRole('Owner')")
    public ResponseEntity<ReportAccommodation> getOwnerReportYear(@PathVariable("idAccommodation") Long id){
        ReportAccommodation report = new ReportAccommodation(new Accommodation(id));
        Collection<ReservationDTO> reservationDTOS = reservationService.findByAccommodation(id);

        for(ReservationDTO reservation: reservationDTOS){
            HashMap map = report.getMap();
            int month = reservation.getStartDate().getMonth();
            if (map != null) {
                if (map.containsKey(month)) {
                    ArrayList<Integer> value = (ArrayList<Integer>) map.get(month);

                    if (value != null) {
                        int profit = value.get(0);
                        int number = value.get(1);
                        profit += reservation.getTotalPrice();
                        number += 1;
                        ArrayList<Integer> newValue = new ArrayList<>();
                        newValue.add(profit);
                        newValue.add(number);

                        map.put(month, newValue);
                    }
                }
            }
        }
        return new ResponseEntity<ReportAccommodation>(report, HttpStatus.OK);
    }

    @GetMapping(value = "/accommodations/{idOwner}", produces = MediaType.APPLICATION_JSON_VALUE)
    //@PreAuthorize("hasRole('Owner')")
    public ResponseEntity<Collection<Accommodation>> getAccommodationByOwner(@PathVariable Long idOwner){
        Collection<Accommodation> accommodations=accommodationService.findAllByOwner(idOwner);
        return new ResponseEntity<Collection<Accommodation>>(accommodations, HttpStatus.OK);
    }

    @GetMapping(value = "/requestsSearchMobile", produces = MediaType.APPLICATION_JSON_VALUE)
    //@PreAuthorize("hasRole('Owner')")
    public ResponseEntity<Collection<ReservationDTO>> searchedRequestsMobile(@RequestParam(required = false) String type,
                                                                       @RequestParam(required = false) String start,
                                                                       @RequestParam(required = false)  String end,
                                                                       @RequestParam(required = false) String nameAccommodation,
                                                                       @RequestParam(required = false) Long idOwner
    ) throws ParseException {
        Date startDate = null;
        Date endDate = null;
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd"); // Format datuma

        if (!start.equals("")){
            startDate = formatter.parse(start);
        }
        if(!end.equals("")){
            endDate = formatter.parse(end);
        }
        Collection<ReservationDTO> reservationDTOS = new ArrayList<>();
        if(type.equals("") && start == null && end==null && nameAccommodation.equals("")){
            reservationDTOS = reservationService.getOwnersRequests(idOwner);
        }else {
            reservationDTOS = reservationService.searchedRequests(type,startDate,endDate,nameAccommodation,idOwner);
        }

        return new ResponseEntity<Collection<ReservationDTO>>(reservationDTOS, HttpStatus.OK);
    }
}
