package com.booking.ProjectISS.dto.accomodations;

import com.booking.ProjectISS.enums.TypeAccommodation;
import com.booking.ProjectISS.model.accomodations.Accommodation;

public class AccommodationDTO {
    private Long id;
    private String description;
    private int minPeople;
    private int maxPeople;
    private String photo;
    private TypeAccommodation typeAccomodation;
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

    public TypeAccommodation getTypeAccomodation() {
        return typeAccomodation;
    }

    public void setTypeAccomodation(TypeAccommodation typeAccommodation) {
        this.typeAccomodation = typeAccommodation;
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

    public AccommodationDTO(){

    }

    public AccommodationDTO(Long id, String description, int minPeople, int maxPeople, String photo, TypeAccommodation typeAccomodation, double rating, int cancelDeadline) {
        this.id = id;
        this.description = description;
        this.minPeople = minPeople;
        this.maxPeople = maxPeople;
        this.photo = photo;
        this.typeAccomodation = typeAccomodation;
        this.rating = rating;
        this.cancelDeadline = cancelDeadline;
    }

    public AccommodationDTO(Accommodation a) {
        this();
        this.id = a.getId();
        this.description = a.getDescription();
        this.minPeople = a.getMinPeople();
        this.maxPeople = a.getMaxPeople();
        this.photo =a.getPhoto();
        this.typeAccomodation = a.getTypeAccomodation();
        this.rating = a.getRating();
        this.cancelDeadline = a.getCancelDeadline();
    }


}
