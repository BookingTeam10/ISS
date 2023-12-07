package com.booking.ProjectISS.repository.users.user;

import com.booking.ProjectISS.model.users.Guest;
import com.booking.ProjectISS.model.users.Owner;
import com.booking.ProjectISS.model.users.User;
import com.booking.ProjectISS.repository.users.user.IUserRepository;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

//@Repository
//public class UserRepository implements IUserRepository {
//
//    private List<User> users;
//    private static AtomicLong counter = new AtomicLong();
//
//    public UserRepository(){
//
//        loadAll();
//    }
//
//    public Collection<User> loadAll(){
//        this.users=new ArrayList<User>();
//        Guest g1=new Guest(5L,"admin","admin","c","d","065555555","fwae",false,false);
//        Owner g2=new Owner(1L,"a","b","c","d","065555555","fwae",true,false);
//        Guest g3=new Guest(7L,"a","b","c","d","065555555","fwae",false,false);
//        Guest g4=new Guest(8L,"a","b","c","d","065555555","fwae",false,false);
//        Owner g5=new Owner(9L,"a","b","c","d","065555555","fwae",true,true);
//        users.add(g1);
//        users.add(g2);
//        users.add(g3);
//        users.add(g4);
//        users.add(g5);
//        return users;
//    }
//    @Override
//    public User findOne(Long id) {
//        for(User g:users){
//            if(g.getId()==id){
//                return g;
//            }
//        }
//        return null;
//    }
//
//    @Override
//    public Collection<User> findAll() {
//        return users;
//    }
//    @Override
//    public void delete(Long id) {
//        users.removeIf(person -> person.getId() == id);
//    }
//    @Override
//    public User create(User user) {
//        Long id = user.getId();
//
//        if (id == null) {
//            id = counter.incrementAndGet();
//            user.setId(id);
//        }
//
//        this.users.add(user);
//        return user;
//    }
//
//    @Override
//    public User findOne(String email, String password) {
//        for(User u:this.users){
//            if(u.getEmail().equals(email) && u.getPassword().equals(password)){
//                return u;
//            }
//        }
//        return null;
//    }
//
//    @Override
//    public User findOne(String email) {
//        for(User u:this.users){
//            if(u.getEmail().equals(email)){
//                return u;
//            }
//        }
//        return null;
//    }
//
//    public List<User> getUsers() {
//        return users;
//    }
//    public void addUser(User u){
//        this.users.add(u);
//    }
//
//    public static AtomicLong getCounter() {
//        return counter;
//    }
//}
