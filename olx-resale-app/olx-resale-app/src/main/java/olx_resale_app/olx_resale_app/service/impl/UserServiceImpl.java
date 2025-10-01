package olx_resale_app.olx_resale_app.service.impl;

import lombok.extern.slf4j.Slf4j;
import olx_resale_app.olx_resale_app.entity.User;
import olx_resale_app.olx_resale_app.entity.enums.Role;
import olx_resale_app.olx_resale_app.exception.UserAlreadyExistsException;
import olx_resale_app.olx_resale_app.exception.UserNotExistsException;
import olx_resale_app.olx_resale_app.payload.RegistrationRequest;
import olx_resale_app.olx_resale_app.payload.RegistrationResponse;
import olx_resale_app.olx_resale_app.repository.UserRepository;
import olx_resale_app.olx_resale_app.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Set;

@Slf4j
@Service
public class UserServiceImpl implements UserService {

    private ModelMapper modelMapper;
    private UserRepository userRepository;
    private PasswordEncoder passwordEncoder;

    public UserServiceImpl(ModelMapper modelMapper, UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.modelMapper = modelMapper;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public RegistrationResponse signUp(RegistrationRequest registrationRequest, Set<Role> role) {
        if (userRepository.findByEmail(registrationRequest.getEmail()).isPresent()) {
            log.error("Email already present {}", registrationRequest.getEmail());
            throw new UserAlreadyExistsException("Email already exists");
        }
        if (userRepository.findByMobileNumber(registrationRequest.getMobileNumber()).isPresent()) {
            log.error("MObile Number already present {}", registrationRequest.getMobileNumber());
            throw new UserAlreadyExistsException("mobile number already exists");
        }
        if(!registrationRequest.getPassword().equals(registrationRequest.getConfirmPassword())){
            log.error("Passsword is not matching{}{}",registrationRequest.getPassword(), registrationRequest.getConfirmPassword());
        }
        User user = new User();
        user.setName(registrationRequest.getName());
        user.setEmail(registrationRequest.getEmail());
        user.setMobileNumber(registrationRequest.getMobileNumber());
        user.setPassword(passwordEncoder.encode(registrationRequest.getPassword()));
        user.setConfirmPassword(passwordEncoder.encode(registrationRequest.getConfirmPassword()));
        user.setRoles(role);
        user.setCity(registrationRequest.getCity());
        user.setState(registrationRequest.getState());
        user.setCountry(registrationRequest.getCountry());
        user.setPincode(registrationRequest.getPincode());
        User savedUser = userRepository.save(user);
        log.info("Registration details saved {}", savedUser);
        log.info("Roles {}", savedUser.getRoles());
//        sendRegistrationMail(savedUser);
        RegistrationResponse response = mapToRegistrationResponse(savedUser);
        return response;
    }

    @Override
    public User getUserById(Long userId) {
        log.info("fetch the user by id {}", userId);
        return userRepository.findById(userId).orElseThrow(()->new UserNotExistsException("User Not Fonud"));
    }

    private RegistrationResponse mapToRegistrationResponse(User savedUser) {
        RegistrationResponse registrationResponse = modelMapper.map(savedUser, RegistrationResponse.class);
        return registrationResponse;
    }
}
