package com.booking.ProjectISS.repository.reservations;

import com.booking.ProjectISS.enums.ReservationStatus;
import com.booking.ProjectISS.enums.TypeAccommodation;
import com.booking.ProjectISS.model.accomodations.Accommodation;
import com.booking.ProjectISS.model.accomodations.Location;
import com.booking.ProjectISS.model.reservations.Reservation;
import com.booking.ProjectISS.model.users.Guest;
import com.booking.ProjectISS.model.users.Owner;
import org.springframework.stereotype.Repository;

import java.util.*;
import java.util.concurrent.atomic.AtomicLong;

@Repository
public class ReservationRepository implements IReservationRepository{
    private List<Reservation> reservations;
    private static AtomicLong counter = new AtomicLong();

    public ReservationRepository(){
        this.reservations = loadAll();
    }
    @Override
    public Reservation findOne(long id) {
        for(Reservation r:reservations ){
            if(Objects.equals(r.getId(), id)){
                return r;
            }
        }
        return null;
    }

    @Override
    public Collection<Reservation> findAll() {
        return reservations;
    }

    @Override
    public void delete(long id) {
        reservations.removeIf(reservation -> reservation.getId() == id);
    }

    @Override
    public Reservation create(Reservation reservation) {
        long id = reservation.getId();

        if (id == 0) {
            id = counter.incrementAndGet();
            reservation.setId(id);
        }

        this.reservations.add(reservation);
        return reservation;
    }

    @Override
    public Collection<Reservation> findAllByGuest(Long id) {
        List<Reservation> reservationsByGuest=new ArrayList<Reservation>();
        for(Reservation r:this.reservations){
            if(r.getGuest().getId().equals(id)){
                reservationsByGuest.add(r);
            }
        }
        return reservationsByGuest;
    }

    private List<Reservation> loadAll(){
        this.reservations = new ArrayList<Reservation>();

        Location loc = new Location(1L,"Srbija", "Novi Sad", "Bulevar", 5, null);
        Accommodation a1 = new Accommodation(1L,false,false,"dadasda",3,3,"dasasd",
                TypeAccommodation.Apartment,2,2,null,null,null,loc,null,null);
        loc = new Location(2L,"", "", "", 0, null);
        Accommodation a2 = new Accommodation(2L,false,false,"dadasda",3,3,"dasasd",
                TypeAccommodation.Apartment,2,2,null,null,null,loc,null,null);
        Accommodation a3 = new Accommodation(3L,false,false,"dadasda",3,3,"dasasd",
                TypeAccommodation.Apartment,2,2,null,null,null,loc,null,null);

        Guest g = new Guest();
        Owner o = new Owner();
        g.setId(5L);
        o.setId(6L);

        Reservation r1 = new Reservation(200, ReservationStatus.ACCEPTED, new Date(), new Date(), 5);
        r1.setAccommodation(a1);
        r1.setId(counter.incrementAndGet());
        r1.setGuest(g);
        r1.getAccommodation().setOwner(o);
        Reservation r2 = new Reservation(100, ReservationStatus.REJECTED, new Date(), new Date(), 3);
        r2.setAccommodation(a2);
        r2.setGuest(g);
        r2.getAccommodation().setOwner(o);
        r2.setId(counter.incrementAndGet());
        Reservation r3 = new Reservation(50, ReservationStatus.WAITING, new Date(), new Date(), 1);
        r3.setAccommodation(a3);
        r3.setGuest(g);
        r3.setId(counter.incrementAndGet());
        Owner o2 = new Owner();
        o2.setId(9L);
        r3.getAccommodation().setOwner(o2);
        reservations.add(r1);
        reservations.add(r2);
        reservations.add(r3);

        return reservations;
    }
}
