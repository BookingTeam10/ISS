package com.booking.ProjectISS.model.reservations;

import com.booking.ProjectISS.enums.ReservationStatus;
import com.booking.ProjectISS.model.CustomDateDeserializer;
import com.booking.ProjectISS.model.accomodations.Accommodation;
import com.booking.ProjectISS.model.reviews.Review;
import com.booking.ProjectISS.model.users.Guest;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import jakarta.persistence.*;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "reservations")
public class Reservation implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private long id;
    @Column(name= "total_price")
    private double totalPrice;
    @Enumerated(EnumType.STRING)
    @Column(name= "reservation_status")
    private ReservationStatus status;
    @Temporal(TemporalType.DATE)
    @Column(name = "start_date")
    private Date startDate;
    @Temporal(TemporalType.DATE)
    @Column(name = "end_date")
    private Date endDate;
    @Column(name="number_of_nights")
    private int numberOfNights = 1;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "accommodation_id", nullable = false)
    private Accommodation accommodation;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "guest_id")
    private Guest guest;
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Review> reviews;
    public Reservation() {
    }

    public Reservation(long id) {
        this.id = id;
    }

    public Reservation(long id, Date startDate, Date endDate) {
        this.id = id;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public Reservation(double totalPrice, ReservationStatus status, Date startDate, Date endDate, int numberOfNights) {
        this.totalPrice = totalPrice;
        this.status = status;
        this.startDate = startDate;
        this.endDate = endDate;
        this.numberOfNights = numberOfNights;
    }

    public Reservation(long id, ReservationStatus status, Date startDate, Date endDate, Accommodation accommodation) {
        this.id = id;
        this.status = status;
        this.startDate = startDate;
        this.endDate = endDate;
        this.accommodation = accommodation;
    }
    public Reservation(long id, Date startDate, Date endDate, Accommodation accommodation) {
        this.id = id;
        this.status = status;
        this.startDate = startDate;
        this.endDate = endDate;
        this.accommodation = accommodation;
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

    public Accommodation getAccommodation() {
        return accommodation;
    }

    public void setAccommodation(Accommodation accommodation) {
        this.accommodation = accommodation;
    }

    @Override
    public String toString() {
        return "Reservation{" +
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
    public void copyValues(Reservation reservation) {
    }
    public Reservation( Long id,double totalPrice,ReservationStatus reservationStatus, Date startDate, Date endDate,int numberOfNights,Accommodation accommodation,Guest guest,List<Review> reviews) {
        this.id = id;
        this.totalPrice = totalPrice;
        this.startDate = startDate;
        this.endDate = endDate;
        this.status = reservationStatus;
        this.accommodation = accommodation;
        this.guest = guest;
        this.numberOfNights = numberOfNights;
        this.reviews = reviews;
    }



}