package com.booking.ProjectISS.model.reviews;

import com.booking.ProjectISS.enums.ReviewStatus;
import com.booking.ProjectISS.model.reservations.Reservation;

//@Entity
public class Review {
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private double rate;
    private String comment;

//    @Enumerated(EnumType.STRING)
    private ReviewStatus status;

//    @ManyToOne
    private Reservation reservation;

    public Review() {
    }

    public Review(double rate, String comment, ReviewStatus status) {
        this.rate = rate;
        this.comment = comment;
        this.status = status;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public double getRate() {
        return rate;
    }

    public void setRate(double rate) {
        this.rate = rate;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public ReviewStatus getStatus() {
        return status;
    }

    public void setStatus(ReviewStatus status) {
        this.status = status;
    }

    public Reservation getReservation() {
        return reservation;
    }

    public void setReservation(Reservation reservation) {
        this.reservation = reservation;
    }


    @Override
    public String toString() {
        return "Review{" +
                "id=" + id +
                ", rate=" + rate +
                ", comment='" + comment + '\'' +
                ", status=" + status +
                '}';
    }

    public void copyValues(Review review) {
    }
}

