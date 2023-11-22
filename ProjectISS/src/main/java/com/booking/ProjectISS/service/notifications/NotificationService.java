package com.booking.ProjectISS.service.notifications;

import com.booking.ProjectISS.dto.accomodations.AccommodationDTO;
import com.booking.ProjectISS.dto.notifications.NotificationDTO;
import com.booking.ProjectISS.model.accomodations.Accommodation;
import com.booking.ProjectISS.model.notifications.Notification;
import com.booking.ProjectISS.repository.notifications.INotificationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;

@Service
public class NotificationService implements INotificationService {

    @Autowired
    private INotificationRepository notificationRepository;

    @Override
    public NotificationDTO findOneDTO(Long id) {
        return new NotificationDTO(notificationRepository.findOne(id));
    }

    @Override
    public Notification findOne(Long id) {
        return notificationRepository.findOne(id);
    }

    @Override
    public Collection<NotificationDTO> findAllDTO() {
        Collection<Notification> notifications = notificationRepository.findAll();
        Collection<NotificationDTO> notificationDTOs = new ArrayList<>();

        for (Notification n : notifications) {
            notificationDTOs.add(new NotificationDTO(n));
        }

        return notificationDTOs;
    }

    @Override
    public Collection<Notification> findAll() {
        return notificationRepository.findAll();
    }

    @Override
    public void delete(Long id) {
        notificationRepository.delete(id);
    }

    @Override
    public NotificationDTO create(Notification notification) throws Exception {
        Notification savedNotification = notificationRepository.create(notification);
        return new NotificationDTO(savedNotification);
    }

    @Override
    public NotificationDTO update(Notification notification) throws Exception {
        // Implement your update logic if needed
        return null;
    }
}
