package com.booking.ProjectISS.service.users.user;

import com.booking.ProjectISS.dto.users.GuestDTO;
import com.booking.ProjectISS.dto.users.LoginDTO;
import com.booking.ProjectISS.dto.users.RegistrationRequestDTO;
import com.booking.ProjectISS.dto.users.UserDTO;
import com.booking.ProjectISS.model.users.Guest;
import com.booking.ProjectISS.model.users.ReportUser;
import com.booking.ProjectISS.model.users.User;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.Collection;

public interface IUserService {

    UserDTO findOneDTO(Long id);

    User findOne(Long id);

    Collection<UserDTO> findAllDTO();

    Collection<User> findAll();

    void delete(Long id);

    UserDTO create(User user) throws Exception;

    UserDTO update(User user) throws Exception;

    User findUser(LoginDTO login);

    User register(RegistrationRequestDTO registrationRequest) throws Exception;

    User activateUser(String code);

    void updatePassword(User user);

    boolean doesUsernameExist(String username);

    boolean findActivation(String username);

    ReportUser createReport(ReportUser reportUser, Long idOwner, Long idGuest);

    ReportUser findGuestReportOwner(Long idOwner, Long idGuest);

    ReportUser findGuestReportGuest(Long idOwner, Long idGuest);
}
