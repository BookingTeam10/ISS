package com.booking.ProjectISS.repository;

import com.booking.ProjectISS.model.Guest;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Repository
public class GuestRepository implements IGuestRepository{

    private final List<Guest> guests=new ArrayList<Guest>();

    public GuestRepository(){
        loadAll();
    }

    public Collection<Guest> loadAll(){
        Guest g1=new Guest(5L,"a","b","c","d","065555555","fwae");
        Guest g2=new Guest(6L,"a","b","c","d","065555555","fwae");
        Guest g3=new Guest(7L,"a","b","c","d","065555555","fwae");
        Guest g4=new Guest(8L,"a","b","c","d","065555555","fwae");
        Guest g5=new Guest(9L,"a","b","c","d","065555555","fwae");
        guests.add(g1);
        guests.add(g2);
        guests.add(g3);
        guests.add(g4);
        guests.add(g5);
        return guests;
    }
    @Override
    public Guest findOne(Long id) {
        for(Guest g:guests){
            if(g.getId()==id){
                return g;
            }
        }
        return null;
    }

    @Override
    public Collection<Guest> findAll() {
        return guests;
    }
    @Override
    public void delete(Long id) {
        guests.removeIf(person -> person.getId() == id);
    }
}
