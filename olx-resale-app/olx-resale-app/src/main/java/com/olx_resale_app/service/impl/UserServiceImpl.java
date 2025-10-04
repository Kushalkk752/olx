package com.olx_resale_app.service.impl;

import lombok.extern.slf4j.Slf4j;
import com.olx_resale_app.entity.AppUser;
import com.olx_resale_app.entity.enums.Role;
import com.olx_resale_app.exception.UserAlreadyExistsException;
import com.olx_resale_app.exception.UserNotExistsException;
import com.olx_resale_app.payload.RegistrationRequest;
import com.olx_resale_app.payload.RegistrationResponse;
import com.olx_resale_app.payload.UserDto;
import com.olx_resale_app.repository.UserRepository;
import com.olx_resale_app.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
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
        if (!registrationRequest.getPassword().equals(registrationRequest.getConfirmPassword())) {
            log.error("Passsword is not matching{}{}", registrationRequest.getPassword(), registrationRequest.getConfirmPassword());
        }
        AppUser user = new AppUser();
        user.setName(registrationRequest.getName());
        user.setEmail(registrationRequest.getEmail());
        user.setMobileNumber(registrationRequest.getMobileNumber());
        String encodedPassword = passwordEncoder.encode(registrationRequest.getPassword());
        user.setPassword(encodedPassword);
        String confirmPassword = passwordEncoder.encode(registrationRequest.getConfirmPassword());
        user.setConfirmPassword(confirmPassword);
        user.setRoles(role);
        user.setCity(registrationRequest.getCity());
        user.setState(registrationRequest.getState());
        user.setCountry(registrationRequest.getCountry());
        user.setPincode(registrationRequest.getPincode());

        AppUser savedUser = userRepository.save(user);

        log.info("Registration details saved {}", savedUser);
        log.info("Roles {}", savedUser.getRoles());
//        sendRegistrationMail(savedUser);
        RegistrationResponse response = new RegistrationResponse();
        response.setId(savedUser.getId());
        response.setName(savedUser.getName());
        response.setEmail(savedUser.getEmail());
        response.setMobileNumber(savedUser.getMobileNumber());
        response.setCity(savedUser.getCity());
        response.setState(savedUser.getState());
        response.setCountry(savedUser.getCountry());
        response.setPincode(savedUser.getPincode());
        return response;
    }

    @Override
    public AppUser getUserById(Long userId) {
        log.info("fetch the user by id {}", userId);
        return userRepository.findById(userId).orElseThrow(() -> new UserNotExistsException("User Not Fonud"));
    }

    @Override
    public UserDto getProfileDetails() {
        log.info("fetching current user details. ");
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        AppUser user = (AppUser) authentication.getPrincipal();
        return modelMapper.map(user, UserDto.class);
    }


}
