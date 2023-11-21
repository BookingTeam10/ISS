package com.booking.ProjectISS.model.accomodations;

import java.util.List;
import java.util.Objects;

public class Location {
    private Long id;
    private String country;
    private String city;
    private String street;
    private int number;
    private List<Accommodation> accomodations;

    public Location() {
    }

    public Location(Long id,String country, String city, String street, int number, List<Accommodation> accomodations) {
        this.id = id;
        this.country = country;
        this.city = city;
        this.street = street;
        this.number = number;
        this.accomodations = accomodations;
    }

    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Location location)) return false;
        return number == location.number && Objects.equals(id, location.id) && Objects.equals(country, location.country) && Objects.equals(city, location.city) && Objects.equals(street, location.street) && Objects.equals(accomodations, location.accomodations);
    }
    @Override
    public int hashCode() {
        return Objects.hash(id, country, city, street, number, accomodations);
    }

    public void copyValues(Location l) {
        this.country = l.getCountry();
        this.city = l.getCity();
        this.street = l.getStreet();
        this.number = l.getNumber();
    }
}
