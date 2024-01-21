package com.booking.ProjectISS.service.notifications;

import com.booking.ProjectISS.dto.notifications.NotificationDTO;
import com.booking.ProjectISS.model.notifications.Notification;
import com.booking.ProjectISS.model.notifications.NotificationUserVisible;
import com.booking.ProjectISS.model.notifications.NotificationVisible;

import java.util.Collection;

public interface INotificationService {
    NotificationDTO findOneDTO(Long id);
    Notification findOne(Long id);
    Collection<NotificationDTO> findAllDTO();
    Collection<Notification> findAll();
    void delete(Long id);
    NotificationDTO create(Notification notification) throws Exception;
    NotificationDTO update(Notification notification) throws Exception;

    Collection<NotificationVisible> findAllByOwner(Long idOwner);

    NotificationVisible createNot(NotificationVisible notification, Long idOwner, Long idGuest);

    Collection<NotificationVisible> findAllByGuest(Long idGuest);

    Collection<NotificationUserVisible> findAllByOwnerMobile(Long idOwner);

    NotificationUserVisible createNot(NotificationUserVisible notification, Long idOwner, Long idGuest);

    Collection<NotificationUserVisible> findAllByGuestMobile(Long idGuest);
    public NotificationUserVisible createNotification(NotificationUserVisible notificationUserVisible) throws Exception;

    void deleteVisible(Long id);
}
