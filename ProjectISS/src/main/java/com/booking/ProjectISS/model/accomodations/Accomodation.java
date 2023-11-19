package com.booking.ProjectISS.model.accomodations;

import com.booking.ProjectISS.model.Reservation;
import com.booking.ProjectISS.model.users.Owner;
import enums.TypeAccomodation;

import java.util.List;

public class Accomodation {
    private Long id;
    private boolean accepted;
    private boolean automaticActivation = false;
    private String description;
    private int minPeople;
    private int maxPeople;
    private String photo;
    private TypeAccomodation typeAccomodation;
    private double rating;
    private int cancelDeadline;
    private List<Price> prices;
    private List<TakenDate> takenDates;
    private List<Amenity> amenities;
    private Location location;
    private Owner owner;
    private List<Reservation> reservations;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public boolean isAccepted() {
        return accepted;
    }

    public void setAccepted(boolean accepted) {
        this.accepted = accepted;
    }

    public boolean isAutomaticActivation() {
        return automaticActivation;
    }

    public void setAutomaticActivation(boolean automaticActivation) {
        this.automaticActivation = automaticActivation;
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

    public List<Price> getPrices() {
        return prices;
    }

    public void setPrices(List<Price> prices) {
        this.prices = prices;
    }

    public List<TakenDate> getTakenDates() {
        return takenDates;
    }

    public void setTakenDates(List<TakenDate> takenDates) {
        this.takenDates = takenDates;
    }

    public List<Amenity> getAmenities() {
        return amenities;
    }

    public void setAmenities(List<Amenity> amenities) {
        this.amenities = amenities;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public Owner getOwner() {
        return owner;
    }

    public void setOwner(Owner owner) {
        this.owner = owner;
    }

    public List<Reservation> getReservations() {
        return reservations;
    }

    public void setReservations(List<Reservation> reservations) {
        this.reservations = reservations;
    }

    public Accomodation(Long id, boolean accepted, boolean automaticActivation, String description, int minPeople, int maxPeople, String photo, TypeAccomodation typeAccomodation, double rating, int cancelDeadline, List<Price> prices, List<TakenDate> takenDates, List<Amenity> amenities, Location location, Owner owner, List<Reservation> reservations) {
        this.id = id;
        this.accepted = accepted;
        this.automaticActivation = automaticActivation;
        this.description = description;
        this.minPeople = minPeople;
        this.maxPeople = maxPeople;
        this.photo = photo;
        this.typeAccomodation = typeAccomodation;
        this.rating = rating;
        this.cancelDeadline = cancelDeadline;
        this.prices = prices;
        this.takenDates = takenDates;
        this.amenities = amenities;
        this.location = location;
        this.owner = owner;
        this.reservations = reservations;
    }

    @Override
    public String toString() {
        return "Accomodation{" +
                "id=" + id +
                ", accepted=" + accepted +
                ", automaticActivation=" + automaticActivation +
                ", description='" + description + '\'' +
                ", minPeople=" + minPeople +
                ", maxPeople=" + maxPeople +
                ", photo='" + photo + '\'' +
                ", typeAccomodation=" + typeAccomodation +
                ", rating=" + rating +
                ", cancelDeadline=" + cancelDeadline +
                ", prices=" + prices +
                ", takenDates=" + takenDates +
                ", amenities=" + amenities +
                ", location=" + location +
                ", owner=" + owner +
                ", reservations=" + reservations +
                '}';
    }
}
