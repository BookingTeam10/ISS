package com.booking.ProjectISS.controller.notifications;


import com.booking.ProjectISS.dto.notifications.NotificationDTO;
import com.booking.ProjectISS.enums.NotificationStatus;
import com.booking.ProjectISS.model.notifications.Notification;
import com.booking.ProjectISS.model.notifications.NotificationUserVisible;
import com.booking.ProjectISS.model.notifications.NotificationVisible;
import com.booking.ProjectISS.model.users.Guest;
import com.booking.ProjectISS.model.users.Owner;
import com.booking.ProjectISS.service.notifications.INotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;

import java.awt.*;
import java.util.Collection;

@RestController
@RequestMapping("/api/notifications")
@CrossOrigin
public class NotificationController {
    @Autowired
    private INotificationService notificationService;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    //@PreAuthorize("hasAnyRole( 'Owner', 'Guest')")
    public ResponseEntity<Collection<NotificationDTO>> getNotificationDTO(){
        return new ResponseEntity<Collection<NotificationDTO>>(notificationService.findAllDTO(), HttpStatus.OK);
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    //@PreAuthorize("hasAnyRole( 'Owner', 'Guest')")
    public ResponseEntity<NotificationDTO> getNotification(@PathVariable("id") Long id) {
        NotificationDTO notificationDTO = notificationService.findOneDTO(id);
        if (notificationDTO != null) {
            return new ResponseEntity<>(notificationDTO, HttpStatus.OK);
        }

        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

//    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
//    //@PreAuthorize("hasAnyRole( 'Owner', 'Guest')")
//    public ResponseEntity<NotificationDTO> createNotification(@RequestBody Notification notification) throws Exception {
//        NotificationDTO notificationDTO = notificationService.create(notification);
//        return new ResponseEntity<>(notificationDTO, HttpStatus.CREATED);
//    }

    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
   // @PreAuthorize("hasAnyRole( 'Administrator','Owner', 'Guest')")
    public ResponseEntity<NotificationDTO> updateNotification(@RequestBody Notification notification, @PathVariable Long id)
            throws Exception {
        Notification updateNotification = notificationService.findOne(id);
        if (updateNotification == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        updateNotification.copyValues(notification);
        NotificationDTO updatedNotification = notificationService.update(updateNotification);
        return new ResponseEntity<>(updatedNotification, HttpStatus.OK);
    }
    @DeleteMapping(value = "/{id}")
   // @PreAuthorize("hasAnyRole( 'Administrator','Owner', 'Guest')")
    public ResponseEntity<NotificationDTO> deleteNotification(@PathVariable("id") Long id) {
        notificationService.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping(value = "/visible/{id}")
    // @PreAuthorize("hasAnyRole( 'Administrator','Owner', 'Guest')")
    public ResponseEntity<NotificationDTO> deleteVisibleNotification(@PathVariable("id") Long id) {
        notificationService.deleteVisible(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
    @GetMapping(value = "notifications/{idOwner}",produces = MediaType.APPLICATION_JSON_VALUE)
    //@PreAuthorize("hasAnyRole( 'Owner', 'Guest')")
    public ResponseEntity<Collection<NotificationVisible>> getNotificationOwner(@PathVariable("idOwner") Long idOwner){
        System.out.println("UDJE BEK");
        Collection<NotificationVisible> notificationVisibles=notificationService.findAllByOwner(idOwner);
        return new ResponseEntity<Collection<NotificationVisible>>(notificationVisibles,HttpStatus.OK);
    }

    @PostMapping(value = "turnOfNot/{idOwner}/{idGuest}",consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    //@PreAuthorize("hasAnyRole( 'Owner', 'Guest')")
    public ResponseEntity<NotificationVisible> createNotificationTurnOf(@PathVariable("idOwner") Long idOwner,@PathVariable("idGuest") Long idGuest,@RequestBody NotificationVisible notification) throws Exception {
        System.out.println("USLO5300");
        NotificationVisible notificationVisible = notificationService.createNot(notification,idOwner,idGuest);
        return new ResponseEntity<NotificationVisible>(notificationVisible,HttpStatus.CREATED);
    }

    @GetMapping(value = "notificationsGuest/{idGuest}",produces = MediaType.APPLICATION_JSON_VALUE)
    //@PreAuthorize("hasAnyRole( 'Owner', 'Guest')")
    public ResponseEntity<Collection<NotificationVisible>> getNotificationGuest(@PathVariable("idGuest") Long idGuest){
        Collection<NotificationVisible> notificationVisibles=notificationService.findAllByGuest(idGuest);
        return new ResponseEntity<Collection<NotificationVisible>>(notificationVisibles,HttpStatus.OK);
    }

    //za mobilne
    @GetMapping(value = "/mobileOwner/{idOwner}",produces = MediaType.APPLICATION_JSON_VALUE)
    //@PreAuthorize("hasAnyRole( 'Owner', 'Guest')")
    public ResponseEntity<Collection<NotificationUserVisible>> getNotificationVisibleOwner(@PathVariable("idOwner") Long idOwner){
        System.out.println("UDJE BEK");
        Collection<NotificationUserVisible> notificationVisibles=notificationService.findAllByOwnerMobile(idOwner);
        System.out.println(notificationVisibles);
        return new ResponseEntity<Collection<NotificationUserVisible>>(notificationVisibles,HttpStatus.OK);
    }

    @GetMapping(value = "/mobileGuest/{idGuest}",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Collection<NotificationUserVisible>> getNotificationGuestMobile(@PathVariable("idGuest") Long idGuest){
        System.out.println("UDJE BEK2");
        Collection<NotificationUserVisible> notificationVisibles=notificationService.findAllByGuestMobile(idGuest);
        System.out.println(notificationVisibles);
        return new ResponseEntity<Collection<NotificationUserVisible>>(notificationVisibles,HttpStatus.OK);
    }


//    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
//        //@PreAuthorize("hasAnyRole( 'Owner', 'Guest')")
//    public ResponseEntity<NotificationUserVisible> createNotification(@RequestBody NotificationUserVisible notification) throws Exception {
//            System.out.println("uslo da kreira notifikaciju");
//            NotificationVisible notificationVisible = new NotificationVisible(5L,"aaa",new Guest(3L),new Owner(1L),"OG");
////            NotificationUserVisible notificationVisible = notificationService.createNotification(notification,idOwner,idGuest);
//            return new ResponseEntity<NotificationUserVisible>((MultiValueMap<String, String>) notificationVisible,HttpStatus.CREATED);
//    }

    @PostMapping(value="/createNotification",consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<NotificationUserVisible> createNotification(@RequestParam(required = false) String text,
                                                                      @RequestParam(required = false)Long idGuest,
                                                                      @RequestParam(required = false) Long idOwner,
                                                                      @RequestParam(required = false) String userRate) throws Exception {
        NotificationUserVisible createdNotification = notificationService.createNotification(new NotificationUserVisible(100L,text, NotificationStatus.NOT_VISIBLE,new Guest(idGuest),new Owner(idOwner),"today",userRate));
        return new ResponseEntity<NotificationUserVisible>(createdNotification,HttpStatus.CREATED);
    }



}
