package com.booking.ProjectISS.repository.notifications;

import com.booking.ProjectISS.model.notifications.NotificationUserVisible;
import com.booking.ProjectISS.model.notifications.NotificationVisible;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Collection;

public interface INotificationUserVisibleRepository extends JpaRepository<NotificationUserVisible, Long> {
    @Query("select n from NotificationUserVisible  n where n.owner.id = ?1")
    Collection<NotificationUserVisible> findAllByOwner(Long idOwner);

    @Query("select n from NotificationUserVisible  n where n.guest.id = ?1")
    Collection<NotificationUserVisible> findAllByGuest(Long idGuest);
}
