package com.booking.ProjectISS.dto.reviews;

import com.booking.ProjectISS.enums.ReviewStatus;
import com.booking.ProjectISS.model.reservations.Reservation;
import com.booking.ProjectISS.model.reviews.Review;
import com.booking.ProjectISS.model.reviews.ReviewOwner;
import com.booking.ProjectISS.model.users.Owner;

public class ReviewOwnerDTO {

    private long id;
    private double rate;
    private String comment;
    private ReviewStatus status;

    private double ratingOwner;
    public ReviewOwnerDTO() {
    }

    public ReviewOwnerDTO(long id, double rate, String comment, ReviewStatus status, double ratingOwner) {
        this.id = id;
        this.rate = rate;
        this.comment = comment;
        this.status = status;
        this.ratingOwner= ratingOwner;
    }

    public ReviewOwnerDTO(ReviewOwner review) {
        this.id = review.getId();
        this.rate = review.getRate();
        this.comment = review.getComment();
        this.status = review.getStatus();
        this.ratingOwner = review.getOwner().getRating();
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

}
