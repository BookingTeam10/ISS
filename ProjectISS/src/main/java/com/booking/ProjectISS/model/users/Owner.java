package com.booking.ProjectISS.model.users;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

import java.io.Serializable;

@Entity
@Table(name="owners")
public class Owner extends User implements Serializable {
    @Column(name="total_price")
    private double totalPrice;
    @Column(name="owner_rating")
    private double rating;
    @Column(name="created_notification")
    private boolean createdNotification;
    @Column(name="rate_notification")
    private boolean rateMeNotification;
    @Column(name="cancelled_notification")
    private boolean cancelledNotification;
    @Column(name="accommodation_notification")
    private boolean rateAccomodationNotification;

    public Owner(Long id,String email, String password, String name, String surname, String phone, String address,boolean rep, boolean blocked) {
        super(id,email, password, name, surname, phone, address, rep,blocked);
        this.totalPrice=0;
        this.rating=0;
        this.createdNotification=true;
        this.rateMeNotification=true;
        this.cancelledNotification=true;
        this.rateAccomodationNotification=true;
    }

    public Owner(Long id,String email, String password, String name, String surname, String phone, String address,boolean rep, boolean blocked,double totalPrice,double rating, boolean createdNotification, boolean rateMeNotification, boolean cancelledNotification, boolean rateAccomodationNotification) {
        super(id,email, password, name, surname, phone, address,rep, blocked);
        this.totalPrice=0;
        this.rating=0;
        this.createdNotification=createdNotification;
        this.rateMeNotification=rateMeNotification;
        this.cancelledNotification=cancelledNotification;
        this.rateAccomodationNotification=rateAccomodationNotification;
    }

    public Owner() {
    }

    public Owner(Long id) {
        super(id);
    }

    @Override
    public String toString() {
        return "Owner{" +
                "email="+ super.getEmail()+
                "totalPrice=" + totalPrice +
                ", rating=" + rating +
                ", turnOnNotification1=" + createdNotification +
                ", turnOnNotification2=" + rateMeNotification +
                ", turnOnNotification3=" + cancelledNotification +
                ", turnOnNotification4=" + rateAccomodationNotification +
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
