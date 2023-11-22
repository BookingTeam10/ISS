package com.booking.ProjectISS.dto.reservations;

import com.booking.ProjectISS.enums.ReservationStatus;
import com.booking.ProjectISS.model.accomodations.Accommodation;
import com.booking.ProjectISS.model.reservations.Reservation;
import com.booking.ProjectISS.model.reviews.Review;
import com.booking.ProjectISS.model.users.Guest;

import java.util.Date;
import java.util.List;

public class ReservationDTO {
    private long id;
    private double totalPrice;
    private ReservationStatus status;
    private Date firstDate;
    private int numberOfNights;
    private Accommodation accommodation;
    private Guest guest;
    private List<Review> reviews;

    public ReservationDTO() {
    }

    public ReservationDTO(long id, double totalPrice, ReservationStatus status, Date firstDate, int numberOfNights,
                          Accommodation accommodation, Guest guest, List<Review> reviews) {
        this.id = id;
        this.totalPrice = totalPrice;
        this.status = status;
        this.firstDate = firstDate;
        this.numberOfNights = numberOfNights;
        this.accommodation = accommodation;
        this.guest = guest;
        this.reviews = reviews;
    }

    public ReservationDTO(Reservation reservation) {
        this.id = reservation.getId();
        this.totalPrice = reservation.getTotalPrice();
        this.status = reservation.getStatus();
        this.firstDate = reservation.getFirstDate();
        this.numberOfNights = reservation.getNumberOfNights();

        this.accommodation = reservation.getAccommodation();
        this.guest = reservation.getGuest();

        this.reviews = reservation.getReviews();
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public ReservationStatus getStatus() {
        return status;
    }

    public void setStatus(ReservationStatus status) {
        this.status = status;
    }

    public Date getFirstDate() {
        return firstDate;
    }

    public void setFirstDate(Date firstDate) {
        this.firstDate = firstDate;
    }

    public int getNumberOfNights() {
        return numberOfNights;
    }

    public void setNumberOfNights(int numberOfNights) {
        this.numberOfNights = numberOfNights;
    }

    public Accommodation getAccommodation() {
        return accommodation;
    }

    public void setAccommodation(Accommodation accommodation) {
        this.accommodation = accommodation;
    }

    public Guest getGuest() {
        return guest;
    }

    public void setGuest(Guest guest) {
        this.guest = guest;
    }

    public List<Review> getReviews() {
        return reviews;
    }

    public void setReviews(List<Review> reviews) {
        this.reviews = reviews;
    }
}
