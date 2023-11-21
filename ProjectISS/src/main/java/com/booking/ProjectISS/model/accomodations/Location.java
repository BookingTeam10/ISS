package com.booking.ProjectISS.model.accomodations;

import java.util.List;

public class Location {
    private String country;
    private String city;
    private String street;
    private int number;
    private List<Accommodation> accomodations;

    public Location() {
    }

    public Location(String country, String city, String street, int number, List<Accommodation> accomodations) {
        this.country = country;
        this.city = city;
        this.street = street;
        this.number = number;
        this.accomodations = accomodations;
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

    public List<Accommodation> getAccomodations() {
        return accomodations;
    }

    public void setAccomodations(List<Accommodation> accomodations) {
        this.accomodations = accomodations;
    }


}
