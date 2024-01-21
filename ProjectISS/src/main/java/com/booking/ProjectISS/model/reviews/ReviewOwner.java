package com.booking.ProjectISS.model.reviews;

import com.booking.ProjectISS.enums.ReviewStatus;
import com.booking.ProjectISS.model.reservations.Reservation;
import com.booking.ProjectISS.model.users.Guest;
import com.booking.ProjectISS.model.users.Owner;
import jakarta.persistence.*;

import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name="review_owner")
public class ReviewOwner implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "rate")
    private double rate;
    @Column(name = "review_comment")
    private String comment;
    @Enumerated(EnumType.STRING)
    private ReviewStatus status;

    @Column(name="comment_date")
    private Date commentDate;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "owner_id", nullable = false)
    private Owner owner;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "guest_id", nullable = false)
    private Guest guest;

    @Column(name="is_reported")
    private boolean is_reported;

    public ReviewOwner() {
    }

    public ReviewOwner(double rate, String comment, ReviewStatus status) {
        this.rate = rate;
        this.comment = comment;
        this.status = status;
    }

    public Long getId() {
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

    public Date getCommentDate() {
        return commentDate;
    }

    public void setCommentDate(Date commentDate) {
        this.commentDate = commentDate;
    }

    public Owner getOwner() {
        return owner;
    }

    public void setOwner(Owner owner) {
        this.owner = owner;
    }

    public Guest getGuest() {
        return guest;
    }

    public void setGuest(Guest guest) {
        this.guest = guest;
    }

    public boolean isIs_reported() {
        return is_reported;
    }

    public void setIs_reported(boolean is_reported) {
        this.is_reported = is_reported;
    }

    @Override
    public String toString() {
        return "ReviewOwner{" +
                "id=" + id +
                ", rate=" + rate +
                ", comment='" + comment + '\'' +
                ", status=" + status +
                ", commentDate=" + commentDate +
                ", owner=" + owner +
                ", guest=" + guest +
                ", is_reported=" + is_reported +
                '}';
    }

    public void copyValues(Review review) {
    }

}
