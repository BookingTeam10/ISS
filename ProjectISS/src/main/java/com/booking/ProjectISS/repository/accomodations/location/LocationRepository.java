package com.booking.ProjectISS.repository.accomodations.location;

import com.booking.ProjectISS.enums.TypeAccommodation;
import com.booking.ProjectISS.model.accomodations.Accommodation;
import com.booking.ProjectISS.model.accomodations.Location;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicLong;

@Repository
public class LocationRepository implements ILocationRepository{

    private List<Location> locations;
    private static AtomicLong counter = new AtomicLong();
    public LocationRepository(){
        this.locations = loadAll();
    }
    @Override
    public Location findOne(Long id) {
        for(Location l:locations){
            if(Objects.equals(l.getId(), id)){
                return l;
            }
        }
        return null;
    }

    @Override
    public Collection<Location> findAll() {
        return locations;
    }
    @Override
    public void delete(Long id) {
       locations.removeIf(l -> l.getId() == id);
    }

    @Override
    public Location create(Location location) {
        Long id = location.getId();

        if (id == null) {
            id = counter.incrementAndGet();
            location.setId(id);
        }

        this.locations.add(location);
        return location;
    }
    private List<Location> loadAll() {
        this.locations=new ArrayList<Location>();
        Location l1 = new Location(1L,"Srbija","Novi Sad","Gunduliceva",21,null);
        Location l2 = new Location(2L,"Srbija","Sabac","Save Mrkalja",12,null);
        Location l3 = new Location(3L,"Srbija","Novi Sad","Bulevar oslobodjenja",12,null);
        locations.add(l1);
        locations.add(l2);
        locations.add(l3);
        return locations;
    }
    public static AtomicLong getCounter() {
        return counter;
    }
}
