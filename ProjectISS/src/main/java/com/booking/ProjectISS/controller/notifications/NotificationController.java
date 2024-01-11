package com.booking.ProjectISS.controller.notifications;


import com.booking.ProjectISS.dto.notifications.NotificationDTO;
import com.booking.ProjectISS.model.notifications.Notification;
import com.booking.ProjectISS.model.notifications.NotificationVisible;
import com.booking.ProjectISS.service.notifications.INotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
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

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    //@PreAuthorize("hasAnyRole( 'Owner', 'Guest')")
    public ResponseEntity<NotificationDTO> createNotification(@RequestBody Notification notification) throws Exception {
        NotificationDTO notificationDTO = notificationService.create(notification);
        return new ResponseEntity<>(notificationDTO, HttpStatus.CREATED);
    }

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
}
