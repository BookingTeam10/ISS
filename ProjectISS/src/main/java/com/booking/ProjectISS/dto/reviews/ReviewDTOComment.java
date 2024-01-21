package com.booking.ProjectISS.dto.reviews;

import com.booking.ProjectISS.enums.ReviewStatus;
import com.booking.ProjectISS.model.reviews.Review;

public class ReviewDTOComment {
    private long id;
    private String comment;
    private ReviewStatus status;

    public ReviewDTOComment(long id, String comment, ReviewStatus status) {
        this.id = id;
        this.comment = comment;
        this.status = status;
    }

    public ReviewDTOComment() {
    }

    public ReviewDTOComment(Review r) {
        this.id = r.getId();
        this.comment = r.getComment();
        this.status = r.getStatus();
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
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
    @Override
    public String toString() {
        return "ReviewDTOComment{" +
                "id=" + id +
                ", comment='" + comment + '\'' +
                ", status=" + status +
                '}';
    }
}
