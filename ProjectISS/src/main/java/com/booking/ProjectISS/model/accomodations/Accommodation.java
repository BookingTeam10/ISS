package com.booking.ProjectISS.model.accomodations;

import com.booking.ProjectISS.enums.AccommodationStatus;
import com.booking.ProjectISS.model.reservations.Reservation;
import com.booking.ProjectISS.model.users.Owner;
import com.booking.ProjectISS.enums.TypeAccommodation;
import jakarta.persistence.*;
import org.hibernate.annotations.Fetch;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "accommodations")
@Embeddable
public class Accommodation implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="accommodation_id", nullable = false)
    private Long id;
    private boolean accepted;
    @Column(name = "automatic_activation")
    private boolean automaticActivation = false;
    private String description;
    @Column(name = "min_people")
    private int minPeople;
    @Column(name = "max_people")
    private int maxPeople;
    @ElementCollection(fetch = FetchType.EAGER)
    @Column(name = "photo")
    private List<String> photos;
    @Enumerated(EnumType.STRING)
    @Column(name = "type_acc")
    private TypeAccommodation typeAccomodation;
    @Enumerated(EnumType.STRING)
    @Column(name = "acc_status")
    private AccommodationStatus accommodationStatus;
    private double rating;
    @Column(name = "cancel_deadline")
    private int cancelDeadline;
    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "accommodation_id")
    private List<Price> prices;

    @Column(name = "weekend_price")
    private double weekendPrice;

    @Column(name = "holiday_price")
    private double holidayPrice;

    @Column(name = "acc_name")
    private String name;
    @Column(name = "summer_price")
    private double summerPrice;

    @Column(name = "night_person")
    private boolean isNight;

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "accommodation_taken_dates", joinColumns = @JoinColumn(name = "accommodation_id"))
    private List<TakenDate> takenDates;
    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "accommodation_amenity", joinColumns = @JoinColumn(name = "accommodation_id"))
    private List<Amenity> amenities;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "location_id",referencedColumnName = "id")
    private Location location;

    //dodati many to one
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "owner_id",referencedColumnName = "id")
    private Owner owner;
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Reservation> reservations;
    @Column(name = "auto_conf")
    private boolean automaticConfirmation=false;
    public Accommodation() {}

    public Accommodation(Long id, List<Reservation> reservations, List<Price> prices) {
        this.id = id;
        this.prices = prices;
        this.reservations = reservations;
    }

    public Accommodation(Long id, List<Reservation> reservations, List<Price> prices, int limitDay) {
        this.id = id;
        this.prices = prices;
        this.reservations = reservations;
        this.cancelDeadline=limitDay;
    }



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

    public void setTypeAccomodation(TypeAccommodation typeAccomodation) {
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
    public Location getLocation() {return location;}
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

    public double getWeekendPrice() {
        return weekendPrice;
    }

    public void setWeekendPrice(double weekendPrice) {
        this.weekendPrice = weekendPrice;
    }

    public double getHolidayPrice() {
        return holidayPrice;
    }

    public void setHolidayPrice(double holidayPrice) {
        this.holidayPrice = holidayPrice;
    }

    public double getSummerPrice() {
        return summerPrice;
    }

    public void setSummerPrice(double summerPrice) {
        this.summerPrice = summerPrice;
    }

    public boolean isNight() {
        return isNight;
    }

    public void setNight(boolean night) {
        isNight = night;
    }

    public Accommodation(String description) {
        this.description = description;
    }


    public Accommodation(Long id, boolean accepted, boolean automaticActivation, String description, int minPeople, int maxPeople,String name, List<String> photos, TypeAccommodation typeAccomodation, double rating, int cancelDeadline, List<Price> prices, List<TakenDate> takenDates, List<Amenity> amenities, Location location, Owner owner, List<Reservation> reservations,double weekendPrice, double holidayPrice, double summerPrice,boolean isNight, AccommodationStatus status) {

        this.id = id;
        this.accepted = accepted;
        this.automaticActivation = automaticActivation;
        this.description = description;
        this.minPeople = minPeople;
        this.maxPeople = maxPeople;
        this.photos = photos;
        this.typeAccomodation = typeAccomodation;
        this.rating = rating;
        this.cancelDeadline = cancelDeadline;
        this.prices = prices;
        this.takenDates = takenDates;
        this.amenities = amenities;
        this.location = location;
        this.owner = owner;
        this.reservations = reservations;
        this.accommodationStatus = status;
        this.automaticConfirmation = automaticActivation;
        this.weekendPrice=weekendPrice;
        this.holidayPrice=holidayPrice;
        this.summerPrice=summerPrice;
        this.isNight=isNight;
        this.name = name;
    }

    public boolean isAutomaticConfirmation() {
        return automaticConfirmation;
    }

    public void setAutomaticConfirmation(boolean automaticConfirmation) {
        this.automaticConfirmation = automaticConfirmation;
    }

    @Override
    public String toString() {
        return "Accommodation{" +
                "id=" + id +
                ", accepted=" + accepted +
                ", automaticActivation=" + automaticActivation +
                ", description='" + description + '\'' +
                ", minPeople=" + minPeople +
                ", maxPeople=" + maxPeople +
                ", photos=" + photos +
                ", typeAccomodation=" + typeAccomodation +
                ", accommodationStatus=" + accommodationStatus +
                ", rating=" + rating +
                ", cancelDeadline=" + cancelDeadline +
                ", prices=" + prices +
                ", weekendPrice=" + weekendPrice +
                ", holidayPrice=" + holidayPrice +
                ", name='" + name + '\'' +
                ", summerPrice=" + summerPrice +
                ", isNight=" + isNight +
                ", takenDates=" + takenDates +
                ", amenities=" + amenities +
                ", location=" + location +
                ", owner=" + owner +
                ", reservations=" + reservations +
                ", automaticConfirmation=" + automaticConfirmation +
                '}';
    }


    public Accommodation(Long id, int cancelDeadline, List<Price> prices, double weekendPrice, double holidayPrice, double summerPrice, List<Reservation> reservations) {
        this.id = id;
        this.cancelDeadline = cancelDeadline;
        this.prices = prices;
        this.weekendPrice = weekendPrice;
        this.holidayPrice = holidayPrice;
        this.summerPrice = summerPrice;
        this.reservations = reservations;
    }
    public Accommodation(Long id, int cancelDeadline, List<Price> prices, double weekendPrice, double holidayPrice, double summerPrice) {
        this.id = id;
        this.cancelDeadline = cancelDeadline;
        this.prices = prices;
        this.weekendPrice = weekendPrice;
        this.holidayPrice = holidayPrice;
        this.summerPrice = summerPrice;
    }


    public Accommodation(Long id, int cancelDeadline, List<Price> prices, List<Reservation> reservations) {
        this.id = id;
        this.cancelDeadline = cancelDeadline;
        this.prices = prices;
        this.reservations = reservations;
    }

    public Accommodation(Long id, int cancelDeadline, List<Price> prices) {
        this.id = id;
        this.cancelDeadline = cancelDeadline;
        this.prices = prices;
    }

    public Accommodation(Long id, int cancelDeadline) {
        this.id = id;
        this.cancelDeadline = cancelDeadline;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Accommodation that)) return false;
        return accepted == that.accepted && automaticActivation == that.automaticActivation && minPeople == that.minPeople && maxPeople == that.maxPeople && Double.compare(that.rating, rating) == 0 && cancelDeadline == that.cancelDeadline && Objects.equals(id, that.id) && Objects.equals(description, that.description) && Objects.equals(photos, that.photos) && typeAccomodation == that.typeAccomodation && Objects.equals(prices, that.prices) && Objects.equals(takenDates, that.takenDates) && Objects.equals(amenities, that.amenities) && Objects.equals(owner, that.owner) && Objects.equals(reservations, that.reservations);
    }
    @Override
    public int hashCode() {
        return Objects.hash(id, accepted, automaticActivation, description, minPeople, maxPeople, photos, typeAccomodation, rating, cancelDeadline, prices, takenDates, amenities, owner, reservations);
    }

    public AccommodationStatus getAccommodationStatus() {
        return accommodationStatus;
    }

    public void setAccommodationStatus(AccommodationStatus accommodationStatus) {
        this.accommodationStatus = accommodationStatus;
    }

    public void copyValues(Accommodation a) {
        this.setAccepted(a.isAccepted());
        this.description = a.getDescription();
        this.minPeople = a.getMinPeople();
        this.maxPeople = a.getMaxPeople();
        this.photos = a.getPhotos();
        this.typeAccomodation = a.getTypeAccomodation();
        this.rating = a.getRating();
        this.cancelDeadline = a.getCancelDeadline();
        this.location = a.getLocation();
        this.accommodationStatus = a.getAccommodationStatus();
        this.automaticConfirmation = a.isAutomaticConfirmation();
        this.name = a.getName();
        this.weekendPrice=a.getWeekendPrice();
        this.holidayPrice=a.getHolidayPrice();
        this.summerPrice=a.getSummerPrice();
        this.isNight=a.isNight();
    }
    public Accommodation(Accommodation accommodation) {
        this.id = accommodation.id;
        this.accepted = accommodation.accepted;
        this.automaticActivation = accommodation.automaticActivation;
        this.description = accommodation.description;
        this.minPeople = accommodation.minPeople;
        this.maxPeople = accommodation.maxPeople;
        this.photos = accommodation.photos;
        this.typeAccomodation = accommodation.typeAccomodation;
        this.rating = accommodation.rating;
        this.cancelDeadline = accommodation.cancelDeadline;
        this.prices = accommodation.prices;
        this.takenDates = accommodation.takenDates;
        this.amenities = accommodation.amenities;
        this.location = accommodation.location;
        this.owner = accommodation.owner;
        this.reservations = accommodation.reservations;
        this.name = accommodation.name;
        this.accommodationStatus= accommodation.accommodationStatus;
        this.automaticConfirmation = accommodation.isAutomaticConfirmation();
        this.weekendPrice=accommodation.weekendPrice;
        this.holidayPrice=accommodation.holidayPrice;
        this.summerPrice=accommodation.summerPrice;
        this.isNight=accommodation.isNight;
    }
    public Accommodation(Long id) {
        this.id = id;

    }
    public Accommodation(Long id,boolean automaticConfirmation) {
        this.id = id;
        this.automaticConfirmation = automaticConfirmation;

    }


}
