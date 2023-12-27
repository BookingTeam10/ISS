package com.booking.ProjectISS.service.notifications;

import com.booking.ProjectISS.dto.accomodations.AccommodationDTO;
import com.booking.ProjectISS.dto.notifications.NotificationDTO;
import com.booking.ProjectISS.dto.reservations.ReservationDTO;
import com.booking.ProjectISS.model.accomodations.Accommodation;
import com.booking.ProjectISS.model.notifications.Notification;
import com.booking.ProjectISS.repository.notifications.INotificationRepository;
import org.aspectj.weaver.ast.Not;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;

@Service
public class NotificationService implements INotificationService {

    @Autowired
    private INotificationRepository notificationRepository;

    @Override
    public NotificationDTO findOneDTO(Long id) {
        Optional<Notification> found = notificationRepository.findById(id);
        return found.map(NotificationDTO::new).orElse(null);
    }
    @Override
    public Notification findOne(Long id) {
        Optional<Notification> found = notificationRepository.findById(id);
        return found.orElse(null);
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
        Optional<Notification> found = notificationRepository.findById(id);
        if(found.isEmpty()){ return;}
        notificationRepository.delete(found.get());
        notificationRepository.flush();
    }

    @Override
    public NotificationDTO create(Notification notification) throws Exception {
        Notification savedNotification = notificationRepository.save(notification);
        return new NotificationDTO(savedNotification);
    }

    @Override
    public NotificationDTO update(Notification notification) throws Exception {

        return null;
    }
}
