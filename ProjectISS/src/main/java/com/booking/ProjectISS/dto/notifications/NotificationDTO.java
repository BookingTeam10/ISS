package com.booking.ProjectISS.dto.notifications;


import com.booking.ProjectISS.enums.NotificationStatus;
import com.booking.ProjectISS.model.notifications.Notification;
import com.booking.ProjectISS.model.users.Guest;
import com.booking.ProjectISS.model.users.Owner;

import java.util.Date;

public class NotificationDTO {
    private Long id;
    private String text;
    private NotificationStatus status;
    private Guest guest;
    private Owner owner;
    private Date sentDate;

    public NotificationDTO() {
    }

    public NotificationDTO(Long id, String text, NotificationStatus status, Guest guest, Owner owner, Date sentDate) {
        this.id = id;
        this.text = text;
        this.status = status;
        this.guest = guest;
        this.owner = owner;
        this.sentDate = sentDate;
    }

    public NotificationDTO(Notification notification) {
        this.id = notification.getId();
        this.text = notification.getText();
        this.status = notification.getStatus();
        this.guest = notification.getGuest();
        this.owner = notification.getOwner();
        this.sentDate = notification.getSentDate();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public NotificationStatus getStatus() {
        return status;
    }

    public void setStatus(NotificationStatus status) {
        this.status = status;
    }

    public Guest getGuest() {
        return guest;
    }

    public void setGuest(Guest guest) {
        this.guest = guest;
    }

    public Owner getOwner() {
        return owner;
    }

    public void setOwner(Owner owner) {
        this.owner = owner;
    }

    public Date getSentDate() {
        return sentDate;
    }

    public void setSentDate(Date sentDate) {
        this.sentDate = sentDate;
    }
}

