package com.booking.ProjectISS.repository.notifications;

import com.booking.ProjectISS.enums.NotificationStatus;
import com.booking.ProjectISS.model.notifications.Notification;
import com.booking.ProjectISS.model.users.Guest;
import com.booking.ProjectISS.model.users.Owner;
import org.springframework.stereotype.Repository;

import java.util.*;
import java.util.concurrent.atomic.AtomicLong;
@Repository
public class NotificationRepository implements INotificationRepository{
    private List<Notification> notifications;  // Change the list type to Notification
    private static AtomicLong counter = new AtomicLong();

    public NotificationRepository() {
        this.notifications = loadAll();
    }

    @Override
    public Notification findOne(long id) {
        for (Notification notification : notifications) {
            if (Objects.equals(notification.getId(), id)) {
                return notification;
            }
        }
        return null;
    }

    @Override
    public Collection<Notification> findAll() {
        return notifications;
    }

    @Override
    public void delete(long id) {
        notifications.removeIf(notification -> notification.getId() == id);
    }

    @Override
    public Notification create(Notification notification) {
        long id = notification.getId();

        if (id == 0) {
            id = counter.incrementAndGet();
            notification.setId(id);
        }

        this.notifications.add(notification);
        return notification;
    }

    private List<Notification> loadAll(){

        this.notifications = new ArrayList<>();
        Guest g = new Guest();
        Owner o = new Owner();
        g.setId(1L);
        o.setId(50L);
        Notification n1 = new Notification("text1", NotificationStatus.NOT_VISIBLE, new Guest(),new Owner(), new Date());
        n1.setGuest(g);
        n1.setId(counter.incrementAndGet());
        n1.setOwner(o);
        Notification n2 = new Notification("test3", NotificationStatus.VISIBLE, new Guest(),new Owner(), new Date());
        n2.setId(counter.incrementAndGet());
        n2.setGuest(g);
        n2.setOwner(o);
        notifications.add(n1);
        notifications.add(n2);
        return notifications;
    }

}
