package com.booking.ProjectISS.repository.users.guests;

import com.booking.ProjectISS.model.users.Guest;
import com.booking.ProjectISS.model.users.User;
import com.booking.ProjectISS.repository.users.user.IUserRepository;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

//@Repository
//public class GuestRepository implements IGuestRepository {
//
//    private final List<Guest> guests=new ArrayList<Guest>();
//    private IUserRepository userRepository;
//
//    public GuestRepository(){
//        //this.userRepository=new UserRepository();
//        //loadAll(userRepository.getUsers());
//    }
//
//    public Collection<Guest> loadAll(List<User> users){
//        for(User u:users){
//            if(u instanceof Guest){
//                guests.add((Guest) u);
//            }
//        }
//        return guests;
//    }
//    @Override
//    public Guest findOne(Long id) {
//        for(Guest g:guests){
//            if(g.getId()==id){
//                return g;
//            }
//        }
//        return null;
//    }
//
//    @Override
//    public Collection<Guest> findAll() {
//        return guests;
//    }
//    @Override
//    public void delete(Long id) {
//        guests.removeIf(person -> person.getId() == id);
//    }
//
//    @Override
//    public Guest create(Guest guest) {
//        return guest;
////        Long id = guest.getId();
////
////        if (id == null) {
////            id = userRepository.getCounter().incrementAndGet();
////            guest.setId(id);
////        }
////
////        this.guests.add(guest);
////        this.userRepository.addUser(guest);
////        for(User u:this.userRepository.getUsers()){
////            System.out.println(u);
////        }
////        return guest;
//    }
//
//}
