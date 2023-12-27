package com.booking.ProjectISS.model.users;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;

import java.io.Serializable;

@Entity
@Table(name="owners")
public class Owner extends User implements Serializable {
    @Column(name="total_price")
    private double totalPrice;
    @Column(name="owner_rating")
    private double rating;

    public void setCreatedNotification(boolean createdNotification) {
        this.createdNotification = createdNotification;
    }

    public void setRateMeNotification(boolean rateMeNotification) {
        this.rateMeNotification = rateMeNotification;
    }

    public void setCancelledNotification(boolean cancelledNotification) {
        this.cancelledNotification = cancelledNotification;
    }

    public void setRateAccomodationNotification(boolean rateAccommodationNotification) {
        this.rateAccommodationNotification = rateAccommodationNotification;
    }

    @Getter
    @Column(name="created_notification")
    private boolean createdNotification;
    @Getter
    @Column(name="rate_notification")
    private boolean rateMeNotification;
    @Getter
    @Column(name="cancelled_notification")
    private boolean cancelledNotification;
    @Getter
    @Column(name="accommodation_notification")
    private boolean rateAccommodationNotification;

    public Owner(Long id,String email, String password, String name, String surname, String phone, String address,boolean rep, boolean blocked) {
        super(id,email, password, name, surname, phone, address, rep,blocked);
        this.totalPrice=0;
        this.rating=0;
        this.createdNotification=true;
        this.rateMeNotification=true;
        this.cancelledNotification=true;
        this.rateAccommodationNotification=true;
    }

    public Owner(Long id,String email, String password, String name, String surname, String phone, String address,boolean rep, boolean blocked,double totalPrice,double rating, boolean createdNotification, boolean rateMeNotification, boolean cancelledNotification, boolean rateAccomodationNotification) {
        super(id,email, password, name, surname, phone, address,rep, blocked);
        this.totalPrice=0;
        this.rating=0;
        this.createdNotification=createdNotification;
        this.rateMeNotification=rateMeNotification;
        this.cancelledNotification=cancelledNotification;
        this.rateAccommodationNotification=rateAccomodationNotification;
    }

    public Owner() {
    }

    public Owner(Long id) {
        super(id);
    }

    public Owner(Owner owner) {
        super(owner.getId(), owner.getEmail(), owner.getPassword(), owner.getName(), owner.getSurname(), owner.getPhone(), owner.getAddress(),owner.isReported(), owner.isBlocked());
        this.totalPrice=0;
        this.rating=0;
        this.createdNotification=true;
        this.rateMeNotification=true;
        this.cancelledNotification=true;
        this.rateAccommodationNotification=true;
    }

    @Override
    public String toString() {
        return "Owner{" +
                "email="+ super.getEmail()+
                "totalPrice=" + totalPrice +
                ", rating=" + rating +
                ", turnOnNotification1=" + createdNotification +
                ", rate me=" + rateMeNotification +
                ", cancelled=" + cancelledNotification +
                ", rate accommodation=" + rateAccommodationNotification +
                '}';
    }

    public void copyValues(Owner ownerForUpdate) {
        this.setEmail(ownerForUpdate.getEmail());
        this.setPassword(ownerForUpdate.getPassword());
        this.setName(ownerForUpdate.getName());
        this.setSurname(ownerForUpdate.getSurname());
        this.setPhone(ownerForUpdate.getPhone());
        this.setAddress(ownerForUpdate.getAddress());
        this.setBlocked(ownerForUpdate.isBlocked());
    }
    public Long id() {
        return super.getId();
    }

}
