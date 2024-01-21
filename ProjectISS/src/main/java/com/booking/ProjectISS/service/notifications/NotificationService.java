package com.booking.ProjectISS.service.notifications;

import com.booking.ProjectISS.dto.accomodations.AccommodationDTO;
import com.booking.ProjectISS.dto.notifications.NotificationDTO;
import com.booking.ProjectISS.dto.reservations.ReservationDTO;
import com.booking.ProjectISS.model.accomodations.Accommodation;
import com.booking.ProjectISS.model.notifications.Notification;
import com.booking.ProjectISS.model.notifications.NotificationUserVisible;
import com.booking.ProjectISS.model.notifications.NotificationVisible;
import com.booking.ProjectISS.model.reviews.ReviewOwner;
import com.booking.ProjectISS.model.users.Guest;
import com.booking.ProjectISS.model.users.Owner;
import com.booking.ProjectISS.repository.notifications.INotificationRepository;
import com.booking.ProjectISS.repository.notifications.INotificationUserVisibleRepository;
import com.booking.ProjectISS.repository.notifications.INotificationVisibleRepository;
import com.booking.ProjectISS.repository.users.guests.IGuestRepository;
import com.booking.ProjectISS.repository.users.owner.IOwnerRepository;
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

    @Autowired
    private INotificationVisibleRepository notificationVisibleRepository;

    @Autowired
    private IOwnerRepository ownerRepository;

    @Autowired
    private IGuestRepository guestRepository;
    @Autowired
    private INotificationUserVisibleRepository notificationUserVisibleRepository;

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
    @Override
    public Collection<NotificationVisible> findAllByOwner(Long idOwner) {
        Collection<NotificationVisible> not=new ArrayList<>();
        Collection<NotificationVisible> notificationVisibles=notificationVisibleRepository.findAllByOwner(idOwner);
        for(NotificationVisible nv:notificationVisibles){
            if(nv.getUserRate().equals("GO")){
                not.add(nv);
            }
        }
        return not;
    }

    @Override
    public NotificationVisible createNot(NotificationVisible notification, Long idOwner, Long idGuest) {
        Optional<Owner> o=ownerRepository.findById(idOwner);
        Optional<Guest> g=guestRepository.findById(idGuest);
        notification.setOwner(o.get());
        notification.setGuest(g.get());
        NotificationVisible savedNotification = notificationVisibleRepository.save(notification);
        return  savedNotification;
    }

    @Override
    public Collection<NotificationVisible> findAllByGuest(Long idGuest) {
        Collection<NotificationVisible> not=new ArrayList<>();
        Collection<NotificationVisible> notificationVisibles=notificationVisibleRepository.findAllByGuest(idGuest);
        for(NotificationVisible nv:notificationVisibles){
            if(nv.getUserRate().equals("OG")){
                not.add(nv);
            }
        }
        return not;
    }

    //ovo je dodato zbog mobilnih
    @Override
    public Collection<NotificationUserVisible> findAllByOwnerMobile(Long idOwner) {
        Collection<NotificationUserVisible> notifications=new ArrayList<>();
        Collection<NotificationUserVisible> notificationUserVisibles=notificationUserVisibleRepository.findAllByOwner(idOwner);
        for(NotificationUserVisible nv:notificationUserVisibles){
            if(nv.getUserRate().equals("GO")){
                notifications.add(nv);
            }
        }
        return notifications;
    }

    @Override
    public NotificationUserVisible createNot(NotificationUserVisible notification, Long idOwner, Long idGuest) {
        return null;
    }

    @Override
    public Collection<NotificationUserVisible> findAllByGuestMobile(Long idGuest) {
        Collection<NotificationUserVisible> notifications=new ArrayList<>();
        Collection<NotificationUserVisible> notificationUserVisibles=notificationUserVisibleRepository.findAllByGuest(idGuest);
        for(NotificationUserVisible nv:notificationUserVisibles){
            if(nv.getUserRate().equals("OG")){
                notifications.add(nv);
            }
        }
        return notifications;
    }
    @Override
    public NotificationUserVisible createNotification(NotificationUserVisible notificationUserVisible) throws Exception {
        return notificationUserVisibleRepository.save(notificationUserVisible);
    }

    @Override
    public void deleteVisible(Long id) {
        notificationUserVisibleRepository.deleteById(id);
    }


}
