package com.booking.ProjectISS.model.accomodations;

import java.util.Date;

public class TakenDate {
    private Date firstDate;
    private Date lastDate;

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
}
