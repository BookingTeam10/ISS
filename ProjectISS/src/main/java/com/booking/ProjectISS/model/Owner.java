package com.booking.ProjectISS.model;

public class Owner extends User{

    private double totalPrice;
    private double rating;

    private boolean turnOnNotification1;
    private boolean turnOnNotification2;
    private boolean turnOnNotification3;
    private boolean turnOnNotification4;

    public Owner(Long id,String email, String password, String name, String surname, String phone, String address, boolean blocked) {
        super(id,email, password, name, surname, phone, address, blocked);
        this.totalPrice=0;
        this.rating=0;
        this.turnOnNotification1=true;
        this.turnOnNotification2=true;
        this.turnOnNotification3=true;
        this.turnOnNotification4=true;
    }

    public Owner(Long id,String email, String password, String name, String surname, String phone, String address, boolean blocked,double totalPrice,double rating, boolean turnOnNotification1, boolean turnOnNotification2, boolean turnOnNotification3, boolean turnOnNotification4) {
        super(id,email, password, name, surname, phone, address, blocked);
        this.totalPrice=0;
        this.rating=0;
        this.turnOnNotification1=turnOnNotification1;
        this.turnOnNotification2=turnOnNotification2;
        this.turnOnNotification3=turnOnNotification3;
        this.turnOnNotification4=turnOnNotification4;
    }

    public Owner() {
    }

    @Override
    public String toString() {
        return "Owner{" +
                "totalPrice=" + totalPrice +
                ", rating=" + rating +
                ", turnOnNotification1=" + turnOnNotification1 +
                ", turnOnNotification2=" + turnOnNotification2 +
                ", turnOnNotification3=" + turnOnNotification3 +
                ", turnOnNotification4=" + turnOnNotification4 +
                '}';
    }
}
