package com.booking.ProjectISS.dto.reservations;

import com.booking.ProjectISS.enums.ReservationStatus;
import com.booking.ProjectISS.model.CustomDateDeserializer;
import com.booking.ProjectISS.model.accomodations.Accommodation;
import com.booking.ProjectISS.model.reservations.Reservation;
import com.booking.ProjectISS.model.reviews.Review;
import com.booking.ProjectISS.model.users.Guest;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import java.util.Date;
import java.util.List;
import java.util.Objects;

public class ReservationDTO {
    private long id;
    private double totalPrice;
    private ReservationStatus status;
//    @JsonDeserialize(using = CustomDateDeserializer.class)
    private Date startDate;
//    @JsonDeserialize(using = CustomDateDeserializer.class)
    private Date endDate;
    private int numberOfNights;
    private Accommodation accommodation;
    private Guest guest;
    private List<Review> reviews;

    public ReservationDTO() {
    }

    public ReservationDTO(long id, double totalPrice, ReservationStatus status, Date startDate, Date endDate, int numberOfNights,
                          Accommodation accommodation, Guest guest, List<Review> reviews) {
        this.id = id;
        this.totalPrice = totalPrice;
        this.status = status;
        this.startDate = startDate;
        this.endDate = endDate;
        this.numberOfNights = numberOfNights;
        this.accommodation = accommodation;
        this.guest = guest;
        this.reviews = reviews;
    }

    public ReservationDTO(Reservation reservation) {
        this.id = reservation.getId();
        this.totalPrice = reservation.getTotalPrice();
        this.status = reservation.getStatus();
        this.startDate = reservation.getStartDate();
        this.endDate = reservation.getEndDate();
        this.numberOfNights = reservation.getNumberOfNights();

        this.accommodation = reservation.getAccommodation();
        this.guest = reservation.getGuest();

        this.reviews = reservation.getReviews();
    }


    public ReservationDTO(Long id){
        this.id = id;
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

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
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

    @Override
    public String toString() {
        return "ReservationDTO{" +
                "id=" + id +
                ", totalPrice=" + totalPrice +
                ", status=" + status +
                ", startDate=" + startDate +
                ", endDate=" + endDate +
                ", numberOfNights=" + numberOfNights +
                ", accommodation=" + accommodation +
                ", guest=" + guest +
                ", reviews=" + reviews +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ReservationDTO that)) return false;
        return id == that.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
