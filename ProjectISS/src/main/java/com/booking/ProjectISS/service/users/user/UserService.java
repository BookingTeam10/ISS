package com.booking.ProjectISS.service.users.user;

import com.booking.ProjectISS.dto.accomodations.AccommodationDTO;
import com.booking.ProjectISS.dto.users.LoginDTO;
import com.booking.ProjectISS.dto.users.RegistrationRequestDTO;
import com.booking.ProjectISS.dto.users.UserDTO;
import com.booking.ProjectISS.enums.TypeUser;
import com.booking.ProjectISS.model.accomodations.Accommodation;
import com.booking.ProjectISS.model.users.Guest;
import com.booking.ProjectISS.model.users.Owner;
import com.booking.ProjectISS.model.users.User;
import com.booking.ProjectISS.repository.users.guests.IGuestRepository;
import com.booking.ProjectISS.repository.users.owner.IOwnerRepository;
import com.booking.ProjectISS.repository.users.user.IUserRepository;
import com.booking.ProjectISS.service.users.user.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;

@Service
public class UserService implements IUserService {

    @Autowired
    private IUserRepository UserRepository;
    @Autowired
    private IGuestRepository GuestRepository;
    @Autowired
    private IOwnerRepository OwnerRepository;

    @Override
    public UserDTO findOneDTO(Long id) {
        Optional<User> User=UserRepository.findById(id);
        if(User.isEmpty()){
            return null;
        }
        return new UserDTO(User.get());
    }

    @Override
    public User findOne(Long id) {
        Optional<User> User=UserRepository.findById(id);
        if(User.isEmpty()){
            return null;
        }
        return User.get();
    }

    @Override
    public Collection<UserDTO> findAllDTO() {
        Collection<User> Users = UserRepository.findAll();
        Collection<UserDTO> ret = new ArrayList<UserDTO>();
        for(User g : Users) {
            ret.add(new UserDTO(g));
        }
        return ret;
    }

    @Override
    public Collection<User> findAll() {
        Collection<User> Users = UserRepository.findAll();
        return Users;
    }

    @Override
    public void delete(Long id) {
        User found = findOne(id);
        UserRepository.delete(found);
        UserRepository.flush();
    }

    @Override
    public UserDTO create(User user) throws Exception {
        return new UserDTO(UserRepository.save(new User(user)));
    }

    @Override
    public UserDTO update(User User) throws Exception {
        return null;
    }

    @Override
    public User findUser(LoginDTO login) {
        User user=UserRepository.findByEmail(login.getEmail(),login.getPassword());
        if(user==null){
            return null;
        }
        return user;
    }

    @Override
    public RegistrationRequestDTO register(RegistrationRequestDTO registrationRequest) {
        return null;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> ret = UserRepository.findByEmail(username);
        if (!ret.isEmpty() ) {
            return org.springframework.security.core.userdetails.User.withUsername(username).password(ret.get().getPassword()).roles(ret.get().getRole()).build();
        }
        throw new UsernameNotFoundException("User not found with this username: " + username);
    }

//    @Override
//    public RegistrationRequestDTO register(RegistrationRequestDTO registrationRequest) {
//        User user=UserRepository.findOne(registrationRequest.getemail());
//        if(user==null){
//            if(registrationRequest.getTypeUser()== TypeUser.GUEST){
//                Guest g=new Guest(null,registrationRequest.getemail(),registrationRequest.getPassword(),registrationRequest.getFirstName(),registrationRequest.getLastName(),registrationRequest.getPhoneNumber(),registrationRequest.getAddress(),false,false);
//                GuestRepository.create(g);
//                return registrationRequest;
//            }
//            else{
//                Owner g=new Owner(null,registrationRequest.getemail(),registrationRequest.getPassword(),registrationRequest.getFirstName(),registrationRequest.getLastName(),registrationRequest.getPhoneNumber(),registrationRequest.getAddress(),false,false);
//                OwnerRepository.create(g);
//                return registrationRequest;
//            }
//        }
//        return null;
//    }
}
