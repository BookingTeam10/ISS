package com.booking.ProjectISS.dto.accomodations;

import com.booking.ProjectISS.model.Reservation;
import com.booking.ProjectISS.model.accomodations.Amenity;
import com.booking.ProjectISS.model.accomodations.Location;
import com.booking.ProjectISS.model.accomodations.Price;
import com.booking.ProjectISS.model.accomodations.TakenDate;
import com.booking.ProjectISS.model.users.Owner;
import enums.TypeAccomodation;

import java.util.List;

public class AccomodationDTO {
    private Long id;
    private String description;
    private int minPeople;
    private int maxPeople;
    private String photo;
    private TypeAccomodation typeAccomodation;
    private double rating;
    private int cancelDeadline;
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getMinPeople() {
        return minPeople;
    }

    public void setMinPeople(int minPeople) {
        this.minPeople = minPeople;
    }

    public int getMaxPeople() {
        return maxPeople;
    }

    public void setMaxPeople(int maxPeople) {
        this.maxPeople = maxPeople;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public TypeAccomodation getTypeAccomodation() {
        return typeAccomodation;
    }

    public void setTypeAccomodation(TypeAccomodation typeAccomodation) {
        this.typeAccomodation = typeAccomodation;
    }

    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }

    public int getCancelDeadline() {
        return cancelDeadline;
    }

    public void setCancelDeadline(int cancelDeadline) {
        this.cancelDeadline = cancelDeadline;
    }

    public AccomodationDTO(Long id, String description, int minPeople, int maxPeople, String photo, TypeAccomodation typeAccomodation, double rating, int cancelDeadline) {
        this.id = id;
        this.description = description;
        this.minPeople = minPeople;
        this.maxPeople = maxPeople;
        this.photo = photo;
        this.typeAccomodation = typeAccomodation;
        this.rating = rating;
        this.cancelDeadline = cancelDeadline;
    }


}
