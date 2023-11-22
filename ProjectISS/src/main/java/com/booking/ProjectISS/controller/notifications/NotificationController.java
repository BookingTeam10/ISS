package com.booking.ProjectISS.controller.notifications;

import com.booking.ProjectISS.dto.notifications.NotificationDTO;
import com.booking.ProjectISS.model.notifications.Notification;
import com.booking.ProjectISS.service.notifications.INotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.awt.*;
import java.util.Collection;

@RestController
@RequestMapping("/api/notifications")
public class NotificationController {
    @Autowired
    private INotificationService notificationService;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Collection<NotificationDTO>> getNotificationDTO(){
        return new ResponseEntity<Collection<NotificationDTO>>(notificationService.findAllDTO(), HttpStatus.OK);
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<NotificationDTO> getNotification(@PathVariable("id") Long id) {
        NotificationDTO notificationDTO = notificationService.findOneDTO(id);
        if (notificationDTO != null) {
            return new ResponseEntity<>(notificationDTO, HttpStatus.OK);
        }

        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<NotificationDTO> createNotification(@RequestBody Notification notification) throws Exception {
        NotificationDTO notificationDTO = notificationService.create(notification);
        return new ResponseEntity<>(notificationDTO, HttpStatus.CREATED);
    }

    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
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
    public ResponseEntity<NotificationDTO> deleteNotification(@PathVariable("id") Long id) {
        notificationService.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
