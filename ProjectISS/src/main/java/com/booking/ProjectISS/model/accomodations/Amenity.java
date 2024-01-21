package com.booking.ProjectISS.model.accomodations;

import jakarta.persistence.*;

import java.io.Serializable;
import java.util.Objects;

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
        return "name='" + name + "'";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Amenity amenity)) return false;
        return Objects.equals(name, amenity.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }
}
