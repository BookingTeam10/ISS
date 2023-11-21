package com.booking.ProjectISS.dto.reservations;

import com.booking.ProjectISS.enums.ReservationStatus;
import com.booking.ProjectISS.model.reservations.Reservation;
import com.booking.ProjectISS.model.reviews.Review;

import java.util.Date;
import java.util.List;

public class ReservationDTO {
    private int id;
    private double totalPrice;
    private ReservationStatus status;
    private Date firstDate;
    private int numberOfNights;
    private int accommodationId;
    private int guestId;
    private List<Review> reviews;

    public ReservationDTO() {
    }

    public ReservationDTO(int id, double totalPrice, ReservationStatus status, Date firstDate, int numberOfNights,
                          int accommodationId, int guestId, List<Review> reviews) {
        this.id = id;
        this.totalPrice = totalPrice;
        this.status = status;
        this.firstDate = firstDate;
        this.numberOfNights = numberOfNights;
        this.accommodationId = accommodationId;
        this.guestId = guestId;
        this.reviews = reviews;
    }

    public ReservationDTO(Reservation reservation) {
        this.id = reservation.getId();
        this.totalPrice = reservation.getTotalPrice();
        this.status = reservation.getStatus();
        this.firstDate = reservation.getFirstDate();
        this.numberOfNights = reservation.getNumberOfNights();

        // Assuming Accommodation and Guest objects have getId() methods
        this.accommodationId = Math.toIntExact(reservation.getAccommodation().getId());
        this.guestId = Math.toIntExact(reservation.getGuest().getId());

        this.reviews = reservation.getReviews();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
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

    public int getAccommodationId() {
        return accommodationId;
    }

    public void setAccommodationId(int accommodationId) {
        this.accommodationId = accommodationId;
    }

    public int getGuestId() {
        return guestId;
    }

    public void setGuestId(int guestId) {
        this.guestId = guestId;
    }

    public List<Review> getReviews() {
        return reviews;
    }

    public void setReviews(List<Review> reviews) {
        this.reviews = reviews;
    }
}
