package com.booking.ProjectISS.dto.reviews;

import com.booking.ProjectISS.enums.ReviewStatus;
import com.booking.ProjectISS.model.reservations.Reservation;
import com.booking.ProjectISS.model.reviews.Review;

public class ReviewDTO {
    private long id;
    private double rate;
    private String comment;
    private ReviewStatus status;
    private Reservation reservation;

    public ReviewDTO() {
    }

    public ReviewDTO(long id, double rate, String comment, ReviewStatus status, Reservation reservation) {
        this.id = id;
        this.rate = rate;
        this.comment = comment;
        this.status = status;
        this.reservation = reservation;
    }

    public ReviewDTO(Review review) {
        this.id = review.getId();
        this.rate = review.getRate();
        this.comment = review.getComment();
        this.status = review.getStatus();
        this.reservation = review.getReservation();
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
}
