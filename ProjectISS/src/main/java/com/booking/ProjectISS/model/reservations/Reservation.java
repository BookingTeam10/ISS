package com.booking.ProjectISS.model.reservations;

import com.booking.ProjectISS.enums.ReservationStatus;
import com.booking.ProjectISS.model.accomodations.Accommodation;
import com.booking.ProjectISS.model.reviews.Review;
import com.booking.ProjectISS.model.users.Guest;

import java.util.Date;
import java.util.List;

//@Entity
public class Reservation {
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private double totalPrice;
//    @Enumerated(EnumType.STRING)
    private ReservationStatus status;
//    @Temporal(TemporalType.DATE)
    private Date firstDate;
    private int numberOfNights = 1;
//    @OneToOne
    private Accommodation accommodation;
    private Guest guest;
//    @OneToMany
    private List<Review> reviews;
    public Reservation() {
    }

    public Reservation(double totalPrice, ReservationStatus status, Date firstDate, int numberOfNights) {
        this.totalPrice = totalPrice;
        this.status = status;
        this.firstDate = firstDate;
        this.numberOfNights = numberOfNights;
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
                ", firstDate=" + firstDate +
                ", numberOfNights=" + numberOfNights +
                '}';
    }
}