package com.booking.ProjectISS.model.accomodations;

import jakarta.persistence.*;

import java.io.Serializable;
//@Entity
//@Table(name="amenity")
@Embeddable
public class Amenity implements Serializable {
    @Column(name="amenity_name")
    private String name;

    public Amenity() {}
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public Amenity(String name) {
        this.name = name;
    }
    @Override
    public String toString() {
        return "Amenity{" +
                "name='" + name + '\'' +
                '}';
    }
}
