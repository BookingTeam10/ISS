package com.booking.ProjectISS.service.users.user;

import com.booking.ProjectISS.dto.reviews.ReviewOwnerDTO;
import com.booking.ProjectISS.dto.users.LoginDTO;
import com.booking.ProjectISS.dto.users.RegistrationRequestDTO;
import com.booking.ProjectISS.dto.users.UserDTO;
import com.booking.ProjectISS.enums.ReviewStatus;
import com.booking.ProjectISS.enums.TypeUser;
import com.booking.ProjectISS.model.reviews.ReviewOwner;
import com.booking.ProjectISS.model.users.Guest;
import com.booking.ProjectISS.model.users.Owner;
import com.booking.ProjectISS.model.users.ReportUser;
import com.booking.ProjectISS.model.users.User;
import com.booking.ProjectISS.repository.users.guests.IGuestRepository;
import com.booking.ProjectISS.repository.users.owner.IOwnerRepository;
import com.booking.ProjectISS.repository.users.user.IReportUserRepository;
import com.booking.ProjectISS.repository.users.user.IUserRepository;
import com.booking.ProjectISS.service.users.EmailService;
import com.booking.ProjectISS.service.users.guest.IGuestService;
import com.booking.ProjectISS.service.users.owner.IOwnerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;

@Service
public class UserService implements IUserService, UserDetailsService {

    @Autowired
    private IUserRepository UserRepository;
    @Autowired
    private IGuestService GuestService;
    @Autowired
    private IOwnerService OwnerService;
    @Autowired
    private EmailService emailService;
    @Autowired
    private IOwnerRepository OwnerRepository;
    @Autowired
    private IGuestRepository GuestRepository;
    @Autowired
    private IReportUserRepository ReportUserRepository;
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
    public UserDTO create(User user){
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
    public User register(RegistrationRequestDTO registrationRequest) throws Exception {
        User user=null;
        if(registrationRequest.getUserType()== TypeUser.GUEST){
            user=new Guest(500L,registrationRequest.getemail(),registrationRequest.getPassword(),registrationRequest.getFirstName(),registrationRequest.getLastName(),registrationRequest.getPhoneNumber(),registrationRequest.getAddress());
        }
        if(registrationRequest.getUserType()==TypeUser.OWNER){
            user=new Owner(500L,registrationRequest.getemail(),registrationRequest.getPassword(),registrationRequest.getFirstName(),registrationRequest.getLastName(),registrationRequest.getPhoneNumber(),registrationRequest.getAddress(),false,false);
        }
        user.setActivationCode(UUID.randomUUID().toString());
        user.setActivationExpiry(LocalDateTime.now().plusHours(24));
        user.setActive(false);
        sendActivationEmail(user.getEmail(), user.getActivationCode());
        if(registrationRequest.getUserType()== TypeUser.GUEST){
            PasswordEncoder encoder = new BCryptPasswordEncoder();
            user.setPassword(encoder.encode(registrationRequest.getPassword()));
            GuestService.create((Guest) user);
        }
        if(registrationRequest.getUserType()==TypeUser.OWNER){
            PasswordEncoder encoder = new BCryptPasswordEncoder();
            user.setPassword(encoder.encode(registrationRequest.getPassword()));
            OwnerService.create((Owner) user);
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

        if (user == null) {
            return null;
        }
        PasswordEncoder encoder = new BCryptPasswordEncoder();
        user.setPassword(encoder.encode(user.getPassword()));
        System.out.println(user.getPassword());
        user.setActive(true);
        user.setActivationCode("");
        user.setActivationExpiry(null);
        return user;
    }
    @Override
    public void updatePassword(User user) {
        System.out.println(" UPDATE ---- -   "  + user.getPassword());
        this.UserRepository.save(user);

        User optionalUser = this.UserRepository.findOne(user.getId());
        System.out.println(" POSLE UPDATE -- - "  + optionalUser.getPassword());
    }
    @Override
    public boolean doesUsernameExist(String username) {
        return UserRepository.doesUsernameExist(username) > 0;
    }

    @Override
    public boolean findActivation(String username) {
        Guest user=GuestService.findUsername(username);
        if(user.isActive()){
            return true;
        }
        return false;
    }

    @Override
    public ReportUser createReport(ReportUser reportUser, Long idOwner, Long idGuest) {
        Optional<Owner> o=OwnerRepository.findById(idOwner);
        Optional<Guest> g=GuestRepository.findById(idGuest);
        reportUser.setOwner(o.get());
        reportUser.setGuest(g.get());
        reportUser.setStatus(ReviewStatus.ACTIVE);
        System.out.println(reportUser);
        ReportUser savedReportUser = ReportUserRepository.save(reportUser);
        return savedReportUser;
    }

    @Override
    public ReportUser findGuestReportOwner(Long idOwner, Long idGuest) {
        System.out.println("USLO U SERVICE");
        Collection<ReportUser> reportUser=ReportUserRepository.findByOwnerGuestStatus1(idOwner,idGuest);
        System.out.println(reportUser.size());
        System.out.println(reportUser);
        for(ReportUser ru:reportUser){
            if(ru.getUserReportUser().equals("GO")){
                return ru;
            }
        }
        return null;
    }

    @Override
    public ReportUser findGuestReportGuest(Long idOwner, Long idGuest) {
        System.out.println("USLO U SERVICE");
        Collection<ReportUser> reportUser=ReportUserRepository.findByOwnerGuestStatus1(idOwner,idGuest);
        System.out.println(reportUser.size());
        System.out.println(reportUser);
        for(ReportUser ru:reportUser){
            if(ru.getUserReportUser().equals("OG")){
                return ru;
            }
        }
        return null;
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
