package com.booking.ProjectISS.repository.notifications;

import com.booking.ProjectISS.model.notifications.Notification;
import com.booking.ProjectISS.model.notifications.NotificationVisible;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Collection;

public interface INotificationVisibleRepository extends JpaRepository<NotificationVisible, Long> {

    @Query("select n from NotificationVisible  n where n.owner.id = ?1")
    Collection<NotificationVisible> findAllByOwner(Long idOwner);

    @Query("select n from NotificationVisible  n where n.guest.id = ?1")
    Collection<NotificationVisible> findAllByGuest(Long idGuest);
}
