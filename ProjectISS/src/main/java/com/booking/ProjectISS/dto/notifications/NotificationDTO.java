package com.booking.ProjectISS.dto.notifications;


import com.booking.ProjectISS.enums.NotificationStatus;
import com.booking.ProjectISS.model.notifications.Notification;

import java.util.Date;

public class NotificationDTO {
    private Long id;
    private String text;
    private NotificationStatus status;
    private Long guestId;
    private Long ownerId;
    private Date sentDate;

    public NotificationDTO() {
    }

    public NotificationDTO(Long id, String text, NotificationStatus status, Long guestId, Long ownerId, Date sentDate) {
        this.id = id;
        this.text = text;
        this.status = status;
        this.guestId = guestId;
        this.ownerId = ownerId;
        this.sentDate = sentDate;
    }

    public NotificationDTO(Notification notification) {
        this.id = notification.getId();
        this.text = notification.getText();
        this.status = notification.getStatus();
        this.guestId = notification.getGuest().getId();
        this.ownerId = notification.getOwner().getId();
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

    public Long getGuestId() {
        return guestId;
    }

    public void setGuestId(Long guestId) {
        this.guestId = guestId;
    }

    public Long getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(Long ownerId) {
        this.ownerId = ownerId;
    }

    public Date getSentDate() {
        return sentDate;
    }

    public void setSentDate(Date sentDate) {
        this.sentDate = sentDate;
    }
}

