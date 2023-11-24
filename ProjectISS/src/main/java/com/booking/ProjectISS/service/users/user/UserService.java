package com.booking.ProjectISS.service.users.user;

import com.booking.ProjectISS.dto.users.LoginDTO;
import com.booking.ProjectISS.dto.users.RegistrationRequestDTO;
import com.booking.ProjectISS.dto.users.UserDTO;
import com.booking.ProjectISS.enums.TypeUser;
import com.booking.ProjectISS.model.users.Guest;
import com.booking.ProjectISS.model.users.Owner;
import com.booking.ProjectISS.model.users.User;
import com.booking.ProjectISS.repository.users.guests.IGuestRepository;
import com.booking.ProjectISS.repository.users.owner.IOwnerRepository;
import com.booking.ProjectISS.repository.users.user.IUserRepository;
import com.booking.ProjectISS.service.users.user.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
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
        User User=UserRepository.findOne(id);
        if(User==null){
            return null;
        }
        return new UserDTO(User);
    }

    @Override
    public User findOne(Long id) {
        User User=UserRepository.findOne(id);
        if(User==null){
            return null;
        }
        return User;
    }

    @Override
    public Collection<UserDTO> findAllDTO() {
        Collection<User> Users = UserRepository.findAll();
        Collection<UserDTO> ret = new ArrayList<UserDTO>();       //primitive way, delete other this line after
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
        UserRepository.delete(id);
    }

    @Override
    public UserDTO create(User User) throws Exception {
        if (User.getId() != null) {
            throw new Exception("Id mora biti null prilikom perzistencije novog entiteta.");
        }
        User savedUser = UserRepository.create(User);
        return new UserDTO(savedUser);
    }

    @Override
    public UserDTO update(User User) throws Exception {
        return null;
    }

    @Override
    public UserDTO findUser(LoginDTO login) {
        User user=UserRepository.findOne(login.getEmail(),login.getPassword());
        if(user==null){
            return null;
        }
        return new UserDTO(user.getId(),user.getEmail());
    }

    @Override
    public RegistrationRequestDTO register(RegistrationRequestDTO registrationRequest) {
        User user=UserRepository.findOne(registrationRequest.getemail());
        if(user==null){
            if(registrationRequest.getTypeUser()== TypeUser.GUEST){
                Guest g=new Guest(null,registrationRequest.getemail(),registrationRequest.getPassword(),registrationRequest.getFirstName(),registrationRequest.getLastName(),registrationRequest.getPhoneNumber(),registrationRequest.getAddress(),false,false);
                GuestRepository.create(g);
                return registrationRequest;
            }
            else{
                Owner g=new Owner(null,registrationRequest.getemail(),registrationRequest.getPassword(),registrationRequest.getFirstName(),registrationRequest.getLastName(),registrationRequest.getPhoneNumber(),registrationRequest.getAddress(),false,false);
                OwnerRepository.create(g);
                return registrationRequest;
            }
        }
        return null;
    }
}
