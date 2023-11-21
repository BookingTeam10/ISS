package com.booking.ProjectISS.repository.users.owner;

import com.booking.ProjectISS.model.users.Owner;
import com.booking.ProjectISS.model.users.User;
import com.booking.ProjectISS.repository.users.owner.IOwnerRepository;
import com.booking.ProjectISS.repository.users.user.UserRepository;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Repository
public class OwnerRepository implements IOwnerRepository {

    private final List<Owner> owners=new ArrayList<Owner>();
    private UserRepository userRepository;

    public OwnerRepository(){
        this.userRepository=new UserRepository();
        loadAll(userRepository.getUsers());
    }

    public Collection<Owner> loadAll(List<User> users){
        for(User u:users){
            if(u instanceof Owner){
                owners.add((Owner) u);
            }
        }
        return owners;
    }
    @Override
    public Owner findOne(Long id) {
        for(Owner g:owners){
            if(g.getId()==id){
                return g;
            }
        }
        return null;
    }

    @Override
    public Collection<Owner> findAll() {
        return owners;
    }
    @Override
    public void delete(Long id) {
        owners.removeIf(person -> person.getId() == id);
    }

    @Override
    public Owner create(Owner Owner) {
        Long id = Owner.getId();

        if (id == null) {
            id = userRepository.getCounter().incrementAndGet();  //MOZDA GRESKA
            Owner.setId(id);
        }

        this.owners.add(Owner);
        this.userRepository.addUser(Owner);
        for(User u:this.userRepository.getUsers()){
            System.out.println(u);
        }
        return Owner;
    }

}
