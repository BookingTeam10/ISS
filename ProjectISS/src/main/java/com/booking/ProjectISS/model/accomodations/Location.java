package com.booking.ProjectISS.model.accomodations;

import jakarta.persistence.*;
import org.hibernate.annotations.GenericGenerator;

import java.io.Serializable;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name="locations")
public class Location implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id", nullable = false)
    private Long id;
    private String country;
    private String city;
    private String street;
    @Column(name="number_street")
    private int number;
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Accommodation> accommodations;


    public Location() {
    }

    public Location(Long id,String country, String city, String street, int number,List<Accommodation> accommodations) {
        this.id = id;
        this.country = country;
        this.city = city;
        this.street = street;
        this.number = number;
        this.accommodations = accommodations;
    }

    public Location(String country, String city, String street) {
        this.country = country;
        this.city = city;
        this.street = street;
    }

    public Location(Location location) {
        this.id = location.id;
        this.country = location.country;
        this.city = location.city;
        this.street = location.street;
        this.number=location.number;
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

    public List<Accommodation> getAccommodations() {
        return accommodations;
    }

    public void setAccommodations(List<Accommodation> accommodations) {
        this.accommodations = accommodations;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Location location)) return false;
        return number == location.number && Objects.equals(id, location.id) && Objects.equals(country, location.country) && Objects.equals(city, location.city) && Objects.equals(street, location.street);
    }

    public void copyValues(Location l) {
        this.country = l.getCountry();
        this.city = l.getCity();
        this.street = l.getStreet();
        this.number = l.getNumber();
        this.accommodations = l.getAccommodations();
    }

    @Override
    public String toString() {
        return "Location{" +
                "id=" + id +
                ", country='" + country + '\'' +
                ", city='" + city + '\'' +
                ", street='" + street + '\'' +
                ", number=" + number +
                ", accommodations=" + accommodations +
                '}';
    }
}
