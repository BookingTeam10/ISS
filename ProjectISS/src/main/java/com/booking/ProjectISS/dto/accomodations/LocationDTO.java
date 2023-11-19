package com.booking.ProjectISS.dto.accomodations;

import com.booking.ProjectISS.model.accomodations.Accomodation;

import java.util.List;

public class LocationDTO {
    private String country;
    private String city;
    private String street;
    private int number;
    public LocationDTO() {
    }

    public LocationDTO(String country, String city, String street, int number) {
        this.country = country;
        this.city = city;
        this.street = street;
        this.number = number;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }
}
