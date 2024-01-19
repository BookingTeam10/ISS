package com.booking.ProjectISS.model.notifications;



import com.booking.ProjectISS.enums.NotificationStatus;
import com.booking.ProjectISS.model.users.Guest;
import com.booking.ProjectISS.model.users.Owner;
import jakarta.persistence.*;

import java.io.Serial;
import java.io.Serializable;

@Entity
@Table(name = "notifications_user_visible")
public class NotificationUserVisible implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name= "id")
    private Long id;
    @Column(name= "text")
    private String text;
    @Column(name= "notification_status")
    @Enumerated(EnumType.STRING)
    private NotificationStatus status;
    @ManyToOne
    @JoinColumn(name = "guest_id")
    private Guest guest;
    @ManyToOne
    @JoinColumn(name = "owner_id")
    private Owner owner;
    @Column(name = "sent_date")
    private String sentDate;
    @Column(name= "user_rate")
    private String userRate;

    public NotificationUserVisible(Long id, String text, NotificationStatus status, Guest guest, Owner owner, String sentDate, String userRate) {
        this.id = id;
        this.text = text;
        this.status = status;
        this.guest = guest;
        this.owner = owner;
        this.sentDate = sentDate;
        this.userRate = userRate;
    }

    public NotificationUserVisible() {

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public NotificationStatus getStatus() {
        return status;
    }
    public void setStatus(NotificationStatus status) {
        this.status = status;
    }

    public Guest getGuest() {
        return guest;
    }
    public void setGuest(Guest guest) {
        this.guest = guest;
    }
    public Owner getOwner() {
        return owner;
    }
    public void setOwner(Owner owner) {
        this.owner = owner;
    }
    public String getSentDate() {
        return sentDate;
    }
    public void setSentDate(String sentDate) {
        this.sentDate = sentDate;
    }
    public String getUserRate() {
        return userRate;
    }

    public void setUserRate(String userRate) {
        this.userRate = userRate;
    }

    @Override
    public String toString() {
        return "NotificationUserVisible{" +
                "id=" + id +
                ", text='" + text + '\'' +
                ", status=" + status +
                ", guest=" + guest +
                ", owner=" + owner +
                ", sentDate=" + sentDate +
                ", userRate='" + userRate + '\'' +
                '}';
    }
}
