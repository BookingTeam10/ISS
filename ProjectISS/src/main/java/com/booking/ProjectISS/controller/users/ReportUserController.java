package com.booking.ProjectISS.controller.users;

import com.booking.ProjectISS.dto.reviews.ReviewOwnerDTO;
import com.booking.ProjectISS.dto.users.UserDTO;
import com.booking.ProjectISS.model.reviews.ReviewOwner;
import com.booking.ProjectISS.model.users.ReportUser;
import com.booking.ProjectISS.service.reviews.IReviewService;
import com.booking.ProjectISS.service.users.user.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@RestController
@CrossOrigin
@RequestMapping("/api/reportUser")
public class ReportUserController {

    @Autowired
    private IUserService userService;

    @PostMapping(value = "/{idOwner}/{idGuest}",consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    //@PreAuthorize("hasRole('Guest')")
    public ResponseEntity<ReportUser> createReview(@PathVariable("idOwner") Long idOwner,
                                                       @PathVariable("idGuest") Long idGuest, @RequestBody ReportUser reportUser) throws Exception {
        System.out.println("REPORT USER");
        System.out.println(reportUser);
        ReportUser newReportUser = userService.createReport(reportUser,idOwner,idGuest);
        return new ResponseEntity<>(reportUser, HttpStatus.CREATED);
    }

    @GetMapping(value = "/GO/{idOwner}/{idGuest}", produces = MediaType.APPLICATION_JSON_VALUE)
    //@PreAuthorize("hasRole('Owner')")
    public ResponseEntity<ReportUser> getReportOwner(@PathVariable("idOwner") Long idOwner,@PathVariable("idGuest") Long idGuest) {
        System.out.println("USLO");
        ReportUser reportUser= userService.findGuestReportOwner(idOwner,idGuest);
        System.out.println(reportUser);
        return new ResponseEntity<ReportUser>(reportUser,HttpStatus.OK);
    }

    @GetMapping(value = "/OG/{idOwner}/{idGuest}", produces = MediaType.APPLICATION_JSON_VALUE)
    //@PreAuthorize("hasRole('Owner')")
    public ResponseEntity<ReportUser> getReportGuest(@PathVariable("idOwner") Long idOwner,@PathVariable("idGuest") Long idGuest) {
        System.out.println("USLO1112");
        ReportUser reportUser= userService.findGuestReportGuest(idOwner,idGuest);
        if(reportUser!=null){
            return new ResponseEntity<ReportUser>(reportUser,HttpStatus.OK);
        }
        ReportUser reportUser1=new ReportUser(500L);
        return new ResponseEntity<ReportUser>(reportUser1,HttpStatus.OK);
    }

    @GetMapping( produces = MediaType.APPLICATION_JSON_VALUE)
    //@PreAuthorize("hasRole('Owner')")
    public ResponseEntity<Collection<ReportUser>> getUserReports() {
        Collection<ReportUser> reportUser = userService.findAllReports();
        System.out.println("REPORT USER SIZE");
        System.out.println(reportUser.size());
        return new ResponseEntity<Collection<ReportUser>>(reportUser,HttpStatus.OK);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<UserDTO> deleteUser(@PathVariable("id") Long id) {
        userService.deleteReport(id);
        return new ResponseEntity<UserDTO>(HttpStatus.NO_CONTENT);
    }
}
