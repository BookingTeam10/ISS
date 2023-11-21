package com.booking.ProjectISS.service.users.user;

import com.booking.ProjectISS.dto.users.UserDTO;
import com.booking.ProjectISS.model.users.User;
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
}
