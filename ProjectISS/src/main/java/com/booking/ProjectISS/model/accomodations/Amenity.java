package com.booking.ProjectISS.model.accomodations;

import java.io.Serializable;

public class Amenity implements Serializable {
    private String name;

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
