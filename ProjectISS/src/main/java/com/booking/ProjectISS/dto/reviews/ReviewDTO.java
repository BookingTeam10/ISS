package com.booking.ProjectISS.dto.reviews;

import com.booking.ProjectISS.enums.ReviewStatus;
import com.booking.ProjectISS.model.reviews.Review;

public class ReviewDTO {
    private int id;
    private double rate;
    private String comment;
    private ReviewStatus status;
    private int reservationId;

    public ReviewDTO() {
    }

    public ReviewDTO(int id, double rate, String comment, ReviewStatus status, int reservationId) {
        this.id = id;
        this.rate = rate;
        this.comment = comment;
        this.status = status;
        this.reservationId = reservationId;
    }

    public ReviewDTO(Review review) {
        this.id = review.getId();
        this.rate = review.getRate();
        this.comment = review.getComment();
        this.status = review.getStatus();
        this.reservationId = review.getReservation().getId();
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
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

    public int getReservationId() {
        return reservationId;
    }

    public void setReservationId(int reservationId) {
        this.reservationId = reservationId;
    }
}
