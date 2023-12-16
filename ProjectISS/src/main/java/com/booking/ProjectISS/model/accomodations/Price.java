package com.booking.ProjectISS.model.accomodations;

import jakarta.persistence.*;

import java.io.Serializable;
import java.util.Date;

@Embeddable
public class Price implements Serializable {
    @Column(name="price")
    private double price;
    @Column(name="begin_date")
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

    @Override
    public String toString() {
        return "Price{" +
                "price=" + price +
                ", beginDate=" + beginDate +
                '}';
    }
}
