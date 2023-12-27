package com.booking.ProjectISS.model.accomodations;

import jakarta.persistence.*;

import java.io.Serializable;
import java.util.Date;

@Embeddable
public class TakenDate implements Serializable {
    @Column(name="first_date")
    private Date firstDate;
    @Column(name="last_date")
    private Date lastDate;

    public TakenDate() {

    }

    public Date getFirstDate() {
        return firstDate;
    }

    public void setFirstDate(Date firstDate) {
        this.firstDate = firstDate;
    }

    public Date getLastDate() {
        return lastDate;
    }

    public void setLastDate(Date lastDate) {
        this.lastDate = lastDate;
    }

    public TakenDate(Date firstDate, Date lastDate) {
        this.firstDate = firstDate;
        this.lastDate = lastDate;
    }
    @Override
    public String toString() {
        return "TakenDate{" +
                "firstDate=" + firstDate +
                ", lastDate=" + lastDate +
                '}';
    }
}
