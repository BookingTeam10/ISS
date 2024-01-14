package com.booking.ProjectISS.service.accommodation;

import com.booking.ProjectISS.dto.accomodations.AccommodationDTO;
import com.booking.ProjectISS.dto.accomodations.LocationDTO;
import com.booking.ProjectISS.dto.users.GuestDTO;
import com.booking.ProjectISS.dto.users.UserDTO;
import com.booking.ProjectISS.enums.TypeAccommodation;
import com.booking.ProjectISS.model.accomodations.Accommodation;
import com.booking.ProjectISS.model.accomodations.Amenity;
import com.booking.ProjectISS.model.accomodations.Location;
import com.booking.ProjectISS.model.accomodations.Price;
import com.booking.ProjectISS.model.reservations.Reservation;
import com.booking.ProjectISS.model.users.Owner;
import com.booking.ProjectISS.model.users.User;
import com.booking.ProjectISS.repository.accomodations.IAccommodationRepository;
import com.booking.ProjectISS.repository.accomodations.location.ILocationRepository;
import com.booking.ProjectISS.repository.reservations.IReservationRepository;
import com.booking.ProjectISS.repository.users.guests.IGuestRepository;
import com.booking.ProjectISS.service.accommodation.location.ILocationService;
import com.booking.ProjectISS.service.accommodation.price.IPriceService;
import com.booking.ProjectISS.service.reservations.IReservationService;
import com.booking.ProjectISS.service.users.owner.IOwnerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class AccommodationService implements IAccommodationService {

    @Autowired
    private IAccommodationRepository accommodationRepository;
    @Autowired
    private ILocationService locationService;
    @Autowired
    private IPriceService priceService;

    @Autowired
    private IOwnerService ownerService;

    @Autowired
    private IReservationRepository reservationRepository;

    //ResourceBundle bundle = ResourceBundle.getBundle("ValidationMessages", LocaleContextHolder.getLocale());
    @Override
    public AccommodationDTO findOneDTO(Long id) {
        Optional<Accommodation> found = accommodationRepository.findById(id);
        if(found.isEmpty())
            return null;
        return new AccommodationDTO(found.get());
    }
    @Override
    public Accommodation findOne(Long id) {
        Optional<Accommodation> found = accommodationRepository.findById(id);

        if(found.isEmpty()){
            return null;
        }
        return found.get();
    }

    @Override
    public Collection<AccommodationDTO> findAllDTO() {
        Collection<Accommodation> accommodations = accommodationRepository.findAll();
        Collection<AccommodationDTO> accommodationDTOS= new ArrayList<AccommodationDTO>();
        for(Accommodation a : accommodations)
            accommodationDTOS.add(new AccommodationDTO(a));
        return accommodationDTOS;
    }

    @Override
    public Collection<Accommodation> findAll() {
        return accommodationRepository.findAll();
    }
    @Override
    public void delete(Long id) {
        Accommodation found = findOne(id);
        accommodationRepository.delete(found);
        accommodationRepository.flush();
    }
    @Override
    public AccommodationDTO create(Accommodation accommodation) throws Exception {
        return new AccommodationDTO(accommodationRepository.save(new Accommodation(accommodation)));
    }

    @Override
    public AccommodationDTO update(Accommodation accommodation) throws Exception {
        return null;
    }

    @Override
    public String updateAccommodation(Accommodation accommodationForUpdate){

        String message="";

        System.out.println(accommodationForUpdate);

        Optional<Accommodation> optionalAccommodation = this.accommodationRepository.findById(accommodationForUpdate.getId());

        boolean change=canChangeDates(accommodationForUpdate);

        if(change){
            optionalAccommodation.ifPresent(oldAccommodation -> {
                oldAccommodation.setCancelDeadline(accommodationForUpdate.getCancelDeadline());
                oldAccommodation.setPrices(accommodationForUpdate.getPrices());
                this.updatePrices(accommodationForUpdate.getPrices());
                oldAccommodation.setHolidayPrice(accommodationForUpdate.getHolidayPrice());
                oldAccommodation.setWeekendPrice(accommodationForUpdate.getWeekendPrice());
                oldAccommodation.setSummerPrice(accommodationForUpdate.getSummerPrice());
                this.accommodationRepository.save(oldAccommodation);
            });
            message="Successful edit";
        }else{
            if(optionalAccommodation.get().getCancelDeadline()==accommodationForUpdate.getCancelDeadline()){
                message="You can only change the cancelled deadline, due to reservations";
            }else{
                optionalAccommodation.ifPresent(oldAccommodation -> {
                    oldAccommodation.setCancelDeadline(accommodationForUpdate.getCancelDeadline());
                    this.accommodationRepository.save(oldAccommodation);
                });
                message="Changed only cancelled deadline, due to reservations";
            }
        }
        return message;
    }

    private boolean canChangeDates(Accommodation accommodationForUpdate) {
        List<Price> prices=accommodationForUpdate.getPrices();
        Collection<Reservation> reservations= reservationRepository.findByAccommodation(accommodationForUpdate.getId());

        for(Reservation reservation:reservations){
            Date startDateReservation=reservation.getStartDate();
            Date endDateReservation=reservation.getEndDate();
            for(Price price:prices){
                if((startDateReservation.after(price.getStartDate()) && startDateReservation.before(price.getEndDate())) || (endDateReservation.after(price.getStartDate()) && endDateReservation.before(price.getEndDate()))){
                    return false;
                }
            }
        }
        return true;
    }

    private void updatePrices(List<Price> prices) {
        for(Price p: prices){
            this.priceService.update(p);
        }
    }

    private void updateLocation(Location location)  {
        locationService.update(location);
    }

    @Override
    public Collection<AccommodationDTO> findAllByOwnerDTO(Long id) {
        Collection<Accommodation> accommodations = accommodationRepository.findAllByOwner(id);
        System.out.println(accommodations);
        Collection<AccommodationDTO> accommodationDTOS= new ArrayList<AccommodationDTO>();
        for(Accommodation a : accommodations)
            accommodationDTOS.add(new AccommodationDTO(a));
        return accommodationDTOS;
    }
    @Override
    public Collection<Accommodation> findAllByOwner(Long id) {
        Collection<Accommodation> accommodations = accommodationRepository.findAllByOwner(id);
        return accommodations;
    }
    @Override
    public AccommodationDTO createByOwner(Long id, Accommodation accommodation) {
//        Accommodation savedAccommodation= accommodationRepository.createByOwner(id, accommodation);
//        return new AccommodationDTO(savedAccommodation);
        return null;
    }
    @Override
    public Collection<AccommodationDTO> getAccommodationsSearched(Date start, Date end, int numPeople,String location) {
        Collection<Accommodation> accommodations = accommodationRepository.findAll();
        Collection<AccommodationDTO> accommodationDTOS= new ArrayList<AccommodationDTO>();
        for (Accommodation a:accommodations){
            if(a.getMinPeople()<=numPeople && a.getMaxPeople()>=numPeople && matchesLocation(a,location))
                accommodationDTOS.add(new AccommodationDTO(a));
        }
        return accommodationDTOS;
    }

    @Override
    public Collection<AccommodationDTO> getAccommodationsSearched(Date start, Date end, int numPeople,String location,String minPrice,String maxPrice, List<String> amenities) {

        Collection<Accommodation> accommodations = accommodationRepository.findAll();
        Collection<AccommodationDTO> accommodationDTOS= new ArrayList<AccommodationDTO>();
        for (Accommodation a:accommodations){
            if(a.getMinPeople()<=numPeople && a.getMaxPeople()>=numPeople && matchesLocation(a,location) && matchesAmenity(a,amenities))
                accommodationDTOS.add(new AccommodationDTO(a));
        }
        System.out.println(accommodationDTOS);
        return accommodationDTOS;
    }

    private boolean matchesAmenity(Accommodation a, List<String> amenities) {
        List<Amenity> amenitiesList = new ArrayList<>();
        for(String amenity:amenities){
            System.out.println(amenity);
            amenitiesList.add(new Amenity(amenity));
        }
        if(new HashSet<>(a.getAmenities()).containsAll(amenitiesList)) {
            return true;
        }
        return false;
    }

    @Override
    public boolean deleteAllByOwner(Long id) {

        accommodationRepository.deleteAll(accommodationRepository.findAllByOwner(id));
        return true;
    }

    @Override
    public AccommodationDTO add(Accommodation accommodation) {
        System.out.println(accommodation);
        accommodation.setTypeAccomodation(TypeAccommodation.Apartment);
        Owner owner=ownerService.findOne(1L);
        accommodation.setOwner(owner);
        System.out.println(accommodation);
        return new AccommodationDTO(this.accommodationRepository.save(accommodation));
    }

    private boolean matchesLocation(Accommodation accomodation, String location) {
        if(location == null || location.isEmpty()){return true;}
        Location accomodationLocation = accomodation.getLocation();
        String country = accomodationLocation.getCountry();
        String city = accomodationLocation.getCity();
        String street = accomodationLocation.getStreet();

        return containsIgnoreCase(country, location) || containsIgnoreCase(city, location) || containsIgnoreCase(street, location);
    }
    private boolean containsIgnoreCase(String str, String searchTerm) {
        return str != null && str.toLowerCase().contains(searchTerm.toLowerCase());
    }
}
