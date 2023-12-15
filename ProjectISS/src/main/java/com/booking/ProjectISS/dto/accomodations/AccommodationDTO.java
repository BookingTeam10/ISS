package com.booking.ProjectISS.dto.accomodations;

import com.booking.ProjectISS.enums.TypeAccommodation;
import com.booking.ProjectISS.model.accomodations.Accommodation;
import com.booking.ProjectISS.model.accomodations.Location;
import com.booking.ProjectISS.model.users.Owner;

import java.util.List;

public class AccommodationDTO {
    private Long id;
    private String description;
    private int minPeople;
    private int maxPeople;
    private String name;
    private List<String> photos;
    private TypeAccommodation typeAccomodation;
    private double rating;
    private int cancelDeadline;
    private boolean isAccepted;
    private Location location;
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


    public List<String> getPhotos() {
        return photos;
    }
    public void setPhotos(List<String> photos) {
        this.photos = photos;
    }
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public boolean isAccepted() {
        return isAccepted;
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

    public void setAccepted(boolean accepted) {
        isAccepted = accepted;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public AccommodationDTO(){

    }

    public AccommodationDTO(Long id, String description, int minPeople, int maxPeople, List<String> photos, TypeAccommodation typeAccomodation, double rating, int cancelDeadline, boolean isAccepted,String name) {
        this.id = id;
        this.description = description;
        this.minPeople = minPeople;
        this.maxPeople = maxPeople;
        this.photos = photos;
        this.typeAccomodation = typeAccomodation;
        this.rating = rating;
        this.cancelDeadline = cancelDeadline;
        this.isAccepted=isAccepted;
        this.name = name;
    }

    public AccommodationDTO(Long id, String description, int minPeople, int maxPeople, List<String> photo, TypeAccommodation typeAccomodation, double rating, int cancelDeadline, boolean isAccepted,Location location,String name) {
        this.id = id;
        this.description = description;
        this.minPeople = minPeople;
        this.maxPeople = maxPeople;
        this.photos = photo;
        this.typeAccomodation = typeAccomodation;
        this.rating = rating;
        this.cancelDeadline = cancelDeadline;
        this.isAccepted=isAccepted;
        this.location = location;
        this.name = name;
    }

    public AccommodationDTO(Accommodation a) {
        this();
        this.id = a.getId();
        this.description = a.getDescription();
        this.minPeople = a.getMinPeople();
        this.maxPeople = a.getMaxPeople();
        this.photos =a.getPhotos();
        this.typeAccomodation = a.getTypeAccomodation();
        this.rating = a.getRating();
        this.cancelDeadline = a.getCancelDeadline();
        this.isAccepted = a.isAccepted();
        this.location = a.getLocation();
        this.name = a.getName();
    }

    @Override
    public String toString() {
        return "AccommodationDTO{" +
                "id=" + id +
                ", description='" + description + '\'' +
                ", minPeople=" + minPeople +
                ", maxPeople=" + maxPeople +
                ", photo='" + photos + '\'' +
                ", typeAccomodation=" + typeAccomodation +
                ", rating=" + rating +
                ", cancelDeadline=" + cancelDeadline +
                ", isAccepted=" + isAccepted +
                ", location=" + location +
                ", name=" + name +
                '}';
    }
}
