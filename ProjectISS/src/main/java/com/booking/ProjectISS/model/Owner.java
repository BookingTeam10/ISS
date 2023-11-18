package com.booking.ProjectISS.model;
//@Entity
public class Owner extends User{

    private double totalPrice;
    private double rating;

    private boolean createdNotification;
    private boolean rateMeNotification;
    private boolean cancelledNotification;
    private boolean rateAccomodationNotification;

    public Owner(Long id,String email, String password, String name, String surname, String phone, String address, boolean blocked) {
        super(id,email, password, name, surname, phone, address, blocked);
        this.totalPrice=0;
        this.rating=0;
        this.createdNotification=true;
        this.rateMeNotification=true;
        this.cancelledNotification=true;
        this.rateAccomodationNotification=true;
    }

    public Owner(Long id,String email, String password, String name, String surname, String phone, String address, boolean blocked,double totalPrice,double rating, boolean createdNotification, boolean rateMeNotification, boolean cancelledNotification, boolean rateAccomodationNotification) {
        super(id,email, password, name, surname, phone, address, blocked);
        this.totalPrice=0;
        this.rating=0;
        this.createdNotification=createdNotification;
        this.rateMeNotification=rateMeNotification;
        this.cancelledNotification=cancelledNotification;
        this.rateAccomodationNotification=rateAccomodationNotification;
    }

    public Owner() {
    }

    @Override
    public String toString() {
        return "Owner{" +
                "totalPrice=" + totalPrice +
                ", rating=" + rating +
                ", turnOnNotification1=" + createdNotification +
                ", turnOnNotification2=" + rateMeNotification +
                ", turnOnNotification3=" + cancelledNotification +
                ", turnOnNotification4=" + rateAccomodationNotification +
                '}';
    }
}
