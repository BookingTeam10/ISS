package com.booking.ProjectISS.service.users.user;

import com.booking.ProjectISS.dto.users.GuestDTO;
import com.booking.ProjectISS.dto.users.UserDTO;
import com.booking.ProjectISS.model.users.Guest;
import com.booking.ProjectISS.model.users.User;

import java.util.Collection;

public interface IUserService {

    UserDTO findOneDTO(Long id);

    User findOne(Long id);

    Collection<UserDTO> findAllDTO();

    Collection<User> findAll();

    void delete(Long id);

    UserDTO create(User user) throws Exception;

    UserDTO update(User user) throws Exception;
}
