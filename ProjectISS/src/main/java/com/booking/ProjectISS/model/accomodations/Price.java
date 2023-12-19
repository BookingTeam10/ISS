package com.booking.ProjectISS.model.accomodations;

import jakarta.persistence.*;
import lombok.Getter;

import java.io.Serializable;
import java.util.Date;

@Getter
@Entity
@Table(name = "accommodation_price")
public class Price implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "price_id")
    private Long id;
    @Column(name="price")
    private double price;
    @Column(name="start_date")
    private Date startDate;
    @Column(name="end_date")
    private Date endDate;

    public Price() {
    }
    public Price(long id, double price, Date beginDate, Date endDate) {
        this.id = id;
        this.price = price;
        this.startDate = beginDate;
        this.endDate = endDate;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    @Override
    public String toString() {
        return "Price{" +
                "id=" + id +
                ", price=" + price +
                ", startDate=" + startDate +
                ", endDate=" + endDate +
                '}';
    }
}