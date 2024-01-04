package com.booking.ProjectISS.model.users;

import com.booking.ProjectISS.enums.ReviewStatus;
import jakarta.persistence.*;

import java.io.Serializable;

@Entity
@Table(name="report")
@Inheritance(strategy = InheritanceType.JOINED)
public class ReportUser implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)         //increment add id+1, e.g. if previous id was 5, the next will be 6
    private Long id;

    @Column(name = "review_comment")
    private String comment;

    @Enumerated(EnumType.STRING)
    private ReviewStatus status;


    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "owner_id", nullable = false)
    private Owner owner;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "guest_id", nullable = false)
    private Guest guest;

    @Column(name = "user_report")
    private String userReportUser;

    public ReportUser() {
    }

    public ReportUser(Long id, String comment, ReviewStatus status, Owner owner, Guest guest, String userReportUser) {
        this.id = id;
        this.comment = comment;
        this.status = status;
        this.owner = owner;
        this.guest = guest;
        this.userReportUser = userReportUser;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
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

    public String getUserReportUser() {
        return userReportUser;
    }

    public void setUserReportUser(String userReportUser) {
        this.userReportUser = userReportUser;
    }

    @Override
    public String toString() {
        return "ReportUser{" +
                "id=" + id +
                ", comment='" + comment + '\'' +
                ", status=" + status +
                ", owner=" + owner +
                ", guest=" + guest +
                ", userReportUser='" + userReportUser + '\'' +
                '}';
    }
}
