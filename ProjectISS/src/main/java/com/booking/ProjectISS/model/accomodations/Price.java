package com.booking.ProjectISS.model.accomodations;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.io.Serializable;
import java.util.Date;

public class Price implements Serializable {
    private double price;

    private Date beginDate;

    public Price() {
    }
    public Price(double price, Date beginDate) {
        this.price = price;
        this.beginDate = beginDate;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public Date getBeginDate() {
        return beginDate;
    }

    public void setBeginDate(Date beginDate) {
        this.beginDate = beginDate;
    }
}
