package com.booking.ProjectISS.model.accomodations;

public class Amenity {
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
