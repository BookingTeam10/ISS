package com.booking.ProjectISS.model.users;

//@Entity
public class Guest extends User {
    private int numberCanceledReservation;
    private boolean turnOnNotification;
    public Guest(Long id,String email, String password, String name, String surname, String phone, String address,boolean rep, boolean blocked) {
        super(id,email, password, name, surname, phone, address,rep, blocked);
        this.numberCanceledReservation=0;
        this.turnOnNotification=true;
    }

    public Guest(Long id,String email, String password, String name, String surname, String phone, String address,boolean rep, boolean blocked, int numberCanceledReservation,boolean turnOnNotification) {
        super(id,email, password, name, surname, phone, address,rep,blocked);
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
    public void copyValues(Guest guestForUpdate) {
        this.setEmail(guestForUpdate.getEmail());
        this.setPassword(guestForUpdate.getPassword());
        this.setName(guestForUpdate.getName());
        this.setSurname(guestForUpdate.getSurname());
        this.setPhone(guestForUpdate.getPhone());
        this.setAddress(guestForUpdate.getAddress());
        this.setNumberCanceledReservation(guestForUpdate.getNumberCanceledReservation());
        this.setTurnOnNotification(guestForUpdate.isTurnOnNotification());
    }
}
