package com.booking.ProjectISS.model;

public class Guest extends User{
    private int numberCanceledReservation;
    private boolean turnOnNotification;
    public Guest(Long id,String email, String password, String name, String surname, String phone, String address) {
        super(id,email, password, name, surname, phone, address);
        this.numberCanceledReservation=0;
        this.turnOnNotification=true;
    }

    public Guest(Long id,String email, String password, String name, String surname, String phone, String address, int numberCanceledReservation,boolean turnOnNotification) {
        super(id,email, password, name, surname, phone, address);
        this.numberCanceledReservation = numberCanceledReservation;
        this.turnOnNotification=turnOnNotification;
    }

    public Guest() {
    }

    public int getNumberCanceledReservation() {
        return numberCanceledReservation;
    }

    public void setNumberCanceledReservation(int numberCanceledReservation) {
        this.numberCanceledReservation = numberCanceledReservation;
    }

    public boolean isTurnOnNotification() {
        return turnOnNotification;
    }

    public void setTurnOnNotification(boolean turnOnNotification) {
        this.turnOnNotification = turnOnNotification;
    }

    @Override
    public String toString() {
        return "Guest{" +
                "numberCanceledReservation=" + numberCanceledReservation +
                ", turnOnNotification=" + turnOnNotification +
                '}';
    }
}
