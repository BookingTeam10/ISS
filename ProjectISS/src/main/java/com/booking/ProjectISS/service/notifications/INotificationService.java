package com.booking.ProjectISS.service.notifications;

import com.booking.ProjectISS.dto.notifications.NotificationDTO;
import com.booking.ProjectISS.model.notifications.Notification;

import java.util.Collection;

public interface INotificationService {
    NotificationDTO findOneDTO(Long id);
    Notification findOne(Long id);
    Collection<NotificationDTO> findAllDTO();
    Collection<Notification> findAll();
    void delete(Long id);
    NotificationDTO create(Notification notification) throws Exception;
    NotificationDTO update(Notification notification) throws Exception;
}
