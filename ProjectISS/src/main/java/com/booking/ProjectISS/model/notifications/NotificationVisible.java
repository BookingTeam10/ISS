package com.booking.ProjectISS.model.notifications;

import com.booking.ProjectISS.model.users.Guest;
import com.booking.ProjectISS.model.users.Owner;
import jakarta.persistence.*;

import java.io.Serializable;

@Entity
@Table(name = "notifications_visible")
public class NotificationVisible implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name= "id")
    private Long id;

    @Column(name= "text")
    private String text;

    @ManyToOne
    @JoinColumn(name = "guest_id")
    private Guest guest;
    @ManyToOne
    @JoinColumn(name = "owner_id")
    private Owner owner;

    @Column(name= "user_rate")
    private String userRate;

    public NotificationVisible() {
    }

    public NotificationVisible(Long id, String text, Guest guest, Owner owner, String userRate) {
        this.id = id;
        this.text = text;
        this.guest = guest;
        this.owner = owner;
        this.userRate = userRate;
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

    public String getUserRate() {
        return userRate;
    }

    public void setUserRate(String userRate) {
        this.userRate = userRate;
    }
    @Override
    public String toString() {
        return "NotificationVisible{" +
                "id=" + id +
                ", text='" + text + '\'' +
                ", guest=" + guest +
                ", owner=" + owner +
                ", userRate='" + userRate + '\'' +
                '}';
    }
}
