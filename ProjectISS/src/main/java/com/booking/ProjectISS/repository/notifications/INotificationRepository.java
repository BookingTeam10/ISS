package com.booking.ProjectISS.repository.notifications;

import com.booking.ProjectISS.model.notifications.Notification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Collection;

public interface INotificationRepository  extends JpaRepository<Notification, Long> {

    @Query("select n from Notification  n where n.guest.id = ?1")
    Notification findByGuest(String notificationId);

    @Query("select n from Notification  n where n.owner.id = ?1")
    Notification findByOwner(String ownerId);
}
