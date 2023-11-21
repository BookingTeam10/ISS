package com.booking.ProjectISS.dto.accomodations;

import com.booking.ProjectISS.model.accomodations.Location;

public class LocationDTO {

    private Long id;
    private String country;
    private String city;
    private String street;
    private int number;
    public LocationDTO() {
    }

    public LocationDTO(Long id,String country, String city, String street, int number) {
        this.id = id;
        this.country = country;
        this.city = city;
        this.street = street;
        this.number = number;
    }

    public LocationDTO(Location l){
        this.id = l.getId();
        this.country = l.getCountry();
        this.city = l.getCity();
        this.street = l.getStreet();
        this.number = l.getNumber();
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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
