package com.booking.ProjectISS.service.users.user;

import com.booking.ProjectISS.dto.users.LoginDTO;
import com.booking.ProjectISS.dto.users.RegistrationRequestDTO;
import com.booking.ProjectISS.dto.users.UserDTO;
import com.booking.ProjectISS.enums.Role;
import com.booking.ProjectISS.model.users.User;
import com.booking.ProjectISS.repository.users.guests.IGuestRepository;
import com.booking.ProjectISS.repository.users.owner.IOwnerRepository;
import com.booking.ProjectISS.repository.users.user.IUserRepository;
import com.booking.ProjectISS.service.users.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;

@Service
public class UserService implements IUserService, UserDetailsService {

    @Autowired
    private IUserRepository UserRepository;
    @Autowired
    private IGuestRepository GuestRepository;
    @Autowired
    private IOwnerRepository OwnerRepository;
    @Autowired
    private EmailService emailService;
    int brojac=1;

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
    public User register(RegistrationRequestDTO registrationRequest) {
        User user=null;
        System.out.println(registrationRequest.getTypeUser());
        user=new User(0L,registrationRequest.getemail(),registrationRequest.getPassword(),registrationRequest.getFirstName(),registrationRequest.getLastName(),registrationRequest.getPhoneNumber(),registrationRequest.getAddress(), Role.USER);
//        if(registrationRequest.getTypeUser()==TypeUser.GUEST){
//            user=new Guest(0L,registrationRequest.getemail(),registrationRequest.getPassword(),registrationRequest.getFirstName(),registrationRequest.getLastName(),registrationRequest.getPhoneNumber(),registrationRequest.getAddress());
//        }
//        if(registrationRequest.getTypeUser()==TypeUser.OWNER){
//            user=new Owner(0L,registrationRequest.getemail(),registrationRequest.getPassword(),registrationRequest.getFirstName(),registrationRequest.getLastName(),registrationRequest.getPhoneNumber(),registrationRequest.getAddress(),false,false);
//        }
        user.setActivationCode(UUID.randomUUID().toString());
        user.setActivationExpiry(LocalDateTime.now().plusHours(24));
        user.setActive(false);
        sendActivationEmail(user.getEmail(), user.getActivationCode());
        if(brojac==1){
            UserRepository.save(user);
            brojac++;
        }
        return user;
    }
    public void sendActivationEmail(String email, String activationCode) {
        String activationUrl = "http://localhost:4200/activate?code=" + activationCode;
        String emailContent = "Molimo kliknite na sledeći link da aktivirate vaš nalog: " + activationUrl;
        emailService.sendVerificationEmail(email, "Aktivirajte vaš nalog", emailContent);
    }
    public User activateUser(String activationCode) {
        User user=null;
        List<User> ret = UserRepository.findAll();

        for(User u:ret){
            if(u.getActivationCode()!=null){
                user=u;
            }
        }

        //User user = UserRepository.findByActivationCode(activationCode);

        if (user == null) {
            return null;
        }

        user.setActive(true);
        user.setActivationCode("");
        user.setActivationExpiry(null);
        if(brojac==2){
            System.out.println("USLO OVDE VAZNO!");
            UserRepository.save(user);
            brojac=1;
        }

        return user;
    }

    @Override
    public boolean doesUsernameExist(String username) {
        return UserRepository.doesUsernameExist(username) > 0;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Optional<User> ret = UserRepository.findByEmail(email);
        if (!ret.isEmpty() ) {
            return org.springframework.security.core.userdetails.User.withUsername(email).password(ret.get().getPassword()).roles(ret.get().getRole().toString()).build();
        }
        throw new UsernameNotFoundException("User not found with this username: " + email);
    }
}
