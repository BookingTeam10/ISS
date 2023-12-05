package com.booking.ProjectISS.repository.accomodations;

import com.booking.ProjectISS.enums.TypeAccommodation;
import com.booking.ProjectISS.model.accomodations.Accommodation;
import com.booking.ProjectISS.model.accomodations.Location;
import com.booking.ProjectISS.model.users.Owner;
import com.booking.ProjectISS.model.users.User;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicLong;

//@Repository
//public class AccommodationRepository implements IAccommodationRepository{
//    private List<Accommodation> accommodations;
//    private static AtomicLong counter = new AtomicLong();
//
//    public AccommodationRepository(){
//        this.accommodations = loadAll();
//    }
//
//    @Override
//    public Accommodation findOne(Long id) {
//        for(Accommodation a:accommodations ){
//            if(Objects.equals(a.getId(), id)){
//                return a;
//            }
//        }
//        return null;
//    }
//    @Override
//    public Collection<Accommodation> findAll() {
//        return accommodations;
//    }
//    @Override
//    public void delete(Long id) {
//        accommodations.removeIf(a -> a.getId() == id);
//    }
//    @Override
//    public Accommodation create(Accommodation accommodation) {
//        Long id = accommodation.getId();
//
//        if (id == null) {
//            id = counter.incrementAndGet();
//            accommodation.setId(id);
//        }
//
//        this.accommodations.add(accommodation);
//        return accommodation;
//    }
//
//    @Override
//    public Collection<Accommodation> findAllByOwner(Long id) {
//        List<Accommodation> accomodationsByOwner=new ArrayList<Accommodation>();
//        for(Accommodation a:this.accommodations){
//            if(a.getOwner().getId().equals(id)){
//                accomodationsByOwner.add(a);
//            }
//        }
//        return accomodationsByOwner;
//    }
//
//    @Override
//    public Accommodation createByOwner(Long id, Accommodation accommodation) {
//        Long idAcc = accommodation.getId();
//
//        if (idAcc == null) {
//            id = counter.incrementAndGet();
//            accommodation.setId(id);
//            accommodation.setOwner(new Owner(id));
//        }
//
//        this.accommodations.add(accommodation);
//        return accommodation;
//    }
//
//    public List<Accommodation> getAccommodations() {
//        return accommodations;
//    }
//    public void addAccommodation(Accommodation a){
//        this.accommodations.add(a);
//    }
//
//    public static AtomicLong getCounter() {
//        return counter;
//    }
//
//    private List<Accommodation> loadAll() {
//        this.accommodations=new ArrayList<Accommodation>();
//        Accommodation a1 = new Accommodation(5L,false,false,"Welcome to our stylish and spacious 2-bedroom apartment nestled in the heart of the city. This urban oasis is designed for comfort and convenience, offering a master bedroom with a queen-sized bed, a cozy second bedroom with twin beds, a fully equipped kitchen, and a modern living area. Enjoy the city skyline from your private balcony or unwind in the contemporary living space. ",2,4,"dasasd",
//                TypeAccommodation.Apartment,2,2,null,null,null,new Location("Srbija","Novi Sad","Bulevar"),new Owner(6L,"a","b","c","d","065555555","fwae",true,false),null);
//        Accommodation a2 = new Accommodation(6L,true,false,"Escape to our charming 2-bedroom apartment with a touch of coastal elegance. Located just a short stroll from the beach, this retreat features a master bedroom with a king-sized bed, a second bedroom with a double bed, a fully equipped kitchen, and a cozy living area. Immerse yourself in the seaside atmosphere on your private patio or explore the nearby shores.",7,8,"dasasd",
//                TypeAccommodation.Apartment,2,2,null,null,null,new Location("Srbija","Novi Sad","Gunduliceva"),new Owner(9L,"a","b","c","d","065555555","fwae",true,true),null);
//        Accommodation a3 = new Accommodation(7L,false,false,"Experience urban living at its finest in our modern chic studio suite. This thoughtfully designed space is perfect for a couple looking for a trendy and comfortable stay. Enjoy the sleek design of the open-concept living area, a plush queen-sized bed, a fully equipped kitchenette, and a stylish bathroom.",3,5,"dasasd",
//                TypeAccommodation.Apartment,2,2,null,null,null,new Location("Srbija","Sabac","Save Mrkalja"),new Owner(6L,"a","b","c","d","065555555","fwae",true,false),null);
//
//        accommodations.add(a1);
//        accommodations.add(a2);
//        accommodations.add(a3);
//        return accommodations;
//    }
//}
