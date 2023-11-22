package com.booking.ProjectISS.repository.notifications;

import com.booking.ProjectISS.model.notifications.Notification;

import java.util.Collection;

public interface INotificationRepository {

    Notification findOne(long id);
    Collection<Notification> findAll();
    void delete(long id);
    Notification create(Notification notification);
}
