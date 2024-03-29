package com.booking.ProjectISS.model.users;

import com.booking.ProjectISS.model.accomodations.Accommodation;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name="guests")
public class Guest extends User {
    @Column(name="number_canceled_notification")
    private int numberCanceledReservation;
    @Column(name="turn_notification")
    private boolean turnOnNotification;
    @ElementCollection(fetch = FetchType.EAGER)
    @Column(name = "favorite_accommodations")
    private List<Accommodation> favouriteAccommodations;

    public Guest(Long id,String email, String password, String name, String surname, String phone, String address,boolean rep, boolean blocked) {
        super(id,email, password, name, surname, phone, address,rep, blocked);
        this.numberCanceledReservation=0;
        this.turnOnNotification=true;
        this.favouriteAccommodations = new ArrayList<>();
    }
    public Guest(Long id,String email, String password, String name, String surname, String phone, String address,boolean rep, boolean blocked, int numberCanceledReservation,boolean turnOnNotification) {
        super(id,email, password, name, surname, phone, address,rep,blocked);
        this.numberCanceledReservation = numberCanceledReservation;
        this.turnOnNotification=turnOnNotification;
        this.favouriteAccommodations = new ArrayList<>();
    }
    public Guest(Long id,String email, String password, String name, String surname, String phone, String address,boolean rep, boolean blocked, int numberCanceledReservation,boolean turnOnNotification,List<Accommodation> favouriteAccommodations) {
        super(id,email, password, name, surname, phone, address,rep,blocked);
        this.numberCanceledReservation = numberCanceledReservation;
        this.turnOnNotification=turnOnNotification;
        this.favouriteAccommodations = favouriteAccommodations;
    }

    public Guest(Long id, String email, String password, String name, String surname, String phone, String address) {
        super(id,email, password, name, surname, phone, address);
        this.numberCanceledReservation=0;
        this.turnOnNotification=true;
        this.favouriteAccommodations = new ArrayList<>();
    }

    public Guest(Long id,String email, String password, String name, String surname, String phone, String address, int numberCanceledReservation,boolean turnOnNotification) {
        super(id,email, password, name, surname, phone, address);
        this.numberCanceledReservation = numberCanceledReservation;
        this.turnOnNotification=turnOnNotification;
        this.favouriteAccommodations = new ArrayList<>();
    }
    public Guest() {}
    public Guest(Guest g) {
        super(g.getId(),g.getEmail(), g.getPassword(), g.getName(), g.getSurname(), g.getPhone(), g.getAddress(),g.isReported(),g.isBlocked());
        this.numberCanceledReservation=0;
        this.turnOnNotification=true;
        this.favouriteAccommodations = new ArrayList<>();
    }
    public int getNumberCanceledReservation() {
        return numberCanceledReservation;
    }
    public void setNumberCanceledReservation(int numberCanceledReservation) {this.numberCanceledReservation = numberCanceledReservation;}
    public boolean isTurnOnNotification() {return turnOnNotification;}
    public void setTurnOnNotification(boolean turnOnNotification) {this.turnOnNotification = turnOnNotification;}
    public List<Accommodation> getFavouriteAccommodations() {return favouriteAccommodations;}
    public void setFavouriteAccommodations(List<Accommodation> favouriteAccommodations) {this.favouriteAccommodations = favouriteAccommodations;}
    public void copyValues(Guest guestForUpdate) {
        this.setEmail(guestForUpdate.getEmail());
        this.setPassword(guestForUpdate.getPassword());
        this.setName(guestForUpdate.getName());
        this.setSurname(guestForUpdate.getSurname());
        this.setPhone(guestForUpdate.getPhone());
        this.setAddress(guestForUpdate.getAddress());
        this.setNumberCanceledReservation(guestForUpdate.getNumberCanceledReservation());
        this.setTurnOnNotification(guestForUpdate.isTurnOnNotification());
        this.setFavouriteAccommodations(guestForUpdate.getFavouriteAccommodations());
    }
    @Override
    public String toString() {
        return "Guest{" +
                "numberCanceledReservation=" + numberCanceledReservation +
                ", turnOnNotification=" + turnOnNotification +
                ", favouriteAccommodations=" + favouriteAccommodations +
                '}';
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Guest guest = (Guest) obj;
        return Objects.equals(getId(), guest.getId()); // Pretpostavljamo da je 'id' jedinstveni identifikator gosta
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId());
    }

    public Guest(Long id) {
        super.setId(id);
    }
}
